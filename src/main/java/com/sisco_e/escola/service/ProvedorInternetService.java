package com.sisco_e.escola.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.sisco_e.escola.api.dto.ProvedorInternetDTO;

public interface ProvedorInternetService {

	ProvedorInternetDTO cadastrarProvedorInternet(ProvedorInternetDTO provedorInternetDto);

	void validarProvedorInternet(ProvedorInternetDTO provedorInternetDto);

	List<ProvedorInternetDTO> buscarTodosProvedoresInternetCadastrados();

	Optional<ProvedorInternetDTO> buscarProvedorInternetPorId(UUID uuid);

	Optional<ProvedorInternetDTO> buscarProvedorInternetPorNome(String nomeProvedor);

	Optional<ProvedorInternetDTO> buscarProvedorInternetPorCnpj(String cnpj);

	List<ProvedorInternetDTO> buscarProvedoresInternetPorParteDoNome(String parteNomeProvedor);

	ProvedorInternetDTO atualizarProvedorInternet(ProvedorInternetDTO provedorInternetDto);

	void deletarProvedorInternet(UUID uuid);

	Optional<ProvedorInternetDTO> obterProvedorInternetPorId(UUID uuid);
}
