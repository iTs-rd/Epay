<h1 align="center"> ePay - Your Digital Wallet </h1>
ePay is a user-centric digital wallet service designed for effortless financial management.
With ePay, users can create and update their unique IDs, add funds securely, withdraw money, 
and seamlessly transfer funds to other users. This platform empowers individuals with control over their finances, 
offering a simple and secure solution for all their digital wallet needs.

## Key Words
    #JWT_Tokens #Transactional_Nature #Elasticsearch #Jenkins #CI/CD
    #Unit_Test_Case #JUnit #Swagger_UI #Docker #Docker_Compose #OTP_Verification


## Key Points

1) JWT Tokens are employed for authentication purposes.
2) Custom exceptions are created for enhanced error handling.
3) Spring Security is implemented for securing the application.
4) The API transactions are configured to be inherently transactional, ensuring **automatic rollback** in the event of errors.
5) The application is containerized using a Dockerfile.
6) Docker Compose is used for streamlined deployment.
7) Jenkins is harnessed to construct a CI/CD pipeline.
8) The incorporation of Elasticsearch serves to boost system performance.
9) Comprehensive application documentation is provided through the integration of Swagger-UI.
10) Unit test cases are crafted in JUnit to ensure that the code meets quality standards.
11) Phone number verification is conducted via OTP.



## APIs

### Swagger-UI -> ```api/swagger-ui/index.html```

### User Controller

| METHOD   | ROUTE                                   | FUNCTIONALITY                       | ACCESS       |
|----------|-----------------------------------------|-------------------------------------|--------------|
| *POST*   | ```/api/user/signup```                  | _Register new user_                 | _Open_       |
| *POST*   | ```/api/user/verify-phoneno```          | _Verify OTP_                        | _Open_       |
| *POST*   | ```/api/user/login```                   | _Login using phone no and password_ | _Open_       |
| *GET*    | ```/api/user```                         | _Get user details_                  | _Need Token_ |
| *PUT*    | ```/api/user```                         | _Update user details_               | _Need Token_ |
| *DELETE* | ```/api/user```                         | _Delete user_                       | _Need Token_ |

### Wallet Controller

| METHOD | ROUTE                          | FUNCTIONALITY                    | ACCESS       |
|--------|--------------------------------|----------------------------------|--------------|
| *GET*  | ```/api/wallet/checkbalance``` | _Check wallet balance_           | _Need Token_ |
| *POST* | ```/api/wallet/deposit```      | _Deposit money in wallet_        | _Need Token_ |
| *POST* | ```/api/wallet/withdrawal```   | _Withdraw money from wallet_     | _Need Token_ |
| *POST* | ```/api/wallet/transfer```     | _Transfer money to another user_ | _Need Token_ |

### Wallet Controller

| METHOD | ROUTE                             | FUNCTIONALITY             | ACCESS       |
|--------|-----------------------------------|---------------------------|--------------|
| *GET*  | ```/api/statement?pageNumber=0``` | _Get Statement of wallet_ | _Need Token_ |

Authorization: Bearer {token}


## Requirements

#### 1) Elasticsearch is operational and accessible on port number 9200 (i.e. http://localhost:9200).
#### 2) MySql is operational and accessible on port number 3306 ( Username=root and password= root )


NOTE: 
1) You have the flexibility to modify the port number according to your preferences in the application.yaml file.
2) The version of Elasticsearch being used is 7.17.14.



## Resources
- use full command 
1)  ```docker run -p 9200:9200 -d -m 1GB -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.17.14```
2) ```docker run -p 3306:3306 -d -e MYSQL_ROOT_PASSWORD=root mysql```


