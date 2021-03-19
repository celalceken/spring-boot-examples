### Send GET request 
GET http://localhost:8005/api/users/5
Accept: application/json

### Send GET request
GET http://localhost:8005/api/users
Accept: application/json

### Send POST request with json body
POST http://localhost:8005/api/users
Content-Type: application/json

{
"username": "uname",
"password": "password",
"name": "xk",
"surname": "z",
"email": "content"
}


### Send DELETE request with json body
DELETE http://localhost:8005/api/users
Content-Type: application/json

{ "id": 7,
"username": "uname",
"password": "password",
"name": "xk",
"surname": "z",
"email": "content"
}
-------

curl --request GET http://localhost:8005/api/users
curl --request GET http://localhost:8005/