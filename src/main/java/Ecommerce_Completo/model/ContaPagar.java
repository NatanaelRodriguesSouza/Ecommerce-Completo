package Ecommerce_Completo.model;

import Ecommerce_Completo.enums.StatusContaPagar;
import Ecommerce_Completo.enums.StatusContaReceber;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tb_contaPagar")
@SequenceGenerator(name = "seq_ContaPagar", sequenceName = "seq_ContaPagar", allocationSize = 1, initialValue = 1)
public class ContaPagar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ContaPagar")
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    private StatusContaPagar status;
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

    @ManyToOne
    @JoinColumn(name = "pessoaFornecedor_id" , nullable = false , foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name = "pessoaFornecedor_id"))
    private Pessoa pessoa_fornecedor;

    @ManyToOne
    @JoinColumn(name = "formaPagamento_id" , nullable = false , foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name = "pagamento_i"))
    private FormaPagamento pagamento;

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

    public StatusContaPagar getStatus() {
        return status;
    }

    public void setStatus( StatusContaPagar status) {
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
        ContaPagar that = (ContaPagar) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
