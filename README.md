# Task API

API REST para gerenciamento de tarefas desenvolvida com Java 21 e Spring Boot. O projeto foca em boas práticas de arquitetura, Clean Code, validações de dados e versionamento de banco de dados.

## Tecnologias e Ferramentas

- **Java 21**
- **Spring Boot 4.0.5**
- **Spring Data JPA** (Hibernate)
- **MySQL 8** (Banco de dados relacional)
- **Flyway** (Versionamento de esquema de banco de dados)
- **Springdoc OpenAPI / Swagger** (Documentação interativa da API)
- **Docker & Docker Compose** (Conteinerização)
- **Lombok** (Redução de boilerplate)
- **Jakarta Bean Validation** (Validação de integridade de dados)
- **Spring Boot Actuator** (Monitoramento da aplicação)

## Status do Projeto

O projeto está na fase inicial de estruturação.

- [x] Configuração inicial (Spring Boot, dependências e plugins).
- [x] Estrutura de pacotes (Arquitetura em camadas).
- [x] Configuração do banco de dados MySQL via Docker.
- [x] Criação da tabela de tarefas via Flyway (`V1__create_tables.sql`).
- [x] Configuração da documentação Swagger OpenAPI.
- [ ] Mapeamento da entidade principal (`Task`) e Enums.
- [ ] Criação dos DTOs e validações.
- [ ] Implementação de CRUD e regras de negócio (Services).
- [ ] Tratamento global de exceções (`@RestControllerAdvice`).

## Como executar o projeto localmente

### Pré-requisitos
- [JDK 21](https://adoptium.net/) ou superior.
- [Maven 3.x](https://maven.apache.org/) (ou utilize o wrapper incluso).
- [Docker](https://www.docker.com/) e Docker Compose.

### Passo a passo

1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/rtheodoro4201/task-api.git](https://github.com/rtheodoro4201/task-api.git)
   cd task-api
   ```

2. **Configure as variáveis de ambiente:**
   Faça uma cópia do arquivo `.env.example` na raiz do projeto, renomeie para `.env` e preencha com as credenciais do seu banco de dados:
   ```env
   DB_USERNAME=root
   DB_PASSWORD=sua_senha
   DB_NAME=task_db
   ```

3. **Suba a infraestrutura (Banco de Dados):**
   Inicie o contêiner do MySQL em segundo plano:
   ```bash
   docker-compose up -d
   ```

4. **Execute a aplicação:**
   Com o banco de dados rodando, utilize o Maven Wrapper para iniciar a API:
   - No **Linux/Mac**:
     ```bash
     ./mvnw spring-boot:run
     ```
   - No **Windows**:
     ```cmd
     mvnw.cmd spring-boot:run
     ```

5. **Acesse a Documentação (Swagger):**
   Com a aplicação iniciada, a documentação interativa estará disponível no navegador em:
   [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Arquitetura e Estrutura de Pastas

O projeto utiliza o padrão de **Arquitetura em Camadas (Package by Layer)** para garantir a separação de responsabilidades:

- `config/`: Configurações globais da aplicação (Swagger, segurança, CORS).
- `controller/`: Porta de entrada da API (Endpoints REST).
- `service/`: Regras de negócio e casos de uso.
- `repository/`: Comunicação com o banco de dados via Spring Data JPA.
- `entity/`: Modelos de domínio mapeados para as tabelas do banco.
- `dto/`: Objetos de transferência de dados (Isolamento da camada web).
- `enums/`: Enumerações de domínio (Status, Prioridade).
- `exception/`: Tratamento centralizado de erros e respostas padronizadas.
- `mapper/`: Classes responsáveis pela conversão entre DTOs e Entidades.
- `utils/`: Funções utilitárias e ajudantes compartilhados.

## 📄 Licença

Este projeto está sob a licença MIT - veja o arquivo [LICENSE](LICENSE) para mais detalhes.
