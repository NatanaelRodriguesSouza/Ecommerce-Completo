package Ecommerce_Completo.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "nota_fiscal_compra")
@SequenceGenerator(name = "seq_nota_fiscal_compra", sequenceName = "seq_nota_fiscal_compra", allocationSize = 1, initialValue = 1)
public class NotaFiscalCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_nota_fiscal_compra")
    private Long id;

    @Column(name = "numero_nota", nullable = false)
    private String numeroNota;

    @Column(name = "serie_nota", nullable = false)
    private String serieNota;

    @Column(name = "descricao_obs")
    private String descricaoObs;

    @Column(name = "valor_total", nullable = false, precision = 19, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "valor_desconto", precision = 19, scale = 2)
    private BigDecimal valorDesconto;

    @Column(name = "valor_icms", nullable = false, precision = 19, scale = 2)
    private BigDecimal valorIcms;

    @Column(name = "data_compra", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataCompra;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "pessoa_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "nota_fiscal_compra_pessoa_fk")
    )
    private Pessoa pessoa;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "conta_pagar_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "nota_fiscal_compra_conta_pagar_fk")
    )
    private ContaPagar contaPagar;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "empresa_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "nota_fiscal_compra_empresa_fk")
    )
    private PessoaJuridica empresa;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroNota() { return numeroNota; }
    public void setNumeroNota(String numeroNota) { this.numeroNota = numeroNota; }

    public String getSerieNota() { return serieNota; }
    public void setSerieNota(String serieNota) { this.serieNota = serieNota; }

    public String getDescricaoObs() { return descricaoObs; }
    public void setDescricaoObs(String descricaoObs) { this.descricaoObs = descricaoObs; }

    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

    public BigDecimal getValorDesconto() { return valorDesconto; }
    public void setValorDesconto(BigDecimal valorDesconto) { this.valorDesconto = valorDesconto; }

    public BigDecimal getValorIcms() { return valorIcms; }
    public void setValorIcms(BigDecimal valorIcms) { this.valorIcms = valorIcms; }

    public Date getDataCompra() { return dataCompra; }
    public void setDataCompra(Date dataCompra) { this.dataCompra = dataCompra; }

    public Pessoa getPessoa() { return pessoa; }
    public void setPessoa(Pessoa pessoa) { this.pessoa = pessoa; }

    public ContaPagar getContaPagar() { return contaPagar; }
    public void setContaPagar(ContaPagar contaPagar) { this.contaPagar = contaPagar; }

    public PessoaJuridica getEmpresa() { return empresa; }
    public void setEmpresa(PessoaJuridica empresa) { this.empresa = empresa; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotaFiscalCompra)) return false;
        NotaFiscalCompra that = (NotaFiscalCompra) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
