package Ecommerce_Completo.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "cupDesc")
@SequenceGenerator(name = "seq_cupDesc" , sequenceName = "seq_cupDesc",allocationSize = 1,initialValue = 1)
public class CupDesc {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cupDesc")
    private Long id;
    @Column(nullable = false)
    private String codDesc;

    private BigDecimal valorRealDesc;

    private BigDecimal valorPorcentDesc;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataValidadeCupom;

    @ManyToOne
    @JoinColumn(name = "empresa_id" , nullable = false ,foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,name = "pessoa_fk"))
    private Pessoa empresa;

    public Date getDataValidadeCupom() {
        return dataValidadeCupom;
    }

    public void setDataValidadeCupom(Date dataValidadeCupom) {
        this.dataValidadeCupom = dataValidadeCupom;
    }

    public Pessoa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Pessoa empresa) {
        this.empresa = empresa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodDesc() {
        return codDesc;
    }

    public void setCodDesc(String codDesc) {
        this.codDesc = codDesc;
    }

    public BigDecimal getValorRealDesc() {
        return valorRealDesc;
    }

    public void setValorRealDesc(BigDecimal valorRealDesc) {
        this.valorRealDesc = valorRealDesc;
    }

    public BigDecimal getValorPorcentDesc() {
        return valorPorcentDesc;
    }

    public void setValorPorcentDesc(BigDecimal valorPorcentDesc) {
        this.valorPorcentDesc = valorPorcentDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CupDesc cupDesc = (CupDesc) o;
        return Objects.equals(id, cupDesc.id) && Objects.equals(codDesc, cupDesc.codDesc) && Objects.equals(valorRealDesc, cupDesc.valorRealDesc) && Objects.equals(valorPorcentDesc, cupDesc.valorPorcentDesc) && Objects.equals(dataValidadeCupom, cupDesc.dataValidadeCupom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codDesc, valorRealDesc, valorPorcentDesc, dataValidadeCupom);
    }
}
