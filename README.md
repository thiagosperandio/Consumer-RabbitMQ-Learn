# RabbitMQ-Consumer
Aprendizado com tutorial do RabbitMQ - Projeto que recebe mensagens

> Seguindo os tutoriais de Java em https://www.rabbitmq.com/tutorials

## Rodando RabbitMQ no Docker

### Baixamos e rodamos localmente o rabbitmq para ser acessado no Docker


### Mudamos as portas para responder com as portas esperadas
- Usando `docker ps` para conhecer as portas, ou via Kitematic.
- Vendo se as portas foram criadas como o comando `netstat -ano | findstr :5672`.
- Mudando as portas via comandos ou via interface visual do Kitematic.

### Ver quais as filas sendo executadas dentro do container:
- `docker exec -it rabbitmq rabbitmqctl list_queues` ou `rabbitmqctl list_queues` via terminal aberto pelo Kitematic
