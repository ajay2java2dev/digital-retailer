# Digital Retailer Introduction
Digital retailer exposes API for retrieving customer records, calculates their rewards points etc.
As the application starts it loads some sample records to test with.

# Sample Rest Request
    curl --location --request GET 'http://localhost:8080/customer/12345/reward-points?num_of_months=3' 
    --header 'X-Application-Id: digital-web'

# H2-config
    1. http://localhost:8080/h2-console
    2. data.sql and schema.sql are created for customer(s) and sample transactions in the period of last one year.
