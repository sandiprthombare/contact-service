# contact-service

*This microservice is based in spring boot framework. Providing API's to manage contact details
## Folder Structure
    ├── build                   # Compiled files (alternatively `dist`)
    ├── docs                    # Documentation files (alternatively `doc`)
    ├── src                     # Source files (alternatively `lib` or `app`)
    │   ├── main
    │   │   ├── java
    │   │   │   ├── com
    │   │   │   │   ├── sandip
    │   │   │   │   │   ├── contact                                   # Package for config classes
    │   │   │   │   │   │   ├── ContactServiceApplication.java        # Spring Bootstraping class
    │   │   │   │   │   │   ├── LoggingAspect.java                    # Spring AOP implementation for logging
    │   │   │   │   │   │   ├── SwaggerConfi.java                     # Swagger API configuration for API documentation
    │   │   │   │   │   │   ├── GlobalExceptionHandler.java           # Global Exception Handler to to catch all Exceptions thrown by by Service and return Error response
    │   │   │   │   │   │   │   ├── controller                        # Package for controller classes
    │   │   │   │   │   │   │   │   ├── ContactController.java        # ContactController All Rest end points registered here
    │   │   │   │   │   │   │   ├── dto                               # Package for DTO classes
    │   │   │   │   │   │   │   │   ├── ContactDto.java               # ContactDto Data Transmission Object for client interactions
    │   │   │   │   │   │   │   │   ├── ErrorDetails.java             # ErrorDetails Dto to return error response incase of any exceptions
    │   │   │   │   │   │   │   ├── exceptions                        # Package for Exception Handling classes
    │   │   │   │   │   │   │   │   ├── InvalidInputException.java    # User Defined Custom Exception to be thrown in case of invalid request from client
    │   │   │   │   │   │   │   │   ├── RecordNotFoundException.java  # User Defined Custom Exception to be thrown in case of Data not available for requested resource
    │   │   │   │   │   │   │   ├── model                             # Package for Entity classes
    │   │   │   │   │   │   │   │   ├── Contact.java                  # Contact Entity for DB interactions
    │   │   │   │   │   │   │   ├── repository                        # Package for Repository Interfaces / classes
    │   │   │   │   │   │   │   │   ├── ContactRepository.java        # Contact Repository implementing JPARepository to get implementation of predefined methods of data fetch
    │   │   │   │   │   │   │   ├── service                           # Package for Service Interfaces & classes
    │   │   │   │   │   │   │   │   ├── ContactService.java           # Contact Service interface for business logic methods
    │   │   │   │   │   │   │   │   ├── ContactServiceImpl.java       # Contact Service Class for business logic methods implementing interface
    │   │   │   │   │   │   │   ├── util                              # Package for Service Interfaces & classes
    │   │   │   │   │   │   │   │   ├── Util.java                     # Utility methods to convert Entity to Dto and vice versa
    │   │   ├── resources                                             # Package for Service Interfaces & classes
    │   │   │   ├── application.properties                            # application configuration 
    │   ├── test                                                      # Automated tests (alternatively `spec` or `tests`)
    │   │   ├── com
    │   │   │   ├── sandip
    │   │   │   │   ├── contact
    │   │   │   │   │   ├── controller                                # Package for controller Test classes
    │   │   │   │   │   │   ├── ContactControllerTest.java            # ContactController test cases
    │   │   │   │   │   ├── service                                   # Package for controller test classes
    │   │   │   │   │   │   ├── ContactServiceTest.java               # ContactController All Rest end points registered
    └── README.md
    └── ...
    
 
## Getting Started

* Machine should have java 8, maven installed
* go to project directory and apply comand **mvn install**
* Once maven build is successful jar **contact-service\target\contact-service-0.0.1-SNAPSHOT.jar** will be created
* It have embedded tomact and runs on default **9090** port
* Go to target folder and apply command **java -jar contact-service-0.0.1-SNAPSHOT.jar**
* Service will start, you can access swagger documentation of UI on **http://localhost:9090/swagger-ui.html** or **http://ip:9090/swagger-ui.html**

## Running UI module with API's
* Current version is added in API static content so you can access UI also with **http://localhost:9090/**
