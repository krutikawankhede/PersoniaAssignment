## Personia-service
Peersonia service application help HR manager of  Personia to get a grasp of her ever-changing company’s hierarchy!

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a system.

## Prerequisites
Things you need to get the project up and running on a local machine:

Docker latest version
docker-compose latest version

## Building and Running the application
Execute the below docker-compose command to run the application from the directory you have copied the application
docker-compose up --build

## REST services

As a HR you will have all the access to add the admin to the database
# Add employee API:
This Api will be available only with the admin cris and before using personia service she needs to register herslef with personia using below api

    POST API : localhost:8080/admin/addEmployee
    Accepts json : {
        "username":"cris",
        "password":"cris"
    }

This api will only be available with the manager and can register herself to use personia service.

# Generate token api:
This api will generate a token to access the application present in personia service.If the employee is not present in the admin database then the user will not able to generate the token.

    POST API : localhost:8080/personia-service/generateToken
    Accepts json : {
        "username":"cris",
        "password":"cris"
    }

# Employee Hirarchy relationship
This api will take the json and send the response with hirarchy

    POST API : localhost:8080/personia-service/employeeRelationship
    Accepts json : {
        "Sophie":"Jonas",
        "Nick":"Sophie"
        "Pete":"Nick",
        "Barbara":"Nick"
    }



# Get Supervisor details for Employee Hirarchy
This api will give all the respective supervisors for the employee in a list where employee is at the start of the list.

    GET API locakhost:8080:/personia-service/getSupervisorDetails/{name}


Stopping the application and cleaning-up
docker-compose down -v --rmi all --remove-orphans