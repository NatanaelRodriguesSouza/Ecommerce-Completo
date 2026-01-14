package Ecommerce_Completo.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "forma_pagamento")
@SequenceGenerator(
        name = "seq_forma_pagamento",
        sequenceName = "seq_forma_pagamento",
        allocationSize = 1,
        initialValue = 1
)
public class FormaPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_forma_pagamento")
    private Long id;

    @Column(name = "discricao", nullable = false)
    private String discricao;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "empresa_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "forma_pagamento_empresa_fk")
    )
    private PessoaJuridica empresa;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDiscricao() { return discricao; }
    public void setDiscricao(String discricao) { this.discricao = discricao; }

    public PessoaJuridica getEmpresa() { return empresa; }
    public void setEmpresa(PessoaJuridica empresa) { this.empresa = empresa; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FormaPagamento)) return false;
        FormaPagamento that = (FormaPagamento) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
