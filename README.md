# 🚚 HubOps — Plataforma de Operações Logísticas

<p align="left">
  <img alt="Java" src="https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white">
  <img alt="Spring Boot" src="https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?logo=springboot&logoColor=white">
  <img alt="Spring Security" src="https://img.shields.io/badge/Spring%20Security-JWT-6DB33F?logo=springsecurity&logoColor=white">
  <img alt="Angular" src="https://img.shields.io/badge/Angular-20-DD0031?logo=angular&logoColor=white">
  <img alt="TypeScript" src="https://img.shields.io/badge/TypeScript-5.x-3178C6?logo=typescript&logoColor=white">
  <img alt="MySQL" src="https://img.shields.io/badge/MySQL-8.0-4479A1?logo=mysql&logoColor=white">
  <img alt="Docker" src="https://img.shields.io/badge/Docker-Compose-2496ED?logo=docker&logoColor=white">
  <img alt="OpenAPI" src="https://img.shields.io/badge/OpenAPI-Swagger-85EA2D?logo=swagger&logoColor=111">
</p>

Sistema de operações logísticas desenvolvido para gerenciamento de:

- clientes
- cotações
- entregas
- autenticação segura
- integração frontend/backend
- APIs REST documentadas
- ambiente containerizado

O projeto foi construído utilizando **Java + Spring Boot** no backend e **Angular** no frontend, simulando um cenário corporativo moderno com autenticação JWT e arquitetura em monorepo.

---

# 📌 Objetivo do Projeto

O HubOps foi desenvolvido com foco em:

- construção de APIs REST
- autenticação com JWT
- integração entre frontend e backend
- consumo de APIs com Angular
- organização de aplicações em monorepo
- containerização com Docker
- documentação com Swagger/OpenAPI

---

## Frontend

- Angular Standalone
- TypeScript
- CSS puro
- HTTP Interceptor

---

## Infraestrutura

- Docker
- Docker Compose
- Rancher Desktop

---

# 🧱 Arquitetura

```text
┌────────────────────┐
│     Frontend       │
│      Angular       │
└─────────┬──────────┘
          │ HTTP + JWT
          ▼
┌────────────────────┐
│   Spring Boot API  │
│    Backend REST    │
└─────────┬──────────┘
          │ JPA/Hibernate
          ▼
┌────────────────────┐
│       MySQL        │
│    Banco de Dados  │
└────────────────────┘
```

---

# 🔄 Fluxo de Autenticação

```text
Usuário
   ↓
Tela Login Angular
   ↓
POST /api/auth/login
   ↓
Spring Security + JWT
   ↓
Token JWT
   ↓
Angular salva token
   ↓
Interceptor adiciona Bearer Token
   ↓
Requisições autenticadas
```

---

# 📂 Estrutura do Projeto

```text
hubops/
├── src/                         # Backend Spring Boot
│   └── main/
│       ├── java/
│       │   └── com/gft/hubops/
│       │       ├── adapters/
│       │       ├── application/
│       │       ├── config/
│       │       └── domain/
│       │
│       └── resources/
│           └── application.properties
│
├── hubops-front/                # Frontend Angular
│   ├── src/
│   │   └── app/
│   │       ├── core/
│   │       └── pages/
│   │
│   ├── angular.json
│   ├── package.json
│   └── Dockerfile
│
├── Dockerfile
├── docker-compose.yml
└── README.md
```

---

# ▶️ Como Executar o Projeto

## 1. Clonar repositório

```bash
git clone https://github.com/VRCHADI/hubops.git
```

---

## 2. Entrar na pasta

```bash
cd hubops
```

---

## 3. Subir aplicação com Docker

```bash
docker compose up --build
```

---

# 🌐 Acessos

| Serviço | URL |
|---|---|
| Frontend Angular | http://localhost:4200 |
| Backend Spring Boot | http://localhost:8080 |
| Swagger/OpenAPI | http://localhost:8080/swagger-ui.html |
| MySQL | localhost:3307 |

---

# 🔐 Segurança

A aplicação utiliza autenticação JWT com:

- login autenticado
- rotas protegidas
- interceptor Angular
- filtro JWT no backend
- validação de token

---

# 📦 Funcionalidades

## ✅ Autenticação

- Login via API
- Geração de JWT
- Proteção de endpoints
- Logout no frontend

---

## ✅ Clientes

- Cadastro de clientes
- Listagem de clientes
- Atualização de clientes
- Integração frontend/backend

---

## ✅ Dashboard

- Sidebar de navegação
- Cards dinâmicos
- Consumo de dados reais da API

---

## ✅ Navegação Frontend

- Login
- Dashboard
- Clientes
- Cotações
- Entregas

---

## ✅ Infraestrutura

- Backend dockerizado
- Frontend dockerizado
- MySQL containerizado
- Docker Compose

---

# 🐳 Docker

## Subir aplicação

```bash
docker compose up --build
```

---

## Derrubar containers

```bash
docker compose down
```

---

# 📘 Swagger

A documentação da API está disponível em:

```text
http://localhost:8080/swagger-ui.html
```

---

# 🧪 Fluxos Testados

- Login autenticado
- Consumo de JWT
- Cadastro de cliente
- Listagem de clientes
- Navegação Angular
- Integração frontend/backend
- Containers Docker

---

# 📋 Roadmap Futuro

- [ ] CRUD completo frontend
- [ ] Feedback visual/toasts
- [ ] Responsividade mobile
- [ ] Logs estruturados
- [ ] Testes automatizados
- [ ] CI/CD
- [ ] Deploy cloud
- [ ] Kubernetes
- [ ] Kafka para mensageria

---

# 👨‍💻 Autor

## Victor Calil

Desenvolvedor Back-end Java em formação com foco em:

- Java
- Spring Boot
- APIs REST
- Angular
- Docker
- Arquitetura de Software

---

# ⭐ Objetivo

O HubOps foi desenvolvido para consolidar conhecimentos em:

- APIs REST
- autenticação JWT
- integração frontend/backend
- Angular
- Docker
- arquitetura corporativa
- organização de aplicações modernas
