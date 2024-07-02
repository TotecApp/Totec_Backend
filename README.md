# Totec

Totec é um aplicativo dedicado a receitas. É possível achar as mais variadas receitas em seu banco de dados e adicionar novas você mesmo.

Aquelas que você mais gostar, você pode salvar em sua lista de favoritos.

As receitas contam também com tags que facilitam a busca por receitas de um determinado tipo.

Como framework para o back-end da aplicação, é utilizado o KTor. Como banco de dados, é utilizado o PostgreSQL.

## Como rodar o projeto

Para rodar o projeto, é necessário ter o gradle e o PostgreSQL instalados.

### Configuração do banco de dados

Para configurar o banco de dados, é necessário criar um banco de dados no PostgreSQL. 

Após escolhido o nome do banco de dados, o usuário e a senha, é necessário criar a seguinte variável de ambiente:

```
DATABASE_URL=postgres://Usuario:Senha@localhost:5432/NomeDoBancoDeDados
```
Dentro do IntelliJ, é possível criar variáveis de ambiente em Run/Debug Configurations -> Environment Variables.

### Rodando o projeto

Para rodar o projeto, basta executar o comando:

```
gradle run
```

