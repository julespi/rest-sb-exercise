# Ejercicio GlobalLogic Springboot

1) En la carpeta **/wars** se encuentra el archivo war correspondiente. Desde una terminal (con Java 8 instalado previamente) ejecutar el siguiente comando: `java -jar rest-sb-exercise-0.0.1-SNAPSHOT.war`
2) Utilizando un navegador, acceder a `localhost:8080/h2-console` y haciendo click en "Connect" se podra comprobar el estado de la base de datos

![Image of docker-compose command with option up](https://github.com/julespi/rest-sb-exercise/blob/main/images/h2-console.png)

3) Podra utilizar una aplicion que le permita realizar peticiones a una api como POSTMAN, INSOMNIA, etc. NOTA: Se encuentra disponible en la carpeta **/POSTMAN Collection** una coleccion JSON de peticiones para ser importada en POSTMAN. Caso contrario, mas abajo cuenta con la lista de endpoints a la API.
4) **PARA UN CORRECTO FUNCIONAMIENTO DEBE REALIZAR LA EJECUCION DE `localhost:8080/api/initdb`**
![Image of docker-compose command with option up](https://github.com/julespi/rest-sb-exercise/blob/main/images/init_db.png)
5) Una vez inicializada la base de datos, ejecutar un [POST] `localhost:8080/login` con las siguientes credenciales:
```json
{
    "email":"julespi@gmail.com",
    "password": "Password88"
}
```
6) Una vez realizado esto, utilizar el Token devuelto en la response como el header **Authorization** y utilizarlo para el resto de la API

# DOCUMENTACION API
| HTTP METHOD | URL                          | HEADER                              | BODY JSON                                                                                                                                                                                                                                                      |
|-------------|------------------------------|-------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| GET        | localhost:8080/api/initdb    |                                     |                                                                                                                                                                                                                                                                |
| POST        | localhost:8080/api/login     |                                     | `{"email": "julespi@gmail.com", "password": "Password88"}`                                                                                                                                                                                                     |
| GET         | localhost:8080/api/user/{id} | `{"Authorization": "Bearer token"}` |                                                                                                                                                                                                                                                                |
| GET         | localhost:8080/api/user      | `{"Authorization": "Bearer token"}` |                                                                                                                                                                                                                                                                |
| POST        | localhost:8080/api/user      | `{"Authorization": "Bearer token"}` | `{ "name": "Nombre Apellido", "email": "email@email.com", "password": "Contraseña12", "phones": [ { "number": "11111111", "citycode": "11", "contrycode": "57"},{"number": "22222222","citycode": "221","contrycode": "54"}]}`                                 |
| PUT         | localhost:8080/api/user/{id} | `{"Authorization": "Bearer token"}` | `{"id": "402881eb7cc8ce41017cc8cef8140000","name": "Julian Spinelli","email": "julespi@gmail.com","password": "Password77","isActive": false,"phones": [{"id": "402881eb7cade4f2017cade58d600001","number": "11111111","citycode": "11","contrycode": "57"}]}` |
| DELETE      | localhost:8080/api/user/{id} | `{"Authorization": "Bearer token"}` |                                                                                                                                                                                                                                                                |
