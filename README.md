# Building Restful Webservices using Spring Boot

Banuprakash C

Full Stack Architect, 

Co-founder Lucida Technologies Pvt Ltd., 

Corporate Trainer,

Email: banuprakashc@yahoo.co.in

https://www.linkedin.com/in/banu-prakash-50416019/

https://github.com/BanuPrakash/Spring

===================================

Softwares Required:
1) Java 8+
	https://www.oracle.com/in/java/technologies/javase/javase8u211-later-archive-downloads.html

2) Eclipse for JEE  
	https://www.eclipse.org/downloads/packages/release/2022-09/r/eclipse-ide-enterprise-java-and-web-developers

3) MySQL  [ Prefer on Docker]

Install Docker Desktop

Docker steps:

```
a) docker pull mysql

b) docker run --name local-mysql –p 3306:3306 -e MYSQL_ROOT_PASSWORD=Welcome123 -d mysql

container name given here is "local-mysql"

For Mac:
docker run -p 3306:3306 -d --name local-mysql -e MYSQL_ROOT_PASSWORD=Welcome123 mysql


c) CONNECT TO A MYSQL RUNNING CONTAINER:

$ docker exec -t -i <container_name> /bin/bash

d) Run MySQL client:

bash terminal> mysql -u "root" -p

mysql> exit

```

* Spring Boot
* JPA and RESTful Webservices
* Testing / Validation / Exception Handling / Metrics / Cache
* HATEAOS
* Security
* CQRS and MicroServices

---------------------

RPC [early 90s] ==> CORBA [93] --> IDL --> WebServices [SOAP with XML] 1996 --> Roy Fielding 2000 --> RESTful Ws --> GraphQL 2011

http://abc.com

<create-product>
	<name>a</name>
	<price>222</price>
</create-product>

<get-product>
	<id>4</id>
</get-product>

REST ==> REpresentational State Transfer ==> archtectural style for distributed hypermedia systems

Resource ==> info reside on server [document or image or record from database or printer]

Resource representation ==> state of resource
* data
* metadata describing the data
* hypermedia links --> help clients in transition to next desired state
order --> Confirm --> Payment --> shipment
order --> Cancel

REST APIs use URI to address resources
* collection --> server managed resources [products/ songs /fooditems]; client may propose to add to collection
plural nouns:
http://amazon.com/api/products
http://amazon.com/api/customers

* store --> client managed
	http://spotify.com/song-management/users/{id}/playlist
	http://amazon.com/cart-management/users/{id}/wishlist
	CRUD operations are done by client

* Controller --> procedural concept --> executable functions --> verbs
	http://spotify.com/song-management/users/{id}/playlist/play
	http://amazon.com/cart-management/users/{id}/cart/checkout

--

HTTP methods for VERBS --> CRUD --> POST GET PUT DELETE

GET, DELETE --> no payload --> IDEMPOTENT --> Safe
PUT, POST --> contains payload --> Not Safe methods

1) 
GET
http://amazon.com/api/products
http://amazon.com/api/products
get all products

2) PathParameter
GET
http://amazon.com/api/products/4
get product whose id is "4"

http://amazon.com/api/orders/{id}
http://amazon.com/api/users/{id}/orders
get sub-resource

3) Query Parameter
GET
http://amazon.com/api/products?type=mobile
http://amazon.com/api/products?page=3&size=10

filter --> sub-set

4)
POST
http://amazon.com/api/products

payload contains new product to be added to "products" resource

5)
PUT
http://amazon.com/api/products/4

payload contains new product data to be updated to "products" resource with id of "4"

6) 
DELETE
http://amazon.com/api/products/5

delete a product with id -> "5"

----------------------------------------------------


Guiding Principles of REST:
1) Uniform Interface
2) Client-server --> separation of concerns, Server pass representation based on ContentNegotiation [JSON / XML / CSV]
3) Stateless --> mandates each request from client to the server must contain all previous info
4) Cacheable

====================================================


Spring Framework --> lightweight container for building enterprise application
Container ==> 
1) Life-cycle management [ objects managed by spring container are referred as "bean"]
2) Dependency Injection 
....

interface BookDao {
	addBook();
}

class BookDaoRdbmsImpl implements BookDao {
	addBook() {..}
}

class BookDaoMongoImpl implements BookDao {
	addBook() {..}
}

class BookService {
	BookDao dao;

	setDao(BookDao dao) {
		this.dao = dao;
	}
	doTask() {
		dao.addBook();
	}
}

beans.xml
<bean id="rdbms" class="pkg.BookDaoRdbmsImpl" />
<bean id="mongo" class="pkg.BookDaoMongoImpl" />

<bean id="service" class="pkg.BookService">
	<property name="dao" ref="rdbms" />
</bean>

ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml"); // create spring container

BookService service = ctx.getBean("service");
service.doTask();

====

Annotation as metadata:

Spring creates instances of classes which has one of the following annoations:
1) @Component
2) @Repository
3) @Service
4) @Configuration
5) @Controller
6) @RestController


interface BookDao {
	addBook();
}

@Repository
class BookDaoMongoImpl implements BookDao {
	addBook() {..}
}

@Service
class BookService {
	@Autowired
	BookDao dao;

	doTask() {
		dao.addBook();
	}
}

ApplicationContext ctx = new AnnoationConfigApplicationContext(); // create spring container
ctx.scan("com.adobe.prj");

---

Eclipse => Help ==> Eclipse Markeplace ==> Search for STS --> GO --> Sprint Tool Suite 4.16.x --> install

Spring Boot [2.x] ?
--> Framework on top of Spring Framework [5.x]

Spring Boot simplifies development which comes with out-of-box configurations

Spring boot is highly opinated framework
--> If building appliations with RDBMS, spring boot creates DataSource --> pool of database connection using HikariCP
--> If Webapplication is being built --> configures Tomcat as Embedded WebServer [ jetty / netty/ ..]
--> If ORM is used Hibernate is configured --> instead of [Toplink / Kodo / OpenJPA/ EclipseLink ]

In Spring Framework:
@Configuration
public class AppConfig {
	@Bean
	public DataSource getDataSource() {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass( "org.postgresql.Driver" ); //loads the jdbc driver            
		cpds.setJdbcUrl( "jdbc:postgresql://localhost/testdb" );
		cpds.setUser("swaldman");                                  
		cpds.setPassword("test-password");                                  
			
		// the settings below are optional -- c3p0 can work with defaults
		cpds.setMinPoolSize(5);                                     
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);

		return cpds;
	}
}


In @configuration classes Spring executes all methods marked as @Bean and returned objects are managed by spring container


public class MyDao {
	@Autowired
	DataSource ds;

}

==============

package com.adobe.demo;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}


SpringApplication.run() --> loosely translates to new AnnotationConfigApplicationContext();

@SpringBootApplication
* @ComponentScan("com.adobe.demo")
* @EnableAutoConfiguration
* @Configuration

---------
https://github.com/spring-projects/spring-framework/blob/main/spring-jdbc/src/main/resources/org/springframework/jdbc/support/sql-error-codes.xml

try {

} catch(SQLException ex) {
	if(ex.getErrorCode() == 1064) {
		thr...
	}
}

***************************
APPLICATION FAILED TO START
***************************

Description:

Field bookDao in com.adobe.demo.service.BookService required a single bean, but 2 were found:
	- bookDaoMongoImpl:  
	- bookDaoRdbmsImpl:


Solution 1:
Making one the implementation class @Primary


@Repository
@Primary
public class BookDaoRdbmsImpl implements BookDao {

@Repository
public class BookDaoMongoImpl implements BookDao {

-----

Solution 2:

@Repository("mongo")
public class BookDaoMongoImpl implements BookDao {


@Repository("rdbms")
public class BookDaoRdbmsImpl implements BookDao {	



@Service
public class BookService {
	@Autowired
	@Qualifier("mongo")
	private BookDao bookDao; 


---------

Solution 3: Using Profile

@Service
public class BookService {
	@Autowired
	private BookDao bookDao; 
	

@Profile("prod")
@Repository
public class BookDaoMongoImpl implements BookDao {


@Profile("dev")
@Repository
public class BookDaoRdbmsImpl implements BookDao {

application.properties
spring.profiles.active=prod

OR

DemoApplication ==> Run ==> Run As ==> Arguments ==> Program
--spring.profiles.active=dev


----

Solution 4:
application.properites
book-dao=rdbms


@Repository
@ConditionalOnProperty(name = "book-dao", havingValue = "mongo")
public class BookDaoMongoImpl implements BookDao {

@Repository
@ConditionalOnProperty(name = "book-dao", havingValue = "rdbms")
public class BookDaoRdbmsImpl implements BookDao {


----

Solution 5:

@Repository
public class BookDaoMongoImpl implements BookDao {

@Repository
@ConditionalOnMissingBean(name= "bookDaoMongoImpl")
public class BookDaoRdbmsImpl implements BookDao {

