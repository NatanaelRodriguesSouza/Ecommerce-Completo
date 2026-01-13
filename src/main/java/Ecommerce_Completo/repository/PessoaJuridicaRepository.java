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

    @Query(
            value = "SELECT * FROM pessoa_juridica WHERE UPPER(nome_fantasia) LIKE %:name% " +
                    "OR UPPER(razao_social) LIKE %:name%",
            nativeQuery = true
    )
    List<PessoaJuridica> findByName(@Param("name") String name);

    @Query(
            value = "SELECT * FROM pessoa_juridica WHERE cnpj = :cnpj",
            nativeQuery = true
    )
    Optional<PessoaJuridica> findByCnpj(@Param("cnpj") String cnpj);

    @Query(
            value = "SELECT * FROM pessoa_juridica",
            countQuery = "SELECT COUNT(*) FROM pessoa_juridica",
            nativeQuery = true
    )
    Page<PessoaJuridica> findPage(Pageable pageable);
}
