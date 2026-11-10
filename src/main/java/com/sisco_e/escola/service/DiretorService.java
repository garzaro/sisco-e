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

	void validarDiretor(DiretorResponseDTO diretorResponseDTO);

	List<DiretorResponseDTO> buscarTodosDiretores();

	Optional<DiretorResponseDTO> buscarDiretorPorId(UUID uuid);

	Optional<DiretorResponseDTO> buscarDiretorPorCpf(String cpfDiretor);

	Optional<DiretorResponseDTO> buscarDiretorPorEmailCorporativo(String emailCorporativo);

	Optional<DiretorResponseDTO> buscarDiretorPorEmailPessoal(String emailPessoal);

	Optional<DiretorResponseDTO> buscarDiretorPorEscola(UUID escolaUuid);
}
