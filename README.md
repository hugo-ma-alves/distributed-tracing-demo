# Distributed tracing demo with ELK stack

Sample application demonstrating the 3 pillars of observability using the ELK stack.
For all the details related with this demo check the following blog post: https://www.hugomalves.com/observability-the-3-pillars-with-elk-stack

# What's included

|               | Location                  |
| --------      | -------                  |
| Kibana        | http://localhost:5601    |
| APM Server    |  localhost:8200           |
| Elastic       | localhost:9200            |
| Demo app        | http://localhost:8080    |



# How to run the project:

Start all the modules using ```docker compose build``` and docker ```compose up -d```


After all the modules are running send some requests to the API to generate some /logs/metrics/traces

    POST http://localhost:8080/orders
    Content-Type: application/json

    {
    "clientName": "John Doe",
    "products": [
        {
        "productId": 1,
        "quantity": 2
        },
        {
        "productId": 3,
        "quantity": 1
        }
    ]
    }

Open APM in Kibana http://localhost:5601/app/discover#/ and explore the traces