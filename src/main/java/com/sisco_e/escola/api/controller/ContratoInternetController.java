package com.sisco_e.escola.api.controller;

import java.util.List;
import java.util.UUID;

import com.sisco_e.escola.api.dto.request.ContratoInternetRequestDTO;
import com.sisco_e.escola.api.dto.response.ContratoInternetResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sisco_e.escola.api.dto.response.ApiResponse;
import com.sisco_e.escola.model.enums.StatusContrato;
import com.sisco_e.escola.service.ContratoInternetService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/contrato")
@RequiredArgsConstructor
public class ContratoInternetController {

	private final ContratoInternetService contratoInternetService;

	@PostMapping("/reg/contrato")
	public ResponseEntity<ApiResponse<ContratoInternetResponseDto>> contratar(
			@RequestBody @Valid ContratoInternetRequestDTO dto) {
		ContratoInternetResponseDto contratoCriado = contratoInternetService.cadastrarContrato(dto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success("Contrato de internet registrado com sucesso!",
					contratoCriado));
	}

	@GetMapping("/ativos")
	public ResponseEntity<List<ContratoInternetRequestDTO>> listarAtivos() {
		return ResponseEntity.ok(contratoInternetService.buscarContratosAtivosComEscolaEProvedor());
	}

	@GetMapping("/escola/{escolaId}")
	public ResponseEntity<List<ContratoInternetRequestDTO>> listarPorEscolaEStatus(
			@PathVariable UUID escolaId,
			@RequestParam StatusContrato status) {
		return ResponseEntity.ok(contratoInternetService.buscarPorEscolaEStatus(escolaId, status));
	}

}
