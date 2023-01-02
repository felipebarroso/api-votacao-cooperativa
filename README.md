# Votação Cooperativa #

Este projeto tem o objetivo de registrar e contabilizar os votos dos associados de uma coperativa durante as sessões de pautas abertas em assembleias.

## Informações do Projeto ##

* Aplicação com Spring-Boot 2 e Java 11.
* Utiliza lombok, necessário instalar na IDE (utilizado Spring Tool Suite 4). https://projectlombok.org/download
* Start configurado para porta 8080.
* Swagger pode ser acessado em http://localhost:8080/swagger-ui.html#
* Aplicação está configurada em três versões (1.0, 1.1 e 1.2) para exemplificação de versionamento.
* Necessário ter instalado o Maven.
* Necessário ter o Docker e Docker-Compose instalado caso deseje subir em container.
* Necessário alterar configuração do arquivo "application.properties" para indicar um banco Postgres caso deseje não utilizar container.
* URL do serviço externo de validação de CPF precisa ser informado no arquivo "application.properties" na propertie "url.valida.cpf"
* Por padrão a propertie "url.valida.cpf" aponta para um endpoint local de teste.

## Start da Aplicação ##

É possível subir aplicação e banco de dados em container docker através do docker-compose seguindo os seguintes passos:

* Executar o comando do maven "mvn package" para gerar o arquivo jar.
* Executar o comando "docker-compose -f docker-compose.yml up" também na pasta raiz do projeto.
* Após inicialização do container, acessar a url http://localhost:8080/swagger-ui.html#

Outra forma é sem container:

* É preciso alterar o arquivo "application.properties" para indicar um banco de dados Postgres.
* Executar o comando "mvn spring-boot:run" na pasta raiz da aplicação.
* Após inicialização acessar a url http://localhost:8080/swagger-ui.html#


## Modelo de dados ##

![Entityrelationshipdiagram1](https://user-images.githubusercontent.com/42699918/111929313-c59b1500-8a94-11eb-9f2b-a99ec3e73e7f.png)


## ARQUITETURA IMPLEMENTADA ##

![Diagrama- Arquitetura-Votacao-Cooperativa-Local](https://user-images.githubusercontent.com/42699918/210192286-a6071542-f7a4-4fc5-8996-2ce6f59c5c09.jpg)

Nesta arquitetura o processo de registrar um voto está síncrono, realizando a verificação do CPF em API externa e gravando no banco antes de responder a requisição do voto, como ilustrado no diagrama abaixo. Esta implementação não considerou um cenário de grande fluxo de requisições.

Implantação local:

![ReceverVotoSincrono](https://user-images.githubusercontent.com/42699918/210193135-ef298267-73b6-4732-aa87-01d723f0cfd8.jpg)

Implantação em nuvem (utilizada aws como exemplo):

![Diagrama- Arquitetura-Votacao-Cooperativa-AWS](https://user-images.githubusercontent.com/42699918/210231311-61acc2b1-fe0e-4dd2-b195-8e5cceca52b3.jpg)


## ARQUITETURA SUGERIDA ##

Considerando um cenário de centanas de milhares de requisições, que exige da aplicação um processamento assíncrono e escalabilidade, proponho a seguinte arquitetura em nuvem (utilizada aws como exemplo):

![Diagrama-Arquitetura-Votacao-Cooperativa-AWS](https://user-images.githubusercontent.com/42699918/210192718-ced6850a-92c4-46e4-8b14-f9bfedcc299a.jpg)

Nesta arquitetura a implementeção também necessita ser diferente, pois a intensa comunicação com o banco de dados relacional, para consultar e gravar, além da integração com API externa certamente irão onerar o processo. Nessa caso vi a necessidade da utilização de fila, para processar os votos, e cache para realizar as validações ao receber os votos. Sendo assim o processo de registrar um voto foi quebrado em dois fluxos:

![ReceberVoto](https://user-images.githubusercontent.com/42699918/210192972-4b6f9636-2de1-42bf-b3f3-625f630e366b.jpg)

Primeiro o voto é recebido, realiza as validações necessárias e produz uma mensagem para a fila de processamento de votos, que então dará início ao segundo fluxo:

![ProcessarVoto](https://user-images.githubusercontent.com/42699918/210193021-b47fd7df-c6e5-4bc0-ac35-72f36b9013e6.jpg)


