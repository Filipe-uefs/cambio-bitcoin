#### Câmbio de Bitcoins
Descrição do projeto pode ser encontrado na raiz do repositório com o nome "Desafio - Câmbio de bitcoins.pdf".

##### Tecnologias necessárias para execução
- Maven 3.8.4
- PostgreSQL
- Java 11
- IDE (preferência Intellij)

##### Configuração
Adicionar as seguintes variáveis de ambiente
```sh
PGHOST = "HOST_DO_SEU_POSTGRESQL"
PGUSER = "SEU_USER"
PGPASSWORD = "SUA_SENHA"
```
##### Como executar
Passo a passo

1 - Crie um banco de dados chamado 'cambio-bitcoin'
2 - Navegue até a raiz do projeto
3 - Rode
```sh
mvn clean
mvn install
```
4 - Execute o projeto pela IDE

##### Links
Você pode consultar a documentação do projeto em: http://localhost:8080/swagger-ui.html
