package Ecommerce_Completo.model;

import jakarta.persistence.*;

import javax.print.attribute.standard.MediaSize;
import java.util.Objects;

@Entity
@Table(name = "formaPagamento")
@SequenceGenerator(sequenceName = "seq_formaPagamento" , name = "seq_formaPagamento", allocationSize = 1 , initialValue = 1)
public class FormaPagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "seq_formaPagamento")
    private Long id;
    @Column(nullable = false)
    private String discricao;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiscricao() {
        return discricao;
    }

    public void setDiscricao(String discricao) {
        this.discricao = discricao;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FormaPagamento that = (FormaPagamento) o;
        return Objects.equals(id, that.id) && Objects.equals(discricao, that.discricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, discricao);
    }
}
