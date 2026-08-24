package com.sisco_e.escola.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import com.sisco_e.escola.api.dto.ContratoInternetDTO;
import com.sisco_e.escola.model.entity.Escola;
import com.sisco_e.escola.model.entity.ProvedorInternet;
import com.sisco_e.escola.model.enums.StatusContrato;
import com.sisco_e.escola.model.enums.TipoEscola;
import com.sisco_e.escola.model.repository.ContratoInternetRepository;
import com.sisco_e.escola.model.repository.EscolaRepository;
import com.sisco_e.escola.model.repository.ProvedorInternetRepository;
import com.sisco_e.escola.service.ContratoInternetService;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
@Transactional
class ContratoInternetServiceImplTest {

	@Autowired
	private ContratoInternetService contratoInternetService;

	@Autowired
	private ContratoInternetRepository contratoInternetRepository;

	@Autowired
	private EscolaRepository escolaRepository;

	@Autowired
	private ProvedorInternetRepository provedorInternetRepository;

	private Escola criarEscola(String codigoEscola) {
		Escola escola = Escola.builder()
			.nomeEscola("Escola Teste")
			.codigoEscola(codigoEscola)
			.municipio("Cidade Teste")
			.estado("SP")
			.tipoEscola(TipoEscola.PUBLICA)
			.isAtivo(true)
			.build();
		return escolaRepository.save(escola);
	}

	private ProvedorInternet criarProvedor(String nomeProvedor) {
		String sufixo = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
		ProvedorInternet provedor = ProvedorInternet.builder()
			.nomeProvedor(nomeProvedor)
			.cnpj("00000000" + sufixo)
			.canalSuportePrioritario("1199999" + sufixo.substring(0, 4))
			.build();
		return provedorInternetRepository.save(provedor);
	}

	@Test
	void deveCriarContratoValido() {
		Escola escola = criarEscola("COD-001");
		ProvedorInternet provedor = criarProvedor("Provedor A");

		ContratoInternetDTO contrato = contratoInternetService.contratar(escola.getUuid(), provedor.getUuid(),
				LocalDate.now(), "100 Mbps", new BigDecimal("199.90"));

		assertNotNull(contrato.getUuid());
		assertEquals(StatusContrato.ATIVO, contrato.getStatus());
		assertEquals(escola.getUuid(), contrato.getUuidEscola());
		assertEquals(provedor.getUuid(), contrato.getUuidProvedor());
		assertEquals("100 Mbps", contrato.getVelocidade());
	}

	@Test
	void deveLancarExcecaoAoDuplicarContrato() {
		Escola escola = criarEscola("COD-002");
		ProvedorInternet provedor = criarProvedor("Provedor B");
		LocalDate dataContratacao = LocalDate.now();

		contratoInternetService.contratar(escola.getUuid(), provedor.getUuid(), dataContratacao,
				"50 Mbps", new BigDecimal("100.00"));
		contratoInternetRepository.flush();

		assertThrows(DataIntegrityViolationException.class, () -> {
			contratoInternetService.contratar(escola.getUuid(), provedor.getUuid(), dataContratacao,
					"100 Mbps", new BigDecimal("150.00"));
			contratoInternetRepository.flush();
		});
	}

	@Test
	void deveLancarEntityNotFoundQuandoEscolaNaoExiste() {
		ProvedorInternet provedor = criarProvedor("Provedor C");
		UUID escolaInexistente = UUID.randomUUID();

		assertThrows(EntityNotFoundException.class, () -> contratoInternetService
			.contratar(escolaInexistente, provedor.getUuid(), LocalDate.now(), "100 Mbps", BigDecimal.TEN));
	}

	@Test
	void deveLancarEntityNotFoundQuandoProvedorNaoExiste() {
		Escola escola = criarEscola("COD-003");
		UUID provedorInexistente = UUID.randomUUID();

		assertThrows(EntityNotFoundException.class, () -> contratoInternetService
			.contratar(escola.getUuid(), provedorInexistente, LocalDate.now(), "100 Mbps", BigDecimal.TEN));
	}

	@Test
	void deveSuportarMultiplosProvedoresAtivosParaMesmaEscola() {
		Escola escola = criarEscola("COD-004");
		ProvedorInternet provedorA = criarProvedor("Provedor D");
		ProvedorInternet provedorB = criarProvedor("Provedor E");

		contratoInternetService.contratar(escola.getUuid(), provedorA.getUuid(), LocalDate.now(),
				"100 Mbps", new BigDecimal("120.00"));
		contratoInternetService.contratar(escola.getUuid(), provedorB.getUuid(), LocalDate.now(),
				"50 Mbps", new BigDecimal("80.00"));

		List<ContratoInternetDTO> contratosAtivos = contratoInternetService.buscarPorEscolaEStatus(escola.getUuid(),
				StatusContrato.ATIVO);

		assertEquals(2, contratosAtivos.size());
	}

}
