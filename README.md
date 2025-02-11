# desafio-backend-itau-jr

Solução do [desafio técnico](https://github.com/rafaellins-itau/desafio-itau-vaga-99-junior) para dev Jr. do Itaú. 

A ideia e referência para a resolução deste desafio veio [deste vídeo](https://www.youtube.com/watch?v=9xrx1pxZEGU) do canal Javanauta.

## Stack utilizada

* Java  
* Spring
* Gradle  
* jUnit
* Mockito  
* Slf4j

## Funcionalidades

* POST /transacao - Cria uma transação com valor e dataHora.
* DELETE /transacao - Deleta todas as transações que foram criadas.
* GET /estatistica - Calcula as estatísticas de transações em determinado intevalo (padrão: 60s). 

## Como rodar localmente

* Clonar o repositório com o seguinte comando:
```
git clone https://github.com/fpsaraiva/desafio-backend-itau-jr
```

* Para rodar os testes unitários, digitar o seguinte comando no terminal:
```
./gradlew test
```

* Para iniciar a aplicação, digitar o seguinte comando no terminal:
```
./gradlew bootRun
```
Comando (no terminal também) para finalizar a aplicação: Ctrl + C

* Acessar a documentação da api em http://localhost:8080/swagger-ui/index.html

* Para acessar o endpoint que verifica a saúde da aplicação: http://localhost:8080/actuator/health

## Faltou fazer (não foi implementando)

* Containerização: configurar aplicação para ser disponibilizada como um container.
* Performance: estimar tempo gasto pela aplicação para cálculo de estatísticas.
