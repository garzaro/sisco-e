package com.sisco_e.escola.api.controller;

import java.util.List;
import java.util.UUID;

import com.sisco_e.escola.api.dto.request.EscolaRequestDTO;
import com.sisco_e.escola.api.dto.response.ApiResponse;
import com.sisco_e.escola.api.dto.response.EscolaResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sisco_e.escola.model.enums.TipoEscola;
import com.sisco_e.escola.service.EscolaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/escola")
@RequiredArgsConstructor
public class EscolaController {

	private final EscolaService escolaService;

	@PostMapping("/reg/unidade")
	public ResponseEntity<ApiResponse<EscolaResponseDTO>> cadastrarEscola(
			@RequestBody @Valid EscolaRequestDTO escolaRequestDto) {
		EscolaResponseDTO escolaCriada = escolaService.cadastrarEscola(escolaRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success("Escola criada com sucesso!",
						escolaCriada));
	}

	@GetMapping
	public ResponseEntity<List<EscolaRequestDTO>> buscarTodasAsEscolas() {
		return ResponseEntity.ok(escolaService.buscarTodasAsEscolasCadastradas());
	}

	@GetMapping("/{uuid}")
	public ResponseEntity<EscolaRequestDTO> buscarEscolaPorId(@PathVariable UUID uuid) {
		return escolaService.buscarEscolaPorId(uuid)
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/nome")
	public ResponseEntity<EscolaRequestDTO> buscarEscolaPorNome(@RequestParam String nomeEscola) {
		return escolaService.buscarEscolaPorNome(nomeEscola)
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/municipio")
	public ResponseEntity<List<EscolaRequestDTO>> buscarEscolaPorMunicipio(@RequestParam String municipio) {
		return ResponseEntity.ok(escolaService.buscarEscolaPorMunicipio(municipio));
	}

	@GetMapping("/estado")
	public ResponseEntity<List<EscolaRequestDTO>> buscarEscolaPorEstado(@RequestParam String estado) {
		return ResponseEntity.ok(escolaService.buscarEscolaPorEstado(estado));
	}

	@GetMapping("/tipo")
	public ResponseEntity<List<EscolaRequestDTO>> buscarEscolaPorTipo(@RequestParam TipoEscola tipoEscola) {
		return ResponseEntity.ok(escolaService.buscarEscolaPorTipo(tipoEscola));
	}

	@GetMapping("/buscar")
	public ResponseEntity<List<EscolaRequestDTO>> buscarEscolaPorParteDoNome(@RequestParam String termo) {
		return ResponseEntity.ok(escolaService.buscarEscolaPorParteDoNome(termo));
	}

//	@PutMapping("/{uuid}")
//	public ResponseEntity<EscolaResponseDTO> atualizarEscola(@PathVariable UUID uuid, @RequestBody @Valid EscolaRequestDTO escolaRequestDto) {
//		escolaRequestDto.setUuid(uuid);
//		return ResponseEntity.ok(escolaService.atualizarEscola(escolaRequestDto));
//	}

	@DeleteMapping("/{uuid}")
	public ResponseEntity<Void> deletarEscola(@PathVariable UUID uuid) {
		escolaService.deletarEscola(uuid);
		return ResponseEntity.noContent().build();
	}
}