# Gym Management API - Sistema de Gerenciamento de Academias

## 📋 Visão Geral

Sistema completo de gerenciamento de academias desenvolvido em Spring Boot, com suporte a múltiplas features implementadas de forma incremental.

**Versão Atual**: Feature 2 ✅  
**Status**: Operacional

---

## 🎯 Features Implementadas

### ✅ Feature 1: Configuração e Gestão de Academias
- Modelagem da entidade Academia
- CRUD completo para Academias
- Carregamento automático de dados

### ✅ Feature 2: Gestão de Instrutores e Alunos
- Hierarquia de classes com Pessoa (abstrata)
- Entidades Instrutor e Aluno (herdam de Pessoa)
- Entidade Endereco com relação 1:1 com Instrutor
- CRUD completo para Instrutores, Alunos e Endereços
- Métodos específicos de busca
- PATCH para inativar/ativar status
- Validações customizadas com exceções

---

## 🏗️ Arquitetura e Componentes

### Modelos de Dados

#### Academia (Feature 1)
| Atributo | Tipo | Descrição |
|----------|------|-----------|
| `id` | `Integer` | ID único (auto-gerado) |
| `nome` | `String` | Nome da academia |
| `cnpj` | `String` | CNPJ (formato: XX.XXX.XXX/XXXX-XX) |
| `endereco` | `String` | Endereço |
| `telefone` | `String` | Telefone |
| `statusAtivo` | `Boolean` | Status operacional |

#### Pessoa (Feature 2 - Abstrata)
| Atributo | Tipo | Descrição |
|----------|------|-----------|
| `id` | `Integer` | ID único (auto-gerado) |
| `nome` | `String` | Nome |
| `email` | `String` | Email |
| `cpf` | `String` | CPF |
| `telefone` | `String` | Telefone |

#### Instrutor (Feature 2 - extends Pessoa)
| Atributo | Tipo | Descrição |
|----------|------|-----------|
| `registro` | `String` | Registro profissional |
| `especialidade` | `String` | Especialidade (ex: Musculação) |
| `salario` | `Double` | Salário |
| `status` | `Boolean` | Status (ativo/inativo) |
| `endereco` | `Endereco` | Endereço (relação 1:1) |

#### Aluno (Feature 2 - extends Pessoa)
| Atributo | Tipo | Descrição |
|----------|------|-----------|
| `matricula` | `String` | Matrícula |
| `plano` | `String` | Plano (Gold/Silver/Bronze) |
| `dataInicio` | `String` | Data de início |
| `status` | `Boolean` | Status (ativo/inativo) |

#### Endereco (Feature 2)
| Atributo | Tipo | Descrição |
|----------|------|-----------|
| `id` | `Integer` | ID único |
| `cep` | `String` | CEP |
| `logradouro` | `String` | Logradouro |
| `complemento` | `String` | Complemento |
| `unidade` | `String` | Número/Unidade |
| `bairro` | `String` | Bairro |
| `localidade` | `String` | Cidade |
| `uf` | `String` | UF |
| `estado` | `String` | Estado |

### Interface CrudService

```java
public interface CrudService<T, ID> {
    T salvar(T entidade);
    T buscarPorId(ID id);
    Boolean excluir(ID id);
    List<T> listarTodos();
}
```

Implementada por:
- `AcademyService`
- `InstrutorService`
- `AlunoService`
- `EnderecoService`

---

## 🌐 Endpoints REST

### Base URLs
- Academias: `http://localhost:8080/academias`
- Instrutores: `http://localhost:8080/instrutores`
- Alunos: `http://localhost:8080/alunos`
- Endereços: `http://localhost:8080/enderecos`

### ACADEMIAS (5 endpoints)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/academias` | Listar todas |
| GET | `/academias/{id}` | Buscar por ID |
| POST | `/academias` | Criar nova |
| PUT | `/academias/{id}` | Atualizar |
| DELETE | `/academias/{id}` | Deletar |

### INSTRUTORES (9 endpoints)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/instrutores` | Listar todos |
| GET | `/instrutores/{id}` | Buscar por ID |
| GET | `/instrutores/cpf/{cpf}` | Buscar por CPF |
| GET | `/instrutores/especialidade/{esp}` | Buscar por especialidade |
| POST | `/instrutores` | Criar novo |
| PUT | `/instrutores/{id}` | Atualizar |
| PATCH | `/instrutores/{id}/inativar` | Inativar |
| PATCH | `/instrutores/{id}/ativar` | Ativar |
| DELETE | `/instrutores/{id}` | Deletar |

### ALUNOS (10 endpoints)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/alunos` | Listar todos |
| GET | `/alunos/{id}` | Buscar por ID |
| GET | `/alunos/cpf/{cpf}` | Buscar por CPF |
| GET | `/alunos/matricula/{matricula}` | Buscar por matrícula |
| GET | `/alunos/plano/{plano}` | Buscar por plano |
| POST | `/alunos` | Criar novo |
| PUT | `/alunos/{id}` | Atualizar |
| PATCH | `/alunos/{id}/inativar` | Inativar |
| PATCH | `/alunos/{id}/ativar` | Ativar |
| DELETE | `/alunos/{id}` | Deletar |

### ENDEREÇOS (5 endpoints)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/enderecos` | Listar todos |
| GET | `/enderecos/{id}` | Buscar por ID |
| POST | `/enderecos` | Criar novo |
| PUT | `/enderecos/{id}` | Atualizar |
| DELETE | `/enderecos/{id}` | Deletar |

**Total: 29 endpoints REST**

---

## 📊 Exemplos de Requisições

### Listar Instrutores
```bash
curl -X GET http://localhost:8080/instrutores
```

### Buscar Instrutor por CPF
```bash
curl -X GET "http://localhost:8080/instrutores/cpf/123.456.789-10"
```

### Criar Novo Instrutor
```bash
curl -X POST http://localhost:8080/instrutores \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Fernando Souza",
    "email": "fernando@gmail.com",
    "cpf": "999.888.777-00",
    "telefone": "11900000001",
    "registro": "REG005",
    "especialidade": "CrossFit",
    "salario": 5500,
    "status": true,
    "endereco": {
      "cep": "05426-100",
      "logradouro": "Rua Bandeira",
      "complemento": "Sala 1",
      "unidade": "1",
      "bairro": "Vila Mariana",
      "localidade": "São Paulo",
      "uf": "SP",
      "estado": "São Paulo"
    }
  }'
```

### Inativar Instrutor (PATCH)
```bash
curl -X PATCH http://localhost:8080/instrutores/1/inativar
```

### Listar Alunos por Plano
```bash
curl -X GET "http://localhost:8080/alunos/plano/Gold"
```

---

## 🔐 Validações e Exceções

### Instrutor - Validações
- ✅ Nome obrigatório
- ✅ Email obrigatório
- ✅ CPF obrigatório
- ✅ Registro obrigatório
- ✅ Salário > 0
- ✅ Status obrigatório

**Exceções**:
- `InstrutorInvalidoException` - Dados inválidos
- `InstrutorNaoEncontradoException` - Não encontrado (HTTP 404)

### Aluno - Validações
- ✅ Nome obrigatório
- ✅ Email obrigatório
- ✅ CPF obrigatório
- ✅ Matrícula obrigatória
- ✅ Status obrigatório

**Exceções**:
- `AlunoInvalidoException` - Dados inválidos
- `AlunoNaoEncontradoException` - Não encontrado (HTTP 404)

### Endereco - Validações
- ✅ CEP obrigatório
- ✅ Logradouro obrigatório

**Exceções**:
- `EnderecoInvalidoException` - Dados inválidos

---

## 💾 Dados Pré-carregados

### Instrutores (4)
1. **João Silva** - Musculação - R$ 5.000,00 - Ativo
2. **Maria Santos** - Pilates - R$ 4.500,00 - Ativo
3. **Carlos Oliveira** - Funcional - R$ 5.500,00 - Ativo
4. **Ana Costa** - Yoga - R$ 4.000,00 - Inativo

### Alunos (6)
1. **Pedro Gomes** - MAT001 - Plano Gold - Ativo
2. **Juliana Ferreira** - MAT002 - Plano Silver - Ativo
3. **Rafael Santos** - MAT003 - Plano Bronze - Ativo
4. **Beatriz Almeida** - MAT004 - Plano Gold - Inativo
5. **Gabriel Lima** - MAT005 - Plano Silver - Ativo
6. **Camila Rosa** - MAT006 - Plano Bronze - Ativo

### Academias (4)
1. **Academia Força Total** - CNPJ: 12.345.678/0001-00 - Ativo
2. **Academia Elite** - CNPJ: 98.765.432/0001-99 - Ativo
3. **Academia Musculação Pro** - CNPJ: 11.222.333/0001-44 - Ativo
4. **Academia Bem-estar** - CNPJ: 55.666.777/0001-88 - Inativo

---

## 🚀 Como Executar

### Pré-requisitos
- Java 21+
- Maven 3.6+
- Postman (para testes)

### Passos

#### 1. Compilar
```bash
mvn clean compile
```

#### 2. Build
```bash
mvn package -DskipTests
```

#### 3. Executar
```bash
mvn spring-boot:run
```

Ou via IDE (IntelliJ/Eclipse):
- Clique direito em `GymManagementApplication.java`
- Selecione "Run"

#### 4. Verificar se está rodando
- Abra: `http://localhost:8080`
- API estará disponível em `http://localhost:8080`

---

## 🧪 Testes com Postman

### Importar Collection

1. Abra o **Postman**
2. Clique em **Import**
3. Selecione `Postman_Collection_Feature2.json`
4. Clique em **Import**

A collection contém requisições pré-configuradas para testar:
- ✅ Todos os endpoints
- ✅ Validações e erros
- ✅ CRUD completo
- ✅ Métodos específicos


