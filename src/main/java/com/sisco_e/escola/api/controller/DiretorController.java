package com.sisco_e.escola.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sisco_e.escola.api.dto.DiretorDTO;
import com.sisco_e.escola.service.DiretorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/diretor")
@RequiredArgsConstructor
public class DiretorController {

	private final DiretorService diretorService;

	@PostMapping("/reg/dir")
	public ResponseEntity<DiretorDTO> cadastrarDiretor(@RequestBody @Valid DiretorDTO diretorDto) {
		DiretorDTO diretorCriado = diretorService.cadastrarDiretor(diretorDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(diretorCriado);
	}

	@GetMapping
	public ResponseEntity<List<DiretorDTO>> buscarTodosDiretores() {
		return ResponseEntity.ok(diretorService.buscarTodosDiretores());
	}

	@GetMapping("/{uuid}")
	public ResponseEntity<DiretorDTO> buscarDiretorPorId(@PathVariable UUID uuid) {
		return diretorService.buscarDiretorPorId(uuid)
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
	}
    
	@GetMapping("/cpf")
	public ResponseEntity<DiretorDTO> buscarDiretorPorCpf(@RequestParam String cpf) {
		return diretorService.buscarDiretorPorCpf(cpf)
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/email")
	public ResponseEntity<DiretorDTO> buscarDiretorPorEmail(@RequestParam String email) {
		return diretorService.buscarDiretorPorEmailCorporativo(email)
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/email-pessoal")
	public ResponseEntity<DiretorDTO> buscarDiretorPorEmailPessoal(@RequestParam String emailPessoal) {
		return diretorService.buscarDiretorPorEmailPessoal(emailPessoal)
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/escola/{escolaUuid}")
	public ResponseEntity<DiretorDTO> buscarDiretorPorEscola(@PathVariable UUID escolaUuid) {
		return diretorService.buscarDiretorPorEscola(escolaUuid)
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
	}
}
