<p align="center">
  <a href="http://www.catho.com.br">
      <img src="http://static.catho.com.br/svg/site/logoCathoB2c.svg" alt="Catho"/>
  </a>
</p>
# backend-test

Uma pessoa esta a procura de emprego e dentre as várias vagas que existem no mercado (disponibilizadas nesse <a href="https://github.com/catho/backend-test/blob/master/vagas.json">JSON</a>) e ela quer encontrar vagas que estejam de acordo com o que ela saiba fazer, seja direto pelo cargo ou atribuições que podem ser encontradas na descrição das vagas. Para atender essa necessidade precisamos:

- uma API simples p/ procurar vagas (um GET p/ procurar as vagas no .json disponibilizado);
- deve ser possível procurar vagas por texto (no atributos title e description);
- deve ser possível procurar vagas por uma cidade;
- deve ser possível ordenar o resultado pelo salário (asc e desc);

O teste deve ser feito utilizando PHP (com ou sem framework, a escolha é sua). Esperamos como retorno, fora o GET da API funcionando, os seguintes itens:

- uma explicação do que é necessário para fazer seu projeto funcionar;
- como executar os testes, se forem testes de unidade melhor ainda;
- comentários nos códigos para nos explicar o que está sendo feito.

Lembre-se que na hora da avaliação olharemos para:

- organização de código;
- desempenho;
- manutenabilidade.


## Catho - Job Search API

Implementação do teste backend-test da catho.

### Importação do projeto:
Para importar o projeto no eclipse rode: `mvn eclipse:eclipse` e importe como um projeto normal ou importe como um projeto maven.

## Build e Testes unitários:
mvn clean install

### Tecnologias utilizadas:

  - java (guice + resteasy)
  - maven
  - redis
  - Docker (redis, jetty)
  
https://hub.docker.com/_/jetty/
https://hub.docker.com/_/redis/
https://docs.docker.com/machine/get-started/

### Variáveis de ambiente disponíveis e seus valores default:

 - catho.jobs.file=src/main/resources/vagas.json
 - catho.cache.enabled=true
 - catho.cache.password=pass
 - catho.cache.master.server=localhost
 - catho.cache.master.connection.pool.size=5
 - catho.cache.slave.servers=localhost
 - catho.cache.slave.connection.pool.size=5
 - catho.cache.connect.timeout=300
 - catho.cache.default.ttl=300
 - catho.cache.use.password=false
 - catho.cache.connection.pool.max.wait=100
 - catho.cache.connection.pool.test.on.borrow=false
 - catho.cache.port=6379

### Como utilizar:

Para rodar o projeto no ambiente de dev rode o comando abaixo na pasta do projeto substituindo os parâmetros necessários:

 - -Dcatho.cache.master.server=192.168.99.100 - host do redis server master.
 - -Dcatho.cache.slave.servers=192.168.99.100 - host do redis server slave.
 - -Dcatho.cache.port=6379  - porta do redis server.
 - -Dcatho.cache.password=pass - password definido para acessar o redis.
 - -Dcatho.cache.use.password=false - define se o redis necessita de password ou não.

```sh
$ mvn jetty:run -Dcatho.cache.master.server=192.168.99.100 -Dcatho.cache.slave.servers=192.168.99.100 -Dcatho.cache.port=6379 -Dcatho.cache.use.password=false
```

Para testar a api acesse:

[http://localhost:8080/api/v1/job/find?qType=CITY&q=Porto%20Alegre&hash=0&size=20&sortType=SALARY&orderType=ASC](http://localhost:8080/api/v1/job/find?qType=CITY&q=Porto%20Alegre&hash=0&size=20&sortType=SALARY&orderType=ASC)

[http://localhost:8080/catho/index.html](http://localhost:8080/catho/index.html)


### Configuração de ambiente

Se você tem o docker instalado:
inicialize uma nova instancia do redis
```sh
docker run -d -p 6379:6379 --name redis redis redis-server
```

Para inicializar via docker:
Variaveis necessárias para inicializar com cache ativo:

 - catho.cache.master.server=192.168.99.100 - host do redis server master.
 - catho.cache.slave.servers=192.168.99.100 - host do redis server slave.
 - catho.cache.port=6379  - porta do redis server.
 - catho.cache.password=pass - password definido para acessar o redis.
 - catho.cache.use.password=false - define se o redis necessita de password ou não.
 - catho.jobs.file=/tmp/vagas.json - define o local do arquivo json utilizado para buscar as vagas.
 - -v /path/to/vagas/json/vagas.json:/tmp/vagas.json - mapeamento do arquivo json dentro do container docker.

inicializar como serviço:

```sh
docker run -d -p 8080:8080 -v /path/to/war/file/target/jobsearch.war:/var/lib/jetty/webapps/ROOT.war -v /path/to/vagas/json/vagas.json:/tmp/vagas.json -e "catho.jobs.file"="/tmp/vagas.json" -e "catho.cache.master.server"="192.168.99.100" -e "catho.cache.slave.servers"="192.168.99.100" -e "catho.cache.port"="6379" -e "catho.cache.use.password"="false" --name jetty jetty:9.2
```

inicializar acompanhando o log:
```sh
docker run -ti -p 8080:8080 -v /path/to/war/file/target/jobsearch.war:/var/lib/jetty/webapps/ROOT.war -v /path/to/vagas/json/vagas.json:/tmp/vagas.json -e "catho.jobs.file"="/tmp/vagas.json" -e "catho.cache.master.server"="192.168.99.100" -e "catho.cache.slave.servers"="192.168.99.100" -e "catho.cache.port"="6379" -e "catho.cache.use.password"="false" --name jetty jetty:9.2
```


### Recurso disponível na API

[http://localhost:8080/api/v1/job/find?qType=CITY&q=Porto%20Alegre&hash=0&size=20&sortType=SALARY&orderType=ASC](http://localhost:8080/api/v1/job/find?qType=CITY&q=Porto%20Alegre&hash=0&size=20&sortType=SALARY&orderType=ASC)

[http://localhost:8080/catho/index.html](http://localhost:8080/catho/index.html)

Parâmetros disponíveis para as buscas:
 - qType=CITY - tipo da busca. Valores aceitos: [DESCRIPTION, CITY]
 - q=Porto%20Alegre - valor da busca.
 - hash=0 - página da busca
 - size=20 - tamanho da paginação
 - sortType=SALARY - tipo da ordenação. Valores aceitos: [TITLE, SALARY]
 - orderType=ASC - ordenação crescente ou decrescente: [DESC, ASC]

