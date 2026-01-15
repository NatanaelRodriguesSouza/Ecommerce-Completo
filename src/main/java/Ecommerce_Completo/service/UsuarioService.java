package Ecommerce_Completo.service;

import Ecommerce_Completo.model.Acesso;
import Ecommerce_Completo.model.Pessoa;
import Ecommerce_Completo.model.PessoaJuridica;
import Ecommerce_Completo.model.Usuario;
import Ecommerce_Completo.repository.AcessoRepository;
import Ecommerce_Completo.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final AcessoRepository acessoRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          AcessoRepository acessoRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.acessoRepository = acessoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario criarUsuarioParaPessoa(Pessoa pessoa, PessoaJuridica empresa, String loginEmail) {
        if (usuarioRepository.existsByLogin(loginEmail)) {
            throw new IllegalArgumentException("Já existe usuário com login: " + loginEmail);
        }

        Usuario u = new Usuario();
        u.setLogin(loginEmail);
        u.setSenha(passwordEncoder.encode("123"));
        u.setDataAtualSenha(new Date());

        u.setPessoa(pessoa);
        u.setEmpresa(empresa);

        Acesso roleUser = acessoRepository.findByDescricao("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("Acesso ROLE_USER não cadastrado."));
        u.addAcesso(roleUser);

        return usuarioRepository.save(u);
    }
}
