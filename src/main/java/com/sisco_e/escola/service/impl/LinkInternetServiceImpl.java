package com.sisco_e.escola.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.sisco_e.escola.model.entity.ContratoInternet;
import com.sisco_e.escola.model.entity.Escola;
import com.sisco_e.escola.model.entity.ProvedorInternet;
import org.springframework.stereotype.Service;

import com.sisco_e.escola.exception.RegraNegocioException;
import com.sisco_e.escola.model.entity.LinkInternet;
import com.sisco_e.escola.model.repository.LinkInternetRepository;
import com.sisco_e.escola.service.LinkInternetService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LinkInternetServiceImpl implements LinkInternetService {

	private final LinkInternetRepository linkInternetRepository;

	@Override
	@Transactional
	public LinkInternet cadastrarLinkInternet(LinkInternet linkInternet) {		
		return linkInternetRepository.save(linkInternet);
	}

	@Override
	public List<LinkInternet> buscarTodosLinksInternet() {
		return linkInternetRepository.findAll();
	}

	@Override
	public Optional<LinkInternet> buscarLinkInternetPorId(UUID uuid) {
		return linkInternetRepository.findById(uuid);
	}

	@Override
	public List<LinkInternet> buscarLinksPorContrato(UUID contratoUuid) {
		return linkInternetRepository.findByContratoInternet_Uuid(contratoUuid);
	}

	@Override
	public List<LinkInternet> buscarLinksPorContratoEAtivo(UUID contratoUuid, Boolean isAtivo) {
		return linkInternetRepository.findByContratoInternet_UuidAndIsAtivo(contratoUuid, isAtivo);
	}

	@Override
	public List<LinkInternet> buscarLinksPorIpWanOuDns(String termo) {
		if (termo == null || termo.isBlank()) {
			throw new RegraNegocioException("IP ou DNS deve ser informado para a busca");
		}

		return linkInternetRepository.findByIpPublicoOrDns(termo.trim());
	}

	@Override
	@Transactional
	public LinkInternet atualizarLinkInternet(LinkInternet linkInternet) {
		if (linkInternet == null || linkInternet.getUuid() == null) {
			throw new RegraNegocioException("Id do link de internet é obrigatório para atualização");
		}

		if (!linkInternetRepository.existsById(linkInternet.getUuid())) {
			throw new RegraNegocioException("Link de internet não encontrado para atualização");
		}

		return linkInternetRepository.save(linkInternet);
	}

	@Override
	@Transactional
	public void deletarLinkInternet(UUID uuid) {
		if (!linkInternetRepository.existsById(uuid)) {
			throw new RegraNegocioException("Link de internet não encontrado para exclusão");
		}
		linkInternetRepository.deleteById(uuid);
	}

	@Override
	public Optional<LinkInternet> obterLinkInternetPorId(UUID uuid) {
		return buscarLinkInternetPorId(uuid);
	}
	 /**cadeia de guard clauses**/

	private void validarConsistenciaEscolaEProvedor(LinkInternet linkInternet) {
		var contrato = linkInternet.getContratoInternet();
		var escola   = linkInternet.getEscola();
		var provedor = linkInternet.getProvedorInternet();

		validarRelacaoObrigatoria(contrato, "Contrato de internet é obrigatório para o link de internet");
		validarRelacaoObrigatoria(escola, "Escola é obrigatória para o link de internet");
		validarRelacaoObrigatoria(provedor, "Provedor de internet é obrigatório para o link de internet");

		validarEscolaCorresponde(escola, contrato);
		validarProvedorCorresponde(provedor, contrato);
	}

	private void validarRelacaoObrigatoria(Object valor, String mensagem) {
		if (valor == null) {
			throw new RegraNegocioException(mensagem);
		}
	}

	private void validarEscolaCorresponde(Escola escola, ContratoInternet contrato) {
		var escolaContrato = contrato.getEscola();
		if (escolaContrato == null || !Objects.equals(escola.getUuid(), escolaContrato.getUuid())) {
			throw new RegraNegocioException(
					"A escola informada não corresponde à escola do contrato de internet selecionado");
		}
	}

	private void validarProvedorCorresponde(ProvedorInternet provedor, ContratoInternet contrato) {
		var provedorContrato = contrato.getProvedor();
		if (provedorContrato == null || !Objects.equals(provedor.getUuid(), provedorContrato.getUuid())) {
			throw new RegraNegocioException(
					"O provedor informado não corresponde ao provedor do contrato de internet selecionado"
			);
		}
	}
}
