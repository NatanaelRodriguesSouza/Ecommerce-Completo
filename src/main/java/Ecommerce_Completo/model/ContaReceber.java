package Ecommerce_Completo.model;

import Ecommerce_Completo.enums.StatusContaReceber;
import Ecommerce_Completo.enums.TipoEndereco;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tb_contaReceber")
@SequenceGenerator(name = "seq_conta_Receber", sequenceName = "seq_conta_Receber", allocationSize = 1, initialValue = 1)
public class ContaReceber implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_conta_Receber")
    private Long id;
    @Column(nullable = false)
    private String descricao;
    @Enumerated(EnumType.STRING)
    private StatusContaReceber status;
    @Temporal(TemporalType.DATE)
    private Date dataVencimento;
    @Temporal(TemporalType.DATE)
    private Date dataPagamento;
    @Column(nullable = false)
    private BigDecimal valorTotal;
    private BigDecimal ValorDesconto;

    @ManyToOne
    @JoinColumn(name = "pessoa_id" , nullable = false , foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name = "pessoa_id"))
    private Pessoa pessoa;

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

    public StatusContaReceber getStatus() {
        return status;
    }

    public void setStatus(StatusContaReceber status) {
        this.status = status;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorDesconto() {
        return ValorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        ValorDesconto = valorDesconto;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ContaReceber that = (ContaReceber) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
