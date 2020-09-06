# TFM - Springboot 

Web application created by Acevedoapps that allows you to calculate a budget for the implementation of Oracle NETSuite in your business quickly and easily.

![acevedo.png](images/logos/acevedo.png) ![oracleNetsuite.png](images/logos/oracleNetsuite.png)

It is based on an intuitive form, in charge of collecting basic data from the client and his company to store them in an independent database that serves to study the bulk of interested clients, as well as their business characteristics.

This project raises two technological perspectives notably distanced:

- The first alternative is a *Java* project implemented with *Maven* and using *Springboot* as a framework, which offers a REST application with MySQL database deployed in a local *Kubernetes* cluster with *minikube*. **This option is explained in the current repository**.

- The second option is based on *AWS serverless* technologies, using *API Gateway*, *Lambda* functions, *DynamoDB* database and *SAM*. [**Linked repository**](https://github.com/Gabriel-Acevedo/tfm-aws).

## API DOC

* ### [REST](https://github.com/Rubru94/tfm-springboot/wiki/API-REST)

* ### [WEB](https://github.com/Rubru94/tfm-springboot/wiki/API-WEB)

## CI - GitHub Actions

Continuous integration is carried out using **GitHub Actions**, defining some [workflows](.github/workflows) differentiated according to the interaction event with the repository:

- [**main.yml**](.github/workflows/main.yml): It will be triggered for each *push* to *master*, and contains the following jobs:

  - ***Build app without test***: Build the application without passing tests and store the binary as an artifact.

  ```
  build:
    name: Build app without test
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v2
    - name: Set up JDK 11
      uses: actions/setup-java@v1
      with:
        java-version: 11
    - name: Build with Maven
      run: mvn -B clean package -DskipTests --file pom.xml
    - name: Upload target for next job
      uses: actions/upload-artifact@v1
      with:
        name: target
        path: target
  ```

  - ***Pass unit tests***: Unit tests are passed using the artifact built in the previous job.

  ```
  unit_test:
    name: Pass unit tests
    runs-on: ubuntu-latest
    needs: [build]
    services:
      mysql:
        image: mysql:8
        ports:
          - 3306
        env:
          MYSQL_DATABASE: test
          MYSQL_ROOT_PASSWORD: ${{ secrets.MYSQL_ROOT_PASSWORD }}
        options: --health-cmd="mysqladmin ping" --health-interval=25s --health-timeout=5s --health-retries=3
    steps:
    - uses: actions/checkout@v2
    - name: Download target from previous job
      uses: actions/download-artifact@v1
      with:
        name: target
    - name: Set up MySQL
      uses: mirromutth/mysql-action@v1.1
      with:
        character set server: 'utf8' 
        collation server: 'utf8_general_ci'
        mysql database: 'test' 
        mysql root password: ${{ secrets.MYSQL_ROOT_PASSWORD }}
    - name: Pass test
      run: mvn test -Dtest=ControllerUnitTest
      env: 
        DB_PORT: ${{ job.services.mysql.ports[3306] }}
  ```

  - ***Analyze code with SonarCloud***: The repository code is analyzed using *Sonarcloud* with the artifact built in the initial job.

  ```
  sonarcloud:
  name: Analyze code with SonarCloud
  runs-on: ubuntu-latest
  needs: [build]
  services:
    mysql:
      image: mysql:8
      ports:
        - 3306
      env:
        MYSQL_DATABASE: test
        MYSQL_ROOT_PASSWORD: ${{ secrets.MYSQL_ROOT_PASSWORD }}
      options: --health-cmd="mysqladmin ping" --health-interval=25s --health-timeout=5s --health-retries=3
  steps:
  - uses: actions/checkout@v2
  - name: Download target from previous job
    uses: actions/download-artifact@v1
    with:
      name: target
  - name: Analyze with SonarCloud
    run: mvn -B -DskipTests verify sonar:sonar -Dsonar.projectKey=Rubru94_tfm-springboot -Dsonar.organization=rubru94 -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=$SONAR_TOKEN
    env:
      GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
      SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}
  ```

- [**pullrequest.yml**](.github/workflows/pullrequest.yml): It will be triggered for each *merge* to *master* & every day at 00:00 AM on a scheduled basis, and contains the following jobs:

  - ***Build app with all test***: The application is built passing all the tests

  ```
  all_test:
        name: Build app with all test
        runs-on: ubuntu-latest
        services:
          mysql:
            image: mysql:8
            ports:
              - 3306
            env:
              MYSQL_DATABASE: test
              MYSQL_ROOT_PASSWORD: ${{ secrets.MYSQL_ROOT_PASSWORD }}
            options: --health-cmd="mysqladmin ping" --health-interval=25s --health-timeout=5s --health-retries=3
        steps:
        - uses: actions/checkout@v2
        - name: Set up JDK 11
          uses: actions/setup-java@v1
          with:
            java-version: 11
        - name: Set up MySQL
          uses: mirromutth/mysql-action@v1.1
          with:
            character set server: 'utf8' 
            collation server: 'utf8_general_ci'
            mysql database: 'test' 
            mysql root password: ${{ secrets.MYSQL_ROOT_PASSWORD }}
        - name: Build with Maven
          run: mvn -B clean package -DskipTests --file pom.xml
        - name: Pass all test
          run: mvn -B test
          env: 
            DB_PORT: ${{ job.services.mysql.ports[3306] }}
  ```

  - ***Analyze code with SonarCloud***: The repository code is analyzed by *Sonarcloud* after building the application and passing all the tests.

  ```
  sonarcloud:
    name: Analyze code with SonarCloud
    runs-on: ubuntu-latest
    needs: [all_test]
    services:
      mysql:
        image: mysql:8
        ports:
          - 3306
        env:
          MYSQL_DATABASE: test
          MYSQL_ROOT_PASSWORD: ${{ secrets.MYSQL_ROOT_PASSWORD }}
        options: --health-cmd="mysqladmin ping" --health-interval=25s --health-timeout=5s --health-retries=3
    steps:
    - uses: actions/checkout@v2
    - name: Set up JDK 11
      uses: actions/setup-java@v1
      with:
        java-version: 11
    - name: Analyze with SonarCloud
      run: mvn -B -DskipTests verify sonar:sonar -Dsonar.projectKey=Rubru94_tfm-springboot -Dsonar.organization=rubru94 -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=$SONAR_TOKEN
      env:
        GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
        SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}
  ```

- [**release.yml**](.github/workflows/release.yml): It will be triggered for each new release, and contains the following job:

  - ***Build & push docker image***: Build and publish the docker image in the *Dockerhub* repository

  ```
  docker-image:
      name: Build & push docker image
      runs-on: ubuntu-latest
      steps:
      - uses: actions/checkout@v2
      - name: Get the version
        id: get_version
        run: echo ::set-output name=VERSION::$(echo $GITHUB_REF | cut -d / -f 3)
      - name: Build & push docker image
        uses: docker/build-push-action@v1
        with:
          username: ${{ secrets.DOCKER_USERNAME }}
          password: ${{ secrets.DOCKER_PASSWORD }}
          repository: rubru94/tfm-springboot
          tags: ${{ steps.get_version.outputs.VERSION }}
  ```


## CD - Flux

### Requirements

- **Helm**, that will allow you to package and manage your *Kubernetes* applications. Version equal to or greater than v3.1.1.

- **fluxctl**, on version equal to or greater than v1.19.0, which will allow us to access the FluxCD service installed in the *Kubernetes* cluster.

***

The logical grouping of the repository will be based on *namespaces*, which will be those that we will also find in the *Kubernetes* cluster where the application is deployed.

The following *namespaces* are currently available:

- **flux-system**: Pods and services that are enabled with the installation of [*Flux*](script/flux_install.sh), including *helm-operator*.

- **tfm-springboot**: **PRODUCTION** environment where the application and the associated *MySQL* database are deployed. We will indicate in *deployment annotations* from file [**application.yml**](namespaces/tfm-springboot/application.yml), the configuration of *Flux* related to automatic update of application images.

- **tfm-springboot-test**: **TEST** environment where the application and the associated *MySQL* database are deployed. Application images will be updated manually, using:

```
sudo fluxctl release --workload=${NAMESPACE}:deployment/${APP_NAME} -n ${NAMESPACE} --k8s-fwd-ns ${NAMESPACE} --update-image=${DOCKER_IMAGE_NAME}:${VERSION}
```

***

File [**flux_install.sh**](script/flux_install.sh), contains all the settings to install *Flux*, and we must highlight some specifications such as:

```
--set sync.interval=2m
```

Which sets the synchronization interval (reconciliation cycle) of the cluster with the GitHub repository.

***

A key will be required to establish communication between the *Flux* installation and the GitHub repository. We can obtain it with following instruction:

```
sudo fluxctl identity --k8s-fwd-ns flux-system
```

This will return an *ssh* key that we must add as a **deploy key** in the GitHub repository. Also, we will have to activate the *Allow write access* option.

Once the deploy key is validated, we must synchronize *Flux* with the repository, executing the following instruction:

```
sudo fluxctl sync --k8s-fwd-ns flux-system
```

When the synchronization finishes, a release will automatically be created in the GitHub repository that will always point to latest commit.

At that time the cluster will be updated and we can see that we have all the deployments and namespaces that the repository has.

In case of deleting a deployment by mistake, *Flux* in its reconciliation period, set in this case to 2 minutes, will detect that the state of the cluster does not match that of the repository, and will reestablish it.

***

*Flux* will guarantee the automation of the deployment through semantic versioning, updating the cluster and the GitHub repository, each time that a version higher than the previous one is added to the image repository.

If the latest version of the image is removed, it will rollback and automatically return to the previous version.