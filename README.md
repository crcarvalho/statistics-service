# Serviço: statistics-service

### Method: POST
* *Endpoint:* http://localhost:8080/statistics-service/transaction
* *Payload:* 
```json5
{
    "timestamp": 1556655941934,
    "amount": 25000.15
}
```
* *Response:*

```
code = 201
message = "Criado"
```
```
code = 204
message = "Diferenças entre chamadas maior 60 segundos"
```

### Method: GET
* *Endpoint:* http://localhost:8080/statistics-service/statistics
* *Response:* 
```json5
{
    "sum": 300.00,
    "min": 50.00,
    "max": 150.00,
    "avg": 100.00,
    "count": 3
}
```

## Docker
* Criando a imgem com o Docker
		
`				
docker build -f Dockerfile -t statistics-service .
`
Nota: Utilize o ponto final no comando acima.

* Executando a imagem
									
`
docker run -p 8080:8080 -t statistics-service
`

## Swagger
`
http://localhost:8080/swagger-ui.html
`