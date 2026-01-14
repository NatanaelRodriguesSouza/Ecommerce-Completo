package Ecommerce_Completo.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "categoria_produto")
@SequenceGenerator(
        name = "seq_categoria_produto",
        sequenceName = "seq_categoria_produto",
        allocationSize = 1,
        initialValue = 1
)
public class CategoriaProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_categoria_produto")
    private Long id;

    @Column(name = "nome_desc", nullable = false)
    private String nomeDesc;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "empresa_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "categoria_empresa_fk")
    )
    private PessoaJuridica empresa;

    public CategoriaProduto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeDesc() { return nomeDesc; }
    public void setNomeDesc(String nomeDesc) { this.nomeDesc = nomeDesc; }

    public PessoaJuridica getEmpresa() { return empresa; }
    public void setEmpresa(PessoaJuridica empresa) { this.empresa = empresa; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoriaProduto)) return false;
        CategoriaProduto that = (CategoriaProduto) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
