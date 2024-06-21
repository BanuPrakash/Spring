# Spring Boot

Banuprakash C

Full Stack Architect, Corporate Trainer

Co-founder & CTO: Lucida Technologies Pvt Ltd., 

Emails:

banuprakashc@yahoo.co.in;banuprakash.cr@gmail.com;banu@lucidatechnologies.com

https://www.linkedin.com/in/banu-prakash-50416019/

https://github.com/BanuPrakash/Spring

===================================

Softwares Required:
1)  openJDK 17
https://jdk.java.net/java-se-ri/17

2) IntelliJ Ultimate edition 
https://www.jetbrains.com/idea/download/?section=mac

OR

Eclipse for JEE  
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

$ docker exec -t -i local-mysql bash

d) Run MySQL client:

bash terminal> mysql -u "root" -p

mysql> exit

=====================

Spring Framework
Spring Boot
Spring and JPA integration
Spring Restful Web services
Secure
Introduction to Microservices
BeanPropertyWriter & BeanSerializerModifier
------------

Spring Framework
--> light weight container for building enterprise application
* life cycle management of beans and wiring dependecies

SOLID design principles

D => dependency Injection

Spring has modules to integrate with RDBMS / MONGODB / MVC ,...

```
public interface EmployeeDao {
    void addEmployee();
}

// Realization
public class EmployeeDaoDbImpl implements EmployeeDao {
    // state and behaviour
    public void addEmployee() {
        // logic
    }
}


// Realization
public class EmployeeDaoMongoImpl implements EmployeeDao {
    // state and behaviour
    public void addEmployee() {
        // logic
    }
}

public class AppService {
    private EmployeeDao employeeDao;

    public void setDao(EmployeeDao empDao) {
        this.employeeDao = empDao;
    }

    public void add() {
        this.employeeDao.addEmployee();
    }
}

beans.xml
<beans>
    <bean id="dbImpl" class="pkg.EmployeeDaoDbImpl" />
    <bean id="mongoImpl" class="pkg.EmployeeDaoMongoImpl" />
    <bean id="service" class="pkg.AppService">
        <property name="dao" ref="mongoImpl" /> 
    </bean>
</beans>

service.setDao(mongoImpl); // setter DI

ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
AppService service = ctx.getBean("service", AppService.class);
service.add();


```

Annotation for metadata:
a) class level annotation
1) @Component
2) @Repository
3) @Service
4) @Configuration
5) @Controller
6) @RestController
7) @ControllerAdvice

```
public interface EmployeeDao {
    void addEmployee();
}

@Repository
public class EmployeeDaoDbImpl implements EmployeeDao {
    // state and behaviour
    public void addEmployee() {
        // logic
    }
}

@Service
public class AppService {
    @Autowired //byType 
    private EmployeeDao employeeDao;
    public void add() {
        this.employeeDao.addEmployee();
    }
}

OR
@Service
public class AppService {
    private EmployeeDao employeeDao;

    // ConstructorDI
    public AppService(EmployeeDao empDao) {
        this.employeeDao = empDao;
    }
    public void add() {
        this.employeeDao.addEmployee();
    }
}

ApplicationContext ctx = AnnotationConfigApplicationContext();
ctx.scan("pkg");
ctx.ready();

```

ByteCode Instrumentation libraries : Byte Buddy / JavaAssist / CGLib [proxy]
https://github.com/spring-projects/spring-framework/blob/main/spring-jdbc/src/main/resources/org/springframework/jdbc/support/sql-error-codes.xml

why Spring Boot?
* Spring boot framework is on top of Spring  Framework
Spring boot 3.x is on top of Spring Framework 6.x
* simplify development
* highly opiniated framework
Example:
1) if we are building web applicaiton: Tomcat embedded web container is provided
2) if we are using database: Connection Pool is provided out of box
...
* Docker ready


@SpringBootApplication is 3 in 1:
1) @Configuration
2) @EnableAutoConfiguration [ built-in configuration beans]
3) @ComponentScan

@ComponentScan(basePackage="com.myorg.project")
```

Issue:
```
Could not autowire. There is more than one bean of 'EmployeeDao' type.
Beans:
employeeDaoJdbcImpl   (EmployeeDaoJdbcImpl.java) 
employeeDaoMongoImpl   (EmployeeDaoMongoImpl.java)
```

Solution 1:
Mark one of the type as @Primary
```
@Repository
@Primary
public class EmployeeDaoJdbcImpl implements  EmployeeDao{
 
```

Solution 2:
use @Qualifier 

```

@Service
//@RequiredArgsConstructor
public class AppService {
    @Autowired
    @Qualifier("employeeDaoJdbcImpl")
    private  EmployeeDao employeeDao;


```

Solution 3: using @Profile

```
@Repository
@Profile("prod")
public class EmployeeDaoJdbcImpl implements  EmployeeDao{

@Repository
@Profile("dev")
public class EmployeeDaoMongoImpl implements  EmployeeDao{
 
Program arguments:
--spring.profiles.active=prod=dev

OR
https://docs.spring.io/spring-boot/appendix/application-properties/index.html

application.properties
spring.profiles.active=prod

OR 
Environment variable
```

Solution 4:
@ConditionalOnProperty


@Repository
@ConditionalOnProperty(name="dao", havingValue = "jdbc")
public class EmployeeDaoJdbcImpl implements  EmployeeDao{

@Repository
@ConditionalOnProperty(name = "dao", havingValue = "mongo")
public class EmployeeDaoMongoImpl implements  EmployeeDao{
 
application.properties
dao=mongo

Solution 5:
@ConditionalOnMissingBean

@ConditionalOnMissingBean(EmployeeDaoJdbcImpl.class)
public class EmployeeDaoMongoImpl implements  EmployeeDao{

Java <--> JSON ===> GSON 
default is Jackson Objectmapper [@ConditionalOnMissingBean ]

============

Factory method: @Bean
* we need to use 3rd party libraries in Spring boot
* Custom way of creating bean and initializing.

Adding dependency com.mchange:c3p0:0.10.1

Example:
```

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

@Repository
public class EmployeeDaoJdbcImpl implements  EmployeeDao{
    @Autowired
    DataSource ds;

    method() {
        Connection con = ds.getConnection();
        ...
    }
}
```

Scope of the bean:
1) Singleton: default scope

@Repository
@Singleton
or
@Scope("singleton")
public class EmployeeDaoJdbcImpl implements  EmployeeDao{

2) Prototype
```
@Repository
@Scope("prototype")
public class EmployeeDaoMongoImpl implements  EmployeeDao{
    List<MyData> data = ...
}
  
@Service
public class OrderService {
    @Autowired
    EmployeeDao empDao;
}

@Service
public class DashboardService {
    @Autowired
    EmployeeDao empDao;
}
```
Applicapble only for web based application:
3) request
@Scope("request")
@RequestScope
4) session [conversational state of client] ==> Cart Handling
@SessionScope
@Scope("session")
5) application [one per web container]

========================================

ORM ==> Object Relational Mapping

Java Object <----> Relational database table
fields <---> columns of table

```
@Entity
@Table(name="products")
public class Product {
    @Id
    private int id;
    private String name;
    @Column(name="amount")
    private double price;
    @Column(name="qty")
    private int quantity;
}

```
ORM frameworks are going to generate DDL and DML based on mapping
--> simplifiy development for CRUD operations using RDBMS

with JDBC: lots of plumbing code
```
  @Override
    public void addProduct(Product product) throws PersistenceException {
        String SQL = "INSERT INTO products(id, name, price, quantity) VALUES(0, ? ,? ,?)";
        Connection con = null;
        try {
            con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setInt(3, product.getQuantity());
            ps.executeUpdate();
        } catch (SQLException e) {
           // log
            throw  new PersistenceException("unable to add product", e);
        } finally {
            DBUtil.closeConnection(con);
        }
    }
```
with ORM framework
``
EntityManager em; 
 @Override
    public void addProduct(Product product) {
        em.persist(product);
    }
```
 ORM Frameworks: Hibernate, TopLink, KODO, JDO, OpenJPA, EcpliseLink,,
 JPA: Java Persistence API is a specification for ORM

Data Mapping library:jooQ, iBatis

Configuration:
```

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
    public EntityManagerFactory getEmf(DataSource ds) {
        LocalContainerEntityManagerFactory emf = new LocalContainerEntityManagerFactory();
        emf.setDataSource(ds);
        emf.setJpaVendor(new HibernateJpaVendor());
        ...
        emf.setPackagestoScan("com.synchronoss.prj.entity"); // where are my entity classes
        return emf;
    }
}

@Repository
public class ProductDao {
    @PersistenceContext
    EntityManager em; 
    
    @Override
    public void addProduct(Product product) {
        em.persist(product);
    }
}

```

Spring Data JPA:
by default configures HikariCP as datasoure: pool of database connections
configures Hibernate as ORM provider

Application "shopapp" with :lombok, mysql, jpa dependency

Spring Data JPA simplies CRUD operations
```
No need for Repository class, implementation class is gnerated by Spring Data JPA
public interface ProductDao extends JpaRepository<Product, Integer> {
}
``



Recap:
```
Spring Framework : ApplicationContext --> Spring Container

ServletContainer : reated only to web components like HttpServlet, HttpServletRequest, HttpServletResponse

doGet(HttpServletRequest req, HttpServletResponse res) {
}

EJBContainer: related only to EJB like SessionBean, MessageDriveBean, EntityBean, ...

Spring Boot vs Spring Framework

JPA / ORM using Hibernate as ORM Framework
PersitenceContext where entities are managed [ sync with RDBMS ]
EntityManager --> EntityManagerFactory [ DataSource and JPAVendor]

Spring Data JPA: simplifies JPA usage
```

Day 2

Spring Boot 2.x
import javax.*
changes to:
Spring Boot 3.x
import jakarta.*;

Methods:
built-in
```
1) Optional<T> findById(ID id); //EAGER loading, select statement ...
2) T getById(ID id); --> Proxy Object -> Lazy Loading

Product p = productDao.getById(2); // No HIT to database
// p is a proxy object
// p.getName(); // hits the DB and fetches the data [ Make sure Connection to DB is alive, else you get
LazyInitializationException]

for(i = 1; i <= 100; i++) {
    productDao.getById(i); // 100 proxy objects
}

3) T getReferenceById(ID id); --> Proxy Object populated only with ID / Primary Key
Useful only for association Mapping

Post and Comment
// Association
public class Comment {
    id, review
    Post post; // belongs to a Post
}

Scenario 1:
void addNewComment(String body, int postId) {
    Comment comment = new Comment()
        .setReview(body)
        .setPost(postRepo.findById(postId)) // select * from posts where postId = ?
        .orElseThrow( ...)
    
    commentRepo.save(comment);
}

Scenario 2:
void addNewComment(String body, int postId) {
    Comment comment = new Comment()
        .setReview(body)
        .setPost(postRepo.getReferenceById(postId)) // Post Proxy with only ID populated
        .orElseThrow( ...)
    commentRepo.save(comment);
}
```

Spring Data JPA Projections:
1) Entity Projections:
* selects all mapped database columns
* returns as managed object
* can perform write operations on managed object

2) Scalar Projections:
* one or more database columns
* returns unmanaged objects
* read-only operation

3) DTO projections:
* one or more database columns
* returns unmanaged objects
* read-only operation

JP-QL 
1) uses classname and field name in query
2) case-sensitive
3) polymorphic

Examples:
1) from Product
// select * from products
2) from Product where price = 100
// select * from products where amount = 100

start with "select" for scalar values
3) select name, price from Product
// select name, amount from products

4) from Object
    // get all rows of all tables in database

   class Product {} // maps to products
   class Tv extends Product{} // maps to tvs
   class Mobile extends Product {}  // maps to mobiles table

   from Product;
   // get data from products, tvs and mobiles

SQL
1) uses table and column names

record vs class

```
Class Option 1:
  @Query("select name, price from Product where price >= :l and price <= :h")
    List<ProductRecord> getByRange(@Param("l") double low, @Param("h") double high);

public class ProductRecord {
    name, price
    public ProductRecord(name, price) {
    }
    // getters and setters
}

Class Option 2:
  @Query("select new pkg.ProductRecord(name, price) from Product where price >= :l and price <= :h")
    List<ProductRecord> getByRange(@Param("l") double low, @Param("h") double high);

public class ProductRecord {
    name, price
    public ProductRecord() {}
    public ProductRecord(String name) {}
    public ProductRecord(name, price) {
    }
    // getters and setters
}
```

@Query ==> Select statement ==> ResultSet executeQuery("sql");
@Modifying ==> int executeUpdate(SQL); // for INSERT , DELETE , UPDATE 

A) create tables if not exist, if exists use them, alter if required
spring.jpa.hibernate.ddl-auto=update

B) no changes to existing schema, map entities to existing tables
spring.jpa.hibernate.ddl-auto=verify

C) create tables and drop them for every run of application [ good for testing only]
spring.jpa.hibernate.ddl-auto=create


H2 database: In-memory database

runtimeOnly 'com.h2database:h2'

application.properties
```
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```
===========

Mapping association:
1) onetomany
2) manytoone
3) onetoone
4) manytomany

for ManyToOne: @JoinColumn introduces FK in owning table
for onetoMany: @JoinColumn introduces FK in child table

Without Cascade:
order has 4 items;
```
Save:
orderDao.save(order);
itemDao.save(item1);
itemDao.save(item2);
itemDao.save(item3);
itemDao.save(item4);
```

Delete:
```
orderDao.delete(order);
itemDao.delete(item1);
itemDao.delete(item2);
itemDao.delete(item3);
itemDao.delete(item4);
``
WithCascade:
```
orderDao.save(order);
orderDao.delete(order);
```

EAGER vs LAZY loading: 
default: one to many associations are lazy loading, many to one are EAGER fetched

Lazy:
Order order = orderDao.findById(11);
select * from orders where id = 11;

later: [make sure db connection is not lost, else we get LazyInitializationException]
order.getLineItems();  //select * from line_items where order_fk = 11;

Eager:
Order order = orderDao.findById(11);
select * from line_items where order_fk = 11; 


Recap:
JPA
Enity mapping
Association mapping
* OneToMany
* ManyToOne
* Cascade
* FetchType.EAGER vs FetchType.LAZY

Scalar Projection
DTO Projection
Entity Projection

CommandLineRunner --> run() @Order

@Transactional --> Atomic code

Day 3:

States of Object
1) Transient state
2) Managed state [ attached to PersistenceContext]
Any changes --> Dirty Check --> sync with database
3) Detached State [ once it was a part of managed state, now it is no longer]
Any changed done to detached will have to be re-attached to sync with database
4) Removed state
    entity is no longer has a mapping in persistent store [ like database]

@Transactional --> Aspect
* Declartive Transaction support as well as distributed Tx.

Programatic Transaction for JDBC:
```
public void doTask(...) {
    Connection con = ...
    try {
        con.setAutoCommit(false);
        // insert
        // update
        con.commit();
    } catch(SQLException ex) {
        con.rollback();
    }
}

Programatic Transaction for Hibernate:

public void doTask(...) {
    Session session = sessionFactory.getSession();
    Transaction tx = null;
    try {
         tx = session.beginTransaction();
        // insert
        // update
        tx.commit();
    } catch(SQLException ex) {
        tx.rollback();
    }
}
```

Spring MVC module and building RESTful WS
implementation 'org.springframework.boot:spring-boot-starter-web'
https://start.spring.io/

Build web, including RESTful, applications using Spring MVC. Uses Apache Tomcat as the default embedded container.

spring.jpa.open-in-view is enabled by default. 
Tomcat started on port 8080 (http) with context path '/'
application.properties
server.port=9999

spring.jpa.open-in-view --> OpenSessionInView pattern
OpenSessionInViewFilter

Building RESTful WS:
REST --> REpresetnational State Transfer --> Architectural style for communiting between distributed systems
Roy Fielding --> 2000

Resource?
Any information on Server that we can name.

Representation of the resource: [state at a given point of time]
* data
* metadata describing the data
* hypermedia links

Content Negotiation:
asking for suitable presentation by the client of the represention
JSON / XML / CSV / RSS ....

Guiding Principles:
* Uniform Interface
* Client-server: seperation of concerns
* Stateless: No conversation state of client is stored on server
* Cacheable

Best Practices:
* use nouns to represent resources
* Collection: server managed directory of resources, clients may propose a new resource to be added to a collection
* Store: is client managed resource repository
http://spotify.com/song-managment/users/banu@gmail.com/playlists
* Controller: procedural concept like executable functions
    http://spotify.com/song-managment/users/banu@gmail.com/playlists/{id}/play
    https://amazon.com/cart-managment/.../checkout
* use hypen to imporove readability
* lower case URIs
* NEVER use CRUD functions in URIs
* use pathparameter for fetching by Primary Key
* use Query Parameter to get subset / filter

Including: implementation 'org.springframework.boot:spring-boot-starter-web'
* DispatcherServlet --> FrontController
* OpenSessionInViewFilter --> to handle JPA Session [ not HttpSession]
* HandlerMapping
* ViewResolver
* HttpMessageConverter
a) for java primitive types [ String to <==> Double , Integer, Long, Byte,..]
b) Jackson library for Java <---> JSON


for web application we get : WebApplicationContext instead of ApplicationContext
WebApplicationContext are of 2 types:
1) Servlet WebApplicationContext
2) Root WebApplicationContext

URI identifies the resource
CRUD operations:
GET --> SELECT
POST --> INSERT
PUT / PATCH --> UPDATE
DELETE --> DELETE
===========

```
@Controller
public class ProductController {
@RequestMapping(value="/getProducts", method=RequestMethod.GET)
public String doTask() {
    return "list" ; // Controller assumes it's the name of view
} 

Based on ViewResolver 
list ==> list.jsp
list ==> list.pdf
```

PATCH operations:
Prefer this for partial updates if the entity is complex
https://jsonpatch.com/

```
package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;

public class Main {
    public static void main(String[] args) throws Exception {
        var employee = """
                {
                    "title" : "Sr. Software Eng",
          
                     "skills" : [
                            "Spring Boot",
                            "ReactJS"
                     ],
                     "communication": 
                         {
                         "email": "abc@company.com"
                         }
                      
                }
                """;

        var patch = """
                    [
                        {"op":"replace", "path": "/title" , "value" : "Team Lead"},
                        {"op" : "add" , "path": "/communication/phone", "value": "1234567890"},
                        {"op": "remove", "path": "/communication/email"},
                        {"op" : "add" , "path": "/skills/1", "value": "AWS"}
                    ]
                """;

        ObjectMapper mapper = new ObjectMapper();
        JsonPatch jsonPatch = JsonPatch.fromJson(mapper.readTree(patch));

        var target = jsonPatch.apply(mapper.readTree(employee));
        System.out.println(target);
    }
}

curl --location --request PATCH 'http://localhost:8080/api/employees/1' \
--header 'Accept: application/json' \
--header 'Content-Type:  application/json-patch+json' \
--data '[
                        {"op":"replace", "path": "/title" , "value" : "Team Lead"},
                        {"op" : "add" , "path": "/communication/phone", "value": "1234567890"},
                        {"op": "remove", "path": "/communication/email"},
                        {"op" : "add" , "path": "/skills/1", "value": "AWS"}
                    ]'

```

Recap:

```
Building RESTful WS:
spring-boot-starter-web including this dependency we get:
1) Tomcat as embedded Servlet Container / Web Container / Servlet engine
2) Spring MVC module is include
* DispatcherServlet --> FrontController --> intercept all requests [url-pattern as "*"]
* HandlerMapping [key/value] --> For which URL which method of a class should be invoked
returned reference method is invoked by DispatcherServlet
    method.invoke(parameters); // Reflection API
* ViewResolver [ not required for @RestController, only used for @Controller {for traditional web application development -> SSR }]
* Instead of ApplicationContext we get WebApplicationContext [ Servlet WebApplicationContext {HandlerMapping and ViewResolver} and Root WebApplicationContext {all spring beans}]
* OpenSessionInViewFilter


Day 4:

Documentation of RESTful WS
1) RAML
2) OPENAPI - Swagger
 implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0'
 https://springdoc.org/

=======

AOP: Aspect Oriented Programming
helps eliminate cross cutting concerns which lead to code tangling and code scattering.

General concerns which lead to code tangling and code scattering : Logging, Security, Profile, Transaction, ...

```
Logback logger, Transaction from JPA to JTA
public void transferFunds(Account fromAcc, Account toAcc, double amount) {
    log.info("Started to transfer funds");
        if(securityContext.getRole() === 'CUSTOMER, ADMIN') {
            Transaction tx = ...
                // get balance from from Account
                log.info("Balance : " ...);
                updateFromAcc(..);
                 log.info("Balance : " ...);
            tx.commit();
        }
    log.info("Fund transfer successfull!!!");
}
```

AOP terminology:
1) Aspect: concerns like loggers, transaction, 
2) JoinPoint: place in your code where aspect can be weaved [ with spring we can have method and exception only as Joinpoint]
3) Advice: how aspect is weaved to JoinPoint
* Before
* After
* AfterReturning
* Around
* AfterThrowing
4) PointCut: selected JoinPoint

* Spring depends on AspectJ for AOP
* Spring by defaults uses RuntimeWeaving of aspects
* Can be configured as LoadtimeWeaving --> bytecode is modified and loaded to JVM
@EnableLoadtimeWeaving
and pass spring-instrumetation library with command line
-javaagent:/path/path2/spring-instrument-3.2.9.RELEASE.jar
https://docs.spring.io/spring-framework/docs/2.0.x/reference/aop.html


By enabling validation we get javax.validation.constraints implemented by Hibernate

```
public Product addProduct(@Valid @RequestBody Product p) 
  @NotBlank(message = "Name is required")
    private String name;

    @Min(value = 10, message = "Price ${validatedValue} should be more than {value}")
    @Column(name="amount")
    private double price;

    @Min(value = 1, message = "Quantity ${validatedValue} should be more than {value}")
    @Column(name="qty")
    private int quantity; // inventory
```

 Unit Testing:
 testImplementation 'org.springframework.boot:spring-boot-starter-test'
 provides:
 * JUnit-Jupiter as unit testing framework
 * Hamcrest --> matchers for assertion --> useful for collections
 * Jsonpath https://jsonpath.com/
 * Mockito --> mocking library to test in isolation

 RestController --> Service --> DAO ---> Database

 @WebMvcTest --> Creates a minimal WebApplicationContext with DispatcherTestServlet

 ======

 Rest Clients --> Consume REST Api: Application invoking RESTful endpoints
 * RestTemplate
 * RestClient [ Spring Boot 3.2]
 * WebClient --> need to add webflux dependency


 =====

Day 5
To Consume RESTendpoints in Java application
* RestTemplate
* WebClient [ webflux and not with web module]
* RestClient [ Spring Boot 3.3 version onwards] similar to WebClient

Async operations in Spring WebApplicationContext

```
@SpringBootApplication
@EnableAsync
public class ShopappApplication {

@EnableAsync --> to use application Threadpool for execution instead of Tomcat thread pool

@EnableAsync by default is going to provide a Thread pool [ ForkJoinPool Algorithm]

To make sure that our code uses specified thread pool we need:

@Async("flights-pool")
getFlights() {

}
@Async("hotels-pool")
getHotels() {

}
```

Spring Declarative HTTP Client using @HttpExchange

@HttpExchange: is the generic annotation to specify an HTTP endpoint. When used at the interface level, it applies to all methods.
@GetExchange: specifies @HttpExchange for HTTP GET requests.
@PostExchange: specifies @HttpExchange for HTTP POST requests.
@PutExchange: specifies @HttpExchange for HTTP PUT requests.
@DeleteExchange: specifies @HttpExchange for HTTP DELETE requests.
@PatchExchange: specifies @HttpExchange for HTTP PATCH requests.

dto: User, Post
service: UserService , PostService
cfg: AppConfig: Proxy for UserService , PostService and Threadpool

Callable interface we get Future [ a place]











