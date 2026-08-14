package com.sisco_e.escola.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

}
