# COVID-19 Impact Analysis API

Johns Hopkins University Center for Systems Science and Engineering (JHU CSSE) publishes data about Coronavirus COVID-19 impact on a daily basis.
This Spring Boot Restful service project uses a subset of that data-set and exposes some restful end-points for analytical purposes.

* Architecture
* Authentication and Authorization
* End-Points and Assumptions
* Tradeoffs

**1. Architecture**
I used *Spring Tools Suit* as it uses *maven* architecture. I created separate packages for each module.
* Security related working is placed under io.munib.covid19impactanalysisapi.security package.
* Api related working is placed under io.munib.covid19impactanalysisapi package and sub packages.

**2. Authentication and Authorization:**
For Authentication and Authorization, I used JWT (JSON Web Tokens).
As payload is encoded in base64 using a secret key to create JWT, I used *secret* as secret key.
* For JWT reference: *Java Brains: https://www.youtube.com/watch?v=X80nJ5T7YpE&list=PLqq-6Pq4lTTYTEooakHchTGglSvkZAjnE&index=12*

**3. End-Points and Assumptions**
* To get a JWT token: *http://localhost:8080/authenticate/{username}* (GET)
* All new cases reported today: *http://localhost:8080/api/totalcasestoday* (GET, Authorization header attribute with JWT token)
* All new cases reported today country wise (sorted by cases reported today descending): *http://localhost:8080/api/totalcasestoday-countrywise* (GET, Authorization header attribute with JWT token)
* All new cases reported today in a country: *http://localhost:8080/api/totalcasestoday/{country-name}* (GET, Authorization header attribute with JWT token)
* All new cases reported since a date in a country: *http://localhost:8080/api/totalcasessince/{country-name}/{date}* (Date format: yyyy-MM-dd i.e. 2020-8-3) (GET, Authorization header attribute with JWT token)
* Top N countries with most reported cases today: *http://localhost:8080/api/top-n-countries/{topNCountries}* (topNCountries int i.e. 10, 20) (GET, Authorization header attribute with JWT token)

**4. Tradeoffs:**
I have a method with *@PostConstruct* and *@Scheduling* annotations which will be called after the creation of the instance and after 24 hours.
* I am not storing covid data in any database or in any file instead I am storing it in a List. It will be a lost of space to store it as remote covid data is changing every day.
* On each request, data is retrieved from the list and sent as response.
* I used H2 database for storing and retrieving Jwt token associated with a username.
* Previous jwt token is invalidated and new jwt token is generated if a user again sends request to /authenticate with the same username.