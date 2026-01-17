package Ecommerce_Completo.repository;

import Ecommerce_Completo.model.Usuario;
import Ecommerce_Completo.projections.UserDetailsProjection;
import Ecommerce_Completo.projections.UsuarioSenhaVencidaView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    Usuario findByLogin(String email);
    boolean existsByLogin(String login);

    @Query(nativeQuery = true, value = """
    SELECT 
        u.login AS username,
        u.senha AS password,
        a.id AS roleId,
        a.descricao AS authority
    FROM usuario u
    LEFT JOIN usuarios_acesso ua ON u.id = ua.usuario_id
    LEFT JOIN acesso a ON a.id = ua.acesso_id
    WHERE u.login = :email
""")
    List<UserDetailsProjection> searchUserAndRolesByEmail(@Param("email") String email);

    @Query(value = """
   select u.login as login, p.nome as nome
   from usuario u
   join pessoa p on p.id = u.pessoa_id
   where u.data_atual_senha <= current_date - 90
""", nativeQuery = true)
    List<UsuarioSenhaVencidaView> usuarioSenhaVencidaView();

}
