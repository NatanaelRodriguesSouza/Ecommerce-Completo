package Ecommerce_Completo.service;

import java.util.List;

import Ecommerce_Completo.projections.UsuarioSenhaVencidaView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import Ecommerce_Completo.repository.UsuarioRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class TrocaSenhaNotificacaoService {

    private final UsuarioRepository usuarioRepository;
    private final ServiceSendEmail serviceSendEmail;

    @Scheduled(cron = "0 0 11 * * *", zone = "America/Sao_Paulo")
    //@Scheduled(cron = "*/10 * * * * *", zone = "America/Sao_Paulo") // Teste
    public void notificarUserTrocaSenha() {

        List<UsuarioSenhaVencidaView> usuarios = usuarioRepository.usuarioSenhaVencidaView();

        if (usuarios == null || usuarios.isEmpty()) {
            log.info("Nenhum usuário com senha vencida para notificar.");
            return;
        }

        for (UsuarioSenhaVencidaView usuario : usuarios) {
            try {
                String html = montarMensagem(usuario);
                serviceSendEmail.enviarEmailHtml("Troca de senha", html, usuario.getLogin());
            } catch (Exception e) {
                log.error("Falha ao notificar troca de senha para: {}", usuario.getLogin(), e);
            }
        }

        log.info("Notificação de troca de senha disparada para {} usuário(s).", usuarios.size());
    }

    private String montarMensagem(UsuarioSenhaVencidaView usuario) {
        String nome = (usuario.getNome() != null && !usuario.getNome().isBlank())
                ? usuario.getNome()
                : "usuário";

        return new StringBuilder()
                .append("Olá, ").append(nome).append("<br/>")
                .append("Está na hora de trocar sua senha, já passou 90 dias de validade.<br/>")
                .toString();
    }
}
