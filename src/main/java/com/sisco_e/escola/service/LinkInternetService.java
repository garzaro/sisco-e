package com.sisco_e.escola.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sisco_e.escola.model.entity.LinkInternet;

@Service
public interface LinkInternetService {

	LinkInternet cadastrarLinkInternet(LinkInternet linkInternet);

	List<LinkInternet> buscarTodosLinksInternet();

	Optional<LinkInternet> buscarLinkInternetPorId(UUID uuid);

	List<LinkInternet> buscarLinksPorContrato(UUID contratoUuid);

	List<LinkInternet> buscarLinksPorContratoEAtivo(UUID contratoUuid, Boolean isAtivo);

	List<LinkInternet> buscarLinksPorIpWanOuDns(String termo);

	LinkInternet atualizarLinkInternet(LinkInternet linkInternet);

	void deletarLinkInternet(UUID uuid);

	Optional<LinkInternet> obterLinkInternetPorId(UUID uuid);

}
