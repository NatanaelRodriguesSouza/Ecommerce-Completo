package Ecommerce_Completo.repository;

import Ecommerce_Completo.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    List<Endereco> findByPessoaId(Long pessoaId);

    Optional<Endereco> findByIdAndPessoaId(Long id, Long pessoaId);
}
