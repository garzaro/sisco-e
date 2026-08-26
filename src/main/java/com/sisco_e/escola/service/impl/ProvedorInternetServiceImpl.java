package com.sisco_e.escola.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sisco_e.escola.api.dto.ProvedorInternetDTO;
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
    public ProvedorInternetDTO cadastrarProvedorInternet(ProvedorInternetDTO provedorInternetDto) {
        validarProvedorInternet(provedorInternetDto);
        ProvedorInternet provedor = provedorInternetMapper.DtoToEntity(provedorInternetDto);
        return provedorInternetMapper.entityToDto(provedorInternetRepository.save(provedor));
    }

    @Override
    public void validarProvedorInternet(ProvedorInternetDTO provedorInternetDto) {
        if (provedorInternetDto == null) {
            throw new RegraNegocioException("Dados do provedor não informados!");
        }
        if (provedorInternetRepository.existsByNomeProvedor(provedorInternetDto.getNomeProvedor())
                || provedorInternetRepository.existsByCnpj(provedorInternetDto.getCnpj())) {
            throw new RegraNegocioException("Já existe um provedor com os dados informados!");
        }
        if (provedorInternetRepository
                .existsByTelefone(provedorInternetDto.getTelefone())){
            throw new RegraNegocioException("O contato pertence a outro provedor!");
        }
    }

    @Override
    public List<ProvedorInternetDTO> buscarTodosProvedoresInternetCadastrados() {
        return provedorInternetRepository.findAll().stream().map(provedorInternetMapper::entityToDto).toList();
    }

    @Override
    public Optional<ProvedorInternetDTO> buscarProvedorInternetPorId(UUID uuid) {
        return provedorInternetRepository.findById(uuid).map(provedorInternetMapper::entityToDto);
    }

    @Override
    public Optional<ProvedorInternetDTO> buscarProvedorInternetPorNome(String nomeProvedor) {
        return provedorInternetRepository.findByNomeProvedor(nomeProvedor).map(provedorInternetMapper::entityToDto);
    }

    @Override
    public Optional<ProvedorInternetDTO> buscarProvedorInternetPorCnpj(String cnpj) {
        return provedorInternetRepository.findByCnpj(cnpj).map(provedorInternetMapper::entityToDto);
    }

    @Override
    public List<ProvedorInternetDTO> buscarProvedoresInternetPorParteDoNome(String parteNomeProvedor) {
        return provedorInternetRepository.findByNomeProvedorContainingIgnoreCase(parteNomeProvedor).stream()
                .map(provedorInternetMapper::entityToDto)
                .toList();
    }

    @Override
    @Transactional
    public ProvedorInternetDTO atualizarProvedorInternet(ProvedorInternetDTO provedorInternetDto) {
        if (provedorInternetDto == null || provedorInternetDto.getUuid() == null) {
            throw new RegraNegocioException("Id do provedor é obrigatório para atualização");
        }

        ProvedorInternet provedor = provedorInternetRepository.findById(provedorInternetDto.getUuid())
                .orElseThrow(() -> new RegraNegocioException("Provedor não encontrado para atualização"));
        provedor.setNomeProvedor(provedorInternetDto.getNomeProvedor());
        provedor.setCnpj(provedorInternetDto.getCnpj());
        provedor.setTelefone(provedorInternetDto.getTelefone());
        return provedorInternetMapper.entityToDto(provedorInternetRepository.save(provedor));
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
    public Optional<ProvedorInternetDTO> obterProvedorInternetPorId(UUID uuid) {
        return buscarProvedorInternetPorId(uuid);
    }
}
