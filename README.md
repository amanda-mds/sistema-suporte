# Sistema de Suporte Técnico

Projeto desenvolvido em Java com Spring Boot para gerenciamento de chamados de suporte técnico em um ambiente educacional, como o SENAI.

A proposta do sistema é centralizar as solicitações de suporte, organizar o atendimento e permitir o acompanhamento do status de cada chamado.

## Problema

Em ambientes com muitas salas, equipamentos e diferentes tipos de ocorrência, as solicitações de suporte podem acabar sendo feitas de forma desorganizada, sem registro centralizado, sem acompanhamento de status e sem definição clara de quem está responsável pelo atendimento.

Isso pode dificultar o controle dos chamados e atrasar a resolução dos problemas.

## Solução

O sistema permite que usuários registrem solicitações de suporte informando os dados necessários para o atendimento.

Os técnicos possuem uma área restrita onde podem visualizar os chamados, aplicar filtros, assumir solicitações, editar informações, concluir atendimentos e excluir registros.

Cada chamado passa pelo fluxo:

`PENDENTE → EM_ANDAMENTO → CONCLUIDO`

Dessa forma, é possível acompanhar de maneira simples em qual etapa cada solicitação se encontra.

## Funcionalidades

- abertura de chamados;
- cadastro e login de técnicos;
- painel restrito para técnicos;
- busca por nome do solicitante;
- filtros por tipo de problema e status;
- técnico pode assumir um chamado;
- registro do técnico responsável;
- edição de solicitações;
- conclusão de chamados;
- exclusão de registros;
- controle de status do atendimento.

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA
- Hibernate
- Bean Validation
- MySQL
- Thymeleaf
- HTML
- CSS
- Maven

## Estrutura do projeto

```text
src/main/java/com/suporte/
├── config
├── controller
├── model
├── repository
└── service
```

A aplicação foi organizada em camadas para separar responsabilidades:

controller: recebe as requisições e controla a navegação;
service: concentra as regras de negócio;
repository: realiza a comunicação com o banco de dados;
model: contém as entidades do sistema;
config: contém as configurações da aplicação e de segurança.

## Fluxo dos chamados

Quando uma solicitação é criada, ela começa com o status:

PENDENTE

Quando um técnico assume o atendimento:

EM_ANDAMENTO

Após a conclusão:

CONCLUIDO

Esse fluxo ajuda a organizar e acompanhar o andamento de cada chamado.

## Banco de dados

O projeto utiliza MySQL para persistência dos dados.

Exemplo de configuração:

```text
spring.datasource.url=jdbc:mysql://localhost:3306/suporte
spring.datasource.username=root
spring.datasource.password=SUA_SENHA

spring.jpa.hibernate.ddl-auto=update
```

## Sobre o projeto

Este projeto foi desenvolvido com foco em Back-end utilizando Java e Spring Boot.

O objetivo foi praticar conceitos como autenticação, persistência de dados, arquitetura em camadas, regras de negócio e gerenciamento de chamados.

A interface foi desenvolvida apenas como apoio para demonstrar o funcionamento do sistema.
