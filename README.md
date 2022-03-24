# Alterações no ReadMe.md ainda não consluídas (Haja sono)
# ilia-folha-de-ponto

## Descrição
API para controle de folha de ponto.
* registrar a batida do ponto 
* alocar horas em projeto 
* visualizar o tempo alocado no mês por projeto 

### Endpoints
* Bater Ponto: /batidas
* Alocar Horas: /alocacoes
* Relatório Folha de Ponto: /folhas-de-ponto/{mes}

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

## Execução

### Clone

```console
foo@bar:~$ git clone https://github.com/<alterar-seu-repositório>/ilia-folha-de-ponto
foo@bar:~$ cd ilia-folha-de-ponto
```

### Execução Linux | Windows

O projeto pode ser executado em ambiente Linux ou Windows, sendo os comandos
diferenciando por duas opções Linux e Windows respectivamente

```console
foo@bar:ilia-folha-de-ponto [./mvnw | mvnw.cmd] clean spring-boot:run
```

A aplicação estará rodando na porta **8080** em 
[http://localhost:8080/](http://localhost:8080/)
