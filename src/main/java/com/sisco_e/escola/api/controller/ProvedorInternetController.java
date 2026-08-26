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
import com.sisco_e.escola.api.dto.ProvedorInternetDTO;
import com.sisco_e.escola.service.ProvedorInternetService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/provedor-internet")
@RequiredArgsConstructor
public class ProvedorInternetController {

    private final ProvedorInternetService provedorInternetService;

    @PostMapping("/reg/provedor")
    public ResponseEntity<ProvedorInternetDTO> cadastrarProvedorInternet(
            @RequestBody @Valid ProvedorInternetDTO provedorInternetDto) {
        ProvedorInternetDTO provedorCriado =
                provedorInternetService.cadastrarProvedorInternet(provedorInternetDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(provedorCriado);
    }

    @GetMapping
    public ResponseEntity<List<ProvedorInternetDTO>> buscarTodosProvedoresInternet() {
        return ResponseEntity.ok(provedorInternetService.buscarTodosProvedoresInternetCadastrados());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ProvedorInternetDTO> buscarProvedorInternetPorId(@PathVariable UUID uuid) {
        return provedorInternetService.buscarProvedorInternetPorId(uuid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nome")
    public ResponseEntity<ProvedorInternetDTO> buscarProvedorInternetPorNome(@RequestParam String nomeProvedor) {
        return provedorInternetService.buscarProvedorInternetPorNome(nomeProvedor)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cnpj")
    public ResponseEntity<ProvedorInternetDTO> buscarProvedorInternetPorCnpj(@RequestParam String cnpj) {
        return provedorInternetService.buscarProvedorInternetPorCnpj(cnpj)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProvedorInternetDTO>> buscarProvedoresInternetPorParteDoNome(
            @RequestParam String termo) {
        return ResponseEntity.ok(provedorInternetService.buscarProvedoresInternetPorParteDoNome(termo));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ProvedorInternetDTO> atualizarProvedorInternet(
            @PathVariable UUID uuid,
            @RequestBody @Valid ProvedorInternetDTO provedorInternetDto) {
        provedorInternetDto.setUuid(uuid);
        return ResponseEntity.ok(provedorInternetService.atualizarProvedorInternet(provedorInternetDto));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deletarProvedorInternet(@PathVariable UUID uuid) {
        provedorInternetService.deletarProvedorInternet(uuid);
        return ResponseEntity.noContent().build();
    }
}
