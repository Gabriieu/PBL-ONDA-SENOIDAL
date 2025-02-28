# Project Based Learning - Ondas Senoidais

Projeto interdisciplinar desenvolvido como parte do curso de Engenharia de Computação da Faculdade Engenheiro Salvador Arena (FESA). Envolve as disciplinas de Física Geral e Experimental II, Cálculo Avançado, Programação Orientada a Objetos e Banco de Dados II. A proposta é desenvolver um sistema de simulação de ondas senoidais, permitindo visualização de gráficos e análise dos dados.

## Índice
- [Sobre o Projeto](#sobre-o-projeto)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Endpoints da API](#endpoints-da-api)

## Sobre o Projeto

O objetivo deste projeto é desenvolver um sistema para simulação de ondas senoidais, com funcionalidades como:
- Visualização de gráficos;
- Criação de usuários e autenticação;
- CRUD de simulações;
- Reprodução sonora das ondas;
- Geração de PDFs;
- Integração com a OpenAI API.

## Tecnologias Utilizadas

- **Linguagens**: Java e VBA
- **Framework**: Spring Boot 3.2.10
- **Banco de Dados**: SQL Server
- **Bibliotecas e Ferramentas**:
   - Spring Boot Starter Data JPA
   - Spring Boot Starter Validation
   - Spring Boot Starter Web
   - Spring Boot Starter Security
   - Spring Boot DevTools
   - SQL Server (mssql-jdbc)
   - JWT (jjwt-api, jjwt-impl, jjwt-jackson)
   - JasperReports (jasperreports, jasperreports-chart-customizers)
   - Lombok



# Endpoints da API

URL base: `http://localhost:8080`

## Usuários
| Ação               | Endpoint          | Método HTTP | Descrição                           |
|--------------------|------------------|------------|-----------------------------------|
| Cadastrar         | `/users`         | POST       | Cria usuário                     |
| Obter todos      | `/users`         | GET        | Obtém todos usuários             |
| Obter por ID     | `/users/{id}`    | GET        | Obtém usuário por ID             |
| Obter informações | `/users/info`    | GET        | Obtém informações do usuário logado |
| Atualizar senha  | `/users/password` | PATCH      | Atualiza a senha                 |
| Atualizar nome   | `/users/name`    | PATCH      | Atualiza o nome                  |
| Excluir usuário  | `/users/delete`  | DELETE     | Exclui usuário                   |

## Simulações de Onda
| Ação               | Endpoint          | Método HTTP | Descrição                     |
|--------------------|------------------|------------|-----------------------------|
| Criar             | `/onda`          | POST       | Cria simulação               |
| Obter todas       | `/onda`          | GET        | Obtém todas simulações       |
| Obter por ID      | `/onda/{id}`     | GET        | Obtém simulação por ID       |
| Excluir           | `/onda/{id}`     | DELETE     | Exclui simulação             |

## Relatórios
| Ação               | Endpoint                      | Método HTTP | Descrição                                            |
|--------------------|------------------------------|------------|----------------------------------------------------|
| Obter PDF         | `/report/print?id=x&time=y`  | GET        | Obtém PDF com gráfico da onda em um instante de tempo |
| Obter simulações  | `/report/print/user`         | GET        | Obtém todos os registros do usuário logado        |
| Obter por ID      | `/report/print/user/all`     | GET        | Obtém todos usuários cadastrados (apenas admin)   |

## Autenticação
| Ação       | Endpoint       | Método HTTP | Descrição       |
|-----------|---------------|------------|---------------|
| Autenticar | `/auth/login` | POST       | Realiza login |

## Áudio
| Ação        | Endpoint      | Método HTTP | Descrição                           |
|------------|--------------|------------|-----------------------------------|
| Obter áudio | `/audio/{id}` | GET        | Obtém arquivo .WAV da simulação  |

## Explicação da Onda
| Ação         | Endpoint  | Método HTTP | Descrição                                |
|-------------|----------|------------|----------------------------------------|
| Explicação  | `/gpt`   | POST       | Obtém áudio de explicação da onda     |

## Dashboard
| Ação               | Endpoint               | Método HTTP | Descrição                      |
|--------------------|-----------------------|------------|--------------------------------|
| Obter dashboard   | `/dashboard/download` | GET        | Download da planilha          |



