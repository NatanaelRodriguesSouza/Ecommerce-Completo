package Ecommerce_Completo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "imagem_produto")
@SequenceGenerator(name = "seq_imagem_produto", sequenceName = "seq_imagem_produto", allocationSize = 1, initialValue = 1)
public class ImagemProduto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_imagem_produto")
    private Long id;

    @Column(name = "imagem_original", columnDefinition = "text", nullable = false)
    private String imagemOriginal;

    @Column(name = "imagem_miniatura", columnDefinition = "text", nullable = false)
    private String imagemMiniatura;

    @JsonIgnoreProperties(allowGetters = true)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "produto_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "imagem_produto_produto_fk")
    )
    private Produto produto;

    @JsonIgnoreProperties(allowGetters = true)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "empresa_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "imagem_produto_empresa_fk")
    )
    private PessoaJuridica empresa;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getImagemOriginal() { return imagemOriginal; }
    public void setImagemOriginal(String imagemOriginal) { this.imagemOriginal = imagemOriginal; }

    public String getImagemMiniatura() { return imagemMiniatura; }
    public void setImagemMiniatura(String imagemMiniatura) { this.imagemMiniatura = imagemMiniatura; }

    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }

    public PessoaJuridica getEmpresa() { return empresa; }
    public void setEmpresa(PessoaJuridica empresa) { this.empresa = empresa; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImagemProduto)) return false;
        ImagemProduto that = (ImagemProduto) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
