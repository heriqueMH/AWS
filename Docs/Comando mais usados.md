## 🐳 Docker
```bash
docker ps                              # Contêineres rodando
docker ps -a                           # Todos os contêineres
docker images                          # Imagens disponíveis
docker build -t app-release3.0 .       # Cria imagem
docker run -d -p 8080:8080 --name app-release3 app-release3.0
docker logs -f app-release3            # Ver logs
docker stop app-release3               # Parar contêiner
docker rm app-release3                 # Remove contêiner
docker rmi app-release3.0              # Remove imagem
```
## 🐧 Linux
```bash
mkdir nome               # Cria uma pasta
ls                       # Lista os arquivos
cd nome-da-pasta         # Entra na pasta
mv origem /destino       # Move
mv nome novoNome         # Renomeia
sudo comando             # Executa como root
```
## 🧰 Utilitários diversos
```bash
curl http://52.71.87.155/                                                                                                            
# Faz requisição HTTP

mvn clean package -DskipTests                                                                                                         
# Gerar o .jar skipando testes

ssh -i "Proj-back.pem" ubuntu@ec2-52-71-87-155.compute-1.amazonaws.com                                                                
# Acesso à EC2 via SSH CloudShell

mysql -h db-proj.ceobz4w6p8z4.us-east-1.rds.amazonaws.com -u admin -p                                                                  
# Acesso à instância do RDS

scp -i "/home/cloudshell-user/Proj-back.pem" "/home/cloudshell-user/bahtche-0.0.1-SNAPSHOT.jar" ubuntu@52.71.87.155:/home/ubuntu/app/                                                                                                 
# Mover o .jar do CloudShell para EC2
