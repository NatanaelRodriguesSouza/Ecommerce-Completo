package Ecommerce_Completo.model.DTO;

import Ecommerce_Completo.util.validation.CNPJ;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class PessoaJuridicaDTO {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 120, message = "Nome deve ter entre 2 e 120 caracteres")
    private String nome;

    @Email(message = "Email inválido")
    @Size(max = 150, message = "Email deve ter no máximo 150 caracteres")
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Size(min = 8, max = 20, message = "Telefone deve ter entre 8 e 20 caracteres")
    private String telefone;

    @NotBlank(message = "CNPJ é obrigatório")
    @CNPJ
    private String cnpj;

    @Size(max = 30, message = "Inscrição estadual deve ter no máximo 30 caracteres")
    private String inscEstadual;

    @Size(max = 30, message = "Inscrição municipal deve ter no máximo 30 caracteres")
    private String inscMunicipal;

    @NotBlank(message = "Nome fantasia é obrigatório")
    @Size(max = 150, message = "Nome fantasia deve ter no máximo 150 caracteres")
    private String nomeFantasia;

    @NotBlank(message = "Razão social é obrigatória")
    @Size(max = 150, message = "Razão social deve ter no máximo 150 caracteres")
    private String razaoSocial;

    @NotBlank(message = "Categoria é obrigatória")
    @Size(max = 80, message = "Categoria deve ter no máximo 80 caracteres")
    private String categoria;

    private List<@Valid EnderecoDTO> enderecos = new ArrayList<>();

    public PessoaJuridicaDTO() {
    }

    public PessoaJuridicaDTO(
            Long id,
            String nome,
            String email,
            String telefone,
            String cnpj,
            String inscEstadual,
            String inscMunicipal,
            String nomeFantasia,
            String razaoSocial,
            String categoria,
            List<EnderecoDTO> enderecos
    ) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cnpj = cnpj;
        this.inscEstadual = inscEstadual;
        this.inscMunicipal = inscMunicipal;
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.categoria = categoria;
        this.enderecos = enderecos != null ? enderecos : new ArrayList<>();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getInscEstadual() { return inscEstadual; }
    public void setInscEstadual(String inscEstadual) { this.inscEstadual = inscEstadual; }

    public String getInscMunicipal() { return inscMunicipal; }
    public void setInscMunicipal(String inscMunicipal) { this.inscMunicipal = inscMunicipal; }

    public String getNomeFantasia() { return nomeFantasia; }
    public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }

    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public List<EnderecoDTO> getEnderecos() { return enderecos; }
    public void setEnderecos(List<EnderecoDTO> enderecos) {
        this.enderecos = enderecos != null ? enderecos : new ArrayList<>();
    }
}
