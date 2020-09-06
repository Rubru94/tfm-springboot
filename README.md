# TFM - Springboot 

Web application created by Acevedoapps that allows you to calculate a budget for the implementation of Oracle NETSuite in your business quickly and easily.

![acevedo.png](images/logos/acevedo.png) ![oracleNetsuite.png](images/logos/oracleNetsuite.png)

It is based on an intuitive form, in charge of collecting basic data from the client and his company to store them in an independent database that serves to study the bulk of interested clients, as well as their business characteristics.

This project raises two technological perspectives notably distanced:

- The first alternative is a *Java* project implemented with *Maven* and using *Springboot* as a framework, which offers a REST application with MySQL database deployed in a local *Kubernetes* cluster with *minikube*. This option is explained in the [**current repository WIKI**](https://github.com/Rubru94/tfm-springboot/wiki).

- The second option is based on *AWS serverless* technologies, using *API Gateway*, *Lambda* functions, *DynamoDB* database and *SAM*. [**Linked repository**](https://github.com/Gabriel-Acevedo/tfm-aws).

## API DOC

* ### [REST](https://github.com/Rubru94/tfm-springboot/wiki/API-REST)

* ### [WEB](https://github.com/Rubru94/tfm-springboot/wiki/API-WEB)