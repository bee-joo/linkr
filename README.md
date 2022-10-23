# linkr
Little reactive service to make URLs tiny

## Features
- URL shortener
- Redirection
- URL validation -> URL storing

## Stack
- Java 17 - lang
- [Gradle](https://github.com/gradle/gradle) - build system
- [Redis](https://github.com/redis/redis) - database
- [Spring WebFlux](https://github.com/spring-projects/spring-framework/tree/main/spring-webflux) - reactive library, DI & IoC
- [Spring Data Redis (Reactive)](https://github.com/spring-projects/spring-data-redis) - reactive data access
- [Apache Commons Lang](https://github.com/apache/commons-lang) - string generation
- [Apache Commons Validator](https://github.com/apache/commons-validator) - URL validation (java.net validator is bad)
