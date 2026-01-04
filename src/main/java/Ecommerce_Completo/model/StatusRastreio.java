package Ecommerce_Completo.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tb_statusRastreio")
@SequenceGenerator(name = "seq_StatusRastreio" , sequenceName = "seq_StatusRastreio",allocationSize = 1,initialValue = 1)
public class StatusRastreio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_StatusRastreio")
    private Long id;

    private String centroDeDestibuicao;

    private String cidade;

    private String estado;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCentroDeDestibuicao() {
        return centroDeDestibuicao;
    }

    public void setCentroDeDestibuicao(String centroDeDestibuicao) {
        this.centroDeDestibuicao = centroDeDestibuicao;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StatusRastreio that = (StatusRastreio) o;
        return Objects.equals(id, that.id) && Objects.equals(centroDeDestibuicao, that.centroDeDestibuicao) && Objects.equals(cidade, that.cidade) && Objects.equals(estado, that.estado) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, centroDeDestibuicao, cidade, estado, status);
    }
}
