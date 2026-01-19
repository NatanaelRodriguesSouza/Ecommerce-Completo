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

    @Query(value = """
    SELECT pj.*
    FROM pessoa_juridica pj
    WHERE pj.nome_fantasia ILIKE CONCAT('%', :name, '%')
       OR pj.razao_social ILIKE CONCAT('%', :name, '%')
""", nativeQuery = true)
    List<PessoaJuridica> findByName(@Param("name") String name);

    Optional<PessoaJuridica> findByCnpj(String cnpj);

    @Query(value = "SELECT pj.* FROM pessoa_juridica pj",
            countQuery = "SELECT COUNT(*) FROM pessoa_juridica",
            nativeQuery = true)
    Page<PessoaJuridica> findPage(Pageable pageable);

}
