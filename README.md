## MARIO MENCIA - COLSUBSIDIO TEST

* main branch -> INIT PROJECT
    * dev branch  -> 1.0.0 for release
    * Stack principal:
      * Java 17
      * Spring Boot 3.1.4
      * PostgreSQL 14.8
      * JWT
      * JUnit 5 

Bienvenidos a la resolución de

_"PRUEBA TÉCNICA DESARROLLADOR
FULLSTACK"_
(Parte : BackEnd )

## Registro de usuario
(1. Endpoint para registrar usuario)
```
POST    /api/user/register

Body example:
{
  "name"  :"logisticNew01",
  "email" :"iamnew5gmail.com",
  "password"  :"12345",
  "phones":[
        {
        "number" : "77775555",
        "citycode":"1",
        "contrycode":"503"
        }
    ]
 }
```
Comprueba / Valida:
 * validez de campo "email"
 * Formato adecuado de "password" (Configurable*)
 * __Verifica si el correo ha sido registrado previamente__

*Configuración de password actual: 
  Al menos Una mayúscula, 
  Al menos Un número,  
  Al menos 5 Dígitos.

## Consulta de usuario
(2. Endpoint para consultar usuarios)
```
GET    /api/user/find

Body example:
{
  "name"  :"",
  "email" :"",
  "number" : "",
  "isActive": "true"    
}

Header example:
Authorization : Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBnbWFpbC5jb20iLCJjcmVhdGVkIjoxNzAwODYwMTU5MTM4LCJleHAiOjE3MDA4NzgxNTksImlhdCI6MTcwMDg2MDE1OX0.M3mRKKMxs6RmSf4lPaUfjcVaSpliK4eqUVP3SOj0YGo
```
- Endpoint requiere de token válido (En caso Token haya expirado la API devolverá el error, ver apartado respuestas ejemplos)
- Cada campo puede o no llevar valor
- Devuelve bajo el estandar los usuarios resultantes, campo numero es consultado en tabla hija

## Ejemplo de respuesta
Se implementó un estándar de respuesta en el que se devuelve tres parámetros para todos endpoints como se solicitó:
1. success -> Estado resultante de la petición 
2. message -> Mensaje resultante de la operación
3. data -> Objeto o listado de objetos resultantes

Ejemplo para Consulta de usuario:
```
GET    /api/user/find
Data used:
{
  "name"  :"Admin",
  "email" :"",
  "number" : "",
  "isActive": "true"    
}

R:
{
    "success": true,
    "message": "Total 1",
    "data": [
        {
            "id": 1,
            "name": "Administrador",
            "userEmail": "admin@gmail.com",
            "created": "mario_mencia@hotmail.com",
            "modified": "",
            "lastLogin": "25/11/2023 00:58:37",
            "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBnbWFpbC5jb20iLCJjcmVhdGVkIjoxNzAwODk1NTE3MzUwLCJleHAiOjE3MDA5MjQzMTcsImlhdCI6MTcwMDg5NTUxN30.GbIWHrOfprpeiJVUmgzi0ZLKgIzKpGfyCEqSstQxEgE",
            "isActive": true
        }
    ]
}
```



Ejemplo para Token expirado
```
{
    "success": false,
    "message": "JWT expired at 2023-11-24T20:09:19Z. Current time: 2023-11-25T00:54:36Z, a difference of 17117661 milliseconds.  Allowed clock skew: 0 milliseconds.",
    "data": null
}
```

(API retorna Http Status 403 FORBIDDEN con ese objeto JSON)


## Enpoints EXTRAS


### Autenticación de Usuario
```
POST    /api/authenticate

Body example
{
    "email": "admin@gmail.com",
    "password": "123"    
}
```
Devuelve en apartado Data del Estándar el objeto solicitado 
con el atributo de token, tambien actualiza el registro de token y último logIn en DB



### Activar o Desactivar Usuario
```
PATCH    /api/user/activate
PATCH    /api/user/disable

Body example
{
   "email":"iamnew@gmail.com"
}
```
Solo para token con Rol Administrador - Body example funciona para ambos casos


(_Se comparte la colleccion de POSTMAN en paquete de entrega_)