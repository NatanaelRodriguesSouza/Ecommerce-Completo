package Ecommerce_Completo.model.DTO;

import Ecommerce_Completo.enums.TipoEndereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EnderecoDTO {

    private Long id;

    @NotBlank(message = "Rua/Logradouro é obrigatório.")
    private String ruaLogra;

    @NotBlank(message = "CEP é obrigatório.")
    private String cep;

    @NotBlank(message = "Número é obrigatório.")
    private String numero;

    private String complemento;

    @NotBlank(message = "Bairro é obrigatório.")
    private String bairro;

    @NotBlank(message = "UF é obrigatório.")
    private String uf;

    @NotBlank(message = "Cidade é obrigatória.")
    private String cidade;

    @NotNull(message = "Tipo de endereço é obrigatório.")
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

    public TipoEndereco getTipoEndereco() { return tipoEndereco; }
    public void setTipoEndereco(TipoEndereco tipoEndereco) { this.tipoEndereco = tipoEndereco; }
}
