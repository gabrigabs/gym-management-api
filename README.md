# Gym Management API

Sistema de gerenciamento de academias desenvolvido em Spring Boot, com evolução incremental através de múltiplas features. Cada feature representa uma etapa de desenvolvimento com funcionalidades progressivamente mais avançadas.

## 📋 Visão Geral

Este repositório demonstra a evolução de uma API REST completa, desde conceitos básicos até funcionalidades avançadas de validação, persistência e relacionamentos. Ideal para estudar boas práticas de desenvolvimento Spring Boot e arquitetura em camadas.

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5.7**
- **Spring Data JPA** (Features 3 e 4)
- **Spring Boot Validation** (Feature 4)
- **H2 Database** (Features 3 e 4)
- **Lombok** - Redução de boilerplate
- **Maven** - Gerenciamento de dependências

## 📁 Estrutura do Repositório

O repositório contém 4 projetos Spring Boot independentes, cada um em sua própria pasta:

```
gym-management-api/
├── gym-management-feature-1/    # Feature 1: CRUD básico em memória
├── gym-management-feature-2/    # Feature 2: Hierarquia de classes e relacionamentos
├── gym-management-feature-3/    # Feature 3: Persistência com JPA/H2
├── gym-management-feature-4/    # Feature 4: Validações e tratamento de exceções
└── README.md                    # Este arquivo
```

## 🎯 Features Implementadas

### ✅ Feature 1: Configuração e Gestão de Academias
**Objetivo**: Estabelecer base da aplicação com operações CRUD básicas

**O que você vai aprender:**
- Configuração inicial de projeto Spring Boot
- Padrão Repository/Service/Controller
- Armazenamento em memória (ConcurrentHashMap)
- Carregamento automático de dados de arquivo
- Endpoints REST básicos

**Entidades:**
- `Academia` (CNPJ, nome, endereço, telefone, status)

**Tecnologias principais:**
- Spring Boot Web
- Lombok
- ApplicationRunner para carga inicial

[📖 README detalhado da Feature 1](./gym-management-feature-1/README.md)

---

### ✅ Feature 2: Gestão de Instrutores e Alunos
**Objetivo**: Expandir o domínio com herança e relacionamentos

**O que você vai aprender:**
- Herança de classes (Pessoa abstrata)
- Relacionamentos OneToOne
- Exceções customizadas
- Endpoints de busca específicos
- Operações PATCH para atualização parcial

**Entidades:**
- `Pessoa` (classe abstrata)
- `Instrutor` (extends Pessoa, com Endereco)
- `Aluno` (extends Pessoa)
- `Endereco` (relacionamento 1:1 com Instrutor)

**Validações:**
- Exceções customizadas (AlunoInvalidoException, InstrutorInvalidoException, etc.)
- Validação de status e dados obrigatórios

[📖 README detalhado da Feature 2](./gym-management-feature-2/README.md)

---

### ✅ Feature 3: Persistência com JPA e H2 Database
**Objetivo**: Migrar de armazenamento em memória para banco de dados relacional

**O que você vai aprender:**
- Spring Data JPA e Hibernate
- Banco H2 em memória
- Anotações JPA (@Entity, @Table, @Id, @GeneratedValue)
- @MappedSuperclass para herança
- JPA Repositories
- H2 Console para inspeção do banco
- ResponseEntity com status HTTP adequados

**Melhorias técnicas:**
- IDs auto-incrementais via GenerationType.IDENTITY
- Constraints de banco (unique, nullable)
- Queries derivadas de métodos
- Transações automáticas

**Endpoints retornam status HTTP corretos:**
- 200 OK - Consultas bem-sucedidas
- 201 CREATED - Criação de recursos
- 204 NO CONTENT - Deleções
- 404 NOT FOUND - Recurso não encontrado

[📖 README detalhado da Feature 3](./gym-management-feature-3/README.md)

---

### ✅ Feature 4: Validações e Tratamento Global de Exceções
**Objetivo**: Implementar validações robustas e tratamento de erros padronizado

**O que você vai aprender:**
- Bean Validation (@NotBlank, @NotNull, @Size, @Email, @Pattern, @Min)
- @ControllerAdvice para tratamento global de exceções
- Relacionamentos bidirecionais (@JsonManagedReference, @JsonBackReference)
- Query Methods customizados (JPQL)
- Resposta de erros padronizada em JSON

**Validações implementadas:**
- CPF: `@Pattern` com regex
- CNPJ: `@Pattern` com regex
- Email: `@Email`
- Telefone: `@Pattern` com regex
- Campos obrigatórios: `@NotBlank`, `@NotNull`
- Tamanhos: `@Size(min, max)`
- Valores mínimos: `@Min`

**Relacionamentos bidirecionais:**
- Academia ↔ Instrutores (OneToMany/ManyToOne)
- Academia ↔ Alunos (OneToMany/ManyToOne)
- Instrutor ↔ Endereco (OneToOne)

**Query Methods avançados:**
- Busca por status (ativos/inativos)
- Busca por ranges (salário, datas)
- Queries JPQL com subconsultas
- Queries com JOIN

[📖 README detalhado da Feature 4](./gym-management-feature-4/README.md)

---

## 🏃 Como Executar

### Pré-requisitos

- **Java 21** instalado
- **Maven** instalado (ou use o wrapper mvnw incluído)
- IDE de sua preferência (IntelliJ IDEA, Eclipse, VS Code)

### Executando uma Feature

1. **Navegue até a pasta da feature desejada:**

```powershell
cd gym-management-feature-1
```

2. **Execute o projeto usando Maven:**

**Windows (PowerShell):**
```powershell
.\mvnw.cmd spring-boot:run
```

**Linux/Mac:**
```bash
./mvnw spring-boot:run
```

**Ou com Maven instalado globalmente:**
```powershell
mvn spring-boot:run
```

3. **A aplicação estará disponível em:**
```
http://localhost:8080
```

4. **Para Features 3 e 4, acesse o H2 Console:**
```
http://localhost:8080/h2-console
```
**Credenciais:**
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (deixe em branco)

### Testando os Endpoints

Cada feature inclui uma **Postman Collection** (`Postman_Collection_FeatureX.json`) com exemplos de requisições prontos para uso.

**Para importar no Postman:**
1. Abra o Postman
2. Clique em "Import"
3. Selecione o arquivo JSON da feature
4. Execute as requisições de exemplo

**Ou teste via curl/navegador:**

```powershell
# Listar todas as academias (GET)
curl http://localhost:8080/academias

# Buscar academia por ID (GET)
curl http://localhost:8080/academias/1

# Criar nova academia (POST)
curl -X POST http://localhost:8080/academias `
  -H "Content-Type: application/json" `
  -d '{"nome":"Academia Fit","cnpj":"12.345.678/0001-90","endereco":"Rua A, 123","telefone":"21987654321","statusAtivo":true}'
```

## 📚 Aprendizado Progressivo

Recomenda-se estudar as features na ordem sequencial (1 → 2 → 3 → 4) para melhor compreensão da evolução do projeto:

1. **Feature 1**: Entenda os fundamentos do Spring Boot e padrões básicos
2. **Feature 2**: Aprenda sobre OOP, herança e relacionamentos
3. **Feature 3**: Domine persistência com JPA e banco de dados
4. **Feature 4**: Implemente validações e tratamento de erros profissional

Cada README de feature contém:
- Descrição detalhada dos conceitos
- Exemplos de código comentados
- Endpoints disponíveis com exemplos de uso
- Diagramas e tabelas explicativas
- Casos de teste

## 🔍 Diferenças entre Features

| Aspecto | Feature 1 | Feature 2 | Feature 3 | Feature 4 |
|---------|-----------|-----------|-----------|-----------|
| **Armazenamento** | ConcurrentHashMap | ConcurrentHashMap | H2 Database | H2 Database |
| **Entidades** | Academia | Academia, Instrutor, Aluno, Endereco | Todas com @Entity | Todas com validações |
| **Relacionamentos** | Nenhum | OneToOne | OneToOne + JPA | OneToMany/ManyToOne |
| **Validações** | Básicas | Exceções customizadas | Exceções + DB constraints | Bean Validation completo |
| **Tratamento de Erros** | Try-catch local | Exceções específicas | Status HTTP | @ControllerAdvice global |
| **Query Methods** | Básicos (CRUD) | Busca por atributos | JPA derivados | JPQL customizados |
| **IDs** | AtomicInteger | AtomicInteger | Auto-increment DB | Auto-increment DB |

## 📖 Documentação Adicional

Cada feature possui seu próprio README.md com documentação detalhada incluindo:
- Arquitetura e componentes
- Modelo de dados completo
- Lista de endpoints com exemplos
- Exemplos de requisições/respostas
- Casos de erro e tratamento
- Instruções de teste

## 🤝 Contribuições

Este é um projeto educacional. Sinta-se à vontade para:
- Estudar o código
- Fazer fork e experimentar
- Sugerir melhorias
- Reportar issues

