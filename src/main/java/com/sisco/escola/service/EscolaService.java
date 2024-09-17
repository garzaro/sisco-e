package com.sisco.escola.service;



import com.sisco.escola.model.entity.Escola;
import org.springframework.stereotype.Service;

@Service
public interface EscolaService {
    /*salvar a escola na base*/
    Escola salvar(Escola escola);
    /*preencher os campos ao cadastrar escola*/
    void validarEscola(Escola escola);
}


