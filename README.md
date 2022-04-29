# techbank-api

Nosso grupo é composto por:
* Antônio Carlos Andrade (Junior)
* Ederson Rafael Nonnemacher
* Renato Marques da Silva

O projeto Java proposto pela Wipro e Gama Academy consiste em criar um sistema bancário,
que nomeamos de techBANK, onde este conforme escopo deve possuir uma classe gerencia conta, uma classe conta,
sendo essa abstrata, portanto não podendo ser instanciada, classes conta corrente e conta especial,
que herdam as funções e atributos da classe conta, sendo que a classe conta especial possui a mais o
atributo de limite, onde o cliente pode efetuar um saque se utilizando deste limite, o que não ocorre na classe
conta corrente, onde o cliente somente pode efetuar saques com o valor que consta em saldo na conta,
em ambas as contas é possível também efetuar um depósito, optamos também por criar uma classe cliente,
pois em conversa definimos que cada conta bancária necessita de um cliente. Além disso, deve possuir um cartão de
crédito associado a alguma das contas. A classe gerencia conta, é a classe main, responsável por gerenciar
as operações do menu, direcionando as funções para determinadas classes conforme escolha do usuário.

Abaixo uma descrição dos endpoints de cada classe:

* Funções cliente: Adicionar/atualizar dados/excluir/mostrar todos/mostrar por ID;
* Funções cartão de crédito: Adicionar/excluir/mostrar todos/mostrar por ID;
* Funções conta corrente: Adicionar/excluir/mostrar todos/mostrar por ID;
* Funções conta especial: Adicionar e definir limite/excluir/mostrar todos/mostrar por ID;
* Funções das transações: Efetuar depósito e efetuar saque na conta corrente e conta especial:

Inserimos também a função de extrato de operações, onde a cada operação realizada como saque e/ou depósito, essa
é registrada no extrato, podendo o usuário consultar este extrato a qualquer momento, verificando na impressão
a data da operação, o tipo da operação, o valor da mesma e o tipo da conta onde foi realizado.

Para a solução deste problema apresentado, desenvolvemos uma API, utilizando as tecnologias e dependências abaixo:

* Java 11
* Spring Boot
* MySQL
* ModelMapper
* Lombok
* JUnit 5
* Mockito
* Postman
* Swagger
* Trello

Abaixo passo a passo de como consumir a api em sua máquina:

* Clonar o repositório em sua máquina;
* Importar o projeto preferencialmente utilizando a IDE Intellij;
* Aguardar o Maven importar as dependências;
* Habilitar o lombok;
* Alterar a senha do banco de dados no arquivo application.properties, na linha spring.datasource.password=;
* Instalar o MySQL Server e também o workbench;
* Iniciar a aplicação;
* Colar o link http://localhost:8080/swagger-ui/#/ no seu navegador para acessar a documentação da api via Swagger:

A linguagem de desenvolvimento foi o Java 11, utilizando o framework Spring Boot,
também utilizamos o Swagger e o Postman para a documentação, testes e validação
dos endpoints da aplicação, abaixo seguem imagens de alguns testes realizados.

Criado um cliente, retorno ID = 9


Criado um cartão de crédito, retorno ID = 14


Criado uma conta corrente, retorno ID = 9


Efetuado um depósito


Efetuado um saque


Extrato da operação depósito
