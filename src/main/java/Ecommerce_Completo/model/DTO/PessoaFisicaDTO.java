package Ecommerce_Completo.model.DTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PessoaFisicaDTO {

    private Long id;
    private String nome;
    private String email;
    private String telefone;

    private String cpf;
    private Date dataNascimento;

    private Long empresaId;
    private List<Long> enderecosIds = new ArrayList<>();

    public PessoaFisicaDTO() {
    }

    public PessoaFisicaDTO(Long id, String nome, String email, String telefone, String cpf, Date dataNascimento, Long empresaId, List<Long> enderecosIds) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.empresaId = empresaId;
        this.enderecosIds = enderecosIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

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

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    public List<Long> getEnderecosIds() {
        return enderecosIds;
    }

    public void setEnderecosIds(List<Long> enderecosIds) {
        this.enderecosIds = enderecosIds;
    }
}