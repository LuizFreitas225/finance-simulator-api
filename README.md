## 🧾 Visão Geral

O **Finance Simulator API** é um projeto backend desenvolvido em Kotlin, com o objetivo de fornecer uma API para simulações financeiras. Utiliza o framework Spring Boot e está configurado para execução em ambiente Docker. Atualmente, a API oferece a simulação de financiamentos com prestações fixas, baseada na fórmula oficial do Banco Central do Brasil (BCB) utlizada [Calculadora do cidadão.](https://www3.bcb.gov.br/CALCIDADAO/publico/exibirFormFinanciamentoPrestacoesFixas.do?method=exibirFormFinanciamentoPrestacoesFixas)

## 🚀 Tecnologias Utilizadas

- **Kotlin**: Linguagem de programação principal do projeto.
- **Spring Boot**: Framework para criação de aplicações web e APIs RESTful.
- **Maven**: Ferramenta de automação de compilação e gerenciamento de dependências.
- **Docker**: Plataforma para criação e gerenciamento de containers, facilitando a implantação da aplicação.
- **PostgreSQL**: Banco de dados relacional utilizado para registro das simulaçõe
- **Klint**: Ferramenta de linting para garantir a padronização e qualidade do código Kotlin.
- **JUnit**: Framework de testes unitários utilizado para validar o comportamento da aplicação.
- **JMeter**: Ferramenta de testes de desempenho utilizada para validar a escalabilidade da API.

## 📁 Estrutura do Projeto

A estrutura de diretórios do projeto é a seguinte:

```

finance-simulator-api/
├── .github/workflows/       # Configurações de integração contínua (CI)
├── .mvn/wrapper/            # Scripts do Maven Wrapper
├── docker/                  # Arquivos relacionados à configuração do Docker
├── src/                     # Código-fonte da aplicação
│   └── main/
│       ├── kotlin/          # Código-fonte em Kotlin
│       └── resources/       # Recursos da aplicação (ex: application.properties)
├── .gitignore               # Arquivos e diretórios ignorados pelo Git
├── mvnw                     # Script para execução do Maven Wrapper (Unix)
├── mvnw.cmd                 # Script para execução do Maven Wrapper (Windows)
└── pom.xml                  # Arquivo de configuração do Maven

```

## 

### 🔍 Detalhes Técnicos do Projeto

**📋 Logs:**

Foi utilizada a interface **SLF4J** para o gerenciamento de logs, por ser amplamente adotada na comunidade Java e permitir integração com diversas ferramentas de observabilidade. O uso do **MDC (Mapped Diagnostic Context)** possibilita um mapeamento mais eficiente dos logs por contexto, o que facilita a análise em ferramentas como **Elastic Stack** e **Grafana Loki**.

---

**🧹 Linter:**

O linter escolhido foi o **Klint**, uma ferramenta desenvolvida pelo Pinterest para análise e formatação de código Kotlin. Para facilitar a integração na pipeline, utilizei um recurso não oficial que permite o uso do Klint como plugin Maven (não recomendado para projetos em produção), o que viabilizou a execução automática da verificação de estilo de código como parte do processo de build.

---

**🔁 Pipeline (CI/CD):**

Utilizei o **GitHub Actions** como solução de integração contínua. A pipeline é baseada no Java 17 e executa os seguintes passos:

- `mvn package`: compila e valida o projeto;
- Executa os testes automatizados;
- Verifica o padrão de código com o linter (Klint).

Caso alguma dessas etapas falhe, a pipeline é interrompida, garantindo que apenas código válido e testado seja aceito.

---

**🛡️ Validações:**

As validações dos dados recebidos pela API são feitas com **Jakarta Validation**, garantindo a consistência das entradas. Criei uma exceção personalizada chamada `LoanSimulationException`, utilizada para capturar erros específicos na camada de serviço. Além disso, um handler global foi implementado para interceptar e padronizar todas as respostas de erro, retornando-as no formato definido pela classe `ExceptionResponseDefault`.

---

**⚙️ Recursos Assíncronos:**

Como o foco da API é retornar rapidamente o resultado da simulação de parcelas fixas, o método responsável por persistir os dados foi anotado com `@Async`, permitindo que a gravação no banco ocorra em segundo plano. Em caso de erro durante a persistência, o sistema apenas registra o log sem lançar exceções, garantindo que o cliente receba a resposta da simulação mesmo que o salvamento falhe.

**🐳 Docker:**

O Docker foi utilizado para **containerizar o banco de dados**, facilitando o setup do ambiente de desenvolvimento. Essa abordagem é especialmente útil em ambientes de desenvolvimento colaborativo ou em situações em que o projeto é hospedado em servidores Linux ou máquinas virtuais (VMs), permitindo fácil replicação e isolamento do serviço de banco de dados.

📘 Swagger OpenAPI:
Adicionei essa dependência para facilitar a documentação e a utilização da API. Através dela, é possível visualizar toda a estrutura dos endpoints, suas descrições, parâmetros e respostas. Além disso, a ferramenta permite realizar requisições diretamente pela interface, o que é útil tanto para testes quanto para entendimento da API por outros desenvolvedores.

### 🛠️ Futuras Implementações

- **Integração com ferramenta de análise de logs (Elastic Stack):** disponibilizar os logs da aplicação em ferramentas como **Elasticsearch + Kibana**, permitindo visualização, busca e análise em tempo real.
- **Inclusão de `correlationId` via MDC:** adicionar o **Mapped Diagnostic Context (MDC)** com um `correlationId` gerado por requisição. Isso facilitará o rastreamento de chamadas em ambientes distribuídos e a identificação de fluxos completos nos logs.
- **Separação de validações em classe dedicada:** mover as regras de validação da camada de serviço para uma classe específica de **validações auxiliares**, promovendo uma melhor separação de responsabilidades, tornando o código mais limpo, coeso e testável.

## ⚙️ Configuração e Execução

### Pré-requisitos

- Java 17 ou superior instalado.
- Docker instalado e em execução.
- Maven instalado (ou utilizar o Maven Wrapper fornecido)

### Passos para Execução

1. **Clonar o repositório:**

    ```bash
    
    git clone https://github.com/LuizFreitas225/finance-simulator-api.git
    cd finance-simulator-api
    ```

2. **Disponibilize o banco PostgresSq via Docker:**

   ```bash
   
   cd docker
   docker-compose up -d
   ```

3. **Compilar o projeto:**

   Utilize o Maven Wrapper para compilar o projeto:

    ```bash
    cd ..
    mvn clean install
    ```

4. **Executar a aplicação:**

   Após a compilação, execute a aplicação você pode executar o

    ```bash
    
    mvn spring-boot:run
    ```


   Ou, faça através da sua IDE de preferênica.

5. **Acessar a API:**

   Com a aplicação em execução, a API estará disponível em:

    ```
    
    http://localhost:8080
    
    ```

6. [Clique aqui para acessar Dcumentação OpenAPI](http://localhost:8080/swagger-ui/index.html)

   Ao clicar no link, a página será carregada no seu navegador. Nela, é possível visualizar todos os endpoints disponíveis, seus contratos (métodos, parâmetros e respostas) e até realizar requisições diretamente pela interface.
