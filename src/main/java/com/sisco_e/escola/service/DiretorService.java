package com.sisco_e.escola.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sisco_e.escola.api.dto.DiretorDTO;

@Service
public interface DiretorService {

	DiretorDTO cadastrarDiretor(DiretorDTO diretorDto);

	void validarDiretor(DiretorDTO diretorDto);

	List<DiretorDTO> buscarTodosDiretores();

	Optional<DiretorDTO> buscarDiretorPorId(UUID uuid);

	Optional<DiretorDTO> buscarDiretorPorCpf(String cpfDiretor);

	Optional<DiretorDTO> buscarDiretorPorEmailCorporativo(String emailCorporativo);

	Optional<DiretorDTO> buscarDiretorPorEmailPessoal(String emailPessoal);

	Optional<DiretorDTO> buscarDiretorPorEscola(UUID escolaUuid);
}
