# Introducing >>>>> Digital Retailer
Digital retailer exposes API for retrieving customer records, calculates their rewards points etc.
As the application starts it loads some sample records to test with.

# How to run the application ?
    - Gradle needs to be pre-installed in your machine
    - ./gradlew clean build
    - ./gradlew bootRun
        Note: gradle should be able to auto detail the spring boot application file: ServiceImplApplication
        and start it on port 8080.

    - OR if not gradle, then open the application in Intelij Idea and right click on ServiceImplApplication under service-impl project 
        and run.

# Sample Rest Request
    Customer 9999: This customer has only one transaction made in May 1st 2022 for a $120
    ----------------------------------------------------------------------------------------------------
    curl --location --request GET 'http://localhost:8080/customer/99999/reward-points?numOfMonths=5' \
    --header 'X-Application-Id: digital-web'
        
    OTHER Samples:
    ----------------------------------------------------------------------------------------------------
    curl --location --request GET 'http://localhost:8080/customer/12345/reward-points?numOfMonths=5' \
    --header 'X-Application-Id: digital-web'

    OR without query params (default 3 months)

    curl --location --request GET 'http://localhost:8080/customer/12345/reward-points?numOfMonths=5' \
    --header 'X-Application-Id: digital-web'

    If customer not exists - NOT FOUND will be returned
    If transactions don't exist - NO Content will be returned
    For valid tranactions - Customer ID with rewards points will be returned.
    
    Sample Response:
    {
        "customer-id": 12345,
        "reward-points": 154
    }

# H2-config
    1. data.sql and schema.sql are created for customer(s) and sample transactions are included.
    2. http://localhost:8080/h2 (To view the h2 db console)
    Note: This currently needs to be looked into.
