# Code Challenge: Ganho de capital 

Olá esta é a minha implementação para o exercicio de **ganho de capital**.

##  Decisões técnicas e arquiteturais:

Para não escapar muito do tempo do projeto e por ser um problema relativamente complexo escolhi fazer o projeto em JAVA porque é a linguagem que mais tenho domínio. Na maior parte do projeto usei uma abordagem mais funcional do java usando streams (tentei mirar no clojure mas acho que ficou algo mais parecido com haskell).

O projeto está estruturado da seguinte forma: 
- `/lib`
  |_ todas as bibliotecas necessárias para o projeto
- `/resources`
  |_ recursos gerais (classes compiladas e inputs existentes no pdf se encontram aqui)
- `/src`
  |_ código java
- `run_tests.sh`
  |_ runner para facilitar a compilar e executar os testes do projeto (unitários e integrados)
- `run.sh`
  |_ runner para facilitar a compilar e executar o projeto via command-line

O padrão de arquitetura é simples, um service-model  onde a lógica da aplicação está dentro do pacote service e os dtos se apresentam no pacote model, ao redor desses dois pacotes existem algumas estruturas auxiliares para ajudar na resolução do problema.
Falando um pouco mais sobre a arquitetura, implementei um strategy pattern dentro do serviço para cada tipo de operação (BUY, SELL). Achei interessante fazer assim por alguns motivos: 
1. Eventualmente se surgir mais operações que necessitem de um cálculo de taxa, aluguel de ações por exemplo, basta criar a nova implementação sem a necessidade de modificar as existentes;
2. Caso exista alguma variação do cálculo de taxa para as operações existentes, fica simples fazer uma extensão das classes originais atuais e assim garantimos que as mesmas também não serão modificadas;
3. Se surgir um novo tipo de taxa no futuro ou um novo fator que altere o calculo da taxa atual, basta que as operações existentes implementem essa nova funcionalidade usando uma nova interface;
Em resumo, garantimos o single responsability, open close, interface segregation.
O service principal se chama **CapitalGainService**, ele é basicamente um orquestrador que para cada operação informada no stdin consulta o padrão de strategy para saber como deverá ser efetuado o calculo da taxa. 

## Justificativa para o uso de frameworks ou bibliotecas:

No projeto, optei por não usar nenhum gerenciador de pacotes como maven ou gradle (sempre acho que eles poluem muito a estrutura do projeto) então segui o bom velho padrão "pasta lib" com alguns poucos .jars dentro dela.
As bibliotecas utilizadas são:
1. jackson (annotations, core, databind) para serializar JSON;
2. junit para executar testes no projeto;

Ao final de tudo me arrependi de não ter importado um javax (jakarta) para fazer algumas validações dentro do projeto (tive que construir um simples validator na mão para garantir o basico) mas fica de lição aprendida. 

Também decidi não usar nenhum framework (spring ou algo similar) porque senti que estaria usando um canhão para algo que deveria ser simples (stdin e stdout).  

## Instruções sobre como compilar e executar o projeto

A solução pede que seja possível de executar em um ambiente Unix ou Mac, então criei um script para facilitar a etapa de compilação e execução, em resumo:
1. Ter o java 21 instalado na máquina;
2. Dar permissão de execução para o arquivo `run.sh` com o comando: `chmod +x ./run.sh`
3. No terminal, na raiz do projeto, executar `./run.sh`
4. Após isso basta informar as linhas com os jsons e ao final inserir uma linha vazia para que o fluxo seja iniciado.
5. Você também pode pular a etapa (4) usando o input redirection `./run.sh < path/to/file/json.txt`

Caso tenha curiosidade, todos os `.class` gerados vão ser salvos dentro da pasta `/resources`

## Instruções sobre como executar os testes da solução

Para executar os testes, deve-se: 
1. Ter o java 21 instalado na máquina;
2. Dar permissão de execução para o arquivo `run_tests.sh` com o comando: `chmod +x ./run_tests.sh`
3. No terminal, na raiz do projeto executar `./run_tests.sh`. O projeto irá rodar todos os testes de integração (os casos de testes presentes no PDF) e em seguida irá executar todos os testes unitários da aplicação. Ao final será informado um resumo sobre os testes.

## Notas adicionais
Quando for efetuar a leitura do código você verá que em alguns momentos pode acontecer de ser lançado um **Exception.class** ou um **RuntimeException.class**. Optei por usar as classes default do java por ser mais rápido e simples, mas o ideal é que em um projeto de verdade exista um exception handler e que também sejam criadas exceções especificas para cada caso de uso.
Você também verá que não utilizei nenhum serviço de logging, nem mesmo o bom e velho Sysout pois não vi necessidade. Mas mais uma vez, em um projeto real essas caracteristicas de rastreabilidade (logging, exceptions, métricas, etc..) são extremamente necessárias.
