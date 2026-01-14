package Ecommerce_Completo.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(
        name = "acesso",
        uniqueConstraints = @UniqueConstraint(
                name = "acesso_descricao_uk",
                columnNames = "descricao"
        )
)
@SequenceGenerator(
        name = "seq_acesso",
        sequenceName = "seq_acesso",
        allocationSize = 1,
        initialValue = 1
)
public class Acesso implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_acesso")
    private Long id;

    @Column(nullable = false, length = 100)
    private String descricao;

    public Acesso() {}

    public Acesso(String descricao) {
        this.descricao = descricao;
    }

    public Acesso(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public @Nullable String getAuthority() {
        return this.descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Acesso)) return false;
        Acesso acesso = (Acesso) o;
        return id != null && Objects.equals(id, acesso.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
