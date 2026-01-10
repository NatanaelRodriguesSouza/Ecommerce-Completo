package Ecommerce_Completo.service;

import Ecommerce_Completo.model.Acesso;
import Ecommerce_Completo.model.Usuario;
import Ecommerce_Completo.projections.UserDetailsProjection;
import Ecommerce_Completo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceDetailsService implements UserDetailsService {
    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> result = repository.searchUserAndRolesByEmail(username);
        if(result.isEmpty()){
            throw new UsernameNotFoundException("User not  found");
        }
        Usuario user = new Usuario();
        user.setLogin(result.get(0).getUsername());
        user.setSenha(result.get(0).getPassword());
        for(UserDetailsProjection projection : result){
            user.addAcesso(new Acesso(projection.getRoleId(),projection.getAuthority()));
        }
        return user;
    }
}
