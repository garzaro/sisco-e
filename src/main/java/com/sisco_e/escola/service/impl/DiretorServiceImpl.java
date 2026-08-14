package com.sisco_e.escola.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sisco_e.escola.api.dto.DiretorDTO;
import com.sisco_e.escola.exception.RegraNegocioException;
import com.sisco_e.escola.mapper.DiretorMapper;
import com.sisco_e.escola.model.entity.Diretor;
import com.sisco_e.escola.model.entity.Escola;
import com.sisco_e.escola.model.repository.DiretorRepository;
import com.sisco_e.escola.model.repository.EscolaRepository;
import com.sisco_e.escola.service.DiretorService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiretorServiceImpl implements DiretorService {

	private final DiretorRepository diretorRepository;
	private final EscolaRepository escolaRepository;
	private final DiretorMapper diretorMapper;

	@Override
	@Transactional
	public DiretorDTO cadastrarDiretor(DiretorDTO diretorDto) {
		validarDiretor(diretorDto);
        /**Isso evita tentar vincular um diretor a uma escola fantasma**/
		Escola escola = escolaRepository.findById(diretorDto.getEscolaUuid())
			.orElseThrow(() -> new RegraNegocioException("Escola não encontrada para vínculo com diretor"));

		Diretor diretor = diretorMapper.DtoToEntity(diretorDto);
		diretor.setEscola(escola);
		diretor.setIsAtivo(Boolean.TRUE.equals(diretorDto.getIsAtivo()) || diretorDto.getIsAtivo() == null);
        /**persistencia e retorno
         * recebe a entidade de volta, já contendo o ID gerado
         * * */
		Diretor diretorSalvo = diretorRepository.save(diretor);  
        /**
         * Converte a entidade recém-salva (agora completa e persistida) de volta para um DiretorDTO,
         * que é o formato limpo que será devolvido na resposta da API para quem fez a requisição
         * **/      
		return diretorMapper.entityToDto(diretorSalvo);
	}

	@Override
	public void validarDiretor(DiretorDTO diretorDto) {
		if (diretorDto == null) {
			throw new RegraNegocioException("Dados do diretor não informados!");
		}

		if (diretorRepository.existsByCpfDiretor(diretorDto.getCpf())) {
			throw new RegraNegocioException("Verifique o CPF do diretor e tente novamente!");
		}

		if (diretorRepository.existsByEmailCorporativo(diretorDto.getEmail())) {
			throw new RegraNegocioException("Verifique o e-mail do diretor e tente novamente!");
		}

		if (diretorDto.getEmailPessoal() != null && !diretorDto.getEmailPessoal().isBlank()
				&& diretorRepository.existsByEmailPessoal(diretorDto.getEmailPessoal())) {
			throw new RegraNegocioException("Verifique o e-mail pessoal do diretor e tente novamente!");
		}

		if (diretorRepository.existsByEscolaUuid(diretorDto.getEscolaUuid())) {
			throw new RegraNegocioException("Já existe diretor vinculado a esta escola!");
		}
	}

	@Override
	public List<DiretorDTO> buscarTodosDiretores() {
		return diretorRepository
        .findAll()
        .stream()
        .map(diretorMapper::entityToDto)
        .toList();
	}

	@Override
	public Optional<DiretorDTO> buscarDiretorPorId(UUID uuid) {
		return diretorRepository.findById(uuid).map(diretorMapper::entityToDto);
	}

	@Override
	public Optional<DiretorDTO> buscarDiretorPorCpf(String cpf) {
		return diretorRepository.findByCpfDiretor(cpf).map(diretorMapper::entityToDto);
	}

	@Override
	public Optional<DiretorDTO> buscarDiretorPorEmailCorporativo(String email) {
		return diretorRepository.findByEmailCorporativo(email).map(diretorMapper::entityToDto);
	}

	@Override
	public Optional<DiretorDTO> buscarDiretorPorEmailPessoal(String emailPessoal) {
		return diretorRepository.findByEmailPessoal(emailPessoal).map(diretorMapper::entityToDto);
	}

	@Override
	public Optional<DiretorDTO> buscarDiretorPorEscola(UUID uuidEscola) {
		return diretorRepository.findByEscolaUuid(uuidEscola).map(diretorMapper::entityToDto);
	}
}
