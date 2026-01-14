package Ecommerce_Completo.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "produto")
@SequenceGenerator(
        name = "seq_produto",
        sequenceName = "seq_produto",
        initialValue = 1,
        allocationSize = 1
)
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_produto")
    private Long id;

    @Column(nullable = false)
    private Boolean ativo = Boolean.TRUE;

    @Column(name = "tipo_unidade", nullable = false)
    private String tipoUnidade;

    @Column(nullable = false)
    private String nome;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "empresa_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "produto_empresa_fk")
    )
    private PessoaJuridica empresa;

    @Column(columnDefinition = "text", nullable = false)
    private String descricao;

    @Column(nullable = false)
    private Double peso;

    @Column(nullable = false)
    private Double largura;

    @Column(nullable = false)
    private Double altura;

    @Column(nullable = false)
    private Double profundidade;

    @Column(name = "valor_venda", nullable = false, precision = 19, scale = 2)
    private BigDecimal valorVenda = BigDecimal.ZERO;

    @Column(name = "qtd_estoque", nullable = false)
    private Integer qtdEstoque = 0;

    @Column(name = "qtde_alerta_estoque")
    private Integer qtdeAlertaEstoque = 0;

    @Column(name = "link_youtube")
    private String linkYoutube;

    @Column(name = "alerta_qtde_estoque")
    private Boolean alertaQtdeEstoque = Boolean.FALSE;

    @Column(name = "qtde_clique")
    private Integer qtdeClique = 0;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public String getTipoUnidade() { return tipoUnidade; }
    public void setTipoUnidade(String tipoUnidade) { this.tipoUnidade = tipoUnidade; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public PessoaJuridica getEmpresa() { return empresa; }
    public void setEmpresa(PessoaJuridica empresa) { this.empresa = empresa; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }

    public Double getLargura() { return largura; }
    public void setLargura(Double largura) { this.largura = largura; }

    public Double getAltura() { return altura; }
    public void setAltura(Double altura) { this.altura = altura; }

    public Double getProfundidade() { return profundidade; }
    public void setProfundidade(Double profundidade) { this.profundidade = profundidade; }

    public BigDecimal getValorVenda() { return valorVenda; }
    public void setValorVenda(BigDecimal valorVenda) { this.valorVenda = valorVenda; }

    public Integer getQtdEstoque() { return qtdEstoque; }
    public void setQtdEstoque(Integer qtdEstoque) { this.qtdEstoque = qtdEstoque; }

    public Integer getQtdeAlertaEstoque() { return qtdeAlertaEstoque; }
    public void setQtdeAlertaEstoque(Integer qtdeAlertaEstoque) { this.qtdeAlertaEstoque = qtdeAlertaEstoque; }

    public String getLinkYoutube() { return linkYoutube; }
    public void setLinkYoutube(String linkYoutube) { this.linkYoutube = linkYoutube; }

    public Boolean getAlertaQtdeEstoque() { return alertaQtdeEstoque; }
    public void setAlertaQtdeEstoque(Boolean alertaQtdeEstoque) { this.alertaQtdeEstoque = alertaQtdeEstoque; }

    public Integer getQtdeClique() { return qtdeClique; }
    public void setQtdeClique(Integer qtdeClique) { this.qtdeClique = qtdeClique; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produto)) return false;
        Produto that = (Produto) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
