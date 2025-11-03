# Gym Management API - Sistema de Gerenciamento de Academias

## 📋 Visão Geral

Sistema completo de gerenciamento de academias desenvolvido em Spring Boot, com persistência de dados usando Spring Data JPA e banco H2 em memória.

**Versão Atual**: Feature 3 ✅  
**Status**: Operacional com Banco de Dados Relacional

---

## 🎯 Features Implementadas

### ✅ Feature 1: Configuração e Gestão de Academias
- Modelagem da entidade Academia
- CRUD completo para Academias
- Carregamento automático de dados de arquivo

### ✅ Feature 2: Gestão de Instrutores e Alunos
- Hierarquia de classes com Pessoa (abstrata)
- Entidades Instrutor e Aluno (herdam de Pessoa)
- Entidade Endereco com relação 1:1 com Instrutor
- CRUD completo para Instrutores, Alunos e Endereços
- Métodos específicos de busca
- PATCH para inativar/ativar status
- Validações customizadas com exceções

### ✅ Feature 3: Persistência com JPA e H2 Database
- **Spring Data JPA** integrado para persistência
- **Banco H2** em memória configurado
- **Entidades JPA** com anotações:
  - `@Entity`, `@Table`, `@Id`, `@GeneratedValue`
  - `@MappedSuperclass` para herança (Pessoa)
  - `@OneToOne` para relacionamento Instrutor-Endereco
  - `@Column` com constraints (unique, nullable)
- **JPA Repositories** substituindo ConcurrentHashMap
- **IDs auto-incrementais** via `GenerationType.IDENTITY`
- **H2 Console** habilitado para inspeção do banco
- **Controllers** retornando `ResponseEntity` com status HTTP corretos:
  - **200 OK** para consultas bem-sucedidas
  - **201 CREATED** para criação de recursos
  - **204 NO CONTENT** para deleções bem-sucedidas
  - **400 BAD REQUEST** para validações falhadas
  - **404 NOT FOUND** para recursos não encontrados

---

## 🗄️ Arquitetura e Tecnologias

### Stack Tecnológico
- **Java 21**
- **Spring Boot 3.5.7**
- **Spring Data JPA** - Persistência e Repositórios
- **H2 Database** - Banco de dados em memória
- **Hibernate** - ORM (Object-Relational Mapping)
- **Lombok** - Redução de boilerplate
- **Maven** - Gerenciamento de dependências

### Camadas da Aplicação

```
┌─────────────────────────────────────┐
│    Controllers (REST API)           │
│  - AcademyController                │
│  - AlunoController                  │
│  - InstrutorController              │
│  - EnderecoController               │
└────────────┬────────────────────────┘
             │
┌────────────▼────────────────────────┐
│         Services (Lógica)           │
│  - AcademyService                   │
│  - AlunoService                     │
│  - InstrutorService                 │
│  - EnderecoService                  │
└────────────┬────────────────────────┘
             │
┌────────────▼────────────────────────┐
│    Repositories (JPA)               │
│  - AcademiaRepository               │
│  - AlunoRepository                  │
│  - InstrutorRepository              │
│  - EnderecoRepository               │
└────────────┬────────────────────────┘
             │
┌────────────▼────────────────────────┐
│      H2 Database (em memória)       │
│  - academias                        │
│  - alunos                           │
│  - instrutores                      │
│  - enderecos                        │
└─────────────────────────────────────┘
```

---

## 🗃️ Modelo de Dados (JPA)

### Academia
```java
@Entity
@Table(name = "academias")
public class Academia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false, unique = true)
    private String cnpj;
    
    @Column(nullable = false)
    private String endereco;
    
    private String telefone;
    
    @Column(nullable = false)
    private Boolean statusAtivo;
}
```

### Pessoa (MappedSuperclass)
```java
@MappedSuperclass
public abstract class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false, unique = true)
    private String cpf;
    
    private String telefone;
}
```

### Aluno
```java
@Entity
@Table(name = "alunos")
public class Aluno extends Pessoa {
    @Column(nullable = false, unique = true)
    private String matricula;
    
    private String plano;
    private String dataInicio;
    
    @Column(nullable = false)
    private Boolean status;
}
```

### Instrutor
```java
@Entity
@Table(name = "instrutores")
public class Instrutor extends Pessoa {
    @Column(nullable = false, unique = true)
    private String registro;
    
    private String especialidade;
    
    @Column(nullable = false)
    private Double salario;
    
    @Column(nullable = false)
    private Boolean status;
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;
}
```

### Endereco
```java
@Entity
@Table(name = "enderecos")
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String cep;
    
    @Column(nullable = false)
    private String logradouro;
    
    private String complemento;
    private String unidade;
    private String bairro;
    private String localidade;
    private String uf;
    private String estado;
}
```

---

## ⚙️ Configuração

### application.properties
```properties
spring.application.name=gym-management

# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:gymdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# H2 Console Configuration
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA/Hibernate Configuration
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Logging
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

---

## 🚀 Como Executar

### Pré-requisitos
- Java 21 ou superior
- Maven 3.6+

### Passos

1. **Clone o repositório** (ou navegue até a pasta)
```bash
cd gym-management-feature-3
```

2. **Compile o projeto**
```bash
./mvnw clean compile
```

3. **Execute a aplicação**
```bash
./mvnw spring-boot:run
```

4. **A aplicação estará disponível em:**
- API REST: `http://localhost:8080`
- H2 Console: `http://localhost:8080/h2-console`

### Acessando o H2 Console

1. Abra o navegador em: `http://localhost:8080/h2-console`
2. Use as seguintes credenciais:
   - **JDBC URL**: `jdbc:h2:mem:gymdb`
   - **User Name**: `sa`
   - **Password**: (deixe em branco)
3. Clique em "Connect"

Agora você pode executar queries SQL diretamente no banco:
**Nota**: O banco inicia vazio. Use a API REST para criar dados via endpoints POST.

Você pode executar queries SQL diretamente no banco:
```sql
-- Ver todas as academias
SELECT * FROM ACADEMIAS;

-- Ver todos os alunos
SELECT * FROM ALUNOS;

-- Ver instrutores com seus endereços
SELECT i.*, e.* 
FROM INSTRUTORES i 
LEFT JOIN ENDERECOS e ON i.ENDERECO_ID = e.ID;

-- Contar registros por tabela
SELECT 'ACADEMIAS' AS TABELA, COUNT(*) AS TOTAL FROM ACADEMIAS
UNION ALL
SELECT 'ALUNOS', COUNT(*) FROM ALUNOS
UNION ALL
SELECT 'INSTRUTORES', COUNT(*) FROM INSTRUTORES
UNION ALL
SELECT 'ENDERECOS', COUNT(*) FROM ENDERECOS;
```

---

## 📡 API Endpoints

### Academias

#### GET /academias
Lista todas as academias.

**Resposta: 200 OK**
```json
[
  {
    "id": 1,
    "nome": "Academia Força Total",
    "cnpj": "12.345.678/0001-00",
    "endereco": "Rua A, 123 - Centro",
    "telefone": "(11) 9876-5432",
    "statusAtivo": true
  }
]
```

#### GET /academias/{id}
Busca uma academia por ID.

**Resposta: 200 OK** ou **404 NOT FOUND**

#### POST /academias
Cria uma nova academia.

**Request Body:**
```json
{
  "nome": "Academia Nova",
  "cnpj": "99.888.777/0001-66",
  "endereco": "Rua Nova, 999",
  "telefone": "(11) 9999-8888",
  "statusAtivo": true
}
```

**Resposta: 201 CREATED**
```json
{
  "id": 5,
  "nome": "Academia Nova",
  "cnpj": "99.888.777/0001-66",
  "endereco": "Rua Nova, 999",
  "telefone": "(11) 9999-8888",
  "statusAtivo": true
}
```

#### PUT /academias/{id}
Atualiza uma academia existente.

**Resposta: 200 OK** ou **404 NOT FOUND**

#### DELETE /academias/{id}
Remove uma academia.

**Resposta: 204 NO CONTENT** ou **404 NOT FOUND**

---

### Alunos

#### GET /alunos
Lista todos os alunos.

**Resposta: 200 OK**
```json
[
  {
    "id": 1,
    "nome": "Pedro Gomes",
    "email": "pedro@gmail.com",
    "cpf": "111.222.333-44",
    "telefone": "11955555555",
    "matricula": "MAT001",
    "plano": "Gold",
    "dataInicio": "2024-01-15",
    "status": true
  }
]
```

#### GET /alunos/{id}
Busca um aluno por ID.

**Resposta: 200 OK** ou **404 NOT FOUND**

#### GET /alunos/cpf/{cpf}
Busca um aluno por CPF.

**Exemplo:** `GET /alunos/cpf/111.222.333-44`

**Resposta: 200 OK** ou **404 NOT FOUND**

#### GET /alunos/matricula/{matricula}
Busca um aluno por matrícula.

**Exemplo:** `GET /alunos/matricula/MAT001`

**Resposta: 200 OK** ou **404 NOT FOUND**

#### GET /alunos/plano/{plano}
Busca alunos por plano (case insensitive).

**Exemplo:** `GET /alunos/plano/gold`

**Resposta: 200 OK**

#### POST /alunos
Cria um novo aluno.

**Request Body:**
```json
{
  "nome": "Maria Silva",
  "email": "maria.silva@gmail.com",
  "cpf": "999.888.777-66",
  "telefone": "11988887777",
  "matricula": "MAT999",
  "plano": "Platinum",
  "dataInicio": "2024-11-03",
  "status": true
}
```

**Resposta: 201 CREATED**

#### PUT /alunos/{id}
Atualiza um aluno existente.

**Resposta: 200 OK**, **400 BAD REQUEST** ou **404 NOT FOUND**

#### PATCH /alunos/{id}/ativar
Ativa um aluno (status = true).

**Resposta: 200 OK** ou **404 NOT FOUND**

#### PATCH /alunos/{id}/inativar
Inativa um aluno (status = false).

**Resposta: 200 OK** ou **404 NOT FOUND**

#### DELETE /alunos/{id}
Remove um aluno.

**Resposta: 204 NO CONTENT** ou **404 NOT FOUND**

---

### Instrutores

#### GET /instrutores
Lista todos os instrutores.

**Resposta: 200 OK**
```json
[
  {
    "id": 1,
    "nome": "João Silva",
    "email": "joao@gmail.com",
    "cpf": "123.456.789-10",
    "telefone": "11999999999",
    "registro": "REG001",
    "especialidade": "Musculação",
    "salario": 5000.0,
    "status": true,
    "endereco": {
      "id": 1,
      "cep": "01310-100",
      "logradouro": "Avenida Paulista",
      "complemento": "Apto 100",
      "unidade": "100",
      "bairro": "Bela Vista",
      "localidade": "São Paulo",
      "uf": "SP",
      "estado": "São Paulo"
    }
  }
]
```

#### GET /instrutores/{id}
Busca um instrutor por ID.

**Resposta: 200 OK** ou **404 NOT FOUND**

#### GET /instrutores/cpf/{cpf}
Busca um instrutor por CPF.

**Resposta: 200 OK** ou **404 NOT FOUND**

#### GET /instrutores/especialidade/{especialidade}
Busca instrutores por especialidade.

**Exemplo:** `GET /instrutores/especialidade/musculacao`

**Resposta: 200 OK**

#### POST /instrutores
Cria um novo instrutor.

**Request Body:**
```json
{
  "nome": "Carlos Mendes",
  "email": "carlos.mendes@gmail.com",
  "cpf": "888.777.666-55",
  "telefone": "11977776666",
  "registro": "REG999",
  "especialidade": "CrossFit",
  "salario": 6000.0,
  "status": true,
  "endereco": {
    "cep": "04567-890",
    "logradouro": "Rua das Flores",
    "complemento": "Casa",
    "unidade": "10",
    "bairro": "Jardim Paulista",
    "localidade": "São Paulo",
    "uf": "SP",
    "estado": "São Paulo"
  }
}
```

**Resposta: 201 CREATED**

#### PUT /instrutores/{id}
Atualiza um instrutor existente.

**Resposta: 200 OK**, **400 BAD REQUEST** ou **404 NOT FOUND**

#### PATCH /instrutores/{id}/ativar
Ativa um instrutor.

**Resposta: 200 OK** ou **404 NOT FOUND**

#### PATCH /instrutores/{id}/inativar
Inativa um instrutor.

**Resposta: 200 OK** ou **404 NOT FOUND**

#### DELETE /instrutores/{id}
Remove um instrutor (e seu endereço em cascade).

**Resposta: 204 NO CONTENT** ou **404 NOT FOUND**

---

### Endereços

#### GET /enderecos
Lista todos os endereços.

**Resposta: 200 OK**

#### GET /enderecos/{id}
Busca um endereço por ID.

**Resposta: 200 OK** ou **404 NOT FOUND**

#### POST /enderecos
Cria um novo endereço.

**Resposta: 201 CREATED** ou **400 BAD REQUEST**

#### PUT /enderecos/{id}
Atualiza um endereço.

**Resposta: 200 OK**, **400 BAD REQUEST** ou **404 NOT FOUND**

#### DELETE /enderecos/{id}
Remove um endereço.

**Resposta: 204 NO CONTENT** ou **404 NOT FOUND**

---

## 🧪 Testando com Postman

Uma collection completa do Postman está disponível no arquivo `Postman_Collection_Feature3.json`.

### Importando a Collection

1. Abra o Postman
2. Clique em "Import"
3. Selecione o arquivo `Postman_Collection_Feature3.json`
4. A collection "Gym Management API - Feature 3" será importada

### Exemplos de Teste

#### 1. Listar todas as academias (dados pré-carregados)
```
GET http://localhost:8080/academias
```

#### 2. Buscar aluno por matrícula
```
GET http://localhost:8080/alunos/matricula/MAT001
```

#### 3. Criar novo aluno
```
POST http://localhost:8080/alunos
Content-Type: application/json

{
  "nome": "Teste Aluno",
  "email": "teste@test.com",
  "cpf": "123.123.123-99",
  "telefone": "11999999999",
  "matricula": "MAT9999",
  "plano": "Bronze",
  "dataInicio": "2024-11-03",
  "status": true
}
```

#### 4. Inativar um aluno
```
PATCH http://localhost:8080/alunos/1/inativar
```

#### 5. Buscar instrutores por especialidade
```
GET http://localhost:8080/instrutores/especialidade/pilates
```

#### 6. Verificar dados no H2 Console
Acesse `http://localhost:8080/h2-console` e execute:
```sql
SELECT * FROM ALUNOS WHERE STATUS = TRUE;
```

---

## 📊 Diferenças entre Features

### Feature 1 vs Feature 2
- **Feature 1**: Apenas Academia com ConcurrentHashMap
- **Feature 2**: Adicionou Pessoa (hierarquia), Aluno, Instrutor, Endereco com relacionamento 1:1

### Feature 2 vs Feature 3
| Aspecto | Feature 2 | Feature 3 |
|---------|-----------|-----------|
| **Armazenamento** | ConcurrentHashMap (memória volátil) | H2 Database (persistência) |
| **IDs** | AtomicInteger manual | JPA auto-increment |
| **Repositórios** | Não existem | JpaRepository |
| **Queries** | Streams Java | JPQL/SQL via Spring Data |
| **Relacionamentos** | Apenas objetos Java | Mapeamento ORM com FK |
| **Constraints** | Apenas validação Java | Constraints de banco (unique, nullable) |
| **Inspeção** | Logs console | H2 Console (interface web) |
| **Cascade** | Manual | Automático via JPA |
| **Transações** | Não gerenciadas | Gerenciadas por Spring |

### Benefícios da Feature 3

✅ **Persistência Real**: Dados sobrevivem a reinicializações (em produção)  
✅ **Integridade Referencial**: FK garantem consistência  
✅ **Queries Otimizadas**: Hibernate gera SQL eficiente  
✅ **Inspeção Visual**: H2 Console para debugging  
✅ **Escalabilidade**: Fácil migração para PostgreSQL/MySQL  
✅ **Transações ACID**: Garantia de consistência  
✅ **Menos Código**: Spring Data reduz boilerplate  

---

## 📝 Estrutura de Arquivos

```
gym-management-feature-3/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── br/edu/infnet/gabriel/gym_management/
│   │   │       ├── GymManagementApplication.java
│   │   │       ├── controller/
│   │   │       │   ├── AcademyController.java
│   │   │       │   ├── AlunoController.java
│   │   │       │   ├── InstrutorController.java
│   │   │       │   └── EnderecoController.java
│   │   │       ├── model/
│   │   │       │   ├── Academia.java (@Entity)
│   │   │       │   ├── Pessoa.java (@MappedSuperclass)
│   │   │       │   ├── Aluno.java (@Entity)
│   │   │       │   ├── Instrutor.java (@Entity)
│   │   │       │   └── Endereco.java (@Entity)
│   │   │       ├── repository/
│   │   │       │   ├── AcademiaRepository.java (JpaRepository)
│   │   │       │   ├── AlunoRepository.java (JpaRepository)
│   │   │       │   ├── InstrutorRepository.java (JpaRepository)
│   │   │       │   └── EnderecoRepository.java (JpaRepository)
│   │   │       ├── service/
│   │   │       │   ├── AcademyService.java
│   │   │       │   ├── AlunoService.java
│   │   │       │   ├── InstrutorService.java
│   │   │       │   └── EnderecoService.java
│   │   │       └── exception/
│   │   │           └── ...
│   │   └── resources/
│   │       ├── application.properties (configuração H2/JPA)
│   │       ├── static/
│   │       └── templates/
│   └── test/
├── pom.xml
├── README.md
└── Postman_Collection_Feature3.json
```

---

## 🔍 Logs e Debugging

Com `spring.jpa.show-sql=true`, você verá todas as queries SQL no console:

```sql
Hibernate: 
    create table academias (
        id bigint generated by default as identity,
        cnpj varchar(255) not null,
        endereco varchar(255) not null,
        nome varchar(255) not null,
        status_ativo boolean not null,
        telefone varchar(255),
        primary key (id)
    )

Hibernate: 
    insert into alunos
        (cpf, email, nome, telefone, data_inicio, matricula, plano, status, id)
    values
        (?, ?, ?, ?, ?, ?, ?, ?, default)
```

---

## 🎓 Aprendizados da Feature 3

1. **Spring Data JPA**: Criação de repositórios sem implementação
2. **Hibernate/JPA**: Mapeamento objeto-relacional
3. **H2 Database**: Banco em memória para desenvolvimento/testes
4. **Herança JPA**: Uso de `@MappedSuperclass`
5. **Relacionamentos**: `@OneToOne` com cascade
6. **Constraints**: Unique, nullable via annotations
7. **Auto-increment**: IDs gerenciados pelo banco
8. **Transações**: Gerenciamento automático pelo Spring
9. **ResponseEntity**: Status HTTP adequados em REST APIs
10. **H2 Console**: Ferramenta de inspeção de dados

---

## 🚧 Próximos Passos (Features Futuras)

- **Feature 4**: Autenticação e Autorização (Spring Security)
- **Feature 5**: Validação com Bean Validation (@Valid, @NotNull, etc.)
- **Feature 6**: Paginação e Ordenação
- **Feature 7**: Testes Unitários e de Integração
- **Feature 8**: Documentação com Swagger/OpenAPI
- **Feature 9**: Deploy em ambiente de produção
- **Feature 10**: Migração para PostgreSQL

---

## 👨‍💻 Autor

Gabriel  
Infnet - Desenvolvimento Java com Spring Boot

---

## 📄 Licença

Este projeto é de uso educacional.

