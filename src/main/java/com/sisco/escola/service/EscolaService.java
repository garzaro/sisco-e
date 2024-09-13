package com.sisco.escola.service;



import com.sisco.escola.model.entity.Escola;
import org.springframework.stereotype.Service;

@Service
public interface EscolaService {
	
	Escola buscarEscolaPorNome(String Escola);
       
    /*salvar a escola na base*/
    Escola salvar(Escola escola); /*implementar*/
    
    /*verificar escola na base de dados, unique*/
    void validarEscola(String escola);
    
    
}


