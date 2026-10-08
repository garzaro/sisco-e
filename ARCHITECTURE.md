Documento de Arquitetura de Software (DAS) - v3
1. Visão Geral e Especificações Básicas
   Este documento define a estrutura arquitetural padrão para a aplicação, estabelecendo diretrizes claras e sustentáveis de desenvolvimento. A arquitetura foi desenhada focando em manutenibilidade, separação de responsabilidades e alinhamento com padrões consolidados do mercado.
   Componente / Tecnologia
   Especificação / Versão

Linguagem
Java 21 (LTS)
Framework Base
Spring Boot 3.x (Spring MVC)
Gerenciador de Dependências
Apache Maven
Estilo Arquitetural
Arquitetura em Camadas (Layered Architecture) / Monólito Modular

2. Estrutura de Pacotes (Maven Standard)
   A organização do código segue a convenção do Maven associada ao padrão MVC, garantindo coesão e isolamento de responsabilidades.
   com.sisco_e.escola/  
   │  
   ├── config/             # Configurações globais de frameworks (Beans, Swagger, CORS)  
   ├── security/           # Segurança da Aplicação (SecurityFilterChain, Filters, Providers, JWT)  
   ├── controller/         # Camada de Entrada (REST Endpoints, Request Handling)  
   ├── dto/                # Data Transfer Objects (Requests e Responses)  
   │   ├── request/  
   │   └── response/  
   ├── mapper/             # Mapeamento entre Entidades e DTOs (MapStruct)  
   ├── service/            # Camada de Negócio (Regras de negócio e interfaces)  
   │   └── impl/           # Implementações das regras de negócio  
   ├── repository/         # Camada de Acesso a Dados (Spring Data JPA / Interfaces)  
   ├── domain/             # Entidades de Domínio, Modelos e Audit Listener  
   ├── exception/          # Tratamento Global de Exceções e Exceções Customizadas  
   └── Application.java    # Classe Principal de Inicialização do Spring Boot  


3. Descrição das Camadas
   3.1. Security (Segurança)
   Contém a configuração de segurança do Spring Security (SecurityFilterChain), filtros de autenticação/autorização (ex: JWT/OAuth2), handlers de acesso negado e provedores de autenticação.
   Isola as regras e infraestrutura de segurança das configurações gerais da aplicação.
   3.2. Controller (Apresentação / Entrada)
   Responsável exclusivamente por receber as requisições HTTP, validar a entrada inicial de dados e retornar a resposta adequada.
   Não deve conter regras de negócio nem manipulação direta de dados de banco.
   Consome DTOs de Request e retorna DTOs de Response.
   3.3. Service (Regras de Negócio)
   Contém a lógica de negócio central da aplicação e orquestração de chamadas.
   Lida com transações (@Transactional).
   Processa dados recebidos da Controller e se comunica com a camada de Repository.
   3.4. Repository (Persistência)
   Interface de integração com o banco de dados via Spring Data JPA.
   Deve conter apenas operações de busca e persistência, evitando lógica de domínio.
   3.5. Domain (Modelos / Entidades)
   Mapeamento das entidades relacionais (JPA/Hibernate).
   Representa o estado e comportamento do domínio principal. Contém metadados de auditoria.
   3.6. DTO (Data Transfer Object)
   Objetos simples para transferência de dados entre o cliente e a API.
   Utiliza records do Java 21 para garantir imutabilidade e concisão.
   3.7. Mapper
   Camada isolada para conversão bidirecional entre DTOs e Entidades usando MapStruct.
4. Invariantes de Design e Regras Arquiteturais
   As diretrizes abaixo são regras invioláveis da arquitetura e garantem o isolamento e acoplamento fraco entre as camadas:
   Fluxo Unidirecional de Chamadas: A dependência entre camadas é estritamente top-down.
   Services nunca chamam ou injetam Controllers.
   Repositories nunca chamam Services ou Controllers.
   Isolamento de Persistência (Vazamento de Dados):
   DTOs de entrada e saída nunca vazam para a camada de persistência/repositório.
   Entidades JPA de domínio nunca são expostas diretamente nos Controllers ou retornadas para o cliente da API. Todas as conversões devem ser intermediadas pela camada mapper com MapStruct.
   Injeção de Dependências: Injeção obrigatória via construtor. Evitar @Autowired direto em campos/atributos para facilitar testes unitários e garantir imutabilidade.
5. Padrões de Desenvolvimento e Ferramentas Auxiliares
   MapStruct: Utilizado para o mapeamento automático entre DTOs e Entidades. As interfaces de conversão devem ficar centralizadas no pacote mapper/, gerando o código de conversão em tempo de compilação sem reflexão em runtime.
   Lombok: Permitido exclusivamente para redução de boilerplate em Entidades de domínio (como @Getter, @Setter, @Builder, @NoArgsConstructor). Não deve ser usado para gerar DTOs, pois estes devem utilizar os records nativos do Java 21.
   Auditoria de Dados: Habilitada globalmente via Spring Data JPA (@EnableJpaAuditing). Entidades auditadas devem estender uma classe base de auditoria contendo campos como createdAt, createdBy, updatedAt e updatedBy para rastreabilidade automática.
   Recursos do Java 21: Uso extensivo de Records para imutabilidade dos DTOs, Pattern Matching, e suporte a Virtual Threads quando aplicável para I/O.
   Tratamento de Exceções: Centralizado via @ControllerAdvice ou @RestControllerAdvice, retornando respostas uniformes de erro.
   Gerenciamento de Build: O arquivo pom.xml é a fonte única da verdade para dependências e plugins.
