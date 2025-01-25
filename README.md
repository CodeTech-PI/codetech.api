# codetech.api

## Descrição do Projeto
**CodeTech API** é um sistema backend projetado para atender as necessidades de pequenos estúdios de tatuagem. A aplicação tem como objetivo:
- Facilitar a **gestão de controle de estoque** de insumos e materiais utilizados no dia a dia.
- Oferecer ferramentas para **acompanhamento e análise de faturamento**, proporcionando maior eficiência e organização, sendo utilizado dashboard e gráficos.

O sistema foi desenvolvido utilizando boas práticas de desenvolvimento, como o uso de **DTOs (Data Transfer Objects)** para garantir uma comunicação eficiente entre as camadas da aplicação.

---

## Tecnologias Utilizadas
O projeto foi desenvolvido utilizando as seguintes tecnologias:

- **Java**: Linguagem principal para o desenvolvimento da aplicação.
- **Spring Boot**: Framework utilizado para simplificar a criação de aplicações robustas e escaláveis.
- **Swagger API**: Ferramenta de documentação e teste das rotas de API.
- **Mockito** e **Mock**: Frameworks utilizados para a criação de testes unitários e simulações.
- **DTO (Data Transfer Object)**: Padrão utilizado para transferir dados entre as camadas de forma eficiente e organizada.
- **MySQL**: Banco de dados relacional utilizado para armazenar as informações do sistema.

---

## Estrutura do Projeto
A estrutura do projeto foi planejada para seguir o padrão de camadas, garantindo clareza e separação de responsabilidades:

1. **Controller**: Responsável por receber as requisições e encaminhá-las para os serviços apropriados.
2. **Service**: Contém a lógica de negócio da aplicação.
3. **Repository**: Realiza a interação com o banco de dados.
4. **DTO (Data Transfer Object)**: Utilizado para transferir dados entre a camada Controller e Service, garantindo que apenas as informações necessárias sejam manipuladas.
5. **Model/Entity**: Representa as tabelas do banco de dados e mapeia os dados.

---

## Funcionalidades Principais
- **Controle de Estoque**:
  - Consulta sobre faturamento da empresa.
  - Cadastro, edição, exclusão e consulta de itens no estoque.
  - Cadastro, edição, exclusão e consulta de clientes.
  - Cadastro, edição, exclusão e consulta das unidades da empresa.

- **Documentação Interativa**:
  - Teste e visualização das rotas da API por meio do Swagger.

---

## Requisitos para Execução
Para executar este projeto, é necessário ter instalado:

- Java 17 ou superior.
- Maven para gerenciar as dependências.
- Banco de dados relacional (MySQL).
- Ferramenta de teste para APIs (Postman, Insomnia, etc.).

---

## Como Rodar o Projeto
1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/codetech-api.git


 
