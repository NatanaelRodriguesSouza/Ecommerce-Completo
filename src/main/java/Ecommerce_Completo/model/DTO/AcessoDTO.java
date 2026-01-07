package Ecommerce_Completo.model.DTO;

public class AcessoDTO {
    private Long id;
    private String descricao;

    public AcessoDTO() {
    }

    public AcessoDTO(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }
}
