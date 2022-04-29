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

![1](https://user-images.githubusercontent.com/45982271/165943555-8d021648-bf16-46c2-a344-afe59d449bbb.PNG)
![2](https://user-images.githubusercontent.com/45982271/165943629-36bca478-fcfe-41aa-af77-3c50326e000f.PNG)

Criado um cartão de crédito, retorno ID = 14

![3](https://user-images.githubusercontent.com/45982271/165943652-d10586b2-1fcd-42d1-85d0-e2fd24b1ace4.PNG)
![4](https://user-images.githubusercontent.com/45982271/165943663-d07d943e-6f2d-4500-b917-f383434f7ed8.PNG)

Criado uma conta corrente, retorno ID = 9

![5](https://user-images.githubusercontent.com/45982271/165943673-a5a86c7e-f55c-49a3-9a73-42607f75f855.PNG)
![6](https://user-images.githubusercontent.com/45982271/165943683-b4bd84de-a0bb-46b7-b5b9-67b56760e1ee.PNG)

Efetuado um depósito

![7](https://user-images.githubusercontent.com/45982271/165943698-55aa8dcd-c3ff-4494-8b48-ddcae1050af2.PNG)
![8](https://user-images.githubusercontent.com/45982271/165943704-ffe0891c-aa05-4d27-afc1-d796c8fa23c5.PNG)

Efetuado um saque

![9](https://user-images.githubusercontent.com/45982271/165943724-3180e339-1620-42ea-b9a6-0d060670b8c8.PNG)
![10](https://user-images.githubusercontent.com/45982271/165943728-d2807ebb-e589-458f-be7c-4bd34a7456e1.PNG)

Extrato da operação depósito

![11](https://user-images.githubusercontent.com/45982271/165943742-390119d1-5c75-4882-a3f5-ef31592de638.PNG)
![12](https://user-images.githubusercontent.com/45982271/165943754-5e1916e0-5504-47d2-ab23-d494168bfea8.PNG)
