## Install and run app

- clone repository with `git clone https://github.com/r0mainp/rental-api.git`
- install dependencies with `mvn install`
- create a file `env.properties` in `/src/main/resources/env.properties` and add

 ```
    # aws s3 properties
    aws.access.key=`${AWS_ACCESS_KEY:<YOUR ACCESS KEY>}`
    aws.secret.key=${AWS_SECRET_KEY:<YOUR SECRET KEY>}
    aws.s3.bucket=${AWS_S3_BUCKET:<your-bucket-name>}
    
    # DB infos
    spring.datasource.url=jdbc:mysql://<db_url>::<port>?useSSL=false
    spring.datasource.username=<DB username>
    spring.datasource.password=<DB password>
    
    # JWT config
    security.jwt.secret-key=<SHA-256 secret key>
    # 1h in millisecond
    security.jwt.expiration-time=<custom expiration time in ms>
```

- run app with `mvn spring-boot:run`


### Rental app API

- This is an api for a rental app implementing routes necessary for
    - register a user
    - log a user
    - list all rentals
    - displat renatl detail
    - create rental
    - edit rental
    - send a message to rental owner
- The app is secured with JWT token
- App uses a S3Service to upload rental pictures to as S3 bucket.