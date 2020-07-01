# TFM - Springboot 

## CI - GitHub Actions

La parte de integración continua se lleva a cabo empleando **GitHub Actions**, definiendo unos [workflows](.github/workflows) diferenciados según el evento de interacción con el repositorio:

- [**main.yml**](.github/workflows/main.yml): Se disparará por cada *push* a *máster*, y contiene los siguientes jobs:

  - ***Build app without test***: Construye la aplicación sin pasar test y almacena el binario como artefacto.

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

  - ***Pass unit tests***: Se pasan test unitarios empleando el artefacto construido en el job anterior.

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

  - ***Run docker-compose***: Se ejecuta el *docker-compose* con la imágen de la aplicación almacenada en *dockerhub* (test).

  ```
  docker-compose:
    name: Run docker-compose 
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v2
    - name: Set up JDK 11
      uses: actions/setup-java@v1
      with:
        java-version: 11
    - name: Run docker-compose 
      run: docker-compose up
  ```

- [**pullrequest.yml**](.github/workflows/pullrequest.yml): Se disparará por cada *merge* a *máster*, y contiene el siguiente job:

  - ***Build app with all test***: Se construye la aplicación pasando todos los test

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

## CD - Flux

### Requisitos

- **Kubectl** , para interactuar con el clúster de Kubernetes, con las siguientes versiones:

  - Client Version v1.8.7
  - Server Version v1.18.3

- **Helm** , que permitirá empaquetar y gestionar las aplicaciones de Kubernetes. Versión igual o superior a la v3.1.1.

- **fluxctl** , en una versión igual o superior a la v1.19.0, que nos permitirá acceder al servicio FluxCD instalado en el clúster de Kubernetes.

***

La agrupación lógica del repositorio se basará en *namespaces*, que resultarán aquellos que también encontraremos en el clúster kubernetes en que se despliegue la aplicación.

Actualmente disponemos de los siguientes *namespaces*:

- **flux-system**: Donde situaremos los pods y servicios que se habilitan con la instalación de [*flux*](script/flux_install.sh) (incluido *helm-operator*). 

- **tfm-springboot**: Donde se despliega la aplicación y la base de datos *mysql* asociada. En las *annotations* del *deployment* del fichero [**application.yml**](namespaces/tfm-springboot/application.yml), indicaremos la configuración de *flux* relativa a la actualización automática de las imágenes de la aplicación. 

Esta configuración automática es más propia de entornos de **PRODUCCIÓN**, de manera que posteriormente se dispondrá de un namespace para entorno de **TEST** y uno para entorno de **PROD**

***

En el fichero [**flux_install.sh**](script/flux_install.sh), que contiene todos los ajustes para instalar *flux*, cabe destacar algunas especificaciones como:

```
--set sync.interval=2m
```

Que establece el intervalo de sincronización (ciclo de reconciliación) del clúster con el repositorio github.

***

Para establecer la comunicación entre la instalación de *flux* y el repositorio de github será necesario una key, que podemos obtener con la siguiente instrucción:

```
sudo fluxctl identity --k8s-fwd-ns flux-system
```

Esto nos devolverá una clave *ssh* que deberemos añadir como deploy key en el repositorio en *github*. También tendremos que activar la opción *Allow write access*.

Una vez la deploy key sea validada, deberemos sincronizar *flux* con el repositorio, ejecutando la siguiente instrucción:
```
sudo fluxctl sync --k8s-fwd-ns flux-system
```
Cuando termine la sincronización, de manera automática se creará una release en el repositorio de *github* que siempre apuntará al *latest commit*.

En ese momento el clúster estará actualizado y podremos observar que disponemos de todos los deployments y namespaces que posee el repositorio.

En caso de eliminar un deployment por error, *flux* en su periodo de reconciliación,
establecido en este caso en 2 minutos, detectará que el estado del clúster no coincide con el del repositorio, y por tanto lo reestablecerá.

***

Por otra parte *flux* garantizará la automatización del despliegue mediante versionado semántico, actualizando el clúster y el repositorio *github*, cada vez que se añada al repositorio de imágenes una versión superior a la anterior.

En caso de que la última versión de la imágen se elimine, realizará un rollback y
retornará automáticamente a la versión anterior.