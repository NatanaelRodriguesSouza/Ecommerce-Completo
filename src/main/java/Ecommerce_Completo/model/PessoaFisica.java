package Ecommerce_Completo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "pessoa_fisica")
@PrimaryKeyJoinColumn(name = "id", foreignKey = @ForeignKey(name = "pf_pessoa_fk"))
public class PessoaFisica extends Pessoa {

    @Column(nullable = false, unique = true)
    private String cpf;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_nascimento")
    private Date dataNascimento;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
