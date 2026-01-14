package Ecommerce_Completo.model;

import Ecommerce_Completo.enums.TipoEndereco;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "endereco")
@SequenceGenerator(name = "seq_endereco", sequenceName = "seq_endereco", allocationSize = 1, initialValue = 1)
public class Endereco implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_endereco")
    private Long id;

    @Column(name = "rua_logra", nullable = false)
    private String ruaLogra;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = false)
    private String numero;

    private String complemento;

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false)
    private String uf;

    @Column(nullable = false)
    private String cidade;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "pessoa_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "endereco_pessoa_fk")
    )
    private Pessoa pessoa;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "empresa_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "endereco_empresa_fk")
    )
    private PessoaJuridica empresa;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_endereco", nullable = false)
    private TipoEndereco tipoEndereco;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRuaLogra() { return ruaLogra; }
    public void setRuaLogra(String ruaLogra) { this.ruaLogra = ruaLogra; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public Pessoa getPessoa() { return pessoa; }
    public void setPessoa(Pessoa pessoa) { this.pessoa = pessoa; }

    public PessoaJuridica getEmpresa() { return empresa; }
    public void setEmpresa(PessoaJuridica empresa) { this.empresa = empresa; }

    public TipoEndereco getTipoEndereco() { return tipoEndereco; }
    public void setTipoEndereco(TipoEndereco tipoEndereco) { this.tipoEndereco = tipoEndereco; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Endereco)) return false;
        Endereco that = (Endereco) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
