# 🔗Encurtador de URL

Uma aplicação moderna de encurtamento de URLs desenvolvida com Spring Boot e React, oferecendo uma solução rápida e eficiente para transformar URLs longas em links curtos e personalizados.

## 🚀 Tecnologias Utilizadas

### Backend
- **Java 17+**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Spring Web**
- **Neon DB PostgreSQL** (produção)
- **Maven**

### Frontend
- **React 18**
- **JavaScript (ES6+)**
- **Axios** (requisições HTTP)
- **React Router** (navegação)
- **TailwindCss**

## ✨ Funcionalidades

- ✅ Encurtamento de URLs longas
- ✅ Redirecionamento automático
- ✅ Histórico de URLs criadas
- ✅ Interface responsiva
- ✅ Validação de URLs
- ✅ Expiração de links 

## 📋 Pré-requisitos

- Java 17 ou superior
- Node.js 16+ e npm
- Maven 3.6+
- Git

## 🛠️ Instalação e Configuração

### 1. Clone o repositório
```bash
git clone https://github.com/rafael-men/encurta-link-web-app.git
```

### 2. Configuração do Backend

```bash
# Navegue para o diretório do backend
cd encurta-api

# Instale as dependências
mvn clean install

# Execute a aplicação
mvn spring-boot:run
```

A API estará rodando em `http://localhost:8090/swagger-ui.html`

### 3. Configuração do Frontend

```bash
# Navegue para o diretório do frontend
cd frontend-encurta
cd frontend-encurta

# Instale as dependências
npm install

# Execute em modo de desenvolvimento
npm run dev
```

A aplicação frontend estará disponível em `http://localhost:5173`


### Exemplo de Requisição

```javascript
// Criar URL encurtada
POST /encurta/generate
Content-Type: application/json

{
  "originalUrl": "https://www.exemplo.com/url-muito-longa",
  "customCode": "meulink", // opcional
  "expiresAt": "2024-12-31T23:59:59" // opcional
}
```


## 🚀 Deploy

### Usando Docker

```bash
# Executar
# navegar até a pasta raiz do backend
docker-compose up -d
```


## 🤝 Contribuindo

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request


## 🐛 Problemas Conhecidos

- URLs muito longas podem ter problemas de renderização

⭐ Se este projeto te ajudou, considere dar uma estrela no repositório!
