# Product API

API REST para gerenciamento de produtos com Spring Boot, Spring Web, Spring Data JPA, PostgreSQL, validacao de entrada e links HATEOAS.

## Estrutura do projeto

O repositorio possui o projeto Maven dentro da pasta:

```text
test/test
```

O arquivo `pom.xml` da aplicacao esta em:

[`test/test/pom.xml`](/C:/Users/Selliny/Api-de-Produtos-/test/test/pom.xml)

Pacote raiz da aplicacao:

```text
com.selliny.productapi
```

## Tecnologias

- Java 17
- Spring Boot 3.4.5
- Spring Web
- Spring Data JPA
- Spring HATEOAS
- Swagger / OpenAPI
- PostgreSQL
- Maven
- Lombok

## Requisitos

- JDK 17 ou superior
- Maven instalado no ambiente
- PostgreSQL em execucao

## Configuracao

As configuracoes atuais da aplicacao estao em:

[`test/test/src/main/resources/application.yml`](/C:/Users/Selliny/Api-de-Produtos-/test/test/src/main/resources/application.yml)

Configuracao padrao:

```yml
spring:
  datasource:
    url: ${DB_URL:jdbc:postgresql://localhost:5432/productdb}
    username: ${DB_USERNAME:postgres}
    password: ${DB_PASSWORD:postgres}
```

Voce pode sobrescrever esses valores com variaveis de ambiente:

```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/productdb"
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="sua_senha"
```

Antes de executar, ajuste esses valores conforme o seu ambiente.

## Como executar

Entre na pasta correta do projeto Maven:

```powershell
cd test/test
```

Execute a aplicacao com:

```powershell
mvn spring-boot:run
```

Ou gere o build com:

```powershell
mvn clean install
```

Documentacao interativa da API:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Como executar com Docker

O projeto possui:

- `test/test/Dockerfile` para a API
- `docker-compose.yml` na raiz para subir API, PostgreSQL e pgAdmin

Suba tudo com:

```powershell
docker compose up --build
```

Servicos disponiveis:

- API: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- PostgreSQL: `localhost:5432`
- pgAdmin: `http://localhost:5050`

Credenciais padrao do PostgreSQL:

- database: `productdb`
- user: `postgres`
- password: `postgres`

Credenciais padrao do pgAdmin:

- email: `admin@admin.com`
- password: `admin123`

Para conectar o pgAdmin ao banco:

- host: `postgres`
- port: `5432`
- database: `productdb`
- user: `postgres`
- password: `postgres`

## Como testar

Para rodar os testes:

```powershell
cd test/test
mvn test
```

Observacao: se o Java ativo for 8 ou 11, o build falhara porque o projeto exige Java 17.

## Modelo de dados

Produto:

```json
{
  "id": 1,
  "name": "Mouse Gamer",
  "price": 99.9
}
```

## Validacoes

O payload de entrada exige:

- `name`: obrigatorio, nao vazio, com 3 a 120 caracteres
- `price`: obrigatorio e maior que zero

Quando a validacao falha, a API responde com estrutura padronizada contendo:

- `timestamp`
- `status`
- `error`
- `message`
- `path`
- `validationErrors`

## Endpoints

### Listar produtos

`GET /products`

Resposta:

```json
[
  {
    "id": 1,
    "name": "Mouse",
    "price": 79.9,
    "_links": {
      "self": {
        "href": "http://localhost:8080/products/1"
      }
    }
  }
]
```

### Buscar produto por ID

`GET /products/{id}`

Resposta:

```json
{
  "id": 1,
  "name": "Mouse",
  "price": 79.9,
  "_links": {
    "products": {
      "href": "http://localhost:8080/products"
    }
  }
}
```

### Criar produto

`POST /products`

Payload:

```json
{
  "name": "Teclado",
  "price": 120.0
}
```

### Atualizar produto

`PUT /products/{id}`

Payload:

```json
{
  "name": "Teclado Mecanico",
  "price": 180.0
}
```

### Remover produto

`DELETE /products/{id}`

Resposta esperada:

- `204 No Content`

## Organizacao do codigo

- `controller`: endpoints HTTP e tratamento de requisicoes
- `config`: configuracao do Swagger/OpenAPI
- `services`: regras de negocio
- `repository`: acesso ao banco com JPA
- `models`: entidades persistidas

## Testes

- `ProductsControllerTest`: testa a camada web com `MockMvc`
- `ProductsServiceImplTest`: testa regras da service com Mockito
- `ProductsRepositoryTest`: testa persistencia com JPA e H2
- `ProductsIntegrationTest`: testa fluxo integrado da API com H2 em memoria

## Observacoes

- O schema do banco e atualizado automaticamente por `spring.jpa.hibernate.ddl-auto=update`
- A pasta `target/` contem arquivos gerados no build e nao deve ser tratada como codigo-fonte
- O artefato Maven atual do projeto e `com.selliny:product-api`
