# API Fincon

Fincon é um sistema para controle financeiro que desenvolvi para me auxiliar no balanço financeiro do mês. Este software tem me ajudado a fazer controle fiscal e também a me desenvolver mais nas tecnologias utilizadas.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3**
- **Spring Security**
- **Swagger 3**
- **PostgreSQL**
- **Docker**
- **Render**
- **Better Stack**

## Hospedagem

- **API**: [Render](https://api-fincon-7276.onrender.com/)
- **Banco de Dados**: [Supabase](https://supabase.com/)

## Metodologia de Desenvolvimento

Estamos utilizando metodologias ágeis para o desenvolvimento do Fincon, especificamente o Kanban. Você pode acompanhar o progresso do projeto no nosso quadro do Trello: [Fincon Trello Board](https://trello.com/b/RUn63nJg/fincon).

## Funcionalidades

- Controle de receitas e despesas
- Geração de relatórios financeiros
- Análises de Dashboards
- Documentação da API com Swagger

## Documentação da API

A documentação da API está disponível através do Swagger na seguinte URL: `https://api-fincon-7276.onrender.com/swagger-ui.html`

## Executar o Projeto

```bash
# limpar e construir
    mvn clean install
```

```bash
# executar
    cmd /C ".....java\17\bin\java.exe @C:\Users\....\AppData\Local\Temp\.argfile com.fincon.FinconApplication "
```

## Subir para hub.docker.com

```bash
    docker login
```

```bash
    docker build -t fincon:latest .
```

```bash
    docker tag fincon:latest alexsouzasilvax/fincon:latest
```

```bash
    docker push alexsouzasilvax/fincon:latest
```

## Deploy no Render

 - Acessar painel dashboard do [Render](https://dashboard.render.com/web/srv-cr28kp3tq21c73fmf3ag/logs) 
 - Manual Deploy --> Deploy latest reference

## Contribuição

Se você deseja contribuir com este projeto, sinta-se à vontade para abrir um pull request ou relatar um problema.

## Licença

Este projeto está licenciado sob os termos da [Licença MIT](LICENSE).

## Contato

Se você tiver alguma dúvida ou sugestão, sinta-se à vontade para entrar em contato.

Email: alexsouzasilvax@gmail.com

---

_Este projeto foi criado para uso pessoal e como uma forma de aprimorar minhas habilidades técnicas._
