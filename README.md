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

--------------

Spring Boot --> JPA Integration

ORM --> Framework ==> Object Relational Mapping --> layer on top of JDBC

With Spring:
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

	@Bean
	public EntityManagerFactory emf(DataSource ds) {
		LocalContainerEntityManagerFactoryBean emf = new ...
		emf.setDataSource(ds);
		emf.setJpaVendor(new HibernateJpaVendor());
		...
		return emf;
	}
}

With Spring Boot --> Spring Data JPA

Spring Data JPA --> no need for any configuration and @Repository class

Just create interface

interface BookDao extends JpaRepository<Book, String> {}

BASIC CRUD operations are generated in implmentation class

=========
Hibernate to DDL operation ==> CREATE, ALTER, DROP

spring.jpa.hibernate.ddl-auto=update
--> create table if not exists, use if present, alter table if required 

spring.jpa.hibernate.ddl-auto=create-drop
--> drop table and create new tables for every run of application --> useful in test env

spring.jpa.hibernate.ddl-auto=verify
--> use existing tables as is, no changes allowed --> Bottom to Top approach


===

MySQL terminal:

$ docker exec -it local-mysql /bin/bash
# mysql -u root -p
Enter Password: Welcome123

mysql> use DB_SPRING;
mysql> show tables;
mysql> select * from products;
+----+------------+-------+------+
| id | name       | price | qty  |
+----+------------+-------+------+
|  1 | iPhone 14  | 89000 |  100 |
|  2 | OnePlus12T | 69000 |  100 |
+----+------------+-------+------+

Association Mapping 

=========================================

Day 2

Spring Boot -> Opiniated FW
@SpringBootApplication
-> @ComponentScan
--->@Component , @Repository, @Controller, @RestController, @Configuration, @Service
---> @Bean --> Factory method ==> Object returned from this method is managed by Spring Container
a) Instances of classes which are from 3rd party library
b) use Parameterized constructor instead of default constructor

-> @EnableAutoConfiguration --> built in config instances --> Connection pool , HibernateJPAVendor() ...

@Autowired, @Primary, @Qualifer, @Profile, ...

---

JPA
--> DataSource
--> EntityManagerFactory
--> JPAVendor
--> EntityManager manages PersistenceContext [ env where @Entity is managed]

create interface of type JpaRepository<Entity, PK>; Spring Data Jpa generets @Repository class for the interface


---------------

@Query("") --> SQL or JPQL

// scalar values
public interface ProductDao extends JpaRepository<Product, Integer> {
 @Query("select name, price from Product")
 List<Object[]> getNameAndPrice(); ==> Object[0] name and Object[1] --> price
}

for(List<Object[]> data : productDao.getNameAndPrice()) {
 	System.out.println(data[0] , data[1])
 }
OR

interface ProductView {
	String getName();
	double getPrice();
}

 @Query("select name, price from Product")
 List<ProductView> getNameAndPrice(); ==> Object[0] name and Object[1] --> price

 for(List<ProductView> data : productDao.getNameAndPrice()) {
 	System.out.println(data.getName(), data.getPrice())
 }
---

interface CustomerView {
	String getFirstName();
	String getLastName();
	@Value("#{firstName} , #{lastName}")
	String fullName();
}

# --> SpEL

public interface CustomerDao extends JpaRepository<Customer, String> {
	List<CustomerView> findByAll();
}

JDBC
executeQuery() ==> SELECT
executeUpdate() ==> INSERT, DELETE, UPDATE

====================

ORM association Mapping

Bi-directional

public class Customer {
	@OneToMany(mappedBy="customer")
	List<Order> orders = new ArrayList<>();
}

public class Order {
	@ManyToOne
	@JoinColumn(name="customer_fk")
	private Customer customer;
}

---------------


Order o = new Order();
o.add itmes (i1, i2, i3);

CRUD operations:
save(o);
save(i1);
save(i2);
save(i3);

--

delete(o);
delete(i1);
delete(i2);
delete(i3);

----------

CRUD with cascade:
@OneToMany(cascade = CascadeType.ALL)
@JoinColumn(name="order_fk")
private List<Item> items = new ArrayList<>();


Order o = new Order();
o.add itmes (i1, i2, i3);


CRUD operations:
save(o); --> because of cascade items also are peristed
delete(o); --> delete a order deletes its items also

no need for ItemDao

---
Default is Lazy fetching;

"from Order"
gets data from orders table

explicitly i need to make calls like
from Item where order_fk = 1
from Item where order_fk = 2
from Item where order_fk = 3
from Item where order_fk = 4

fetch = FetchType.EAGER
"from Order"
fetchs items also

OneToMany ==> by default is Lazy fetch
ManyToOne ==> by default is EAGER fetch

--------

@Transactional --> Declarative and Distributed Tx
--> Two Phase Commit Protocol

====================================

Optimistic Lock

 1 | iPhone 14      | 89000 |  90 |       1

 User 1:
 	buys 2
 	update Product set quantity = 88, version = version + 1 where id = 1 and version = 1
User 2:
 	buy 10
 		update Product set quantity = 90, version = version + 1 where id = 1 and version = 0

First Commit wins:

=============================

RESTful

<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
</dependency>

* Tomcat Embedded Web server ==> port 8080
* Spring MVC with servlet container
* HttpMessageHandler --> Jackson

Java <--> JSON
	* Jackson
	* Jettison
	* GSON
	* Moxy

Install POSTMAN

Browser:
http://localhost:8080/api/products
http://localhost:8080/api/products/3
http://localhost:8080/api/products?low=100&high=2000

POSTMAN
POST http://localhost:8080/api/products

HEADERS:
Accept: application/json
Content-type: application/json

Body Raw:
{
    "name": "Mac M1-Pro",
    "price": "159999.00",
    "quantity": 100
}

RESPONSE: 201 Created

{
    "id": 7,
    "name": "Mac M1-Pro",
    "price": 159999.0,
    "quantity": 100,
    "version": 0
}

==============

Order 
POST http://localhost:8080/api/orders

 {
	 	"customer": {"email": "a@adobe.com"},
	 	"items": [
	 		{"product": {"id": 1},"quantity": 2},
	 		{"product": {"id": 3},"quantity": 5}
	 	]
}

Spring MVC WebApplications uses OpenSessionInView Pattern

spring.jpa.open-in-view=false

=========================================================================

AOP --> Aspect Oriented Programming

AOP eliminates CrossCutting Concerns --> Concern is a bit code which can be used along with main logic
like Logging, Security, Profile, Transaction
--> leads to code scatterning and code tangling


public void transferFunds(Account from, Account to, double amt) {
	isUserValid(); // security CrossCutting Concerns
	log("tx starts"); // CrossCutting Concerns
	try {
		tx.begin(); // CrossCutting Concerns
		debit(from);
		log("amount debited"); // CrossCutting Concerns
		credit(to);
		log("amount debited"); // CrossCutting Concerns
		writeToTx();
		tx.commit(); // CrossCutting Concerns
    } catch(Exception ex) {
    	log(ex); // CrossCutting Concerns
    	tx.rollback(); // CrossCutting Concerns
    }
}

---

Aspect --> code which can be weaved to diffent JoinPoints [ Log , Security, Profile, Tx]
JoinPoint --> place where we can weave a Aspect 
PointCut --> selected JoinPoint
Advice --> Before, After, Around, AfterReturning, AfterThrowing

Transaction / Profile --> Around

Task:

CustomerController ==> GET, POST, PUT, DELETE

Day 3

spring-boot-starter-web
--> Spring MVC [DispatcherServlet, OpenSessionInViewFilter, HandlerMapping, Tomcat, Jackson]
@Controller --> return "view"
@RestController --> return representation of resource

@RequestMapping, @GetMapping, @PostMapping, @PutMapping, @DeleteMapping

@ResponseBody --> HttpMessageHandler Java--> JSON based on accept:application/json --> payload

@RequestBody ---> HttpMessageHandler JSON --> Java based on content-type:application/json

JPA --> @Entity, JpaRepository, @Query , Association Mapping [@OneToMany, @ManyToOne, @OneToOne, @ManyToMany]

@Transactional

--------------------------

AOP --> Aspect Oriented Programming

AspectJ --> PointCuts --> Dynamic pointcut --> Runtime

Spring --> Pointcuts --> Static pointcut --> Compiler / ClassLoading

https://docs.spring.io/spring-framework/docs/2.0.x/reference/aop.html

Global Exception Handling Using @ControllerAdvice Classes

Validation:
	<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
	</dependency>

@NotBlank(message="Name is required")
	private String name;
	
	@Min(value = 10, message="Price ${validatedValue} should be more than {value}")
	private double price;
	
	@Min(value = 0, message="Quantity ${validatedValue} should be more than {value}")
	@Column(name="qty")
	private int quantity;

@Validated

addProduct(@RequestBody @Valid Product p)


MethodArgumentNotValidException:  
[Field error in object 'product' on field 'price': rejected value [7.0]; codes [Min.product.price,Min.price,Min.double,Min]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [product.price,price]; arguments []; default message [price],10]; default message [Price 7.0 should be more than 10]] 

[Field error in object 'product' on field 'quantity': rejected value [-8]; codes [Min.product.quantity,Min.quantity,Min.int,Min]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [product.quantity,quantity]; arguments []; default message [quantity],0]; default message [Quantity -8 should be more than 0]] 

[Field error in object 'product' on field 'name': rejected value []; codes [NotBlank.product.name,NotBlank.name,NotBlank.java.lang.String,NotBlank]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [product.name,name]; arguments []; default message [name]]; default message [Name is required]] ]

errors, hasItem("Name Is required")
==============================================

spring-boot-starter-test
* Junit-Jupiter
* Hamcrest --> Matchers / Assertion Library
* json-path
* Mockito --> Mocking library --> testing in isolation

@WebMvcTest --> DispatcherTestServlet, @ControllerAdvice, MockMvc

MockMvc --> to perform GET, POST, PUT and DELETE requests actions on Controller

-------------------

RestTemplate
WebClient

Documentation:
RAML

/books:
  /{bookTitle}
    get:
      queryParameters:
        author:
          displayName: Author
          type: string
          description: An author's full name
          example: Mary Roach
          required: false
        publicationYear:
          displayName: Pub Year
          type: number
          description: The year released for the first time in the US
          example: 1984
          required: false
        rating:
          displayName: Rating
          type: number
          description: Average rating (1-5) submitted by users
          example: 3.14
          required: false
        isbn:
          displayName: ISBN
          type: string
          minLength: 10
          example: 0321736079
    put:
      queryParameters:
        access_token:
          displayName: Access Token
          type: string
          description: Token giving you permission to make call
          required: true
 
=================================

	<dependency>
			<groupId>org.springdoc</groupId>
			<artifactId>springdoc-openapi-ui</artifactId>
			<version>1.6.12</version>
		</dependency>

http://localhost:8080/v3/api-docs
http://localhost:8080/swagger-ui/index.html

=============================

Caching
Client-side Caching:
* Cache-control --> maxAge
* Etag

GET http://localhost:8080/api/products/cache/3
Headers:
Etag: "-1690458404"
SC: 200

body contains product
Next Request
If-None-Match: "-1690458404"

304 Not Modified
no payload

---------
Caching @ API level

	<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-cache</artifactId>
		</dependency>

By Default --> ConcurrentHashMap   is used by default for Cache

@EnableCaching
public class OrderappApplication {

@Cacheable(value="productCache", key ="#id")
@CachePut(value="productCache", key="#id")
@CacheEvict(value="productCache", key="#id")

@Cacheable(value="productCache", key="#p.id", condition = "#p.price > 5000")
public Product addProduct(@RequestBody @Valid Product p) {

@Cacheable(value="productCache", key ="#id", unless="#result != null")
public @ResponseBody Product getProduct(@PathVariable("id") int id) throws NotFoundException { 

==============

Day 4

AOP, @ControllerAdvice, @ExceptionHandler, @Transactional [ AroundAdvice]

javax.validation.constraints ==> @NotBlank, @Min, @Max, @Pattern, ...
@Validated, @Valid --> MethodArgumentNotValidException 

OpenAPI --> JavaDoc --> @Controller, @RestController

Caching --> ETag --> 304 ,If-None-Match / Cache-Control --> maxAge

@EnableCaching ---> ConcurrentHashMapCache implementation -->CacheManager

@Cacheable, @CachPut, @CacheEvit
condition and unless

@EnableScheduling, @Schedule --> fixedRate, cron --> CacheManager to evict 

Redis

docker run --name some-redis -p 6379:6379 -d redis

<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>

spring.redis.port=6379
spring.redis.host=127.0.0.1


JVM --> write --> Stream --> 

public class Product implements Serializable {

RedisCustomConfig.java

Redis Client:
npm i redis-commander -g
$ redis-commander

========================================

<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

Health Check 

Spring Boot Actuator:
* DataSourceHealthIndicator
* MongoHealthIndicator
* RedisHealthIndicator
...

<dependency>
	<groupId>io.micrometer</groupId>
	<artifactId>micrometer-registry-prometheus</artifactId>
</dependency>

Micrometer is a set of libraries for Java that allow you to capture metrics and expose them to several different tools – including Prometheus.

prometheus server --> scrape data from endpoints --> time series collection happens via a pull model over HTTP


Spring Boot Actuator <---- Prometheus [threads , cpu , http requests] --> Line Chart <-- Grafana {Dashboard}

docker-compose up

ab -n 3000 -c 200 http://localhost:8080/api/products/

http://localhost:8080/actuator/prometheus

http://localhost:9090/
http_server_requests_seconds_sum
http_server_requests_seconds_count
sum(jvm_memory_used_bytes{area="heap"})

---
Grafana:
localhost:3000

Add DataSource:
http://192.168.1.6:9090
Save & Test

+
import
--> add JSON
-->  Select DataSource [ Spring Boot Promethues]

==============================

HATEOAS (Hypertext As The Engine Of Application State)
https://martinfowler.com/articles/richardsonMaturityModel.html

GET /doctors/mjones/slots?date=20100104&status=open HTTP/1.1


<openSlotList>
  <slot id = "1234" doctor = "mjones" start = "1400" end = "1450">
     <link rel = "/linkrels/slot/book" 
           uri = "/slots/1234"/>
  </slot>
  <slot id = "5678" doctor = "mjones" start = "1600" end = "1650">
     <link rel = "/linkrels/slot/book" 
           uri = "/slots/5678"/>
  </slot>
</openSlotList>

POST /slots/1234 HTTP/1.1
[various other headers]

<appointmentRequest>
  <patient id = "jsmith"/>
</appointmentRequest>


<appointment>
  <slot id = "1234" doctor = "mjones" start = "1400" end = "1450"/>
  <patient id = "jsmith"/>
  <link rel = "/linkrels/appointment/cancel"
        uri = "/slots/1234/appointment"/>
  <link rel = "/linkrels/appointment/addTest"
        uri = "/slots/1234/appointment/tests"/>
  <link rel = "self"
        uri = "/slots/1234/appointment"/>
  <link rel = "/linkrels/appointment/changeTime"
        uri = "/doctors/mjones/slots?date=20100104&status=open"/>
  <link rel = "/linkrels/appointment/updateContactInfo"
        uri = "/patients/jsmith/contactInfo"/>
  <link rel = "/linkrels/help"
        uri = "/help/appointment"/>
</appointment>

--------

/orders

/orders/1/payment --> /orders/1/trackOrder
/orders/1/cancel

---------------------

<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-hateoas</artifactId>
</dependency>

org.springframework.hateoas.server.mvc.WebMvcLinkBuilder		

RepresentationModel

EntityModel  ==> Entity + Links
CollectionModel ==> List<Enity> + links

{"id":2,"name":"OnePlus12T","price":69000.0,"quantity":97,"version":0,
	"_links":
	{"self":{"href":"http://localhost:8080/api/products/hateoas/2"},
	"products":{"href":"http://localhost:8080/api/products?low=0.0&high=0.0"}
}
}


@EnableHypermediaSupport(type=HypermediaType.HAL_FORMS)

=======================================================

Spring Data Rest
Spring Data REST makes it easy to build hypermedia-driven REST web services on top of Spring Data repositories.
JpaRepository
MongoRepository

* endpoints are created based on Spring Data Repository
* No need for @RestController

------------------

Lombok, spring data jpa, mysql , rest repositories

http://localhost:8080/products/search/getByRange?l=100&h=8889


--> Never use @RestController
--> instead use @BasePathAwareController 
--> modify any existing rest paths
or @RepositoryRestController
--> to add new endpoints


@Component
public class RestConfig implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration restConfig,
      CorsRegistry cors) {
        ExposureConfiguration config = restConfig.getExposureConfiguration();
        config.basePath("api/")
        config.forDomainType(Product.class).withItemExposure((metadata, httpMethods) ->
          httpMethods.disable(HttpMethod.DELETE));
    }
}

---------------

Asynchronous operation 

@EnableAsync

Create Thread Pool:
@Bean(name = "asyncExecutor")
public Executor asyncExecutor() {


CompletableFuture<> services ==> wich uses REST clients like --> RestTemplate or WebClient


==============================

schema.sql
data.sql

companyWithDepartmentsGraph
	company + departments
 select
        company0_.id as id1_2_0_,
        company0_.name as name2_2_0_,
        department1_.company_id as company_3_3_1_,
        department1_.id as id1_3_1_,
        department1_.id as id1_3_2_,
        department1_.company_id as company_3_3_2_,
        department1_.name as name2_3_2_ 
    from
        company company0_ 
    left outer join
        department department1_ 
            on company0_.id=department1_.company_id 
    where
        company0_.id=?


companyWithDepartmentsAndEmployeesGraph
	company + departments 


When using fetchgraph all relationships are considered to be lazy regardless of annotation, 
and only the elements of the provided graph are loaded.

use loadgraph to add entities to the query results which are part of entity Graph and other EAGER fetched

-------------

* Actuator
* Prometheus --> time series database rules.yml --> Alert
* Grafana --> Dashboard

HATEOAS --> WebMvcLinkBuilder [ linkTo, afford, methodOn] --> EntityModel or CollectionModel
Spring Data REST --> based on Spring Repository [ JpaRepository<Product, Integer> , MongoRepository]
@BasePathAwareController

EntityGraph --> JPA
LAZY, EAGER fetching

Company --> Departments --> Employees & Offices 
Company --> Cars

Lazy: n + 1 problems

from Company --> 1 Request [ 5 companies]

from Departments where comany_id = 1;
from Departments where comany_id = 2;
from Departments where comany_id = 3;
from Departments where comany_id = 4;
from Departments where comany_id = 5;

from Cars where comany_id = 1;
from Cars where comany_id = 2;
from Cars where comany_id = 3;
from Cars where comany_id = 4;
from Cars where comany_id = 5;

from Employees where department_id = 1;
from Employees where department_id = 2;

EAGER: payload increase

Map hints = 

hints.put(...loadgraph or fetchgraph)
dao.find(Entity.class, PK , hints)

==========================================================


Day 5

Criteria queries are written using Java programming language APIs, are typesafe, and are portable.

JPA Specification

public interface Specification<T> {
  Predicate toPredicate(Root<T> root, CriteriaQuery query, CriteriaBuilder cb);
}


JpaSpecificationExecutor

---------------------

Reactive RESTful WebServices

* Publisher
* Subscriber

spring-boot-starter-webflux --> Netty Reactor as Web Server --> Event Based
instead of
spring-boot-starter-web --> Tomcat as HTTP Web Server --> Thread based


Reactor Netty is an asynchronous event-driven network application framework. 
It provides non-blocking and backpressure-ready TCP, HTTP, and UDP clients and servers. 

https://projectreactor.io/docs

Flux is a Publisher --> emits 0 to N elements
Mono is a Publisher ---> emit 0 or 1 element

Functional Web Framework

==========================

@Tailable

can be used capped collections [ limited size ]

docker run -it --name mongodb -d mongo --p 27017:27017

============================

OrderService:
 InventoryResponse[] inventoryResponsArray = webClientBuilder.build().get()
                    .uri("http://localhost:8082/api/inventory",
                            uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();


POST: http://localhost:8081/api/orders

{
    "items": [
        {"skuCode": "iphone_13", "price": 89000.99, "quantity": 25},
        {"skuCode": "OnePlus12T", "price": 63434.99, "quantity": 2}
    ]
}

Above fails:

Try:
{
    "items": [
        {"skuCode": "iphone_13", "price": 89000.99, "quantity": 25} 
    ]
}
Success

====

Spring Cloud

Service Discovery: Eureka instances can be registered and clients can discover the instances using Spring-managed beans

======================

Day 6

* reactive --> WebFlux --> Producer and Subscriber --> Flux and Mono --> event-source
* Microservices


Eureka Server:
<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
		</dependency>

server.port=8761
eureka.client.fetchRegistry=false
eureka.client.registerWithEureka=false

@EnableEurekaServer


Eureka Client
@EnableEurekaClient

spring.application.name=inventory-service
eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka

 <dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
 
OrderService module:

 @Bean
 @LoadBalanced
 public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
}
OrderService:
  InventoryResponse[] inventoryResponsArray = webClientBuilder.build().get()
    .uri("http://inventory-service/api/inventory"

--------------------------

Run:
1) Discovery Server --> Eureka Server
2) product-service
3) inventory-service
4) order-service

==============

http://localhost:8761/




