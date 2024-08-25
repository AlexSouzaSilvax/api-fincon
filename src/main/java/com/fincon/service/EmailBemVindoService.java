package com.fincon.service;

import org.springframework.stereotype.Service;

import com.fincon.model.Email;

@Service
public class EmailBemVindoService {

    public void enviar(String destinatario, String nomeDestinatario) {
        try {
            // String response = new EnviaEmailService().enviaEmail(new Email(destinatario,
            new EnviaEmailService().enviaEmail(new Email(destinatario,
                    "Bem-vindo ao Fincon - Seu Novo Sistema de Controle Financeiro!", nomeDestinatario,
                    null, this.conteudoEmail(nomeDestinatario)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String conteudoEmail(String nomeDestinatario) {
        return "<!DOCTYPE html>\n<html lang=\"pt-BR\">\n  <head>\n    <meta charset=\"UTF-8\" />\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n    <title>Bem-vindo ao Fincon</title>\n    <style>\n      html {\n        width: 100%;\n      }\n      body {\n        font-family: Roboto;\n        line-height: 1.6;\n        color: #444;\n        background-color: #f4f4f4;\n        margin: 0;\n        padding: 0;\n      }\n      .lineContainer {\n        width: 100%;\n        height: 7px;\n        background-color: #1461a9;\n      }\n      .container {\n        max-width: 600px;\n        margin: 20px auto;\n        background-color: #fff;\n        padding: 20px;\n      }\n      .header {\n        text-align: center;\n        padding-bottom: 20px;\n      }\n      .header h1 {\n        color: #1461a9;\n      }\n      .content {\n        text-align: left;\n      }\n      .content h2 {\n        color: #1461a9;\n      }\n      .content p {\n        margin: 10px 0;\n      }\n      .footer {\n        text-align: center;\n        padding-top: 20px;\n        border-top: 1px solid #eaeaea;\n      }\n      .footer p {\n        margin: 5px 0;\n        color: #888;\n      }\n      .button {\n        display: inline-block;\n        margin-top: 20px;\n        padding: 10px 20px;\n        background-color: #1461a9;\n        color: #fff;\n        text-decoration: none;\n        border-radius: 5px;\n      }\n    </style>\n  </head>\n  <body>\n    <div class=\"lineContainer\"></div>\n    <div class=\"container\">\n      <div class=\"header\">\n        <h1>Bem-vindo ao Fincon</h1>\n      </div>\n      <div class=\"content\">\n        <h2>Olá "
                + nomeDestinatario
                + ",</h2>\n        <p>Seja bem-vindo ao Fincon!</p>\n        <p>Estamos muito felizes em ter você como parte da nossa comunidade.</p>\n        <p>\n          O Fincon é um sistema de controle financeiro projetado para ajudar\n          você a gerenciar suas finanças de maneira simples e eficiente.\n        </p>\n        <p>Aqui estão algumas das funcionalidades que você pode explorar:</p>\n        <ul>\n          <li>\n            <strong>Controle de Despesas e Receitas:</strong> Registre todas as\n            suas transações financeiras com facilidade.\n          </li>\n          <li>\n            <strong>Relatórios Personalizados:</strong> Acesse relatórios\n            detalhados para monitorar seu progresso financeiro.\n          </li>\n          <li>\n            <strong>Planejamento Financeiro:</strong> Defina metas financeiras e\n            acompanhe seu cumprimento.\n          </li>\n          <li>\n            <strong>Segurança:</strong> Sua segurança é nossa prioridade.\n            Utilizamos as mais recentes tecnologias para garantir que seus dados\n            estejam protegidos.\n          </li>\n        </ul>\n        <p>Para começar, siga os passos abaixo:</p>\n        <ol>\n          <li>\n            Acesse o nosso sistema em\n            <a href=\"https://fincon.onrender.com/\"\n              >[https://fincon.onrender.com/]</a\n            >.\n          </li>\n          <li>Faça login com suas credenciais.</li>\n          <li>\n            Explore as funcionalidades e comece a gerenciar suas finanças!\n          </li>\n        </ol>\n        <p>\n          Caso precise de ajuda, nossa equipe de suporte está à disposição para\n          responder suas perguntas. Entre em contato conosco através do email\n          <a href=\"mailto:email.fincon@gmail.com\">email.fincon@gmail.com</a>.\n        </p>\n        <p>\n          Estamos ansiosos para ajudar você a alcançar suas metas financeiras!\n        </p>\n      </div>\n      <div class=\"footer\">\n        <p>Atenciosamente,</p>\n        <p>Equipe Fincon</p>\n      </div>\n    </div>\n  </body>\n</html>";
    }

}
