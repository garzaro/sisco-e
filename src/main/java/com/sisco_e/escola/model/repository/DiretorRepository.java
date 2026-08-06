package com.sisco_e.escola.model.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sisco_e.escola.model.entity.Diretor;

@Repository
public interface DiretorRepository extends JpaRepository<Diretor, UUID> {

	boolean existsByCpfDiretor(String cpfDiretor);

	boolean existsByEmailCorporativo(String emailCorporativo);

	boolean existsByEmailPessoal(String emailPessoal);
    
    /**impede que seja cadastrado mais de um diretor na mesma escola**/
	boolean existsByEscolaUuid(UUID uuidEscola);

	Optional<Diretor> findByCpfDiretor(String cpfDiretor);

	Optional<Diretor> findByEmailCorporativo(String emailCorporativo);

	Optional<Diretor> findByEmailPessoal(String emailPessoal);

	Optional<Diretor> findByEscolaUuid(UUID uuidEscola);

}
