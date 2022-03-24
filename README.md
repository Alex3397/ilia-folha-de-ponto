# ilia-folha-de-ponto

## Descrição
API para controle de folha de ponto.
* registrar a batida do ponto 
* alocar horas em projeto 
* visualizar o tempo alocado no mês por projeto 

#### Existem duas versões da API, a V1, onde os objetos são armazenados em arquivos locais em um pasta específica, e a V2 onde é utilizado spring JPA para realizar a persistência de dados da Aplicação.

## Endpoints

### Versão 1
* Bater Ponto: v1/batidas
* Alocar Horas: v1/alocacoes
* Relatório Folha de Ponto: v1/folhas-de-ponto/{mes}

### Versão 2
* Bater Ponto: v2/batidas
* Alocar Horas: v2/alocacoes
* Relatório Folha de Ponto: v2/folhas-de-ponto/{mes}

#### Independente da versão utilizada o resultado das requisições são os mesmo, a indiferênça está na performance da API e na quantidade de trabalho realizado para fazer funcionar e na necessidade de cada ambiente/cliente.
###### _Caso você queria/precise armazenar informações e não pode usar banco de dados sinta-se a vontade para copiar._  

## Pré requisitos

### Java 11 ou posterior

<details><summary><b>Instruções</b></summary>

O Java 11 pode tanto ser instalado através da JDK contida no site
da [Oracle](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
ou no site do [OpenJDK](https://openjdk.java.net/projects/jdk/11/)

Como alternativa é possível utilizar o [SDKMan](https://sdkman.io/)
e instalar o Java através do comando:

```console
foo@bar:~$ sdk install java <version>
```

Para listagem de todas as versões do Java disponíveis, execute o comando:

```console
foo@bar:~$ sdk list java
```

</details>

### Maven (opcional)

<details><summary><b>Instruções</b></summary>

O projeto foi concebido para que a instalação do Maven fosse opcional,
para tanto, é possível rodar as configurações do projeto após instalação
do Java pelos arquivos **mvnw.cmd** em sistemas Windows e **mvnw**
 em sistemas Unix, que interagem com o arquivo **maven-wrapper.jar**
 contido na pasta **.mvn/wrapper** na raiz do projeto.

Caso mesmo assim se deseje rodar o projeto pelo Maven na máquina,
o mesmo pode ser instalado através do [site](https://maven.apache.org/).

Como alternativa é possível utilizar o [SDKMan](https://sdkman.io/)
e instalar o Maven através do comando:

```console
foo@bar:~$ sdk install maven
```

Para listagem de todas as versões do Maven disponíveis, execute o comando:

```console
foo@bar:~$ sdk list mavel
```

</details>

## Banco de dados

Esta API utiliza o banco de dados em memória H2 não sendo necessária nenhuma configuração para o funcionamento da API. 
Entretanto, caso queira dar uma olhada nos objetos armazenados basta acessar: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

Assim que entrar nesta tela:
![alt text](https://github.com/[username]/[reponame]/blob/[branch]/image.jpg?raw=true)

## Execução

### Clone o repositório

```console
$ git clone https://github.com/Alex3397/ilia-folha-de-ponto.git
$ cd ilia-folha-de-ponto
```

### Execução Linux | Windows

O projeto pode ser executado em ambiente Linux ou Windows, sendo os comandos
diferenciando por duas opções Linux e Windows respectivamente

```console
mvn clean spring-boot:run
```

A aplicação estará rodando na porta **8080** em 
[http://localhost:8080/](http://localhost:8080/)



