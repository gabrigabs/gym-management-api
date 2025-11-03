# Gym Management API - Feature 4

Sistema de gerenciamento de academias desenvolvido com Spring Boot, incluindo validação robusta, tratamento global de exceções, relacionamentos bidirecionais e query methods customizados.

## 📋 Índice

- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Novidades da Feature 4](#novidades-da-feature-4)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Modelo de Dados e Relacionamentos](#modelo-de-dados-e-relacionamentos)
- [Validações Bean Validation](#validações-bean-validation)
- [Tratamento Global de Exceções](#tratamento-global-de-exceções)
- [Query Methods Customizados](#query-methods-customizados)
- [Endpoints da API](#endpoints-da-api)
- [Como Executar](#como-executar)
- [Testando com Postman](#testando-com-postman)
- [Exemplos de Requisições](#exemplos-de-requisições)
- [Exemplos de Erros](#exemplos-de-erros)

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5.7**
- **Spring Data JPA**
- **Spring Boot Validation** (Bean Validation)
- **H2 Database** (banco em memória)
- **Lombok**
- **Maven**

## 🆕 Novidades da Feature 4

### 1. **Validação Robusta com Bean Validation**

Todas as entidades agora possuem validações robustas usando anotações do Bean Validation:
- `@NotBlank` - Campos obrigatórios que não podem ser vazios
- `@NotNull` - Campos obrigatórios que não podem ser nulos
- `@Size` - Restrições de tamanho mínimo e máximo
- `@Email` - Validação de formato de email
- `@Pattern` - Validação com expressões regulares (CPF, CNPJ, telefone, etc.)
- `@Min` - Valor mínimo para campos numéricos
- `@Valid` - Validação em cascata para objetos aninhados

### 2. **Tratamento Global de Exceções**

Implementado `@ControllerAdvice` com `@ExceptionHandler` para tratamento centralizado de erros:
- Retorna JSON padronizado com detalhes do erro
- Inclui timestamp, status HTTP, mensagem e caminho da requisição
- Para erros de validação, retorna lista detalhada de erros por campo
- Trata violações de integridade (unique constraints, etc.)

### 3. **Relacionamentos Bidirecionais**

Implementados relacionamentos OneToMany e ManyToOne:
- **Academia → Instrutores** (OneToMany)
- **Academia → Alunos** (OneToMany)
- **Instrutor → Academia** (ManyToOne)
- **Aluno → Academia** (ManyToOne)
- **Instrutor → Endereco** (OneToOne)

Todos os relacionamentos usam `@JsonManagedReference` e `@JsonBackReference` para evitar loops infinitos na serialização JSON.

### 4. **Query Methods Customizados**

Repositórios expandidos com query methods para buscas avançadas:
- Busca por status (ativos/inativos)
- Busca por ranges (salário, datas)
- Busca por múltiplos critérios
- Queries JPQL customizadas com subconsultas
- Queries com relacionamentos (JOIN)

### 5. **Remoção de Loaders**

Como a aplicação agora usa banco de dados remoto/persistente, todos os loaders e arquivos de texto foram removidos. Os dados são gerenciados exclusivamente via API REST.

## 📁 Estrutura do Projeto

```
src/main/java/br/edu/infnet/gabriel/gym_management/
├── controller/          # Controllers REST
│   ├── AcademyController.java
│   ├── AlunoController.java
│   ├── InstrutorController.java
│   └── EnderecoController.java
├── exception/           # Exceções e tratamento global
│   ├── GlobalExceptionHandler.java
│   ├── ErrorResponse.java
│   ├── AlunoInvalidoException.java
│   ├── AlunoNaoEncontradoException.java
│   ├── InstrutorInvalidoException.java
│   ├── InstrutorNaoEncontradoException.java
│   └── EnderecoInvalidoException.java
├── model/              # Entidades JPA
│   ├── Academia.java
│   ├── Aluno.java
│   ├── Instrutor.java
│   ├── Endereco.java
│   └── Pessoa.java
├── repository/         # Repositórios JPA
│   ├── AcademiaRepository.java
│   ├── AlunoRepository.java
│   ├── InstrutorRepository.java
│   └── EnderecoRepository.java
└── service/           # Serviços de negócio
    ├── AcademyService.java
    ├── AlunoService.java
    ├── InstrutorService.java
    ├── EnderecoService.java
    └── CrudService.java
```

## 🗂️ Modelo de Dados e Relacionamentos

### Academia
```java
- id: Long (PK, auto-generated)
- nome: String (3-100 caracteres, obrigatório)
- cnpj: String (formato XX.XXX.XXX/XXXX-XX, obrigatório, único)
- endereco: String (10-200 caracteres, obrigatório)
- telefone: String (formato (XX) XXXXX-XXXX, opcional)
- statusAtivo: Boolean (obrigatório)
- instrutores: List<Instrutor> (OneToMany)
- alunos: List<Aluno> (OneToMany)
```

### Pessoa (Classe Abstrata - @MappedSuperclass)
```java
- id: Long (PK, auto-generated)
- nome: String (3-100 caracteres, obrigatório)
- email: String (formato válido, obrigatório, único)
- cpf: String (formato XXX.XXX.XXX-XX, obrigatório, único)
- telefone: String (formato (XX) XXXXX-XXXX, opcional)
```

### Aluno (extends Pessoa)
```java
- matricula: String (formato MATXXX, obrigatório, único)
- plano: String (3-50 caracteres, obrigatório)
- dataInicio: String (formato YYYY-MM-DD, obrigatório)
- status: Boolean (obrigatório)
- academia: Academia (ManyToOne, opcional)
```

### Instrutor (extends Pessoa)
```java
- registro: String (formato REGXXX, obrigatório, único)
- especialidade: String (3-50 caracteres, obrigatório)
- salario: Double (mínimo 1320, obrigatório)
- status: Boolean (obrigatório)
- endereco: Endereco (OneToOne, cascade ALL, opcional)
- academia: Academia (ManyToOne, opcional)
```

### Endereco
```java
- id: Long (PK, auto-generated)
- cep: String (formato XXXXX-XXX, obrigatório)
- logradouro: String (3-200 caracteres, obrigatório)
- complemento: String (máx 100 caracteres, opcional)
- unidade: String (máx 20 caracteres, opcional)
- bairro: String (3-100 caracteres, obrigatório)
- localidade: String (3-100 caracteres, obrigatório)
- uf: String (2 letras maiúsculas, obrigatório)
- estado: String (3-50 caracteres, obrigatório)
```

## ✅ Validações Bean Validation

### Validações Comuns em Todas as Entidades

#### Academia
- **nome**: `@NotBlank`, `@Size(min=3, max=100)`
- **cnpj**: `@NotBlank`, `@Pattern` (formato 99.999.999/9999-99)
- **endereco**: `@NotBlank`, `@Size(min=10, max=200)`
- **telefone**: `@Pattern` (formato (99) 99999-9999)
- **statusAtivo**: `@NotNull`

#### Pessoa (Aluno e Instrutor herdam)
- **nome**: `@NotBlank`, `@Size(min=3, max=100)`
- **email**: `@NotBlank`, `@Email`
- **cpf**: `@NotBlank`, `@Pattern` (formato 999.999.999-99)
- **telefone**: `@Pattern` (formato (99) 99999-9999)

#### Aluno
- **matricula**: `@NotBlank`, `@Pattern` (formato MAT999)
- **plano**: `@NotBlank`, `@Size(min=3, max=50)`
- **dataInicio**: `@NotBlank`, `@Pattern` (formato YYYY-MM-DD)
- **status**: `@NotNull`

#### Instrutor
- **registro**: `@NotBlank`, `@Pattern` (formato REG999)
- **especialidade**: `@NotBlank`, `@Size(min=3, max=50)`
- **salario**: `@NotNull`, `@Min(1320)` (salário mínimo)
- **status**: `@NotNull`
- **endereco**: `@Valid` (validação em cascata)

#### Endereco
- **cep**: `@NotBlank`, `@Pattern` (formato 99999-999)
- **logradouro**: `@NotBlank`, `@Size(min=3, max=200)`
- **complemento**: `@Size(max=100)`
- **unidade**: `@Size(max=20)`
- **bairro**: `@NotBlank`, `@Size(min=3, max=100)`
- **localidade**: `@NotBlank`, `@Size(min=3, max=100)`
- **uf**: `@NotBlank`, `@Pattern` (2 letras maiúsculas)
- **estado**: `@NotBlank`, `@Size(min=3, max=50)`

## 🛡️ Tratamento Global de Exceções

### Estrutura da Resposta de Erro

```json
{
  "timestamp": "2025-11-03T18:30:00",
  "status": 400,
  "error": "Validation Error",
  "message": "Erro de validação nos campos fornecidos",
  "path": "/alunos",
  "fieldErrors": [
    {
      "field": "cpf",
      "message": "CPF deve estar no formato XXX.XXX.XXX-XX",
      "rejectedValue": "12345678900"
    },
    {
      "field": "email",
      "message": "Email deve ser válido",
      "rejectedValue": "email-invalido"
    }
  ]
}
```

### Tipos de Erros Tratados

1. **Validação (400 Bad Request)**
   - Erros de Bean Validation
   - Campos obrigatórios faltando
   - Formatos inválidos

2. **Not Found (404)**
   - Recurso não encontrado por ID
   - CPF/matrícula/registro não encontrado

3. **Conflict (409)**
   - Violação de unique constraints
   - CPF/email/matrícula/registro duplicado

4. **Internal Server Error (500)**
   - Erros inesperados

## 🔍 Query Methods Customizados

### AcademiaRepository

```java
// Busca por status
List<Academia> findByStatusAtivo(Boolean statusAtivo);

// Busca por nome (contém, case insensitive)
List<Academia> findByNomeContainingIgnoreCase(String nome);

// Busca academias ativas com instrutores (JPQL com JOIN FETCH)
List<Academia> findAcademiasAtivasComInstrutores();

// Busca academias com pelo menos X alunos (JPQL com SIZE)
List<Academia> findAcademiasComMinimoAlunos(int minAlunos);

// Conta academias ativas
Long countByStatusAtivo(Boolean statusAtivo);
```

### AlunoRepository

```java
// Busca por status
List<Aluno> findByStatus(Boolean status);

// Busca por plano e status
List<Aluno> findByPlanoIgnoreCaseAndStatus(String plano, Boolean status);

// Busca por academia
List<Aluno> findByAcademiaId(Long academiaId);

// Busca alunos ativos de uma academia (JPQL)
List<Aluno> findAlunosAtivosDeAcademia(Long academiaId);

// Busca por período de início (JPQL com BETWEEN)
List<Aluno> findByDataInicioBetween(String dataInicio, String dataFim);

// Busca alunos sem academia
List<Aluno> findByAcademiaIsNull();

// Conta alunos ativos
Long countByStatus(Boolean status);
```

### InstrutorRepository

```java
// Busca por status
List<Instrutor> findByStatus(Boolean status);

// Busca por especialidade e status
List<Instrutor> findByEspecialidadeIgnoreCaseAndStatus(String especialidade, Boolean status);

// Busca por faixa de salário
List<Instrutor> findBySalarioBetween(Double salarioMin, Double salarioMax);

// Busca instrutores com salário acima de X (JPQL com ORDER BY)
List<Instrutor> findInstrutoresComSalarioAcima(Double salarioMinimo);

// Busca por academia
List<Instrutor> findByAcademiaId(Long academiaId);

// Busca instrutores ativos de uma academia (JPQL)
List<Instrutor> findInstrutoresAtivosDeAcademia(Long academiaId);

// Busca instrutores sem academia
List<Instrutor> findByAcademiaIsNull();

// Busca por cidade do endereço (JPQL com JOIN)
List<Instrutor> findByEnderecoLocalidade(String cidade);

// Conta instrutores ativos
Long countByStatus(Boolean status);
```

## 📡 Endpoints da API

### Academias (`/academias`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/academias` | Lista todas as academias |
| GET | `/academias/{id}` | Busca academia por ID |
| GET | `/academias/status/{status}` | Busca por status (true/false) |
| GET | `/academias/buscar?nome={nome}` | Busca por nome (contém) |
| GET | `/academias/ativas-com-instrutores` | Lista academias ativas com instrutores |
| GET | `/academias/minimo-alunos/{quantidade}` | Academias com pelo menos X alunos |
| GET | `/academias/estatisticas` | Estatísticas (total, ativas, inativas) |
| POST | `/academias` | Cria nova academia |
| PUT | `/academias/{id}` | Atualiza academia |
| DELETE | `/academias/{id}` | Deleta academia |

### Alunos (`/alunos`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/alunos` | Lista todos os alunos |
| GET | `/alunos/{id}` | Busca aluno por ID |
| GET | `/alunos/cpf/{cpf}` | Busca por CPF |
| GET | `/alunos/matricula/{matricula}` | Busca por matrícula |
| GET | `/alunos/plano/{plano}` | Busca por plano |
| GET | `/alunos/status/{status}` | Busca por status |
| GET | `/alunos/plano/{plano}/status/{status}` | Busca por plano E status |
| GET | `/alunos/academia/{academiaId}` | Alunos de uma academia |
| GET | `/alunos/academia/{academiaId}/ativos` | Alunos ativos de uma academia |
| GET | `/alunos/sem-academia` | Alunos sem academia |
| GET | `/alunos/periodo?dataInicio={data}&dataFim={data}` | Alunos por período |
| GET | `/alunos/estatisticas` | Estatísticas |
| POST | `/alunos` | Cria novo aluno |
| PUT | `/alunos/{id}` | Atualiza aluno |
| PATCH | `/alunos/{id}/ativar` | Ativa aluno |
| PATCH | `/alunos/{id}/inativar` | Inativa aluno |
| PATCH | `/alunos/{id}/vincular-academia/{academiaId}` | Vincula a academia |
| PATCH | `/alunos/{id}/desvincular-academia` | Desvincula de academia |
| DELETE | `/alunos/{id}` | Deleta aluno |

### Instrutores (`/instrutores`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/instrutores` | Lista todos os instrutores |
| GET | `/instrutores/{id}` | Busca instrutor por ID |
| GET | `/instrutores/cpf/{cpf}` | Busca por CPF |
| GET | `/instrutores/registro/{registro}` | Busca por registro |
| GET | `/instrutores/especialidade/{especialidade}` | Busca por especialidade |
| GET | `/instrutores/status/{status}` | Busca por status |
| GET | `/instrutores/especialidade/{esp}/status/{status}` | Busca por especialidade E status |
| GET | `/instrutores/salario?min={valor}&max={valor}` | Busca por faixa salarial |
| GET | `/instrutores/salario-acima/{valor}` | Instrutores com salário acima de X |
| GET | `/instrutores/academia/{academiaId}` | Instrutores de uma academia |
| GET | `/instrutores/academia/{academiaId}/ativos` | Instrutores ativos de uma academia |
| GET | `/instrutores/sem-academia` | Instrutores sem academia |
| GET | `/instrutores/cidade/{cidade}` | Busca por cidade do endereço |
| GET | `/instrutores/estatisticas` | Estatísticas |
| POST | `/instrutores` | Cria novo instrutor |
| PUT | `/instrutores/{id}` | Atualiza instrutor |
| PATCH | `/instrutores/{id}/ativar` | Ativa instrutor |
| PATCH | `/instrutores/{id}/inativar` | Inativa instrutor |
| PATCH | `/instrutores/{id}/vincular-academia/{academiaId}` | Vincula a academia |
| PATCH | `/instrutores/{id}/desvincular-academia` | Desvincula de academia |
| DELETE | `/instrutores/{id}` | Deleta instrutor |

### Endereços (`/enderecos`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/enderecos` | Lista todos os endereços |
| GET | `/enderecos/{id}` | Busca endereço por ID |
| POST | `/enderecos` | Cria novo endereço |
| PUT | `/enderecos/{id}` | Atualiza endereço |
| DELETE | `/enderecos/{id}` | Deleta endereço |

## 🚀 Como Executar

### Pré-requisitos
- Java 21 ou superior
- Maven 3.6+

### Executar a Aplicação

```bash
# Clone o repositório
git clone <repository-url>

# Entre no diretório
cd gym-management-feature-4

# Execute com Maven
./mvnw spring-boot:run

# Ou no Windows
mvnw.cmd spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

### Console H2 Database

Acesse o console do H2 em: `http://localhost:8080/h2-console`

```
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: (deixar vazio)
```

## 📮 Testando com Postman

### Importar a Collection

1. Abra o Postman
2. Click em "Import"
3. Selecione o arquivo `Postman_Collection_Feature4.json`
4. A collection estará organizada por feature/pasta

### Estrutura da Collection

```
Gym Management API - Feature 4
├── 01 - Academias
│   ├── Criar Academia (Sucesso)
│   ├── Criar Academia (Erro - Validação)
│   ├── Listar Todas
│   ├── Buscar por Status
│   ├── Buscar por Nome
│   ├── Academias Ativas com Instrutores
│   ├── Academias com Mínimo de Alunos
│   └── Estatísticas
├── 02 - Alunos
│   ├── Criar Aluno (Sucesso)
│   ├── Criar Aluno (Erro - CPF Inválido)
│   ├── Criar Aluno (Erro - Email Inválido)
│   ├── Listar Todos
│   ├── Buscar por Status
│   ├── Buscar por Plano e Status
│   ├── Buscar por Academia
│   ├── Buscar por Período
│   ├── Vincular a Academia
│   ├── Desvincular de Academia
│   └── Estatísticas
├── 03 - Instrutores
│   ├── Criar Instrutor (Sucesso)
│   ├── Criar Instrutor (Erro - Salário Baixo)
│   ├── Criar Instrutor (Erro - Registro Inválido)
│   ├── Listar Todos
│   ├── Buscar por Especialidade e Status
│   ├── Buscar por Faixa Salarial
│   ├── Buscar Salário Acima de Valor
│   ├── Buscar por Cidade
│   ├── Vincular a Academia
│   ├── Desvincular de Academia
│   └── Estatísticas
└── 04 - Endereços
    ├── Criar Endereço (Sucesso)
    ├── Criar Endereço (Erro - CEP Inválido)
    └── Listar Todos
```

## 📝 Exemplos de Requisições

### Criar Academia (Sucesso)

**POST** `/academias`

```json
{
  "nome": "Academia PowerGym",
  "cnpj": "12.345.678/0001-90",
  "endereco": "Rua das Flores, 123 - Centro",
  "telefone": "(21) 98765-4321",
  "statusAtivo": true
}
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "nome": "Academia PowerGym",
  "cnpj": "12.345.678/0001-90",
  "endereco": "Rua das Flores, 123 - Centro",
  "telefone": "(21) 98765-4321",
  "statusAtivo": true,
  "instrutores": [],
  "alunos": []
}
```

### Criar Aluno com Validação

**POST** `/alunos`

```json
{
  "nome": "João Silva",
  "email": "joao.silva@email.com",
  "cpf": "123.456.789-00",
  "telefone": "(21) 99876-5432",
  "matricula": "MAT001",
  "plano": "Plano Mensal",
  "dataInicio": "2025-01-15",
  "status": true
}
```

### Criar Instrutor com Endereco

**POST** `/instrutores`

```json
{
  "nome": "Maria Santos",
  "email": "maria.santos@email.com",
  "cpf": "987.654.321-00",
  "telefone": "(21) 91234-5678",
  "registro": "REG001",
  "especialidade": "Musculação",
  "salario": 3500.00,
  "status": true,
  "endereco": {
    "cep": "20000-000",
    "logradouro": "Rua Principal",
    "complemento": "Apt 101",
    "bairro": "Centro",
    "localidade": "Rio de Janeiro",
    "uf": "RJ",
    "estado": "Rio de Janeiro"
  }
}
```

### Vincular Aluno a Academia

**PATCH** `/alunos/1/vincular-academia/1`

(Sem body necessário)

### Buscar por Período

**GET** `/alunos/periodo?dataInicio=2025-01-01&dataFim=2025-12-31`

### Buscar Instrutores por Faixa Salarial

**GET** `/instrutores/salario?min=2000&max=5000`

## ❌ Exemplos de Erros

### Erro de Validação - CPF Inválido

**POST** `/alunos`

```json
{
  "nome": "João",
  "email": "joao@email.com",
  "cpf": "12345678900",
  "matricula": "MAT001",
  "plano": "Mensal",
  "dataInicio": "2025-01-15",
  "status": true
}
```

**Resposta (400 Bad Request):**
```json
{
  "timestamp": "2025-11-03T18:30:00",
  "status": 400,
  "error": "Validation Error",
  "message": "Erro de validação nos campos fornecidos",
  "path": "/alunos",
  "fieldErrors": [
    {
      "field": "cpf",
      "message": "CPF deve estar no formato XXX.XXX.XXX-XX",
      "rejectedValue": "12345678900"
    }
  ]
}
```

### Erro de Validação - Múltiplos Campos

**POST** `/instrutores`

```json
{
  "nome": "M",
  "email": "email-invalido",
  "cpf": "123",
  "registro": "ABC",
  "especialidade": "M",
  "salario": 500,
  "status": true
}
```

**Resposta (400 Bad Request):**
```json
{
  "timestamp": "2025-11-03T18:30:00",
  "status": 400,
  "error": "Validation Error",
  "message": "Erro de validação nos campos fornecidos",
  "path": "/instrutores",
  "fieldErrors": [
    {
      "field": "nome",
      "message": "Nome deve ter entre 3 e 100 caracteres",
      "rejectedValue": "M"
    },
    {
      "field": "email",
      "message": "Email deve ser válido",
      "rejectedValue": "email-invalido"
    },
    {
      "field": "cpf",
      "message": "CPF deve estar no formato XXX.XXX.XXX-XX",
      "rejectedValue": "123"
    },
    {
      "field": "registro",
      "message": "Registro deve estar no formato REGXXX (3-6 dígitos)",
      "rejectedValue": "ABC"
    },
    {
      "field": "especialidade",
      "message": "Especialidade deve ter entre 3 e 50 caracteres",
      "rejectedValue": "M"
    },
    {
      "field": "salario",
      "message": "Salário deve ser no mínimo R$ 1.320,00 (salário mínimo)",
      "rejectedValue": 500
    }
  ]
}
```

### Erro - Recurso Não Encontrado

**GET** `/alunos/999`

**Resposta (404 Not Found):**
```json
{
  "timestamp": "2025-11-03T18:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Aluno com ID 999 não encontrado",
  "path": "/alunos/999"
}
```

### Erro - Violação de Integridade (CPF Duplicado)

**POST** `/alunos`

Tentando criar aluno com CPF já cadastrado:

**Resposta (409 Conflict):**
```json
{
  "timestamp": "2025-11-03T18:30:00",
  "status": 409,
  "error": "Conflict",
  "message": "CPF já cadastrado no sistema",
  "path": "/alunos"
}
```

## 🎯 Boas Práticas Aplicadas

1. **Separation of Concerns**: Controllers, Services, Repositories e Models bem separados
2. **Bean Validation**: Validações declarativas nas entidades
3. **Exception Handling**: Tratamento centralizado com mensagens claras
4. **RESTful API**: Endpoints seguindo convenções REST
5. **DTO Pattern**: Uso de ErrorResponse para padronizar erros
6. **Cascade Operations**: Relacionamentos com cascade apropriado
7. **Lazy Loading**: FetchType.LAZY em relacionamentos ManyToOne
8. **JSON Management**: @JsonManagedReference/@JsonBackReference para evitar loops
9. **Query Methods**: Métodos de query expressivos e type-safe
10. **Documentation**: Javadoc em todos os métodos importantes

## 📊 Estratégias de Validação

### 1. Validação de Formato
- **CPF**: `\d{3}\.\d{3}\.\d{3}-\d{2}`
- **CNPJ**: `\d{2}\.\d{3}\.\d{3}/\d{4}-\d{2}`
- **Telefone**: `\(\d{2}\) \d{4,5}-\d{4}`
- **CEP**: `\d{5}-\d{3}` ou `\d{8}`
- **UF**: `[A-Z]{2}`
- **Data**: `\d{4}-\d{2}-\d{2}` (YYYY-MM-DD)
- **Matrícula**: `MAT\d{3,6}`
- **Registro**: `REG\d{3,6}`

### 2. Validação de Tamanho
- Campos de texto têm min/max definidos
- Previne ataques de buffer overflow
- Garante qualidade dos dados

### 3. Validação de Valor
- Salário mínimo: R$ 1.320,00
- Campos obrigatórios não podem ser nulos ou vazios
- Email deve ser válido

### 4. Validação em Cascata
- Endereco dentro de Instrutor é validado automaticamente com `@Valid`

## 🔧 Configurações

### application.properties

```properties
# H2 Database
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA/Hibernate
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Server
server.port=8080
```

## 📚 Referências

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Bean Validation (JSR 380)](https://beanvalidation.org/)
- [Hibernate Validator](https://hibernate.org/validator/)
- [RESTful API Design](https://restfulapi.net/)

## 👨‍💻 Autor

Gabriel - [Infnet - Engenharia de Software]

## 📄 Licença

Este projeto foi desenvolvido para fins educacionais.

---

**Desenvolvido com ☕ e Spring Boot**

