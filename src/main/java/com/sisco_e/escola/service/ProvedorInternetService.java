package com.sisco_e.escola.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.sisco_e.escola.api.dto.request.ProvedorInternetRequestDTO;
import com.sisco_e.escola.api.dto.response.ProvedorInternetResponseDTO;

public interface ProvedorInternetService {

	ProvedorInternetResponseDTO cadastrarProvedorInternet(ProvedorInternetRequestDTO provedorInternetRequestDto);

	void validarProvedorInternet(ProvedorInternetRequestDTO provedorInternetRequestDto);

	List<ProvedorInternetRequestDTO> buscarTodosProvedoresInternetCadastrados();

	Optional<ProvedorInternetRequestDTO> buscarProvedorInternetPorId(UUID uuid);

	Optional<ProvedorInternetRequestDTO> buscarProvedorInternetPorNome(String nomeProvedor);

	Optional<ProvedorInternetRequestDTO> buscarProvedorInternetPorCnpj(String cnpj);

	List<ProvedorInternetRequestDTO> buscarProvedoresInternetPorParteDoNome(String parteNomeProvedor);

	ProvedorInternetRequestDTO atualizarProvedorInternet(ProvedorInternetRequestDTO provedorInternetRequestDto);

	void deletarProvedorInternet(UUID uuid);

	Optional<ProvedorInternetRequestDTO> obterProvedorInternetPorId(UUID uuid);
}
