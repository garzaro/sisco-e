package com.sisco.escola.service.impl;

import com.sisco.escola.exception.ErroAutenticacaoException;
import com.sisco.escola.exception.ErroValidacaoException;
import com.sisco.escola.model.entity.Usuario;
import com.sisco.escola.model.repository.UsuarioRepository;
import com.sisco.escola.service.UsuarioService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository usuarioRepository;
    private Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder(16, 32, 1, 16, 3); 

    public UsuarioServiceImpl( UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }
    
    /* lista de emails permitidos */
    private static final List<String> emailsPermitidos = List.of(
            "gmail.com", "gov.br");
    
    @Override
    public Usuario autenticar(String email, String senha) {
    	Usuario usuario = usuarioRepository.findByEmail(email)
    			.orElseThrow(() -> new ErroAutenticacaoException("Credenciais inválidas"));
		return usuario;        
    }
    
    @Override
    @Transactional
    public Usuario salvar(Usuario usuario) {      	
    	usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));    	
    	validacaoEmailCpf(usuario.getEmail(), usuario.getCpf());
    	
    	String emailPermitido = usuario.getEmail().substring(usuario.getEmail().lastIndexOf("@") + 1);
    	if (!emailsPermitidos.contains(emailPermitido)) { 
            throw new ErroValidacaoException("Emails permitidos para cadastro : " + emailsPermitidos);
        }
    	
        return usuarioRepository.save(usuario);
    }
    
    /**AQUI EU QUIS INVENTAR**/
    public void validacaoEmailCpf(String email, String cpf) {
    	validarEmail(email);
    	validarCpf(cpf);
    }

    @Override
    public void validarEmail(String email) {
    	boolean existeEmail = usuarioRepository.existsByEmail(email);
    	if (existeEmail) {
    		throw new ErroValidacaoException("Este email já esta sendo usado por outro usuário.");			
		}
    	    	
    }
    @Override
	public void validarCpf(String cpf) {
    	boolean existeCpf = usuarioRepository.existsByCpf(cpf);
    	if (existeCpf) {
    		throw new ErroValidacaoException("Este CPF já esta sendo usado por outro usuário");			
		}
	}
    

//    @Override
//    public Usuario atualizar(Usuario usuario) {
//        Objects.requireNonNull(usuario.getId());//        
//        return usuarioRepository.save(usuario);
//    }

    @Override
    public Optional<Usuario> obterUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }
    
    public Optional<Usuario> pegarUsuarioPorCpf(String cpf) {
		return usuarioRepository.findByCpf(cpf);
	}
}


/**
 * USAR PARA TIRAR ESSE MONTE DE MESNGEM DA CLASSE, FICAR MAIS PRPOFSSIONAL IGUAL AO QUE CLAIRTON INDICOU
 *  @Override
 *     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
 *         // Busca o usuário no banco de dados pelo campo "usuario"
 *         Usuario usuario = usuarioRepository.findByUsuario(username)
 *                 .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o nome: " + username));
 *
 * **/
