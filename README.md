# 📺 ShonenLog API

> Aplicação REST para gerenciamento de catálogos de anime — registre filmes, categorias e plataformas de streaming com autenticação segura via JWT.

---

## 📑 Sumário

- [Sobre o Projeto](#-sobre-o-projeto)
- [Tecnologias](#-tecnologias)
- [Arquitetura](#-arquitetura)
- [Como Executar](#-como-executar)
- [Autenticação](#-autenticação)
- [Endpoints](#-endpoints)
  - [Auth](#-auth)
  - [Filmes (Movie)](#-filmes-movie)
  - [Categorias (Category)](#-categorias-category)
  - [Streaming](#-streaming)
- [Modelos de Dados](#-modelos-de-dados)
- [Tratamento de Erros](#-tratamento-de-erros)
- [Documentação Swagger](#-documentação-swagger)
- [Contato](#-contato)

---

## 💡 Sobre o Projeto

A **ShonenLog API** é uma API RESTful desenvolvida com **Java + Spring Boot** para gerenciar catálogos de animes no formato de filmes/séries. Ela permite cadastrar obras, associá-las a categorias (como Shonen, Isekai, Mecha) e plataformas de streaming (como Crunchyroll, Netflix), além de gerenciar usuários com autenticação via JWT.

---

## 🛠 Tecnologias

| Tecnologia | Descrição |
|---|---|
| Java 17+ | Linguagem principal |
| Spring Boot | Framework base |
| Spring Security | Autenticação e autorização |
| JWT (Auth0) | Geração e validação de tokens |
| Spring Data JPA | Persistência de dados |
| Hibernate | ORM |
| Bean Validation | Validação de requisições |
| Springdoc OpenAPI | Documentação Swagger |
| Lombok | Redução de boilerplate |
| BCrypt | Hash de senhas |

---

## 🏗 Arquitetura

```
br.com.shonenlog
├── config/          # Segurança, JWT, Swagger
├── controller/      # Endpoints REST
│   ├── request/     # DTOs de entrada
│   └── response/    # DTOs de saída
├── entity/          # Entidades JPA
├── exception/       # Exceções customizadas
├── mapper/          # Conversão Entity ↔ DTO
├── repository/      # Interfaces JPA
└── service/         # Regras de negócio
```

---

## 🚀 Como Executar

### Pré-requisitos

- Java 17+
- Maven 3.8+
- Banco de dados relacional (MySQL, PostgreSQL, H2, etc.)

### Configuração

No seu `application.properties` (ou `application.yml`), configure:

```properties
# Banco de dados
spring.datasource.url=jdbc:mysql://localhost:3306/shonenlog
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update

# Segredo JWT
shonenlog.security.secret=seu_secret_aqui
```

### Executando

```bash
# Clone o repositório
git clone https://github.com/eduardocaetano0/API-ShonenLog.git
cd API-ShonenLog

# Execute com Maven
./mvnw spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

---

## 🔐 Autenticação

A API utiliza **JWT (JSON Web Token)** com autenticação stateless.

**Fluxo:**

1. Crie uma conta via `POST /shonenLog/auth/register`
2. Faça login via `POST /shonenLog/auth/login` e receba o token
3. Inclua o token em todas as requisições protegidas no header:

```
Authorization: Bearer <seu_token>
```

**Rotas públicas** (não requerem token):
- `POST /shonenLog/auth/register`
- `POST /shonenLog/auth/login`
- `/swagger/**`
- `/v3/api-docs/**`

Todas as demais rotas exigem autenticação.

---

## 📡 Endpoints

### 🔑 Auth

#### Registrar usuário

```http
POST /shonenLog/auth/register
```

**Body:**
```json
{
  "name": "Eduardo Caetano",
  "email": "eduardo@email.com",
  "password": "senha123"
}
```

**Response `201 Created`:**
```json
{
  "id": 1,
  "name": "Eduardo Caetano",
  "email": "eduardo@email.com"
}
```

---

#### Login

```http
POST /shonenLog/auth/login
```

**Body:**
```json
{
  "email": "eduardo@email.com",
  "password": "senha123"
}
```

**Response `200 OK`:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Response `400 Bad Request`** (credenciais inválidas):
```
Usuário ou senha inválidos.
```

---

### 🎬 Filmes (Movie)

> ⚠️ Todas as rotas de filmes requerem autenticação.

#### Listar todos os filmes

```http
GET /shonenLog/movie
Authorization: Bearer <token>
```

**Response `200 OK`:**
```json
[
  {
    "id": 1,
    "title": "Demon Slayer: Mugen Train",
    "description": "Tanjiro e seus amigos embarcam em uma nova missão.",
    "releaseDate": "16/04/2021",
    "rating": 9.5,
    "categories": [
      { "id": 1, "name": "Shonen" }
    ],
    "streamings": [
      { "id": 2, "name": "Crunchyroll" }
    ]
  }
]
```

---

#### Buscar filme por ID

```http
GET /shonenLog/movie/{id}
Authorization: Bearer <token>
```

| Parâmetro | Tipo | Descrição |
|---|---|---|
| `id` | `Long` | ID do filme |

**Response `200 OK`:** objeto do filme  
**Response `404 Not Found`:** filme não encontrado

---

#### Cadastrar filme

```http
POST /shonenLog/movie
Authorization: Bearer <token>
Content-Type: application/json
```

**Body:**
```json
{
  "title": "Jujutsu Kaisen 0",
  "description": "A história de Yuta Okkotsu e sua maldição.",
  "releaseDate": "24/12/2021",
  "rating": 9.2,
  "categories": [1, 2],
  "streamings": [1]
}
```

> 📌 `categories` e `streamings` recebem listas de IDs já cadastrados.  
> 📌 `releaseDate` no formato `dd/MM/yyyy`.  
> ⚠️ `title` é obrigatório.

**Response `200 OK`:** objeto do filme criado

---

#### Atualizar filme

```http
PUT /shonenLog/movie/{id}
Authorization: Bearer <token>
Content-Type: application/json
```

| Parâmetro | Tipo | Descrição |
|---|---|---|
| `id` | `Long` | ID do filme a atualizar |

**Body:** mesmo formato do cadastro.

**Response `200 OK`:** objeto do filme atualizado  
**Response `404 Not Found`:** filme não encontrado

---

#### Deletar filme

```http
DELETE /shonenLog/movie/{id}
Authorization: Bearer <token>
```

**Response `200 OK`:** sem corpo

---

### 🏷 Categorias (Category)

> ⚠️ Todas as rotas de categorias requerem autenticação.

#### Listar todas as categorias

```http
GET /shonenLog/category
Authorization: Bearer <token>
```

**Response `200 OK`:**
```json
[
  { "id": 1, "name": "Shonen" },
  { "id": 2, "name": "Isekai" }
]
```

---

#### Buscar categoria por ID

```http
GET /shonenLog/category/{id}
Authorization: Bearer <token>
```

**Response `200 OK`:** objeto da categoria  
**Response `404 Not Found`:** categoria não encontrada

---

#### Cadastrar categoria

```http
POST /shonenLog/category
Authorization: Bearer <token>
Content-Type: application/json
```

**Body:**
```json
{
  "name": "Mecha"
}
```

> ⚠️ `name` é obrigatório.

**Response `201 Created`:**
```json
{
  "id": 3,
  "name": "Mecha"
}
```

---

#### Atualizar categoria

```http
PUT /shonenLog/category/{id}
Authorization: Bearer <token>
Content-Type: application/json
```

**Body:**
```json
{
  "name": "Slice of Life"
}
```

**Response `200 OK`:** objeto da categoria atualizada

---

#### Deletar categoria

```http
DELETE /shonenLog/category/{id}
Authorization: Bearer <token>
```

**Response `204 No Content`**

---

### 📡 Streaming

> ⚠️ Todas as rotas de streaming requerem autenticação.

#### Listar todos os streamings

```http
GET /shonenLog/streaming
Authorization: Bearer <token>
```

**Response `200 OK`:**
```json
[
  { "id": 1, "name": "Crunchyroll" },
  { "id": 2, "name": "Netflix" }
]
```

---

#### Buscar streaming por ID

```http
GET /shonenLog/streaming/{id}
Authorization: Bearer <token>
```

**Response `200 OK`:** objeto do streaming  
**Response `404 Not Found`:** não encontrado (retorna `null`)

---

#### Cadastrar streaming

```http
POST /shonenLog/streaming
Authorization: Bearer <token>
Content-Type: application/json
```

**Body:**
```json
{
  "name": "Amazon Prime Video"
}
```

> ⚠️ `name` é obrigatório.

**Response `200 OK`:**
```json
{
  "id": 3,
  "name": "Amazon Prime Video"
}
```

---

#### Atualizar streaming

```http
PUT /shonenLog/streaming/{id}
Authorization: Bearer <token>
Content-Type: application/json
```

**Body:**
```json
{
  "name": "Disney+"
}
```

**Response `200 OK`:** objeto do streaming atualizado

---

#### Deletar streaming

```http
DELETE /shonenLog/streaming/{id}
Authorization: Bearer <token>
```

**Response `200 OK`:** sem corpo

---

## 📦 Modelos de Dados

### User

| Campo | Tipo | Obrigatório | Descrição |
|---|---|---|---|
| `name` | `String` | ✅ | Nome do usuário |
| `email` | `String` | ✅ | E-mail (usado como username) |
| `password` | `String` | ✅ | Senha (armazenada com BCrypt) |

### Movie

| Campo | Tipo | Obrigatório | Descrição |
|---|---|---|---|
| `title` | `String` | ✅ | Título do anime/filme |
| `description` | `String` | ❌ | Sinopse |
| `releaseDate` | `String` (dd/MM/yyyy) | ❌ | Data de lançamento |
| `rating` | `double` | ❌ | Nota (ex: 9.5) |
| `categories` | `List<Long>` | ❌ | IDs das categorias |
| `streamings` | `List<Long>` | ❌ | IDs dos streamings |

### Category

| Campo | Tipo | Obrigatório | Descrição |
|---|---|---|---|
| `name` | `String` | ✅ | Nome da categoria |

### Streaming

| Campo | Tipo | Obrigatório | Descrição |
|---|---|---|---|
| `name` | `String` | ✅ | Nome da plataforma |

---

## ⚠️ Tratamento de Erros

A API retorna erros padronizados via `@RestControllerAdvice`.

### Credenciais inválidas — `400 Bad Request`
```
Usuário ou senha inválidos.
```

### Validação de campos — `400 Bad Request`
```json
{
  "title": "Titilo obrigatório",
  "name": "Nome da categoria é obrigatório."
}
```

### Recurso não encontrado — `404 Not Found`
Retornado quando um ID não existe no banco.

### Não autenticado — `403 Forbidden`
Retornado quando o token JWT está ausente, inválido ou expirado.

---

## 📖 Documentação Swagger

A documentação interativa da API está disponível em:

```
http://localhost:8080/swagger-ui.html
```

Ou acesse a especificação OpenAPI em:

```
http://localhost:8080/v3/api-docs
```

---

## 📬 Contato

**Eduardo Caetano**  
📧 eduardocaetano110@gmail.com  
🐙 [github.com/eduardocaetano0](https://github.com/eduardocaetano0/API-ShonenLog)
