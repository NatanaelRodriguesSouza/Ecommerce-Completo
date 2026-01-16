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
    private final ServiceSendEmail serviceSendEmail;

    private static final String SENHA_PADRAO = "123";

    public UsuarioService(UsuarioRepository usuarioRepository,
                          AcessoRepository acessoRepository,
                          PasswordEncoder passwordEncoder,
                          ServiceSendEmail serviceSendEmail) {
        this.usuarioRepository = usuarioRepository;
        this.acessoRepository = acessoRepository;
        this.passwordEncoder = passwordEncoder;
        this.serviceSendEmail = serviceSendEmail;
    }

    public Usuario criarUsuarioParaPessoa(Pessoa pessoa, PessoaJuridica empresa, String loginEmail) {

        if (usuarioRepository.existsByLogin(loginEmail)) {
            throw new IllegalArgumentException("Já existe usuário com login: " + loginEmail);
        }

        Usuario u = new Usuario();
        u.setLogin(loginEmail);

        u.setSenha(passwordEncoder.encode(SENHA_PADRAO));
        u.setDataAtualSenha(new Date());

        u.setPessoa(pessoa);
        u.setEmpresa(empresa);

        Acesso roleUser = acessoRepository.findByDescricao("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("Acesso ROLE_USER não cadastrado."));
        u.addAcesso(roleUser);

        Usuario usuarioSalvo = usuarioRepository.save(u);
        enviarEmailBoasVindasComAcesso(loginEmail, SENHA_PADRAO);

        return usuarioSalvo;
    }

    private void enviarEmailBoasVindasComAcesso(String emailDestino, String senha) {
        String assunto = "Seu acesso foi criado";

        String mensagemHtml = """
                <h2>Bem-vindo!</h2>
                <p>Seu usuário foi criado com sucesso.</p>
                <p><b>Login:</b> %s</p>
                <p><b>Senha:</b> %s</p>
                <p>Recomendamos trocar a senha no primeiro acesso.</p>
                """.formatted(emailDestino, senha);

        try {
            serviceSendEmail.enviarEmailHtml(assunto, mensagemHtml, emailDestino);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
