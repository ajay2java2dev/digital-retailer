# Introducing >>>>> Digital Retailer
Digital retailer exposes API for retrieving customer records, calculates their rewards points etc.
As the application starts it loads some sample records to test with.

# How to run the application ?
    - Gradle needs to be pre-installed in your machine
    - ./gradlew clean build
    - ./gradlew bootRun
        Note: gradle should be able to auto detect the spring boot application file: ServiceImplApplication
        and start it on port 8080.

    - OR if not gradle, then open the application in Intelij Idea and right click on ServiceImplApplication under service-impl project 
        and run.

# Build (Github action)
    - Every Push and Pull to github will trigger github actions build which will do the gradle clean build.
    - Also a cron job is included for the build to auto trigger everyday.
    - https://github.com/ajay2java2dev/digital-retailer/actions

![github-actions](/static-content/images/github-actions.png)

# Sample Rest Request

    Customer 9999: This customer has only one transaction made for a $120 amount
    ----------------------------------------------------------------------------------------------------
    curl --location --request GET 'http://localhost:8080/customers/99999/reward-points' \
    --header 'X-Application-Id: digital-web'

    Sample Response:
    {
        "customer-id": 99999,
        "reward-points": 90
    }

<br/>

    Few other examples:
    ----------------------------------------------------------------------------------------------------
    curl --location --request GET 'http://localhost:8080/customers/12345/reward-points?numOfMonths=5' \
    --header 'X-Application-Id: digital-web'

    OR without query params (default 3 months)

    curl --location --request GET 'http://localhost:8080/customers/12345/reward-points' \
    --header 'X-Application-Id: digital-web'

    If customer not exists - NOT FOUND will be returned
    If transactions don't exist - NO Content will be returned
    For valid tranactions - Customer ID with rewards points will be returned.
    
    Sample Response:
    {
        "customer-id": 12345,
        "reward-points": 103
    }

<br/>

    Invalid Param (negative numOfMonths)
    ----------------------------------------------------------------------------------------------------


    Request: curl --location --request GET 'http://localhost:8080/customers/12345/reward-points?numOfMonths=-1' \
    --header 'X-Application-Id: blah'
    
    Response:
    {
        "errors": [
            {
                "status": "Bad Request: numOfMonths",
                "message": "numOfMonths should be between 1 & 120"
            }
        ]
    }


# H2-config
    1. data.sql and schema.sql are created for customer(s) and sample transactions are included.
    2. http://localhost:9082/ (To view the h2 db console)
    3. JDBC_URL: jdbc:h2:mem:digital_retailer_db, username: sa, password: password
    Note: Check H2ConsoleConfig.java for additional info on how h2 console is configured and why webflux doesn't 
    work well directly with.

![h2-console](/static-content/images/h2-console.JPG)

# Code coverage (as of 05.06.2022)

![code-coverage](/static-content/images/code-coverage.JPG)

# Spring Native support
    - https://docs.spring.io/spring-native/docs/current/reference/htmlsingle/
    - gradle bootBuildImage
    - docker run --rm -p 8080:8080 digital-retailer/service-impl:latest