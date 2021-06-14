# [Currency converter](https://jaya-currency-converter.herokuapp.com)

Implementação do convesor de moedas visando cumprir os requisitos do desafio técnico proposto.
https://jaya-currency-converter.herokuapp.com

## Stack

A aplicação foi desenvolvida com:

- [Kotlin](https://github.com/JetBrains/kotlin) como linguagem de programação
- [Javalin](https://github.com/tipsy/javalin) como framework web
- [Koin](https://github.com/InsertKoinIO/koin) framework de injeção de dependências
- [Jackson](https://github.com/FasterXML/jackson-module-kotlin) as data bind serialization/deserialization
- [HikariCP](https://github.com/brettwooldridge/HikariCP) as datasource to abstract driver implementation
- [H2](https://github.com/h2database/h2database) como banco de dados
- [Exposed](https://github.com/JetBrains/Exposed) como framework da camada de persistência de dados

## Estrutura
      + config/
          Todas as configurações da aplicativos. Javalin, Koin e Database.
      + domain/
        + repository/
            Camada de persistência e definição de tabelas.
        + service/
            Camada contendo a lógica do negócio.
      + web/
        + controllers
            Classes que representam os Endpoints da aplicação.
        Router Roteador para recursos
      - Main.kt <- Classe main da aplicação.


### Pré-requisitos
-  Java(JDK 1.8)
-  Gradle


### Inciando a aplicação

#### build
```
$ gradle clean build
```

#### Inicie o servidor
```
$ gradle run
```

## Endpoints

Local
> http://localhost:7000

Heroku
> https://jaya-currency-converter.herokuapp.com
 
Moedas disponíveis `BRL`, `USD`, `EUR`, `JPY`


### Endpoints



| HTTP        | URI           | Ação  | Corpo da requisição  |
| ------------- |-------------|:-----|--------- | 
| `POST` | `/transactions` | Calcula a conversão entre moedas| ``` { "from": "BRL", "to": "USD", "amount": 100, "userId": 1 } ``` |
| `GET` | `/users/{userId}/transactions` | Lista todas transações realizadas por usuário | N/A |



## TODO 


- Documentação dos endpoints
- Tratamento de exceções
- Testes unitários
- Testes de integração
- CI/CD

