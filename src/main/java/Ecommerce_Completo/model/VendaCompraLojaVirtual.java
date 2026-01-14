package Ecommerce_Completo.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "vd_cp_loja_virt")
@SequenceGenerator(name = "seq_vd_cp_loja_virt", sequenceName = "seq_vd_cp_loja_virt", allocationSize = 1, initialValue = 1)
public class VendaCompraLojaVirtual implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_vd_cp_loja_virt")
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "pessoa_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "venda_pessoa_fk")
    )
    private Pessoa pessoa;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "endereco_entrega_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "venda_endereco_entrega_fk")
    )
    private Endereco enderecoEntrega;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "endereco_cobranca_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "venda_endereco_cobranca_fk")
    )
    private Endereco enderecoCobranca;

    @Column(name = "valor_total", nullable = false, precision = 19, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "valor_desconto", precision = 19, scale = 2)
    private BigDecimal valorDesconto;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "forma_pagamento_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "venda_forma_pagamento_fk")
    )
    private FormaPagamento formaPagamento;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "nota_fiscal_venda_id",
            foreignKey = @ForeignKey(name = "venda_nota_fiscal_venda_fk")
    )
    private NotaFiscalVenda notaFiscalVenda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "cupom_desc_id",
            foreignKey = @ForeignKey(name = "venda_cupom_desc_fk")
    )
    private CupDesc cupDesc;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "empresa_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "venda_empresa_fk")
    )
    private PessoaJuridica empresa;

    @Column(name = "valor_fret", nullable = false, precision = 19, scale = 2)
    private BigDecimal valorFret;

    @Column(name = "dia_entrega", nullable = false)
    private Integer diaEntrega;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_venda", nullable = false)
    private Date dataVenda;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_entrega", nullable = false)
    private Date dataEntrega;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Pessoa getPessoa() { return pessoa; }
    public void setPessoa(Pessoa pessoa) { this.pessoa = pessoa; }

    public Endereco getEnderecoEntrega() { return enderecoEntrega; }
    public void setEnderecoEntrega(Endereco enderecoEntrega) { this.enderecoEntrega = enderecoEntrega; }

    public Endereco getEnderecoCobranca() { return enderecoCobranca; }
    public void setEnderecoCobranca(Endereco enderecoCobranca) { this.enderecoCobranca = enderecoCobranca; }

    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

    public BigDecimal getValorDesconto() { return valorDesconto; }
    public void setValorDesconto(BigDecimal valorDesconto) { this.valorDesconto = valorDesconto; }

    public FormaPagamento getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(FormaPagamento formaPagamento) { this.formaPagamento = formaPagamento; }

    public NotaFiscalVenda getNotaFiscalVenda() { return notaFiscalVenda; }
    public void setNotaFiscalVenda(NotaFiscalVenda notaFiscalVenda) { this.notaFiscalVenda = notaFiscalVenda; }

    public CupDesc getCupDesc() { return cupDesc; }
    public void setCupDesc(CupDesc cupDesc) { this.cupDesc = cupDesc; }

    public PessoaJuridica getEmpresa() { return empresa; }
    public void setEmpresa(PessoaJuridica empresa) { this.empresa = empresa; }

    public BigDecimal getValorFret() { return valorFret; }
    public void setValorFret(BigDecimal valorFret) { this.valorFret = valorFret; }

    public Integer getDiaEntrega() { return diaEntrega; }
    public void setDiaEntrega(Integer diaEntrega) { this.diaEntrega = diaEntrega; }

    public Date getDataVenda() { return dataVenda; }
    public void setDataVenda(Date dataVenda) { this.dataVenda = dataVenda; }

    public Date getDataEntrega() { return dataEntrega; }
    public void setDataEntrega(Date dataEntrega) { this.dataEntrega = dataEntrega; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VendaCompraLojaVirtual)) return false;
        VendaCompraLojaVirtual that = (VendaCompraLojaVirtual) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
