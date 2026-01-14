package Ecommerce_Completo.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "avaliacao_produto")
@SequenceGenerator(
        name = "seq_avaliacao_produto",
        sequenceName = "seq_avaliacao_produto",
        allocationSize = 1,
        initialValue = 1
)
public class AvaliacaoProduto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_avaliacao_produto")
    private Long id;

    @Column(nullable = false)
    private Integer nota;

    @Column(nullable = false)
    private String descricao;


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "pessoa_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "avaliacao_pessoa_fk")
    )
    private PessoaFisica pessoa;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "produto_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "avaliacao_produto_fk")
    )
    private Produto produto;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "empresa_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "avaliacao_empresa_fk")
    )
    private PessoaJuridica empresa;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getNota() { return nota; }
    public void setNota(Integer nota) { this.nota = nota; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public PessoaFisica getPessoa() { return pessoa; }
    public void setPessoa(PessoaFisica pessoa) { this.pessoa = pessoa; }

    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }

    public PessoaJuridica getEmpresa() { return empresa; }
    public void setEmpresa(PessoaJuridica empresa) { this.empresa = empresa; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AvaliacaoProduto)) return false;
        AvaliacaoProduto that = (AvaliacaoProduto) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
