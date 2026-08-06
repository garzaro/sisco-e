package com.sisco_e.escola.api.controller;

import java.util.List;
import java.util.UUID;

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

import com.sisco_e.escola.api.dto.EscolaDTO;
import com.sisco_e.escola.model.entity.TipoEscola;
import com.sisco_e.escola.service.EscolaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/escola")
@RequiredArgsConstructor
public class EscolaController {

	private final EscolaService escolaService;

	@PostMapping("/reg/unidade")
	public ResponseEntity<EscolaDTO> cadastrarEscola(@RequestBody @Valid EscolaDTO escolaDto) {
		EscolaDTO escolaCriada = escolaService.cadastrarEscola(escolaDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(escolaCriada);
	}

	@GetMapping
	public ResponseEntity<List<EscolaDTO>> buscarTodasAsEscolas() {
		return ResponseEntity.ok(escolaService.buscarTodasAsEscolasCadastradas());
	}

	@GetMapping("/{uuid}")
	public ResponseEntity<EscolaDTO> buscarEscolaPorId(@PathVariable UUID uuid) {
		return escolaService.buscarEscolaPorId(uuid)
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/nome")
	public ResponseEntity<EscolaDTO> buscarEscolaPorNome(@RequestParam String nomeEscola) {
		return escolaService.buscarEscolaPorNome(nomeEscola)
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/municipio")
	public ResponseEntity<List<EscolaDTO>> buscarEscolaPorMunicipio(@RequestParam String municipio) {
		return ResponseEntity.ok(escolaService.buscarEscolaPorMunicipio(municipio));
	}

	@GetMapping("/estado")
	public ResponseEntity<List<EscolaDTO>> buscarEscolaPorEstado(@RequestParam String estado) {
		return ResponseEntity.ok(escolaService.buscarEscolaPorEstado(estado));
	}

	@GetMapping("/tipo")
	public ResponseEntity<List<EscolaDTO>> buscarEscolaPorTipo(@RequestParam TipoEscola tipoEscola) {
		return ResponseEntity.ok(escolaService.buscarEscolaPorTipo(tipoEscola));
	}

	@GetMapping("/buscar")
	public ResponseEntity<List<EscolaDTO>> buscarEscolaPorParteDoNome(@RequestParam String termo) {
		return ResponseEntity.ok(escolaService.buscarEscolaPorParteDoNome(termo));
	}

	@PutMapping("/{uuid}")
	public ResponseEntity<EscolaDTO> atualizarEscola(@PathVariable UUID uuid, @RequestBody @Valid EscolaDTO escolaDto) {
		escolaDto.setUuid(uuid);
		return ResponseEntity.ok(escolaService.atualizarEscola(escolaDto));
	}

	@DeleteMapping("/{uuid}")
	public ResponseEntity<Void> deletarEscola(@PathVariable UUID uuid) {
		escolaService.deletarEscola(uuid);
		return ResponseEntity.noContent().build();
	}
}