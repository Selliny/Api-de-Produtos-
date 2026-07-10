# API de Produtos

Aplicacao REST simples para cadastro de produtos com Spring Boot, Spring Web, Spring Data JPA, PostgreSQL, validacao de entrada e links HATEOAS.

## Estrutura do projeto

O repositorio possui o projeto Maven dentro da pasta:

```text
test/test
```

O arquivo `pom.xml` da aplicacao esta em:

[`test/test/pom.xml`](/C:/Users/Selliny/Api-de-Produtos-/test/test/pom.xml)

## Tecnologias

- Java 17
- Spring Boot 3.4.5
- Spring Web
- Spring Data JPA
- Spring HATEOAS
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
    url: jdbc:postgresql://localhost:5432/Test
    username: postgres
    password: mangodb
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

- `name`: obrigatorio e nao vazio
- `price`: obrigatorio e maior que zero

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
- `services`: regras de negocio
- `repository`: acesso ao banco com JPA
- `models`: entidades persistidas

## Observacoes

- O schema do banco e atualizado automaticamente por `spring.jpa.hibernate.ddl-auto=update`
- A pasta `target/` contem arquivos gerados no build e nao deve ser tratada como codigo-fonte
