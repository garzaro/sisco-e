package com.sisco_e.escola.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.sisco_e.escola.api.dto.request.ProvedorInternetRequestDTO;
import com.sisco_e.escola.api.dto.response.ProvedorInternetResponseDTO;
import org.springframework.stereotype.Service;

import com.sisco_e.escola.exception.RegraNegocioException;
import com.sisco_e.escola.mapper.ProvedorInternetMapper;
import com.sisco_e.escola.model.entity.ProvedorInternet;
import com.sisco_e.escola.model.repository.ProvedorInternetRepository;
import com.sisco_e.escola.service.ProvedorInternetService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProvedorInternetServiceImpl implements ProvedorInternetService {

    private final ProvedorInternetRepository provedorInternetRepository;
    private final ProvedorInternetMapper provedorInternetMapper;

    @Override
    @Transactional
    public ProvedorInternetResponseDTO cadastrarProvedorInternet(ProvedorInternetRequestDTO provedorInternetRequestDto) {
        validarProvedorInternet(provedorInternetRequestDto);
        ProvedorInternet provedor = provedorInternetMapper.DtoToEntity(provedorInternetRequestDto);
        return provedorInternetMapper.entityToDto(provedorInternetRepository.save(provedor));
    }

    @Override
    public void validarProvedorInternet(ProvedorInternetRequestDTO provedorInternetRequestDto) {
        if (provedorInternetRequestDto == null) {
            throw new RegraNegocioException("Dados do provedor não informados!");
        }
        if (provedorInternetRepository.existsByNomeProvedor(provedorInternetRequestDto.getNomeProvedor())
                || provedorInternetRepository.existsByCnpj(provedorInternetRequestDto.getCnpj())) {
            throw new RegraNegocioException("Já existe um provedor com os dados informados!");
        }
        if (provedorInternetRepository
                .existsByTelefone(provedorInternetRequestDto.getTelefone())){
            throw new RegraNegocioException("O contato pertence a outro provedor!");
        }
    }

    @Override
    public List<ProvedorInternetRequestDTO> buscarTodosProvedoresInternetCadastrados() {
        return provedorInternetRepository.findAll().stream().map(provedorInternetMapper::entityToRequestDto).toList();
    }

    @Override
    public Optional<ProvedorInternetRequestDTO> buscarProvedorInternetPorId(UUID uuid) {
        return provedorInternetRepository.findById(uuid).map(provedorInternetMapper::entityToRequestDto);
    }

    @Override
    public Optional<ProvedorInternetRequestDTO> buscarProvedorInternetPorNome(String nomeProvedor) {
        return provedorInternetRepository.findByNomeProvedor(nomeProvedor).map(provedorInternetMapper::entityToRequestDto);
    }

    @Override
    public Optional<ProvedorInternetRequestDTO> buscarProvedorInternetPorCnpj(String cnpj) {
        return provedorInternetRepository.findByCnpj(cnpj).map(provedorInternetMapper::entityToRequestDto);
    }

    @Override
    public List<ProvedorInternetRequestDTO> buscarProvedoresInternetPorParteDoNome(String parteNomeProvedor) {
        return provedorInternetRepository.findByNomeProvedorContainingIgnoreCase(parteNomeProvedor).stream()
                .map(provedorInternetMapper::entityToRequestDto)
                .toList();
    }

    @Override
    @Transactional
    public ProvedorInternetRequestDTO atualizarProvedorInternet(ProvedorInternetRequestDTO provedorInternetRequestDto) {
        if (provedorInternetRequestDto == null || provedorInternetRequestDto.getUuid() == null) {
            throw new RegraNegocioException("Id do provedor é obrigatório para atualização");
        }

        ProvedorInternet provedor = provedorInternetRepository.findById(provedorInternetRequestDto.getUuid())
                .orElseThrow(() -> new RegraNegocioException("Provedor não encontrado para atualização"));
        provedor.setNomeProvedor(provedorInternetRequestDto.getNomeProvedor());
        provedor.setCnpj(provedorInternetRequestDto.getCnpj());
        provedor.setTelefone(provedorInternetRequestDto.getTelefone());
        return provedorInternetMapper.entityToRequestDto(provedorInternetRepository.save(provedor));
    }

    @Override
    @Transactional
    public void deletarProvedorInternet(UUID uuid) {
        if (!provedorInternetRepository.existsById(uuid)) {
            throw new RegraNegocioException("Provedor não encontrado para exclusão");
        }
        provedorInternetRepository.deleteById(uuid);
    }

    @Override
    public Optional<ProvedorInternetRequestDTO> obterProvedorInternetPorId(UUID uuid) {
        return buscarProvedorInternetPorId(uuid);
    }
}
