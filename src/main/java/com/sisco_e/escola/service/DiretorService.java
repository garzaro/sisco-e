package com.sisco_e.escola.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.sisco_e.escola.api.dto.request.DiretorRequestDTO;
import com.sisco_e.escola.api.dto.response.DiretorResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface DiretorService {

	DiretorResponseDTO cadastrarDiretor(DiretorRequestDTO diretorRequestDto);

	void validarDiretor(DiretorRequestDTO diretorRequestDto);

	List<DiretorRequestDTO> buscarTodosDiretores();

	Optional<DiretorRequestDTO> buscarDiretorPorId(UUID uuid);

	Optional<DiretorRequestDTO> buscarDiretorPorCpf(String cpfDiretor);

	Optional<DiretorRequestDTO> buscarDiretorPorEmailCorporativo(String emailCorporativo);

	Optional<DiretorRequestDTO> buscarDiretorPorEmailPessoal(String emailPessoal);

	Optional<DiretorRequestDTO> buscarDiretorPorEscola(UUID escolaUuid);
}
