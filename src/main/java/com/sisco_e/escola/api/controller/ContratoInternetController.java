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

import com.sisco_e.escola.api.dto.ContratoInternetDTO;
import com.sisco_e.escola.model.enums.StatusContrato;
import com.sisco_e.escola.service.ContratoInternetService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/contratos")
@RequiredArgsConstructor
public class ContratoInternetController {

	private final ContratoInternetService contratoInternetService;

	@PostMapping("/reg/contrato")
	public ResponseEntity<ContratoInternetDTO> contratar(@RequestBody @Valid ContratoInternetDTO dto) {
		ContratoInternetDTO criado = contratoInternetService.registrarContrato(
				dto.getUuidEscola(), dto.getUuidProvedor(),
				dto.getDataContratacao(), dto.getVelocidade(), dto.getValorMensal());
		return ResponseEntity.status(HttpStatus.CREATED).body(criado);
	}

	@GetMapping("/ativos")
	public ResponseEntity<List<ContratoInternetDTO>> listarAtivos() {
		return ResponseEntity.ok(contratoInternetService.buscarContratosAtivosComEscolaEProvedor());
	}

	@GetMapping("/escola/{escolaId}")
	public ResponseEntity<List<ContratoInternetDTO>> listarPorEscolaEStatus(
			@PathVariable UUID escolaId,
			@RequestParam StatusContrato status) {
		return ResponseEntity.ok(contratoInternetService.buscarPorEscolaEStatus(escolaId, status));
	}

}
