# Votação Cooperativa #

Este projeto tem o objetivo de registrar e contabilizar os votos dos associados de uma coperativa durante as sessões de pautas abertas em assembleias.

## Configurações do Projeto ##

* Necessário alterar configuração do arquivo "application.properties" para indicar um banco de dados Postgres.
* Utiliza lombok, necessário instalar na IDE (utilizado Spring Tool Suite 4).
* Start configurado para porta 8080.
* Swagger pode ser acessadoo em http://localhost:8080/swagger-ui.html#
* Aplicação está configurada em três versões (1.0, 1.1 e 1.2) para exemplificação de versionamento.

## Modelo de dados ##

![Entityrelationshipdiagram1](https://user-images.githubusercontent.com/42699918/111929313-c59b1500-8a94-11eb-9f2b-a99ec3e73e7f.png)

## Teste de desempenho ##

Foi realizado teste de desempenho no serviço responsável por receber votos dos associados, utilizando a ferramenta JMeter 5.4. <br />
Método: POST <br />
Serviço: /votos/v1.0 <br />
O arquivo "teste_desempenho_jmeter.rar" na pasta "testes" contém:

* Arquivo do projeto do JMeter (.jmx).
* Relatórios gerados pelo JMeter (.csv).
* Arquivos com massa de dados utilizados para teste (.csv).
* Relatório de resultado do teste em excel (imagem abaixo).

![1relatorio_teste_desempenho_jmeter](https://user-images.githubusercontent.com/42699918/112093599-7c23f600-8b78-11eb-9d96-83384558aab2.jpg)
