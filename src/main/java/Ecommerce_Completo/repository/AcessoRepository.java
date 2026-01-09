package Ecommerce_Completo.repository;

import Ecommerce_Completo.model.Acesso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AcessoRepository extends JpaRepository<Acesso,Long> {
    Optional<Acesso> findByDescricao(String descricao);

    Page<Acesso> findByDescricaoContainingIgnoreCase(String descricao, Pageable pageable);
}
