package com.sisco.escola.service.impl;

import com.sisco.escola.exception.ErroValidacaoException;
import com.sisco.escola.exception.RegraDeNegocioException;
import com.sisco.escola.model.entity.Escola;
import com.sisco.escola.model.repository.EscolaRepository;
import com.sisco.escola.service.EscolaService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EscolaServiceImpl implements EscolaService {

	private EscolaRepository escolaRepository;

	public EscolaServiceImpl(EscolaRepository escolaRepository) {
		this.escolaRepository = escolaRepository;
	}

	@Override
	public long quantidadeEscola() {
		return escolaRepository.count();
	}

	@Override
	@Transactional
	public Escola salvarEscola(Escola escola) {
		validarEscola(escola);
		return escolaRepository.save(escola);
	}

	@Override
	@Transactional
	public Escola atualizarEscola(Escola escola) {
		Objects.requireNonNull(escola.getId());
		validarEscola(escola);
		return escolaRepository.save(escola);
	}

	@Override
	public void deletarEscola(Escola escola) {
		/* so deleta se existir uma escola salva */
		Objects.requireNonNull(escola.getId());
		escolaRepository.delete(escola);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Escola> buscarEscolaPorNome(Escola nomeEscola) {

		Optional<Escola> resultadoDaBusca = escolaRepository.findByNome(nomeEscola);
		if (!resultadoDaBusca.isPresent()) {
			throw new RegraDeNegocioException("O nome de escola fornecido não foi encontrado");
		}
		return resultadoDaBusca;
	}

	@Override
	public Optional<Escola> buscarEscolaPorCodigo(String codigo) {

		/* buscar diretamente no repositorio, ignorando cases */
		Optional<Escola> codigoEscola = escolaRepository.findByCodigo(codigo);
		if (!codigoEscola.isPresent()) {
			throw new RegraDeNegocioException("Não foi encontrado escola com o código fornecido");
		}
		return codigoEscola;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Escola> listarEscolas(Escola escolaFiltro) {

		Example example = Example.of(escolaFiltro,
				ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
		return escolaRepository.findAll(example);
	}

	@Override
	public Optional<Escola> obterEscolaPorId(Long id) {
		return escolaRepository.findById(id);
	}

	@Override
	public void validarEscola(Escola escola) {
		/* preencher campos */
		if (escola.getNome() == null || escola.getNome().trim().equals("")) {
			throw new RegraDeNegocioException("Informar um nome de escola.");
		}
		
		if (escola.getCodigo() == null || escola.getCodigo().trim().equals("")) {
			throw new RegraDeNegocioException("Informar o codigo da escola.");
		}
		
		if (escola.getMunicipio() == null || escola.getMunicipio().trim().equals("")) {
			throw new RegraDeNegocioException("Informar o nome da cidade.");
		}
		
		if (escola.getBairro() == null || escola.getBairro().trim().equals("")) {
			throw new RegraDeNegocioException("Informar o nome do bairro.");
		}
		
		if (escola.getEndereco() == null || escola.getEndereco().trim().equals("")) {
			throw new RegraDeNegocioException("Informar o endereço da escola.");
		}
		
		if (escola.getTelefone() == null || escola.getTelefone().trim().equals("")) {
			throw new RegraDeNegocioException("Informar o telefone da escola.");			
		}
		/* deve saber quando é para atualizar ou criar/salvar uma escola */
		if (escola.getId() == null) {
			validarNomeEscolaDuplicado(escola);
		} else {
			validarParaNaoDuplicarAoAtualizar(escola);
		}
	}
	private void validarNomeEscolaDuplicado(Escola escola) {
		/*validação para criar o registro*/
		if (escolaRepository.existsByNome(escola.getNome())) {
			throw new RegraDeNegocioException("Duplicidade de nome não permitida.");
		}
		if (escolaRepository.existsByCodigo(escola.getCodigo())) {
			throw new RegraDeNegocioException("Código já cadastrado.");
		}
	}
	private void validarParaNaoDuplicarAoAtualizar(Escola escola) {
		/* valida o nome, ignorando o id atual */
		if (escolaRepository.existsByNomeAndIdNot(escola.getNome(), escola.getId())) {
			throw new RegraDeNegocioException("Já existe uma escola cadastrada com este nome.");
		}
		if (escolaRepository.existsByCodigoAndIdNot(escola.getCodigo(), escola.getId())) {
			throw new RegraDeNegocioException("Já existe uma escola cadastrada com este código.");
		}
	}
}
