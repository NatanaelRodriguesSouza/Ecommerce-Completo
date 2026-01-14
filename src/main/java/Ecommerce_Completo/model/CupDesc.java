package Ecommerce_Completo.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "cup_desc")
@SequenceGenerator(
        name = "seq_cup_desc",
        sequenceName = "seq_cup_desc",
        allocationSize = 1,
        initialValue = 1
)
public class CupDesc {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cup_desc")
    private Long id;

    @Column(name = "cod_desc", nullable = false)
    private String codDesc;

    @Column(name = "valor_real_desc", precision = 19, scale = 2)
    private BigDecimal valorRealDesc;

    @Column(name = "valor_porcent_desc", precision = 19, scale = 2)
    private BigDecimal valorPorcentDesc;

    @Column(name = "data_validade_cupom", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataValidadeCupom;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "empresa_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "cup_desc_empresa_fk")
    )
    private PessoaJuridica empresa;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodDesc() { return codDesc; }
    public void setCodDesc(String codDesc) { this.codDesc = codDesc; }

    public BigDecimal getValorRealDesc() { return valorRealDesc; }
    public void setValorRealDesc(BigDecimal valorRealDesc) { this.valorRealDesc = valorRealDesc; }

    public BigDecimal getValorPorcentDesc() { return valorPorcentDesc; }
    public void setValorPorcentDesc(BigDecimal valorPorcentDesc) { this.valorPorcentDesc = valorPorcentDesc; }

    public Date getDataValidadeCupom() { return dataValidadeCupom; }
    public void setDataValidadeCupom(Date dataValidadeCupom) { this.dataValidadeCupom = dataValidadeCupom; }

    public PessoaJuridica getEmpresa() { return empresa; }
    public void setEmpresa(PessoaJuridica empresa) { this.empresa = empresa; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CupDesc)) return false;
        CupDesc that = (CupDesc) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
