package com.fincon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fincon.Util.Util;
import com.fincon.dto.LancamentoDTO;
import com.fincon.dto.UserLancamentoMesAtualDTO;
import com.fincon.model.Email;

import java.util.List;

@Service
public class EnviaEmailBackupMensalService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LancamentoService lancamentoService;

    @Async
    @Scheduled(cron = "59 59 23 L * ?", zone = "America/Sao_Paulo") // Ultimo seg, min das 23h do ultimo dia do mês
    // @Scheduled(cron = "00 57 19 22 * ?", zone = "America/Sao_Paulo")
    public void enviaBackupMensal() {

        int pMesReferencia = Util.getMesAtual();
        int pAnoReferencia = Util.getAnoAtual();

        String pMesReferenciaExtenso = Util.getMesAtualExtenso();

        for (UserLancamentoMesAtualDTO usuario : usuarioService.findUserLancamentoMesAtual(pMesReferencia,
                pAnoReferencia)) {

            String titulo = "Fincon - Backup Mensal " + pMesReferenciaExtenso + "/" + pAnoReferencia;

            this.enviar(usuario.getEmail(), titulo, usuario.getNome(), pMesReferenciaExtenso,
                    pAnoReferencia, lancamentoService.findListMain(usuario.getId(), pMesReferencia, pAnoReferencia));
        }
    }

    public void enviar(String destinatario, String pTitulo, String pNomeUsuario, String pMesReferenciaExtenso,
            int pAnoReferencia, List<LancamentoDTO> pRelatorioMensal) {
        try {
            String response = new EnviaEmailService().enviaEmailAnexo(new Email(destinatario,
                    pTitulo, pNomeUsuario,
                    pRelatorioMensal, this.conteudoEmail(pNomeUsuario, pMesReferenciaExtenso, pAnoReferencia)));
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String conteudoEmail(String pNomeUsuario, String pMesReferenciaExtenso, int pAnoReferencia) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"pt-BR\">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "    <title>Backup Mensal</title>\n" +
                "    <style>\n" +
                "      html {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "      body {\n" +
                "        font-family: Roboto;\n" +
                "        line-height: 1.6;\n" +
                "        color: #444;\n" +
                "        background-color: #f4f4f4;\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "      }\n" +
                "      .lineContainer {\n" +
                "        width: 100%;\n" +
                "        height: 7px;\n" +
                "        background-color: #1461a9;\n" +
                "      }\n" +
                "      .container {\n" +
                "        max-width: 600px;\n" +
                "        margin: 20px auto;\n" +
                "        background-color: #fff;\n" +
                "        padding: 20px;\n" +
                "      }\n" +
                "      .header {\n" +
                "        text-align: center;\n" +
                "        padding-bottom: 20px;\n" +
                "      }\n" +
                "      .header h1 {\n" +
                "        color: #1461a9;\n" +
                "      }\n" +
                "      .content {\n" +
                "        text-align: left;\n" +
                "      }\n" +
                "      .content h2 {\n" +
                "        color: #1461a9;\n" +
                "      }\n" +
                "      .content p {\n" +
                "        margin: 10px 0;\n" +
                "      }\n" +
                "      .footer {\n" +
                "        text-align: center;\n" +
                "        padding-top: 20px;\n" +
                "        border-top: 1px solid #eaeaea;\n" +
                "      }\n" +
                "      .footer p {\n" +
                "        margin: 5px 0;\n" +
                "        color: #888;\n" +
                "      }\n" +
                "      .button {\n" +
                "        display: inline-block;\n" +
                "        margin-top: 20px;\n" +
                "        padding: 10px 20px;\n" +
                "        background-color: #1461a9;\n" +
                "        color: #fff;\n" +
                "        text-decoration: none;\n" +
                "        border-radius: 5px;\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <div class=\"lineContainer\"></div>\n" +
                "    <div class=\"container\">\n" +
                "      <div class=\"header\">\n" +
                "        <h1>Fincon</h1>\n" +
                "      </div>\n" +
                "      <div class=\"content\">\n" +
                "        <h2>Olá " + pNomeUsuario + ",</h2>\n" +
                "        <br />\n" +
                "        <p>Seu backup do mês de " + pMesReferenciaExtenso + " de " + pAnoReferencia
                + " já está pronto.</p>\n" +
                "        <br />        \n" +
                "        <br />\n" +
                "        <br />\n" +
                "        <br />\n" +
                "        <br />\n" +
                "        <p>\n" +
                "          Caso precise de ajuda, nossa equipe de suporte está à disposição para\n" +
                "          responder suas perguntas. Entre em contato conosco através do email\n" +
                "          <a href=\"mailto:email.fincon@gmail.com\">email.fincon@gmail.com</a>.\n" +
                "        </p>\n" +
                "        <p>\n" +
                "          Estamos ansiosos para ajudar você a alcançar suas metas financeiras!\n" +
                "        </p>\n" +
                "      </div>\n" +
                "      <div class=\"footer\">\n" +
                "        <p>Atenciosamente,</p>\n" +
                "        <p>Equipe Fincon</p>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>";
    }
}