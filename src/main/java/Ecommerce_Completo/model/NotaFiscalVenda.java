package Ecommerce_Completo.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "nota_fiscal_venda")
@SequenceGenerator(name = "seq_nota_fiscal_venda", sequenceName = "seq_nota_fiscal_venda", allocationSize = 1, initialValue = 1)
public class NotaFiscalVenda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_nota_fiscal_venda")
    private Long id;

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false)
    private String serie;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private String chave;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "empresa_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "nota_fiscal_venda_empresa_fk")
    )
    private PessoaJuridica empresa;

    @Column(columnDefinition = "text", nullable = false)
    private String xml;

    @Column(columnDefinition = "text", nullable = false)
    private String pdf;

    @OneToOne(mappedBy = "notaFiscalVenda", fetch = FetchType.LAZY)
    private VendaCompraLojaVirtual vendaCompraLojaVirtual;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getSerie() { return serie; }
    public void setSerie(String serie) { this.serie = serie; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getChave() { return chave; }
    public void setChave(String chave) { this.chave = chave; }

    public PessoaJuridica getEmpresa() { return empresa; }
    public void setEmpresa(PessoaJuridica empresa) { this.empresa = empresa; }

    public String getXml() { return xml; }
    public void setXml(String xml) { this.xml = xml; }

    public String getPdf() { return pdf; }
    public void setPdf(String pdf) { this.pdf = pdf; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotaFiscalVenda)) return false;
        NotaFiscalVenda that = (NotaFiscalVenda) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
