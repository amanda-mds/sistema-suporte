# Sistema de Suporte Técnico

Projeto desenvolvido em aula, junto com o professor, no curso técnico do SENAI, como prática de desenvolvimento Back-end com Java e Spring Boot.

> **Sobre este projeto:** este foi um exercício guiado em sala de aula, com foco em fixar conceitos específicos na prática — autenticação, autorização e modelagem de dados — e não um projeto autoral completo.

## Sobre o sistema

O sistema simula um portal de abertura e gerenciamento de chamados técnicos (informática, elétrica e zeladoria), permitindo que qualquer pessoa abra uma solicitação de suporte e que técnicos cadastrados acompanhem, assumam e concluam esses chamados.

## Funcionalidades

### Portal público (sem login)
- Abertura de solicitação de suporte, informando NIF, nome do solicitante, sala, patrimônio, tipo de problema e descrição

### Painel do técnico (requer login)
- Listagem de todas as solicitações, com filtros por tipo, status e nome do solicitante
- Assumir uma solicitação pendente
- Concluir uma solicitação em andamento
- Editar dados de uma solicitação
- Excluir uma solicitação

## Fluxo da solicitação

```
PENDENTE → EM_ANDAMENTO → CONCLUIDO
```

As transições de status são validadas na camada de serviço: só é possível assumir uma solicitação pendente, e só é possível concluir uma solicitação que já está em andamento.

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA
- Hibernate
- Thymeleaf
- Bean Validation
- MySQL
- Maven

## Estrutura do projeto

```
src/main/java/com/senai/suporte/suporte
├── config
├── controller
├── exception
├── model
├── repository
└── service
```

A aplicação segue a arquitetura em camadas: **controller** (requisições e navegação), **service** (regras de negócio), **repository** (acesso a dados), **model** (entidades) e **config** (segurança e configuração da aplicação).

## Segurança

- Senhas de técnico armazenadas com hash **BCrypt**
- Credenciais de banco de dados lidas por variável de ambiente (`DB_PASSWORD`), nunca fixas no código

## Como executar o projeto

### Pré-requisitos
- Java 21
- MySQL

### Configuração do banco de dados

A senha do MySQL é lida da variável de ambiente `DB_PASSWORD`. Defina-a antes de rodar o projeto:

```bash
export DB_PASSWORD=sua_senha_aqui
```

No Windows (PowerShell):

```powershell
$env:DB_PASSWORD="sua_senha_aqui"
```

### Executando

```bash
./mvnw spring-boot:run
```

A aplicação sobe em `http://localhost:8080`.

## Possíveis evoluções

Como projeto de prática, existem pontos que ficariam para uma próxima iteração:

- Testes automatizados das regras de negócio
- Relacionamento direto entre a solicitação assumida e a entidade `Tecnico` (hoje o técnico responsável é registrado como texto livre)
- Fluxo de aprovação para cadastro de novos técnicos, em vez de cadastro direto

## Sobre este projeto

Este projeto foi desenvolvido durante as aulas do curso Desenvolvimento Back-end do SENAI, com o objetivo de praticar conceitos de Back-end com Java e Spring Boot em um contexto guiado.
