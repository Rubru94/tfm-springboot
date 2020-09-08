Continuous integration is carried out using **GitHub Actions**, defining some [workflows](https://github.com/Rubru94/tfm-springboot/tree/master/.github/workflows) differentiated according to the interaction event with the repository:

***

- [**main.yml**](../.github/workflows/main.yml): It will be triggered for each *push* to *master*, and contains the following jobs:

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

***

- [**pullrequest.yml**](../.github/workflows/pullrequest.yml): It will be triggered for each *merge* to *master* & every day at 00:00 AM on a scheduled basis, and contains the following jobs:

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

- [**release.yml**](../.github/workflows/release.yml): It will be triggered for each new release, and contains the following jobs:

    - ***Build app with all test***: Application is built passing all tests before build *Docker* image

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

    - ***Build & push docker image***: Build and publish the *Docker* image in the *Dockerhub* repository

  ```
  docker-image:
      name: Build & push docker image
      runs-on: ubuntu-latest
      needs: [all_test]
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
