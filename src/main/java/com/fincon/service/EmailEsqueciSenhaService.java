package com.fincon.service;

import org.springframework.stereotype.Service;
import com.fincon.model.EmailSimples;

@Service
public class EmailEsqueciSenhaService {

    public void enviar(String destinatario, String usuario, String senha) {
        try {
            String response = new EnviaEmailService().enviaEmailSimples(new EmailSimples(destinatario,
                    "Fincon - Novas Credenciais", null,
                    this.conteudoEmail(usuario, senha)));
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String conteudoEmail(String usuario, String senha) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"pt-BR\">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "    <title>Esqueci senha</title>\n" +
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
                "      .divNovasCredenciais {\n" +
                "        display: flex;\n" +
                "        flex-direction: row;\n" +
                "        justify-content: start;\n" +
                "        color: #444;\n" +
                "      }\n" +
                "      .pNovasCredenciais {\n" +
                "        color: #1461a9;\n" +
                "        font-weight: 800;\n" +
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
                "        <h2>Olá Alex,</h2>\n" +
                "        <p>Vimos que você está precisando de ajuda para acessar sua conta.</p>\n" +
                "        <br />\n" +
                "        <p>Abaixo estão suas novas credenciais</p>\n" +
                "        <br />\n" +
                "        <div class=\"divNovasCredenciais\">\n" +
                "          <p>usuário:&nbsp;</p>\n" +
                "          <p class=\"pNovasCredenciais\">" + usuario + "</p>\n" +
                "        </div>\n" +
                "        <div class=\"divNovasCredenciais\">\n" +
                "          <p>senha:&nbsp;</p>\n" +
                "          <p class=\"pNovasCredenciais\">" + senha + "</p>\n" +
                "        </div>\n" +
                "        <br />\n" +
                "        <h4>Lembre-se quando fazer login novamente altere sua senha</h4>\n" +
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
