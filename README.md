# Probleminhas Aqui
#### Sistema Web de Reporte de Problemas Urbanos 
Probleminhas Aqui é uma aplicação web que procura facilitar o reporte de problemos urbanos. \
Permite aos moradores reportar problemas urbanos e acompanhar seu status de maneira fácil e organizada; e aos administradores - prefeitura ou responsáveis - visualizar e gerenciar as demandas com mais eficiência. \
Dessa forma, promovendo melhorias na infraestrutura e na qualidade de vida da população. \


## Tecnologias
- Backend: Java, Spring Boot \
- Frontend: JavaScript, CSS, HTML \
- Docker \
- RDS - MySql \
- Node.js (Lambda Runtime) \
- AWS Lambda \
- API Gateway (HTTP API) \


## EC2
Sistema - linux - ubuntu 24.04 \
Java 21 \
Docker 26.1.3 \ 


## Rotas Disponíveis
- http://52.71.87.155/usuarios
- http://52.71.87.155/problemas
- http://52.71.87.155/comentarios
- http://52.71.87.155/logs


## Membros do projeto 
João Vitor - 10381778 \
Caio Caramés - 10308718 \
Matheus Henrique - 10409051 \


## Deplay AWS
- EC2 utilizada para hospedar backend e frontend. \
- RDS com MySQL rodando em subnet privada. \
- Lambda acessa API do backend via API Gateway. \
- Segurança configurada via grupos de segurança da VPC. \


## Endpoint RDS privado
db-proj.ceobz4w6p8z4.us-east-1.rds.amazonaws.com \


## Lambda
Lambda coleta dados de /problemas \


## Endpoint API Gateway
https://o5xu8k2ar8.execute-api.us-east-1.amazonaws.com/prod/report \
