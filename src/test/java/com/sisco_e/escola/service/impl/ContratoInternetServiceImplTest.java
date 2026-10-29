package com.sisco_e.escola.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.sisco_e.escola.api.dto.request.ContratoInternetRequestDTO;
import com.sisco_e.escola.api.dto.response.ContratoInternetResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.sisco_e.escola.exception.RegraNegocioException;
import com.sisco_e.escola.model.entity.Escola;
import com.sisco_e.escola.model.entity.ProvedorInternet;
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
			.telefone("1199999" + sufixo.substring(0, 4))
			.build();
		return provedorInternetRepository.save(provedor);
	}

	private ContratoInternetRequestDTO criarContratoDTO(UUID uuidEscola, UUID uuidProvedor) {
		return ContratoInternetRequestDTO.builder()
			.dataContratacao(LocalDate.now())
			.velocidade("100 Mbps")
			.valorMensal(new BigDecimal("199.90"))
			.build();
	}

	@Test
	void deveCriarContratoValido() {
		Escola escola = criarEscola("COD-001");
		ProvedorInternet provedor = criarProvedor("Provedor A");

		ContratoInternetRequestDTO dto = criarContratoDTO(escola.getUuid(), provedor.getUuid());
		ContratoInternetResponseDto contrato = contratoInternetService.cadastrarContrato(dto);

		assertNotNull(contrato.getUuid());
		assertEquals(escola.getUuid(), contrato.getUuidEscola());
		assertEquals(provedor.getUuid(), contrato.getUuidProvedor());
		assertEquals("100 Mbps", contrato.getVelocidade());
	}

	@Test
	void deveLancarExcecaoAoDuplicarContrato() {
		Escola escola = criarEscola("COD-002");
		ProvedorInternet provedor = criarProvedor("Provedor B");
		LocalDate dataContratacao = LocalDate.now();

		ContratoInternetRequestDTO dto1 = ContratoInternetRequestDTO.builder()
			.dataContratacao(dataContratacao)
			.velocidade("50 Mbps")
			.valorMensal(new BigDecimal("100.00"))
			.build();

		contratoInternetService.cadastrarContrato(dto1);
		contratoInternetRepository.flush();

		ContratoInternetRequestDTO dto2 = ContratoInternetRequestDTO.builder()
			.dataContratacao(dataContratacao)
			.velocidade("100 Mbps")
			.valorMensal(new BigDecimal("200.00"))
			.build();

		assertThrows(RegraNegocioException.class, () -> {
			contratoInternetService.cadastrarContrato(dto2);
		});
	}

	@Test
	void deveLancarEntityNotFoundQuandoEscolaNaoExiste() {
		ProvedorInternet provedor = criarProvedor("Provedor C");
		UUID escolaInexistente = UUID.randomUUID();

		ContratoInternetRequestDTO dto = criarContratoDTO(escolaInexistente, provedor.getUuid());

		assertThrows(EntityNotFoundException.class,
			() -> contratoInternetService.cadastrarContrato(dto));
	}

	@Test
	void deveLancarEntityNotFoundQuandoProvedorNaoExiste() {
		Escola escola = criarEscola("COD-003");
		UUID provedorInexistente = UUID.randomUUID();

		ContratoInternetRequestDTO dto = criarContratoDTO(escola.getUuid(), provedorInexistente);

		assertThrows(EntityNotFoundException.class,
			() -> contratoInternetService.cadastrarContrato(dto));
	}

	@Test
	void deveLancarExcecaoQuandoDtoNulo() {
		assertThrows(RegraNegocioException.class,
			() -> contratoInternetService.cadastrarContrato(null));
	}
}
