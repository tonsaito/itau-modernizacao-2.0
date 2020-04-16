# itau-modernizacao-2.0 -  Ayrton Saito
Projeto Itaú Modernização 2.0

## How to run


```bash
mvn spring-boot:run
```

## Usage

## Mocked users
Para fins de usabilidade, ao rodar o projeto, são carregados 3 usuários:

ID: 10001 - João Machado, 123.456.789-11, joao.machado@email.com

ID: 10002 - Maria Diniz, 123.456.789-12, maria.diniz@email.com

ID: 10003 - Marcelo Silva, 123.456.789-13, marcelo.silva@email.com

## Postman
Para facilitar, basta importar o arquivo "Itaú Modernização 2.0.postman_collection.json" no Postman.


#### GET http://localhost:8080/v1/users
##### Listar todos usuários

**Sample Response**

```
[
    {
        "id": 10001,
        "nome": "João Machado",
        "cpf": "123.456.789-11",
        "email": "joao.machado@email.com",
        "cadastro": "2020-04-16T03:00:00.000+0000",
        "links": [
            {
                "rel": "get-user-by-id",
                "href": "http://localhost:8080/v1/users/10001"
            }
        ]
    }
]
```
___

#### GET http://localhost:8080/v1/users/{id}
##### Buscar usuário por ID

**Sample Response**

```
{
    "id": 10001,
    "nome": "João Machado",
    "cpf": "123.456.789-11",
    "email": "joao.machado@email.com",
    "cadastro": "2020-04-16T03:00:00.000+0000",
    "_links": {
        "all-users": {
            "href": "http://localhost:8080/v1/users"
        }
    }
}
```
___

#### POST http://localhost:8080/v1/users
##### Criar usuário

**Body**
```
{
	"nome": "Joana Brito",
	"cpf": "111.111.111-11",
	"email": "joana.brito@email.com",
	"cadastro": "2020-03-30"
}
```

**Possible Responses**

```
[201] Created
[409] CPF ou e-mail já existente
[400] Campos inválidos
```
___

#### PUT http://localhost:8080/v1/users/{id}
##### Alterar Usuário

**Body**
```
{
	"nome": "Joana Brito alterada",
	"cpf": "111.111.111-11",
	"email": "joana.brito@email.com",
	"cadastro": "2020-03-30"
}
```

**Possible Responses**

```
[200] Retorna entidade alterada
[404] Usuário não encontrado
[409] CPF ou e-mail já existente
[400] Campos inválidos
```
___

#### GET http://localhost:8080/v1/users/{id}/logtime
##### Buscar pontos batidos de um usuário

**Sample Response**

```
{
    "usuario": {
        "id": 10001,
        "nome": "João Machado",
        "cpf": "123.456.789-11",
        "email": "joao.machado@email.com",
        "cadastro": "2020-04-16T03:00:00.000+0000",
        "links": []
    },
    "logs": [
        {
            "id": 100011,
            "data": "2020-04-11T09:00:00.000+0000",
            "tipo": "ENTRADA"
        },
        {
            "id": 100012,
            "data": "2020-04-11T12:00:00.000+0000",
            "tipo": "SAIDA"
        },
        {
            "id": 100013,
            "data": "2020-04-11T13:00:00.000+0000",
            "tipo": "ENTRADA"
        },
        {
            "id": 100014,
            "data": "2020-04-11T18:00:00.000+0000",
            "tipo": "SAIDA"
        }
    ],
    "horasTrabalhadas": "8 horas 0 minutos 0 segundos"
}
```
___

#### POST http://localhost:8080/v1/users/{id}/logtime
##### Bater ponto com ID do usuário

**Body**
```
//para entrada
{
    "data": "2020-04-11T09:00:00.000+0000",
    "tipo": "ENTRADA"
}

//para saída
{
    "data": "2020-04-11T12:00:00.000+0000",
    "tipo": "SAIDA"
}
```

**Possible Responses**

```
[201] Created
[404] Usuário não encontrado
[400] Campos inválidos
```

## License
[GNU](https://www.gnu.org/licenses/gpl-3.0.pt-br.html)