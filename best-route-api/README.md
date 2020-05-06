# Projeto

Uma applicação desenvolvida em Java com Spring-boot

## Algortimo de melhor rota

Foi decedido utilizar o algorítmo proposto por Dijkstra para traçar a melhor rota entre dois 
pontos no grafo que geram um custo toal ao final, a decisão por esse algorítmo se deu por ser 
uma solução viável e com tempo de resposta aceitável. 

## Requisitos

* Java versão 1.8 ou superior
* Maven versão 3.6.3 ou superior (https://maven.apache.org/download.cgi)

## Documentação da API

A documentação da API foi gerado usando o Swagger versão 2.0 e pode ser acessado após o 
projeto em execução pelo caminho: `http://localhost:8080/swagger-ui.html`

## Execução do projeto

1. Dentro da pasta do projeto, execute no console o seguinte comando:
	mvn clean package spring-boot:run

2. Abra um navegador de sua preferência e input a url: `http://localhost:8080/swagger-ui.html`

## API's disponíveis

1. POST: /routes

1.1 Payload:
 
	```
		{
			'file': 'multipartfile'
		}
	```
	
1.2 Response:

	```
		{
			'code': '200 Ok'
		}
	```

2. GET /routes/shortests

2.1 Resquest Params:
 
	```
		from: String,
		go: String
	```

2.2 Response:

	```
		{
			'routes': String,
			'totalCust': Integer
		}
	```

## Estrutura de pastas e pacotes do projeto

	```
		\src\main\java
			\br.com.selecao.bestroute
				\api
				\config
				\domain
					\dijkstra
					\dto
						\response
					\entities
					\repositories
				\services
				\util
					
		\src\test\java
			\br.com.selecao.bestroute
				\services
	```
	
## Casos de testes

1. Upload com sucesso
2. Arquivo com dados errados
3. Melhor rota encontrada
4. Destino igual Origem
5. Destino não encontrado
6. Origem não encontrada
7. Rota não encontrada 
