package Ecommerce_Completo.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "usuario")
@SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario", allocationSize = 1, initialValue = 1)
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
    private Long id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String senha;

    @Column(name = "data_atual_senha", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataAtualSenha;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuarios_acesso",
            uniqueConstraints = @UniqueConstraint(
                    columnNames = {"usuario_id", "acesso_id"},
                    name = "unique_acesso_user"
            ),
            joinColumns = @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "usuario_fk")),
            inverseJoinColumns = @JoinColumn(name = "acesso_id", foreignKey = @ForeignKey(name = "acesso_fk"))
    )
    private List<Acesso> acessos = new ArrayList<>();

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_id", nullable = false, foreignKey = @ForeignKey(name = "usuario_pessoa_fk"))
    private Pessoa pessoa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", foreignKey = @ForeignKey(name = "usuario_empresa_fk"))
    private PessoaJuridica empresa;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return acessos; }

    @Override
    public String getUsername() { return login; }

    @Override
    public String getPassword() { return senha; }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    public void addAcesso(Acesso acesso) { acessos.add(acesso); }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDataAtualSenha() {
        return dataAtualSenha;
    }

    public void setDataAtualSenha(Date dataAtualSenha) {
        this.dataAtualSenha = dataAtualSenha;
    }

    public List<Acesso> getAcessos() {
        return acessos;
    }

    public void setAcessos(List<Acesso> acessos) {
        this.acessos = acessos;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public PessoaJuridica getEmpresa() {
        return empresa;
    }

    public void setEmpresa(PessoaJuridica empresa) {
        this.empresa = empresa;
    }
}
