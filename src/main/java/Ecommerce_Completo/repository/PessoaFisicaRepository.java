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

    @Query(value = """
    SELECT pf.*
    FROM pessoa_fisica pf
    JOIN pessoa p ON p.id = pf.id
    WHERE p.nome ILIKE CONCAT('%', :name, '%')
""", nativeQuery = true)
    List<PessoaFisica> findByName(@Param("name") String name);


    @Query(value = "SELECT pf.* FROM pessoa_fisica pf WHERE pf.cpf = :cpf", nativeQuery = true)
    Optional<PessoaFisica> findByCpf(@Param("cpf") String cpf);


    @Query(value = "SELECT pf.* FROM pessoa_fisica pf",
            countQuery = "SELECT COUNT(*) FROM pessoa_fisica",
            nativeQuery = true)
    Page<PessoaFisica> findPage(Pageable pageable);

}
