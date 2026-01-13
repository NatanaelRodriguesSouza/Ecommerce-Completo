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


    @Query(
            value = "SELECT * FROM pessoa_fisica WHERE UPPER(nome) LIKE %:name%",
            nativeQuery = true
    )
    List<PessoaFisica> findByName(@Param("name") String name);

    @Query(
            value = "SELECT * FROM pessoa_fisica WHERE cpf = :cpf",
            nativeQuery = true
    )
    Optional<PessoaFisica> findByCpf(@Param("cpf") String cpf);

    @Query(
            value = "SELECT * FROM pessoa_fisica",
            countQuery = "SELECT COUNT(*) FROM pessoa_fisica",
            nativeQuery = true
    )
    Page<PessoaFisica> findPage(Pageable pageable);
}
