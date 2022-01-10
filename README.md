# API SWAPI Star Wars

## Sobre o Projeto

  O objetivo deste projeto é consultar a API SWAPI disponível em [https://swapi.dev/api](https://swapi.dev/api) para consultar dados das espaçonaves e determinar,
para cada uma delas, quantas paradas serão necessárias para reabastecer são necessárias para uma dada distância a ser percorrida; e qual a melhor espaçonave a para
transportar um dados número de passageiros a uma dada distância.

## Tecnologiaas Utilizadas
  
  Para o desenvolvimento desta API foi utilizada a linguagem JAVA versão 8, Spring Boot na versão 2, Reactor Spring na versão 1 para acessar a API SWAPI, Spring Boot
  Starter Test com JUNIT para realização de testes, a IDE Intellij IDEA, e Springfox Swagger 2 para a documentação da API.
  
## Preparação do ambiente
  
  Para utilizar o projeto deve-se ter instalado o [Java 8](https://www.oracle.com/java/technologies/downloads/#java8), como IDE o Intellij IDEA conforme o sistema
  operacional. Após isto, efetuar o download e importação do projeto na IDE.
  Com o projeto importado, executar o Build no Intellij e logo depois executar Run All Tests.
  Com a execução do Build a API estará disponível em [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html). 
  
## Funcionalidades
  Para obter o número de paradas necessárias para reabastecimento de cada espaçonave acessar o endpoint "spaceships/all/necessaryStops?distance=1000000"
  onde "distance" é a distância a ser informada, em MGLT (megalights)
  
  Ex.: [http://localhost:8080/api/v1/spaceships/all/necessaryStops?distance=1000000](http://localhost:8080/api/v1/spaceships/all/necessaryStops?distance=1000000)
  Método GET
  
  Para definir qual a melhor espaçonave para fazer o transporte de um determinado número de passageiros em um trajeto predefinido (em megalights - MGLT) acessar 
  o endpoint "/spaceships/all/betterStarship?passengers=200&distance=1000000000", onde "passengers" é o número de passageiros e "distance" é a distância do trajeto
  em megalights.
  
  Ex: [http://localhost:8080/api/v1/spaceships/all/betterStarship?passengers=843342&distance=1000000000](http://localhost:8080/api/v1/spaceships/all/betterStarship?passengers=843342&distance=1000000000)
  Método GET
  
## SWAGGER
  Link da documentação criada pelo Swagger é [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
