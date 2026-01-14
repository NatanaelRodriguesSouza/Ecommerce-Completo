package Ecommerce_Completo.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "status_rastreio")
@SequenceGenerator(
        name = "seq_status_rastreio",
        sequenceName = "seq_status_rastreio",
        allocationSize = 1,
        initialValue = 1
)
public class StatusRastreio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_status_rastreio")
    private Long id;

    @Column(name = "centro_distribuicao")
    private String centroDeDestibuicao;

    private String cidade;

    @Column(name = "estado")
    private String estado;

    private String status;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "venda_compra_loja_virt_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "status_rastreio_venda_fk")
    )
    private VendaCompraLojaVirtual vendaCompraLojaVirtual;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "empresa_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "status_rastreio_empresa_fk")
    )
    private PessoaJuridica empresa;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCentroDeDestibuicao() { return centroDeDestibuicao; }
    public void setCentroDeDestibuicao(String centroDeDestibuicao) { this.centroDeDestibuicao = centroDeDestibuicao; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public VendaCompraLojaVirtual getVendaCompraLojaVirtual() { return vendaCompraLojaVirtual; }
    public void setVendaCompraLojaVirtual(VendaCompraLojaVirtual vendaCompraLojaVirtual) { this.vendaCompraLojaVirtual = vendaCompraLojaVirtual; }

    public PessoaJuridica getEmpresa() { return empresa; }
    public void setEmpresa(PessoaJuridica empresa) { this.empresa = empresa; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatusRastreio)) return false;
        StatusRastreio that = (StatusRastreio) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
