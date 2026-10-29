package com.sisco_e.escola.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.sisco_e.escola.api.dto.request.DiretorRequestDTO;
import com.sisco_e.escola.api.dto.response.DiretorResponseDTO;
import org.springframework.stereotype.Service;

import com.sisco_e.escola.exception.RegraNegocioException;
import com.sisco_e.escola.mapper.DiretorMapper;
import com.sisco_e.escola.model.entity.Diretor;
import com.sisco_e.escola.model.entity.Escola;
import com.sisco_e.escola.model.repository.DiretorRepository;
import com.sisco_e.escola.model.repository.EscolaRepository;
import com.sisco_e.escola.service.DiretorService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * TODO-list
 * [] Tirar o IO de dentro das condicionais no validação de diretor
 * [] Validar matricula
 * **/

@Service
@RequiredArgsConstructor
public class DiretorServiceImpl implements DiretorService {

	private final DiretorRepository diretorRepository;
	private final EscolaRepository escolaRepository;
	private final DiretorMapper diretorMapper;

	@Override
	@Transactional
	public DiretorResponseDTO cadastrarDiretor(DiretorRequestDTO diretorRequestDto) {
		validarDiretor(diretorRequestDto);
        /**Isso evita tentar vincular um diretor a uma escola fantasma**/
		Escola escola = escolaRepository.findById(diretorRequestDto.getEscolaUuid())
			.orElseThrow(() -> new RegraNegocioException("Escola não encontrada para vínculo com diretor"));

		Diretor diretor = diretorMapper.DtoToEntity(diretorRequestDto);
		diretor.setEscola(escola);
		diretor.setIsAtivo(Boolean.TRUE.equals(diretorRequestDto.getIsAtivo()) || diretorRequestDto.getIsAtivo() == null);
        /**persistencia e retorno
         * recebe a entidade de volta, já contendo o ID gerado
         * * */
		Diretor diretorSalvo = diretorRepository.save(diretor);  
        /**
         * Converte a entidade recém-salva (agora completa e persistida) de volta para um DiretorRequestDTO,
         * que é o formato limpo que será devolvido na resposta da API para quem fez a requisição
         * **/      
		return diretorMapper.entityToDto(diretorSalvo);
	}

	@Override
	public void validarDiretor(DiretorRequestDTO diretorRequestDto) {
		if (diretorRequestDto == null) {
			throw new RegraNegocioException("Dados do diretor não informados!");
		}

		if (diretorRepository.existsByCpfDiretor(diretorRequestDto.getCpf())) {
			throw new RegraNegocioException("Verifique o CPF do diretor e tente novamente!");
		}

		 if (diretorRepository.existsByMatriculaFuncional(diretorRequestDto.getMatriculaFuncional())) {
		 	throw new RegraNegocioException("Verifique a matrícula do diretor e tente novamente!");
		 }

		if (diretorRepository.existsByEmailCorporativo(diretorRequestDto.getEmail())) {
			throw new RegraNegocioException("Verifique o e-mail do diretor e tente novamente!");
		}

		if (diretorRequestDto.getEmailPessoal() != null && !diretorRequestDto.getEmailPessoal().isBlank()
				&& diretorRepository.existsByEmailPessoal(diretorRequestDto.getEmailPessoal())) {
			throw new RegraNegocioException("Verifique o e-mail pessoal do diretor e tente novamente!");
		}

		if (diretorRepository.existsByEscolaUuid(diretorRequestDto.getEscolaUuid())) {
			throw new RegraNegocioException("Já existe diretor vinculado a esta escola!");
		}
	}

	@Override
	public List<DiretorRequestDTO> buscarTodosDiretores() {
		return diretorRepository
        .findAll()
        .stream()
        .map(diretorMapper::entityToRequestDto)
        .toList();
	}

	@Override
	public Optional<DiretorRequestDTO> buscarDiretorPorId(UUID uuid) {
		return diretorRepository.findById(uuid).map(diretorMapper::entityToRequestDto);
	}

	@Override
	public Optional<DiretorRequestDTO> buscarDiretorPorCpf(String cpf) {
		return diretorRepository.findByCpfDiretor(cpf).map(diretorMapper::entityToRequestDto);
	}

	@Override
	public Optional<DiretorRequestDTO> buscarDiretorPorEmailCorporativo(String email) {
		return diretorRepository.findByEmailCorporativo(email).map(diretorMapper::entityToRequestDto);
	}

	@Override
	public Optional<DiretorRequestDTO> buscarDiretorPorEmailPessoal(String emailPessoal) {
		return diretorRepository.findByEmailPessoal(emailPessoal).map(diretorMapper::entityToRequestDto);
	}

	@Override
	public Optional<DiretorRequestDTO> buscarDiretorPorEscola(UUID uuidEscola) {
		return diretorRepository.findByEscolaUuid(uuidEscola).map(diretorMapper::entityToRequestDto);
	}
}
