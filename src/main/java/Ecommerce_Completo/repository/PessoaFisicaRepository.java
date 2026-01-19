package Ecommerce_Completo.repository;

import Ecommerce_Completo.model.PessoaFisica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica,Long> {
    boolean existsByCpf(String cpf);

    List<PessoaFisica> findByNome(@Param("name") String name);


    Optional<PessoaFisica> findByCpf(@Param("cpf") String cpf);


    Page<PessoaFisica> findAll(Pageable pageable);

}
