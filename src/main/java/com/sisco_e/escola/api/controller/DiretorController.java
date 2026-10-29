package com.sisco_e.escola.api.controller;

import java.util.List;
import java.util.UUID;

import com.sisco_e.escola.api.dto.response.ApiResponse;
import com.sisco_e.escola.api.dto.request.DiretorRequestDTO;
import com.sisco_e.escola.api.dto.response.DiretorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sisco_e.escola.service.DiretorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * TODO-list
 * [] Ver o problema da matricula
 * **/

@RestController
@RequestMapping("/api/diretor")
@RequiredArgsConstructor
public class DiretorController {

	private final DiretorService diretorService;

	@PostMapping("/reg/dir")
	public ResponseEntity<ApiResponse<DiretorResponseDTO>> cadastrarDiretor(
			@RequestBody @Valid DiretorRequestDTO diretorRequestDto) {
		DiretorResponseDTO diretorCriado = diretorService.cadastrarDiretor(diretorRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success("Diretor cadastrado com sucesso!",
						diretorCriado));
	}

	@GetMapping
	public ResponseEntity<List<DiretorRequestDTO>> buscarTodosDiretores() {
		return ResponseEntity.ok(diretorService.buscarTodosDiretores());
	}

	@GetMapping("/{uuid}")
	public ResponseEntity<DiretorRequestDTO> buscarDiretorPorId(@PathVariable UUID uuid) {
		return diretorService.buscarDiretorPorId(uuid)
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
	}
    
	@GetMapping("/cpf")
	public ResponseEntity<DiretorRequestDTO> buscarDiretorPorCpf(@RequestParam String cpf) {
		return diretorService.buscarDiretorPorCpf(cpf)
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/email")
	public ResponseEntity<DiretorRequestDTO> buscarDiretorPorEmail(@RequestParam String email) {
		return diretorService.buscarDiretorPorEmailCorporativo(email)
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/email-pessoal")
	public ResponseEntity<DiretorRequestDTO> buscarDiretorPorEmailPessoal(@RequestParam String emailPessoal) {
		return diretorService.buscarDiretorPorEmailPessoal(emailPessoal)
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/escola/{escolaUuid}")
	public ResponseEntity<DiretorRequestDTO> buscarDiretorPorEscola(@PathVariable UUID escolaUuid) {
		return diretorService.buscarDiretorPorEscola(escolaUuid)
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
	}
}
