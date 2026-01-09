package Ecommerce_Completo.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "notaFiscalCompra")
@SequenceGenerator(name = "seq_NotaFiscalCompra" , sequenceName = "seq_NotaFiscalCompra",allocationSize = 1,initialValue = 1)
public class NotaFiscalCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_NotaFiscalCompra")
    private Long id;

    @Column(nullable = false)
    private String numeroNota;

    @Column(nullable = false)
    private String serieNota;

    private String descricaoObs;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    private BigDecimal valorDesconto;

    @Column(nullable = false)
    private BigDecimal valorIcms;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataCompra;
    @ManyToOne
    @JoinColumn(name = "pessoa_id" , nullable = false , foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name = "pessoa_fk"))
    private Pessoa pessoa;
    @ManyToOne
    @JoinColumn(name = "contaPagar_id" , nullable = false , foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name = "contaPagar_fk"))
    private ContaPagar contaPagar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroNota() {
        return numeroNota;
    }

    public void setNumeroNota(String numeroNota) {
        this.numeroNota = numeroNota;
    }

    public String getSerieNota() {
        return serieNota;
    }

    public void setSerieNota(String serieNota) {
        this.serieNota = serieNota;
    }

    public String getDescricaoObs() {
        return descricaoObs;
    }

    public void setDescricaoObs(String descricaoObs) {
        this.descricaoObs = descricaoObs;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public BigDecimal getValorIcms() {
        return valorIcms;
    }

    public void setValorIcms(BigDecimal valorIcms) {
        this.valorIcms = valorIcms;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public ContaPagar getContaPagar() {
        return contaPagar;
    }

    public void setContaPagar(ContaPagar contaPagar) {
        this.contaPagar = contaPagar;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        NotaFiscalCompra that = (NotaFiscalCompra) o;
        return Objects.equals(id, that.id) && Objects.equals(numeroNota, that.numeroNota) && Objects.equals(serieNota, that.serieNota) && Objects.equals(descricaoObs, that.descricaoObs) && Objects.equals(valorTotal, that.valorTotal) && Objects.equals(valorDesconto, that.valorDesconto) && Objects.equals(valorIcms, that.valorIcms) && Objects.equals(dataCompra, that.dataCompra) && Objects.equals(pessoa, that.pessoa) && Objects.equals(contaPagar, that.contaPagar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numeroNota, serieNota, descricaoObs, valorTotal, valorDesconto, valorIcms, dataCompra, pessoa, contaPagar);
    }
}
