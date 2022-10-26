# System Notification

O sistema de notificação criado tem com intuito de ser uma abstração de envio central
para todos os times que desejem enviar algum tipo de notificação.

## Utilização

Para a utilização basica basta executar um ``docker-compose up -d`` e as configurações 
serão criadas automaticamente.


## Requisitos Funcionais

- Inicialmente será suportado apenas o envio de notificações para uma aplicação web.
    Suporte a novos tipos de notificações, tais como Push, SMS e email, serão incluídos no
    futuro;
- Deverá ser permitido o agendamento para o envio das notificações;
- Usuários que solicitaram o opt-out não deverão mais receber notificações;

## Requisitos não funcionais

- As notificações devem ser entregues o mais rápido possível, porém pequenos delays
são aceitáveis;
- A solução deve ser escalável e resiliente;

## Endpoints

A implementação gira em torno de três endpoints 
1. ``notification/web/send``
2. ``notification/web/schedule``
3. ``notification/customer/config``

### Envio de notificaçoes direta

No endpoint ``notification/web/send`` se envia uma notificação de forma automatica o usuário
através de alguma interface de comunicação de fila, para que o serviço não sofra muito no tempo 
espera de uma cominicação sincrona

#### Request Body
- O objeto ``customer`` identifica qual usuário vai receber a notificação e contem o identificador
unico para que seja entregue na central de notificação
- O ``title`` identifica o titulo que vai ser colocado na caixa da notificação.
- A lista ``contents`` é composta por um hypertext que pode ser utilizado como um "molde"
para a interface de notificação o pensamento é que seja implementado uma biblioteca que 
que extraia essas informações e seja utilizada como modelo para todos os outros serviçs que desejam.
- O campo ``redirectUrl`` é o campo que redireciona a notificação quando clicada e busca as informaçoes
- adicionais da notificação.

```json
{
    "customer": {
        "id": 1
    },
    "title": "Awesome notification",
    "contents": [],
    "redirectUrl": "url"
}
```
#### Response
- O objeto ``customer`` identifica qual usuário vai receber a notificação
- O `status` da notificação que pode ser `SENT | DISABLED`

### Agendamento de envio de notificação
O envio de notificação pode ser agendado pelo endpoint ``notification/web/schedule``

#### Request Body
Consiste em algumas informações importantes
- O objeto ``customer`` identifica qual usuário vai receber a notificação e contem o identificador
  unico para que seja entregue na central de notificação
- O ``title`` identifica o titulo que vai ser colocado na caixa da notificação.
- A lista ``contents`` é composta por um hypertext que pode ser utilizado como um "molde"
  para a interface de notificação o pensamento é que seja implementado uma biblioteca que
  que extraia essas informações e seja utilizada como modelo para todos os outros serviçs que desejam.
- O campo ``redirectUrl`` é o campo que redireciona a notificação quando clicada e busca as informaçoes
adicionais da notificação.
- O campo ``schedule`` fala o momento em que se deve enviar a notificação para o usuários. 

```json
{
    "customer": {
        "id": 1
    },
    "title": "Awesome notification",
    "contents": [],
    "redirectUrl": "url",
    "schedule": "yyyy-MM-dd hh-mm-ss"
}
```

#### Response
- O objeto ``customer`` identifica qual usuário vai receber a notificação
- O `status` da notificação que pode ser `SCHEDULED | DISABLED`

### Central de notificação (Esboço)

A central de notificação inicial faz o controle dos usuários que querem desabilitar ou habilitar as notificações
gerais apenas pelo endpoint ``notification/customer/config``

### Request Body
- O objeto ``customer`` identifica qual usuário vai receber a notificação e contem o identificador
  unico para que seja entregue na central de notificação
- O campo ``notify`` é a flag se ele deve ser notificado ou não.

```json
{
    "customer": {
        "id": 1
    },
    "notify": false
}
```

## Architecture

A arquitetura consiste em duas partes:

1. Uma parte sincrona que recebe os dados para serem enviados para os usuários através dos
endpoints sincronos.
2. A parte assincrona que contém os ```workers``` que processam as notificações de forma assincrona e entregam
com a maior velocidade possivel.
    - A sugestão de comunicação entre a parte sincrona e assincrona dever ser feita por um mecanismo FIFO para 
garantir a prioridade da entrega de forma sequencial
    - Temos um worker especial no desenho que é ``schedule worker`` que funciona com uma busca no banco em um
intervalo de tempo especificado para o envio da notificação naquele intervalo de tempo. 
    - Outro ponto importante é todos os workers devem ser implementados em formato de containers para serem o 
mais isolados possiveis, sendo possivel o shutdown dos mesmos de forma isolada que não impacte o serviço por
um todo. 
    - Outra questão é, dependendo do nivel do sistema pode ser mais interessante se utilizar um provedor de notificação
como a salesforce ou até mesmo um onesignal que já tem abstrações de notificação agendadas de forma mais robusta.
    -  A comunicação entre a notificação e o provedor web em um momento mais simples pode ser implementado um 
websocket para que seja comunicado novas notificações, e no click do botão de notificação ele carregue todas as
notificações armazenadas para determinado usuário, de forma paginada, ou até mesmo uma busca mais avançada apenas com os
identificadores dessa notificação

### Banco de dados

Os banco de dados escolhidos foram ``mongodb`` e ``postgres`` em que como o formato da notificação é mutável e tem um formato
de documento é melhor o armazenamento em mongodb. Já o armazenamento na central de notificações foi pensando em um banco
relacional para se mantar o relacionamento de ``customerId`` global do usuário, aquilo que o define na organização e as 
suas configurações;

[Modelo de arquitetura](docs/big-picture.png)

###Feature Implementation

A implementação da funcionalidade segue o diagrama [Implementação da feature](docs/feature.png) onde mostra a conexão necessária
entre os modulos. 

### Melhorias a serem implementadas na arquitetura

- O uso de spring para os workers (como foi pensando inicialmente) pode gerar daemons muito gigantes fazendo com que os 
clusteres sejam muito pesados. 
- Testes de integração entre os clientes e o serviço
- Implementação da lib de comunicação
