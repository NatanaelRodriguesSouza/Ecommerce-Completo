package Ecommerce_Completo.repository;

import Ecommerce_Completo.model.PessoaJuridica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, Long> {
    boolean existsByCnpj(String cnpj);

    List<PessoaJuridica> findByNome(@Param("name") String name);

    Optional<PessoaJuridica> findByCnpj(String cnpj);

    Page<PessoaJuridica> findAll(Pageable pageable);


}
