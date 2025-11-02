# Gym Management API - Feature 1: Configuração e Gestão de Academias

## 📋 Visão Geral

A Feature 1 implementa a configuração essencial do sistema de gerenciamento de academias com foco na modelagem da entidade principal **Academia** e suas operações CRUD (Create, Read, Update, Delete).

### Objetivo Principal

Estabelecer uma base sólida para a aplicação Spring Boot com:
- Configuração adequada das dependências
- Modelagem clara da entidade Academia
- Implementação de padrões de design (Service, Repository, Controller)
- Carregamento automático de dados no início da aplicação
- Endpoints REST para manipulação de academias

---

## 🏗️ Arquitetura e Componentes

### 1. **Modelo - Academia.java**

A entidade `Academia` representa uma academia no sistema com os seguintes atributos:

| Atributo | Tipo | Descrição |
|----------|------|-----------|
| `id` | `Integer` | Identificador único (gerado automaticamente) |
| `nome` | `String` | Nome da academia |
| `cnpj` | `String` | CNPJ da academia (formato: XX.XXX.XXX/XXXX-XX) |
| `endereco` | `String` | Endereço completo da academia |
| `telefone` | `String` | Telefone de contato |
| `statusAtivo` | `Boolean` | Status operacional (ativo/inativo) |

**Tecnologias utilizadas:**
- **Lombok `@Data`**: Gera automaticamente getters, setters, equals, hashCode e toString
- **`@NoArgsConstructor`**: Construtor sem argumentos
- **`@AllArgsConstructor`**: Construtor com todos os argumentos

### 2. **Interface CrudService.java**

Define o contrato para operações genéricas CRUD:

```java
public interface CrudService<T, ID> {
    T salvar(T entidade);
    T buscarPorId(ID id);
    Boolean excluir(ID id);
    List<T> listarTodos();
}
```

**Benefícios:**
- Padrão reutilizável para futuras entidades
- Contrato claro e consistente
- Facilita testes e manutenção

### 3. **Serviço - AcademyService.java**

Implementação do `CrudService` com armazenamento em memória:

**Características:**
- **ConcurrentHashMap**: Armazenamento thread-safe em memória
- **AtomicInteger**: Gerador de IDs thread-safe
- **@Service**: Anotação Spring que marca a classe como serviço
- Geração automática de IDs sequenciais

**Exemplo de uso:**
```java
@Autowired
private AcademyService academyService;

// Salvar
Academia academia = new Academia(null, "Academia XYZ", "12.345.678/0001-00", "Rua A", "123456", true);
Academia salva = academyService.salvar(academia); // ID gerado automaticamente

// Buscar
Academia encontrada = academyService.buscarPorId(1);

// Listar
List<Academia> todas = academyService.listarTodos();

// Excluir
academyService.excluir(1);
```

### 4. **Loader - AcademyLoader.java**

Componente que executa na inicialização da aplicação:

**Funcionalidades:**
- **@Component**: Registra a classe como componente Spring
- **ApplicationRunner**: Executa código ao iniciar a aplicação
- Lê o arquivo `academias.txt` do classpath
- Parseia cada linha e cria academias automaticamente
- Imprime as academias carregadas no console

**Formato do arquivo academias.txt:**
```
# Comentários começam com #
nome;cnpj;endereco;telefone;statusAtivo

Academia Força Total;12.345.678/0001-00;Rua A, 123;(11) 9876-5432;true
Academia Elite;98.765.432/0001-99;Avenida Paulista, 1000;(11) 3456-7890;true
```

### 5. **Controlador - AcademyController.java**

Endpoints REST para manipulação de academias:

**Anotações principais:**
- **@RestController**: Marca a classe como controlador REST
- **@RequestMapping("/academias")**: Define o prefixo dos endpoints
- **Injeção de dependência via construtor**: Padrão recomendado

---

## 🌐 Endpoints REST

### Base URL: `http://localhost:8080/academias`

### 1. **Listar Todas as Academias**

```http
GET /academias
```

**Resposta (200 OK):**
```json
[
  {
    "id": 1,
    "nome": "Academia Força Total",
    "cnpj": "12.345.678/0001-00",
    "endereco": "Rua A, 123 - Centro",
    "telefone": "(11) 9876-5432",
    "statusAtivo": true
  },
  {
    "id": 2,
    "nome": "Academia Elite",
    "cnpj": "98.765.432/0001-99",
    "endereco": "Avenida Paulista, 1000",
    "telefone": "(11) 3456-7890",
    "statusAtivo": true
  }
]
```

---

### 2. **Buscar Academia por ID**

```http
GET /academias/{id}
```

**Exemplo:**
```http
GET /academias/1
```

**Resposta (200 OK):**
```json
{
  "id": 1,
  "nome": "Academia Força Total",
  "cnpj": "12.345.678/0001-00",
  "endereco": "Rua A, 123 - Centro",
  "telefone": "(11) 9876-5432",
  "statusAtivo": true
}
```

**Resposta (404 Not Found):**
```json
{
  "error": "Academia não encontrada"
}
```

---

### 3. **Criar Nova Academia**

```http
POST /academias
Content-Type: application/json

{
  "nome": "Academia Flex Fitness",
  "cnpj": "44.555.666/0001-77",
  "endereco": "Rua D, 999 - Zona Sul",
  "telefone": "(11) 3333-4444",
  "statusAtivo": true
}
```

**Resposta (201 Created):**
```json
{
  "id": 5,
  "nome": "Academia Flex Fitness",
  "cnpj": "44.555.666/0001-77",
  "endereco": "Rua D, 999 - Zona Sul",
  "telefone": "(11) 3333-4444",
  "statusAtivo": true
}
```

---

### 4. **Atualizar Academia**

```http
PUT /academias/{id}
Content-Type: application/json

{
  "nome": "Academia Força Total - Atualizada",
  "cnpj": "12.345.678/0001-00",
  "endereco": "Rua A, 123 - Centro Expandido",
  "telefone": "(11) 9876-5433",
  "statusAtivo": true
}
```

**Exemplo:**
```http
PUT /academias/1
```

**Resposta (200 OK):**
```json
{
  "id": 1,
  "nome": "Academia Força Total - Atualizada",
  "cnpj": "12.345.678/0001-00",
  "endereco": "Rua A, 123 - Centro Expandido",
  "telefone": "(11) 9876-5433",
  "statusAtivo": true
}
```

**Resposta (404 Not Found):**
ID não existe

---

### 5. **Deletar Academia**

```http
DELETE /academias/{id}
```

**Exemplo:**
```http
DELETE /academias/5
```

**Resposta (204 No Content):**
Sem corpo na resposta

**Resposta (404 Not Found):**
ID não existe

---

## 🚀 Como Executar

### Pré-requisitos

- **Java 21 ou superior** (conforme configurado no pom.xml)
- **Maven 3.6+**
- **Postman** (para testes dos endpoints)
- **IDE**: IntelliJ IDEA, Eclipse ou VSCode com extensões Java

### Passos para Executar

#### 1. **Clonar ou abrir o projeto**

```bash
cd C:\Users\gabri\Desktop\Workspace\Spring\gym-management-api\gym-management
```

#### 2. **Instalar dependências**

```bash
mvn clean install
```

#### 3. **Executar a aplicação**

```bash
mvn spring-boot:run
```

Ou via IDE:
- Clique com botão direito na classe `GymManagementApplication.java`
- Selecione "Run 'GymManagementApplication.main()'"

#### 4. **Verificar se está rodando**

A aplicação estará disponível em: `http://localhost:8080`

**Saída esperada no console:**

```
========================================
ACADEMIAS CARREGADAS NA INICIALIZAÇÃO
========================================
Academia{id=1, nome='Academia Força Total', cnpj='12.345.678/0001-00', endereco='Rua A, 123 - Centro', telefone='(11) 9876-5432', statusAtivo=true}
Academia{id=2, nome='Academia Elite', cnpj='98.765.432/0001-99', endereco='Avenida Paulista, 1000', telefone='(11) 3456-7890', statusAtivo=true}
Academia{id=3, nome='Academia Musculação Pro', cnpj='11.222.333/0001-44', endereco='Rua B, 456 - Vila Mariana', telefone='(11) 2234-5678', statusAtivo=true}
Academia{id=4, nome='Academia Bem-estar', cnpj='55.666.777/0001-88', endereco='Rua C, 789 - Pinheiros', telefone='(21) 99999-8888', statusAtivo=false}
Total: 4 academia(s) carregada(s)
========================================
```

---

## 🧪 Testes com Postman

### Importar Collection

1. Abra o **Postman**
2. Clique em **Import** (canto superior esquerdo)
3. Selecione **Upload Files**
4. Procure por `Postman_Collection_Feature1.json` no projeto
5. Clique em **Import**

### Executar Testes

Após importar, você terá uma coleção com 5 requisições:

1. **Listar Todas as Academias** - GET
2. **Buscar Academia por ID** - GET
3. **Criar Nova Academia** - POST
4. **Atualizar Academia** - PUT
5. **Deletar Academia** - DELETE

**Sequência recomendada de testes:**

```
1. GET /academias
   └─ Verifica academias carregadas no startup

2. GET /academias/1
   └─ Busca academia específica

3. POST /academias
   └─ Cria nova academia
   └─ Observe o ID gerado automaticamente

4. GET /academias
   └─ Verifica se nova academia aparece

5. PUT /academias/1
   └─ Atualiza dados de academia existente

6. DELETE /academias/5
   └─ Remove academia (ajuste ID conforme necessário)

7. GET /academias
   └─ Confirma exclusão
```

---

## 🧪 Testes via Browser

É possível testar algumas operações direto no navegador:

### Listar Academias
```
http://localhost:8080/academias
```

### Buscar Academia por ID
```
http://localhost:8080/academias/1
```

---

## 📝 Estrutura de Diretórios

```
gym-management/
├── src/
│   ├── main/
│   │   ├── java/br/edu/infnet/gabriel/gym_management/
│   │   │   ├── GymManagementApplication.java
│   │   │   ├── controller/
│   │   │   │   └── AcademyController.java
│   │   │   ├── loader/
│   │   │   │   └── AcademyLoader.java
│   │   │   ├── model/
│   │   │   │   └── Academia.java
│   │   │   ├── repository/
│   │   │   └── service/
│   │   │       ├── CrudService.java
│   │   │       └── AcademyService.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── academias.txt
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       └── java/br/edu/infnet/gabriel/gym_management/
│           └── GymManagementApplicationTests.java
├── pom.xml
├── Postman_Collection_Feature1.json
└── README.md
```

---

## 🔧 Configuração do Projeto

### pom.xml - Dependências

As seguintes dependências foram configuradas:

```xml
<!-- Spring Web -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- Lombok para anotações @Data, @NoArgsConstructor, @AllArgsConstructor -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>

<!-- Spring Data JPA -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- H2 Database (para futuros desenvolvimentos) -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

### application.properties

Configurações básicas da aplicação:

```properties
# Porta padrão
server.port=8080

# Nome da aplicação
spring.application.name=gym-management

# Configurações de logging
logging.level.root=INFO
```

---

## 💡 Padrões de Design Utilizados

### 1. **Service Layer Pattern**
- Isolamento da lógica de negócio
- `AcademyService` implementa a interface `CrudService`
- Facilita testes unitários e manutenção

### 2. **Injeção de Dependência (DI)**
- Injeção via construtor no `AcademyController` e `AcademyLoader`
- Padrão recomendado pelo Spring

### 3. **Generic Types**
- `CrudService<T, ID>` é genérica
- Reutilizável para futuras entidades

### 4. **Component Scanning**
- `@Component` em `AcademyLoader` para auto-registro
- `@Service` em `AcademyService` para serviços
- `@RestController` em `AcademyController` para controllers REST

### 5. **Application Initialization**
- `ApplicationRunner` para carregar dados ao iniciar
- Padrão limpo para populações de dados

---

## ⚠️ Observações Importantes

### Armazenamento em Memória

- **Dados são perdidos** quando a aplicação é reiniciada
- Ideal para **desenvolvimento e testes**
- Para **produção**, integrar com banco de dados (JPA/Hibernate)

### Thread-Safety

- `ConcurrentHashMap` garante operações seguras em ambientes multi-thread
- `AtomicInteger` para geração de IDs segura

### Escalabilidade Futura

Quando migrar para banco de dados:
1. Adicionar `@Entity` e `@Table` na classe `Academia`
2. Criar `AcademyRepository extends JpaRepository<Academia, Integer>`
3. Modificar `AcademyService` para usar o repositório
4. Adicionar `@EnableJpaRepositories` na classe main

---

## 🐛 Troubleshooting

### Problema: Porta 8080 já está em uso

**Solução:** Altere a porta em `application.properties`:
```properties
server.port=8081
```

### Problema: Arquivo academias.txt não é encontrado

**Solução:** Certifique-se que o arquivo está em `src/main/resources/academias.txt`

### Problema: Erro de compilação com Lombok

**Solução:** 
- Instale o plugin Lombok na IDE
- Habilite annotation processing: Settings → Build, Execution → Annotation Processors → Enable annotation processing

---

## 📚 Recursos e Referências

- [Spring Boot Official Documentation](https://spring.io/projects/spring-boot)
- [Spring REST Documentation](https://spring.io/guides/gs/rest-service/)
- [Lombok Project](https://projectlombok.org/)
- [Postman Documentation](https://learning.postman.com/)

---

## 📞 Suporte

Para dúvidas ou problemas, verifique:
1. Se o Java 21+ está instalado: `java -version`
2. Se Maven está instalado: `mvn -version`
3. Se os arquivos de configuração estão corretos
4. Se a aplicação está rodando na porta 8080

---

**Feature 1 Implementada com Sucesso! ✅**

