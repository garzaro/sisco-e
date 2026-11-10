package com.sisco_e.escola.api.controller;

import java.util.List;
import java.util.UUID;

import com.sisco_e.escola.api.dto.request.EscolaRequestDTO;
import com.sisco_e.escola.api.dto.response.ApiResponse;
import com.sisco_e.escola.api.dto.response.EscolaResponseDTO;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sisco_e.escola.model.enums.TipoEscola;
import com.sisco_e.escola.service.EscolaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
/**Mantive o ResponseEntity so pra  manter padrao, ja tinha começado com ele ai vai...o Page ja basta**/
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
	public Page<EscolaResponseDTO> listarEscolasCadastradas(Pageable pageable) {
		return escolaService.buscarEscolasCadastradas(pageable);
	}

	@GetMapping("/{escolaUuid}")
	public ResponseEntity<EscolaResponseDTO> buscarEscolaPorId(@PathVariable UUID escolaUuid) {
		return ResponseEntity.ok(escolaService.buscarEscolaPorId(escolaUuid));
	}

	@PutMapping("/{escolaUuid}")
	public ResponseEntity<EscolaResponseDTO> atualizarEscola(
			@PathVariable UUID escolaUuid,
			@RequestBody @Valid EscolaRequestDTO escolaRequestDto
	) {
		return ResponseEntity.ok(escolaService.atualizarEscola(escolaUuid, escolaRequestDto));
	}

	@GetMapping("/buscar-nome-escola")
	public List<EscolaResponseDTO> buscarEscolaPorNome(
			@RequestParam @Size(min = 3) String nomeEscola
	) {
		return escolaService
				.buscarByNomeEscolaContainingIgnoreCaseOrderByNomeEscolaAsc(nomeEscola);
	}

	@GetMapping("/municipio")
	public ResponseEntity<Page<EscolaResponseDTO>> buscarEscolaPorMunicipio(
			@RequestParam String municipio, Pageable pageable
	) {
		return ResponseEntity.ok(escolaService.buscarEscolaPorMunicipio(municipio, pageable));
	}

	@GetMapping("/estado")
	public ResponseEntity<Page<EscolaResponseDTO>> buscarEscolaPorEstado(
			@RequestParam String estado, Pageable pageable) {
		return ResponseEntity.ok(escolaService.buscarEscolaPorEstado(estado, pageable));
	}

	@GetMapping("/tipo-escola")
	public ResponseEntity<Page<EscolaResponseDTO>> buscarEscolaPorTipo(
			@RequestParam TipoEscola tipoEscola, Pageable pageable
	) {
		return ResponseEntity.ok(escolaService.buscarEscolaPorTipo(tipoEscola, pageable));
	}

	@DeleteMapping("/{uuid}")
	public ResponseEntity<Void> deletarEscola(@PathVariable UUID uuid) {
		escolaService.deletarEscola(uuid);
		return ResponseEntity.noContent().build();
	}
}