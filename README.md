
# Spring Training
Banuprakash C

Full Stack Architect, 

Co-founder Lucida Technologies Pvt Ltd., 

Corporate Trainer,

Email: banuprakashc@yahoo.co.in

banu@lucidatechnologies.com



https://www.linkedin.com/in/banu-prakash-50416019/

https://github.com/BanuPrakash/Spring

===================================

Softwares Required:
1)  openJDK 17
https://jdk.java.net/java-se-ri/17

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

$ docker exec -t -i local-mysql bash

d) Run MySQL client:

bash terminal> mysql -u "root" -p

mysql> exit

```

SOLID Design Principles

S ==> Single Responsibility
O ==> Open Close Priniciple
L ==> Liskov Substitution Principle
I ==> Interface Seggregation
D ==> Dependency Injection

Spring Framework:
Provides light weight Container with Life cycle management of beans and wiring dependency

JVM 
Container are applications running on JVM
Servlet Container; EJB Container; OSGi, Spring Container

Java definition of bean: Any resuable software component
Spring definition of a Bean --> Any object managed by a Spring container


interface EmployeeDao {
    void addEmployee(Employee e);
}

public class EmployeeDaoJdbcImpl implements EmployeeDao {
    //
    public void addEmployee(Employee e) {
        SQL --> INSERT INTO employees ....
    }
}

public class EmployeeDaoMongoImpl implements EmployeeDao {
    //
    public void addEmployee(Employee e) {
        db.employees.insertOne(e);
    }
}

public class AppService {
    private EmployeeDao empDao; // program to interface
    public void setEmployeeDao(EmployeeDao dao) {
        this.empDao = dao;
    }

    public void insert(Employee e) {
        this.empDao.addEmployee(e);
    }
}

Strategy Pattern

XML as metadata:

beans.xml

<bean id="jdbc" class="pkg.EmployeeDaoJdbcImpl" />
<bean id="mongo" class="pkg.EmployeeDaoMongoImpl" />

<bean id="service" class="pkg.AppService">
    <property name="employeeDao" ref="jdbc">
</bean>


ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");

ApplicationContext ctx = new FilePathXmlApplicationContext("/users/banu/beans.xml");

ApplicationContext / BeanFactory are Container interface

AppService service = ctx.getBean("service", AppService.class);
Employee e = ..
service.insert(e);

==========================

BeanFactory ==> Can be used in only one context; Advance features like web support/ AOP support, etc are not available

<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter</artifactId>
</dependency>


Spring Boot ==> Framework to simplify using Spring Framework, highly opiniated framework; most of the configurations comes out of the box.

If we are developing Web applications ==> Embedded Tomcat web server is configured by default
If we are developing code to use JDBC ==> configures Database Connection Pool using Hikari CP by default
If we are developing using JPA ==> Hibernate is configured by default
If we need Caching ==> enables default CacheManager by default
....

```
@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
```
SpringApplication.run(DemoApplication.class, args); is similar to 

ApplicationContext ctx = new AnnotationConfigApplicationContext();
ctx.scan("com.xiaomi.prj");
ctx.reset();

@SpringBootApplication is 3 in one:
1) @ComponentScan("com.xiaomi.prj");
2) @EnableAutoConfiguration ==> based on environment it creates the default objects like :Tomcat, HikariCP, ...
3) @Configuration

Spring creates instances of class which has any of the below annotations:
1) @Component ==> Utility / Helper class 
2) @Repository ==> Persistence Code
3) @Service ==> Decorator to say that its a facade and transactional code resides here
4) @Configuration 
5) @Controller
6) @RestController


interface EmployeeDao {
    void addEmployee(Employee e);
}

@Repository
public class EmployeeDaoJdbcImpl implements EmployeeDao {
    public void addEmployee(Employee e) {
        SQL --> INSERT INTO employees ....
    }
}

@Service
public class AppService {
    @Autowired
    private EmployeeDao empDao; 
    public void insert(Employee e) {
        this.empDao.addEmployee(e);
    }
}

ByteCode Instrumentation ==> modifying the bytecode
1) ByteBuddy
2) JavaAssist
3) CGLIB

As of Now CGLib is used only for Proxy

====
MySQL
try {

} catch(SQLException ex) {
    if(ex.getErrorCode() == 1062) {
        throw new DuplicateKeyException(ex);
    } else if( ex.getErrorCode() == 1054) {
        ...
    }
}
--

Oracle
try {

} catch(SQLException ex) {
    if(ex.getErrorCode() == 1) {
        throw new DuplicateKeyException(ex);
    } else if( ex.getErrorCode() == 900) {
        ...
    }
}

====


Field employeeDao in com.xiaomi.prj.service.AppService required a single bean, but 2 were found:
	- employeeDaoJdbcImpl:
	- employeeDaoMongoImpl:


Solution 1: @Primary on one of the implementation

@Primary
@Repository
public class EmployeeDaoJdbcImpl implements EmployeeDao {

Solution 2: @Qualifier
```
@Service
public class AppService {
	@Autowired
	@Qualifier("employeeDaoJdbcImpl")
	private EmployeeDao employeeDao; // program to interface ==> Loose Coupling
```
-------
or
```
@Repository("jdbc")
public class EmployeeDaoJdbcImpl implements EmployeeDao {

@Repository("mongo")
public class EmployeeDaoMongoImpl implements EmployeeDao {

@Service
public class AppService {
	@Autowired
	@Qualifier("jdbc")
	private EmployeeDao employeeDao; // program to interface ==> Loose Coupling
```
----

Solution 3: @Profile
```
@Profile("dev")
@Repository("mongo")
public class EmployeeDaoMongoImpl implements EmployeeDao {


@Profile("prod")
@Repository("jdbc")
public class EmployeeDaoJdbcImpl implements EmployeeDao {
```
Program Arguments:

Run As -> Run Configurations
--spring.profiles.active=dev

OR
application.properties
spring.profiles.active=dev

Solution 4: 
```
@Repository("jdbc")
public class EmployeeDaoJdbcImpl implements EmployeeDao {


@ConditionalOnMissingBean(name = "jdbc")
@Repository("mongo")
public class EmployeeDaoMongoImpl implements EmployeeDao {
```
---
DataSource ==> Pool of database connection
```
@ConditionalOnMissingBean(name = "dataSource")
@Repository("mongo")
public class EmployeeDaoMongoImpl implements EmployeeDao {
```
=====================

Solution 5:
application.properties
DAO=jdbc

```
@ConditionalOnProperty(name="DAO", havingValue = "jdbc")
@Repository("jdbc")
public class EmployeeDaoJdbcImpl implements EmployeeDao {

@ConditionalOnProperty(name="DAO", havingValue = "mongo")
@Repository("mongo")
public class EmployeeDaoMongoImpl implements EmployeeDao {
```
============================================

Factory Method: We need to create instances of class and hand over to Spring container for management
* instances of classes from 3rd party library which doesn't have any of 6 annotations mentioned above
* Our own classes which can be used in different framework; we need to instantiate by our own init data

```
@Configuration
public class AppConfig {

	// Factory methods
	@Bean
	public DataSource getDataSource() throws Exception{
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass("org.postgresql.Driver"); //loads the jdbc driver            
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


@Service
public class AppService {
	@Autowired
	private EmployeeDao employeeDao; // program to interface ==> Loose Coupling
	
	@Autowired
	DataSource ds;
	
	public void insert() {
		System.out.println(ds);
		this.employeeDao.addEmployee();
	}
}
```

ORM --> Object Relational Mapping
* simplifies interaction with RDBMS
* layer on top of JDBC
* OO approach 

class Employee {
    empId,
    name,
    email
}

class Laptop {
    serial,
    desc,
    ram
    manufDate
}

Database approach 1: Foreign Key

Employee 
emp_id name email

Laptop
SERIAL  DESC  RAM MAN_DATE EMP_FK

JDBC code to CRUD operations

Database approach 2:  Single Table
Employee 
emp_id name email SERIAL  DESC  RAM MAN_DATE

 Whole of JDBC code is scrapped and re-write

class <---> table
fields <---> columns of table

```
@Table(name="emp")
public class Employee {
    @Column(name="emp_id")
    int employeeId;

    @Column(name="first_name")
    String firstName;
}
```
ORMs are going to generate DDL and DML based on mapping

JDBC:

public void addEmployee(Employee e) {
    Connection con = null;
    try {
        con = DriverManager.getConnection(...);
        PreparedStatement ps = con.prepareStatement("insert into emp values (?,?,?));
        ps.setInt(1, e.getEmployeeId());
        ps.setString(2, e.getFirstName());
        //
        ps.executeUpdate();
    } catch(SQLException ex) {
        ...
    } finally {
        con.close();
    }
}

With ORM:
public void addEmployee(Employee e) {
    em.save(e);
}

--> Establish connection , Statement , exception handling, release connection


ORM frameworks --> Hibernate, TopLink, KODO, OpenJPA, Eclipse Link, JDO,...

JPA --> Java Persistence API --> Specification

* DataSource
* PersistenceContext
* EntityManager
* EntityManagerFactory

```
@Configuration
public class AppConfig {
	// Factory methods
	@Bean
	public DataSource getDataSource() throws Exception{
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass("org.postgresql.Driver"); //loads the jdbc driver            
		cpds.setJdbcUrl( "jdbc:postgresql://localhost/testdb" );
		cpds.setUser("swaldman");                                  
		cpds.setPassword("test-password");                                  
		return cpds;
	}

    @Bean
    public EntityManagerFactory emf(DataSource ds) {
        LocalContainerEntityManagerFactory emf = new ...();
        emf.setDataSource(ds);
        emf.setJpaVendor(new HibernateJpaVendor()); // which ORM to use
        emf.setPackagesToScan("com.xiaomi.prj.entity");
        ....
        return emf;
    }
}

@Respository
public class EmployeeDaoJpaImpl implements EmployeeDao {
    @PersistenceContext
    EntityManager em;

    public void addEmployee(Employee e) {
        em.save(e);
    }

    public Employee getEmployeeById(int id) {
        em.find(Employee.class, id);
    }
}

Entities are classes with @Entity annotation
@Entity
public class Customer {}
```

Spring Data Jpa --> Simplifies using ORM; no need to configure DataSource, EntityManagerFactory, PersistenceContext ==> All comes out of the box
Spring Boot is highly opinated and configures Hibernate out of the box;
```
public interface EmployeeDaoJpaImpl implements JpaRepository<Employee, Integer> {
}
```
https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html

provides all CRUD operations.

New Spring boot starter project with : Lombok, Spring Data Jpa and MySQL dependency

1) spring.jpa.hibernate.ddl-auto=update

create table if not exists; if already exists map to class

2) spring.jpa.hibernate.ddl-auto=create-drop
create table on application startup; and destroy tables when application shuts down

3) spring.jpa.hibernate.ddl-auto=none
DBA creates tables for me; I just do mapping 

---
Spring Data JPA is creating instance of @Repository class for the below interface:

public interface ProductDao extends JpaRepository<Product, Integer> {
}


==========================
Spring Container: LifeCycle Management and Dependency Injection ==> Spring Core, Context
ApplicationContext: where beans are managed 
@Component, @Repository, @Configuration, @Controller, @RestController, @Service

ORM Module: Spring Data Jpa
HikariCP for database connection pool ==> DataSource
Hibernate as JPA Vendor

ORM --> 
PersistenceContext: An environment where entities are managed [ @Entity ]
EntityManager: class to manage PersistenceContext
EntityManagerFactory: to create EntityManager { specify which database to use and ORM provider to use}

Spring Data JPA --> Simplifies using ORM
we provide url, driver, username, pwd in application.properties
Spring Data JPA creates DataSource {pool of db connection}, EntityManage, PersistenceContext

interface JpaRepository<T,ID> {
    methods for CRUD operations
}

@Repository class is created by ByteCode Instrumentation libraries.

Day 2

$ docker exec -it local-mysql bash
bash-4.4# mysql -u root -p 
Enter password: Welcome123

mysql> use Xi_SPRING;

mysql> desc products;
mysql> select * from products;

@Embeddable
class Name implements Serializable {
    String firstName;
    String lastName;
}

@Enitity
class User {

    @EmbeddedId
    Name name;

    String email;

    String password;
}

=========

JP-QL ==> Java Persistence Query Language
JP-QL is polymorhic, SQL is not

class Product {  ==> products table

}

class Tv extends Product { ==> tv table
}

class Mobile extends Product { ==> mobile table
}

SQL:
select * from products; // gets data only from "products" table

JP-QL:
from Product; // get rows from products, tv and mobile table

from Object; // get rows from all the tables in the database

================

JDBC
executeQuery() ==> SELECT SQL ==> ResultSet
executeUpdate() ==> INSERT, DELETE, UPDATE SQL ==> int

===
By default methods in JpaRepository for save() and  delete() Transaction is enabled
Custom methods we need to enable @Transactional

@Transactional is called as Declarative and distributed transaction management

JDBC --> Programatic Tranaction

public void addEmployee(Employee e) {
    Connection con = null;
    try {
        con = DriverManager.getConnection(...);
        con.setAutoCommit(false);
        PreparedStatement ps = con.prepareStatement("insert into emp values (?,?,?));
        ps.setInt(1, e.getEmployeeId());
        ps.setString(2, e.getFirstName());
        //
        ps.executeUpdate();
        con.commit();
    } catch(SQLException ex) {
        ...
        con.rollback();
    } finally {
        con.close();
    }
}

Hibernate without Spring:

public void addEmployee(Employee e) {
    Session session = sessionFactroy.getSession();
    Transaction tx = session.beginTransaction();
    try {
        session.persiste(e);
        tx.commit();
    } catch(HibernateException ex) {
        tx.rollback();
    }
}

Declarative Transaction:

@Transactional
public void addEmployee(Employee e) {
    // logic for JDBC
    // logic to use REdis
    // logic to use Kinises Stream
    // logic for SMS
    // logic for Email
}

================

Customer class ==> "customers" table
   String email ==> PK
 
   String firstName;  ==> "first_name" column in table


CustomerDao

OrderService ==> CustomerDao 
insertCustomer and findAll

orders

oid    order_date           customer_fk         total
1      20-03-2023 4:50      anna@gmail.com      231000
2      20-03-2023 5:60      smith@gmail.com     2000
3      21-03-2023  10:30    anna@gmail.com      98811


items

item_id  order_fk   product_fk  qty  amount
1         1         2           1    230000 
2         1         3           2      1000  
3         2         3           4      2000


=======

Without cascade:
Assume order has 4 items:

orderDao.save(order);
itemDao.save(i1);
itemDao.save(i2);
itemDao.save(i3);
itemDao.save(i4);

--

orderDao.delete(order);
itemDao.delete(i1);
itemDao.delete(i2);
itemDao.delete(i3);
itemDao.delete(i4);

With Cascade:
@OneToMany(cascade = CascadeType.ALL)
@JoinColumn(name="order_fk")
private List<Item> items = new ArrayList<>();

orderDao.save(order); => takes care of saving all items in Order
orderDao.delete(order); ==> takes care of deleting all items of given order

By using Cascade I can do all operations using OrderDao, no need for ItemDao

Lazy Fetching:
orderDao.findAll(); ==> select * from orders; 
items are not fetched

Eager Fetching:
orderDao.findAll(); 
select * from orders and select items also from items table...

=========

By Default ManyToOne is EAGER fetching
By default OneToMany is LAZY loading

order object in JSON:

{
    "customer": {"email":"anna@gmail.com"},
    "items" : [
        {"product": {"id": 2}, "qty": 1},
        {"product": {"id": 3}, "qty": 4}
    ]
}

Dirty Checking of ORM:

within Transactional Boundary / PersitenceContext if entity becomes dirty ORM issues UPDATE Query,
no need for explicitly invoking update


Ways to update Entity:

1) Using Query
	@Modifying
	@Query("update Product set price = :p where id = :id")
	void updateProduct(@Param("id") int id, @Param("p") double price);

2) Dirty Checking:

@Transactional
void updateProduct(int id, double price, int qty) {
    Product p = productDao.findById(id).get();
    p.setPrice(price);
    p.setQuantity(qty); // product is dirty and UPDATE SQL is triggered 
}

==============

Task 1:

Vehicle Rental application

1) Customer
email
firstName

2) Driver
licenenseId
firstName
lastName
phone

3) Vehicle
REG_NO
type ==> PETROL | DIESEL
rentPerDay ==> 5000/-

4) Rental
rental_id   customer_fk  driver_fk  rent_start_date  rent_end_date  total

Use case:
1) insert customers
2) insert drivers
3) insert vehicles
4) rent a vehicle
rental_id   customer_fk  driver_fk  rent_start_date  rent_end_date  total
123         a@gmail.com  KA343412   20-03-2023          null        0.0

5) Return a Vehcile
rental_id   customer_fk  driver_fk  rent_start_date  rent_end_date      total
123         a@gmail.com  KA343412   20-03-2023          21-03-2023      5000.00


Task 2:
Project Management:
1) Employee ==> email, firstName
2) Project ==> pid, name, start_date, end_date
3) Assign Employee to Project
 EmployeeProject ==>
 employee_project
 id email_fk            project_fk      start_date      end_date        role
 1  smitha@xi.com       123             1-03-2020       12-8-2021       SR.Developer
 2  harry@xi.com         234            3-3-2023        null            TEST LEAD

===================================================

Aspect Oriented Programming ==> AOP 
1) Aspect
2) JoinPoint
3) PointCut
4) Advice

AOP is to eliminate Cross-cutting concerns
aspect is a bit of logic which is not a part of main logic but can be used along with main logic and leads to code tangling and code scattering

Examples of Aspects: ==> Logging, Profile, Transaction, Security, ...

public void transferFunds(Account fromAcc, Account toAcc, double amt) {
    tx.begin(); // transaction code
    profile.startTime(); // profile
    log.debug("transaction started"); // log
    if(context.getRole().equals("MANAGER")) { // security
        log.debug("manager task"); //log
             withdraw(fromAcc, amt); // actual logic
        log.debug("money withdrawn"); // log
            deposit(toAcc, amt); 
        log.debug("money deposited");
            insertIntoTxTable(); 
        log.debug("transcation complete")
    }
    tx.commit();
    profile.endTime(); //
}


public void placeOrder(Order o) {
        tx.begin(); // transaction code
    profile.startTime(); // profile
    log.debug("transaction started"); // log
    if(context.getRole().equals("MANAGER")) { // security
        log.debug("manager task"); //log
             orderDao.save(o);
        log.debug("order placed withdrawn"); // log
            deposit(toAcc, amt); 
        log.debug("money deposited");
            insertIntoTxTable(); 
        log.debug("transcation complete")
    }
    tx.commit();
    profile.endTime(); //
}

======

Day 3

Spring Framework and Spring Boot
Metadata --> XML and Annotation

@Component
@Repository --> Persistence Code
By using JpaRepository interface we don't need @Respoistory class ==> Spring Data Jpa creates instance of this interface
@Service ==> Facade on top of all Business logic and persistence logic ==> code generally in @Service are atomic and transactional ==> Combine many DAO and business calls into one method which is atomic
@Configuration and @Bean ==> Factory method 

ORM and JPA ==> Hibernate as ORM provider ==> Simplify CRUD operations on top of JDBC
* PersitenceContext is an environment where entities are managed by an interface called EntityManager.

EntityManager itself is created using EntityManagerFactory { which uses DataSource --> Pool of DB connections and ORM Provider --> Hiberante}

Annotations of JPA:
1) @Entity --> Required
2) @Table --> Optional -> to map class to database table; if not provided class name will be the table name.
3) @Id --> to mark a Primary Key --> Required
4) @Column -> Optational -> to map field to database column
5) @GeneratedValue ==> Optional ==> to specify how PK is managed [ assigned, auto increment, sequence]

Pre-defined methods like : save(), findById(), findaAll(), delete()...
if we provide methods in JpaRepository like
findByFieldName(); --> select * from <tablename> where fieldName = ?
We can use @Query to write custom methods

Mapping:
@OneToMany
@ManyToOne
@JoinColumn
Cascade, and EAGER/LAZY fetching



Rental * <--> 1 Vehicle

Rental * <--> 1 Driver


rentals

id  date        vehicle_fk  driver_fk
1   30          KA12A1344       a
2   5           KA12A1344       b
3   6           UP12H9823       a



OneToOne

employees

eid         name        email               laptop_id
1           Roger       roger@ab.com            9899
2           Smitha      smitha@ab.com           8900
3           Peter       peter@ab.com            2323

laptops

serial_no   make        RAM   Storage
2323        Macbook     ..


========================================

RESTful Web Services

REST ==> REpresentational State Transfer, architectural pattern for distributed hypermedia systems.
Roy Fielding --> year 2000

Web applications:
1) Server Side Rendering  --> Traditional web application
2) Client Side Rendering --> server sends different formats of "state of data";
    Advantages: Heterogenous clients like mobile / web /desktop/ other applications
    Payload between client and server is lightweight

Resource: Any information that we can name can be resource [ image / file / database / printer]

Resources should be well named:
1) Use plural nouns to represent resources [ customers/ products/ orders]
2) Collection
    * server managed directory of resources [ products/ customers]
    http://amazon.com/products
3) store
    * client managed resource [ playlist / wishlist /cart]
    http://spotify.com/users/banu/playlist
4) Controller
    * Procedural cincept / like executable functions
    http://spotify.com/users/banu/playlist/play

Identify Resources using URL
Perfomr actions using HTTP methods

1)
GET
http://amazon.com/mobiles

to fetch all mobiles

2)
GET
http://amazon.com/mobiles/iphone14

get a single resource form "mobiles" resource colliection ==> "iphone14" 

GET
http://localhost:8080/products/2

get a product whose id is "2"

use PathParameter [/identity] for getting single resource

3)
GET
http://amazon.com/mobiles?page=1&size=20

Paginating mobiles

GET
http://amazon.com/mobiles?company=Xiaomi

for Filter use QueryParameter / RequestParameter [ ? ]

4)
POST
http://amazon.com/mobiles

Payload contains a new Mobile which has to be added to "mobiles" resource

5) 
PUT
http://amazon.com/mobiles/8

Payload contains a new data for Mobile with ID "8" which has to be updated in "mobiles" resource

{
    name:"...",
    "telephone": "",
    "skills": [...]
}

5) 

PATCH
http://amazon.com/mobiles/8

also for update ==> but partial update, not major update

like move song from 3rd position to 1st position in playlist, remove a song from playlist

http://localhost:8080/api/employees/5
id, name, telephone, projects, skills
[

    {"op": "replace", "path":"/telephone", "value":"+91 9823232323"},
    {"op": "add", "path":"/skills/0", "value":"Building REST using Spring Boot"}
]


6)
DELETE
http://amazon.com/mobiles/8

deleta a mobile whose id is "8" ==> Rare endpoint ==> prefer deleteing using backend or traditonal way

Guiding Principles of  REST:
1) Uniform Interface
    Indentification of resources
2) Client-Server ==> Design Pattern to enforce seperation of concerns
3) Stateless ==> No Session Tracking; every request from client should be treated as a new request; no converstational state
4) Cacheable
5) Layered System ==> composed of hierarchial layers 

========

Spring Boot provides "web" dependency
<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-web</artifactId>
</dependency>

adding web dependecy, will configure:
1) Embedded TomcatWebServer 
2) HttpMessageConvertor for Java <--> JSON ==> Jackson library
  Jackson, Jettison, GSON, Moxy are librarires for Java <--> JSON 

    class name is ObjectMapper
```
Product p = Product.builder()
				.id(11)
				.name("iPhone 14")
				.price(120000.00)
				.quantity(100)
				.build();
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(p);
		System.out.println(json);
```
3) Configures Spring MVC ==> Model View Controller for Traditional and RESTful web application

DispatcherSErvlet is the FrontController ==> Intercepts all request coming from client

HandlerMapping has list of resources to be invoked for different URLs

@Controller
@RequestMapping("/register")
public class RegisterServlet {

    @PostMapping()
    m1() {

    }
}


@RestController
public class ProductServlet {

    @GetMapping()
    m1() {}

    @PostMapping()
    m2() {}
}

@ResponseBody ==> is for Java to JSON { optional}
@RequestBody  ==> is for JSON to Java

install POSTMAN

POST: http://localhost:8080/api/products

Headers:
Accept: application/json
content-type: application/json

Body (raw)
{
    "name": "Wacom",
    "price": "8900.00",
    "quantity": 100
}

Send:
check status code to be 201
and returned product should have inserted PK also


DELETE: http://localhost:8080/api/products/5

Response
Deleted product with id 5

PUT: http://localhost:8080/api/products/5
{
    "name": "Wacom",
    "price": "9430.00",
    "quantity": 100
}

============

REQUEST PARAMS:
http://localhost:8080/api/products?low=50000&high=350000


===

POST : http://localhost:8080/api/orders

Headers:
Accept: text/plain
Content-type: application/json

Body(raw)
{
    "customer": {"email": "anna@gmail.com"},
    "items": [
        {"product": {"id": 4}, "qty": 2},
         {"product": {"id": 5}, "qty": 1}
    ]
}
--

GET : http://localhost:8080/api/orders

JSON PATCH Operations

PATCH: http://localhost:8080/api/employees
Headers:
Accept: application/json
Content-type: application/json-patch+json

body(raw):

[
    {"op":"replace", "path":"/telephone", "value":"+91762242323"},
    {"op":"add", "path":"/skills/0", "value":"Spring Boot"}
]
Response:
{
    "id": 123,
    "telephone": "+91762242323",
    "skills": [
        "Spring Boot",
        "Java",
        "PHP",
        "TypeScript"
    ]
}
https://jsonpatch.com/

The patch operations supported by JSON Patch are “add”, “remove”, “replace”, “move”, “copy” and “test”. 

Aspect Oriented Programming --> AOP
Aspect --> bit of code which can be used along with main logic; not a part of main logic ==> Loggers, Transaction, Security, Profile

https://docs.spring.io/spring-framework/docs/2.0.x/reference/aop.html

====

Exception Handling, Validation, Caching, Documentation, Metrics

==

Recap

RESTful Web Services
@RestController
binding URL:@RequestMapping -> URL to Controller

HTTP Methods to Java method binding:
@GetMapping()
@PostMapping()
@PutMapping()
@DeleteMapping()
@PatchMapping()

@PathVariable ==> PathParameter
@RequestParam ==> QueryParameter

Accept:application/json
@ResponseBody ==> Java to JSON; optional in @RestController 
HttpMessageConvertor is used to convert java to representation based on Accept header;
Jackson library is default configured for Java<--->JSON

Content-type:application/json
@RequestBody ==> JSON --> Java; Required

ResponseEntity --> use this in case if we need additional headers to be passed as payload along with entity

SpringMVC ==> DispatcherServlet --> HandlerMapping --> returns method of @Controller or @RestController to be invoked for a Given HTTP method and URL

Day 4

Exception Handling in RestController.

ResourcenotFoundException.java
LogAspect.java
OrderService.java
ProductController.java
GlobalExceptionHandler.java

Validation:
<dependency>			
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-validation</artifactId>
</dependency>


MethodArgumentNotValidException: 
[Name is required]
[Price -9.0 should be more than 10] 
[Quantity -90 should be more than 0]

----
Testing: AAA --> Assemble Action Assert
1) Unit testing
2) Integration testing
3) E2E testing --> Selenium / Cypress / PlayWright

Unit Testing RESTController
	<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

spring-boot-starter-test provides:
1) JUnit --> Unit testing Framework {TestNG}
2) Mockito --> Mocking library {EasyMock, JMock}
3) jsonpath --> validate JSON
https://jsonpath.com/
4) Hamcrest --> matchers for assertion

Caching
* Client-side Caching
1) Http headers Cache-Control
cache-control max-age=3600

2) ETag
The ETag (or entity tag) HTTP response header is an identifier for a specific version of a resource. It lets caches be more efficient and save bandwidth, as a web server does not need to resend a full response if the content was not changed
```
// GET http://localhost:8080/api/products/cache/4
	@GetMapping("/cache/{pid}")
	public ResponseEntity<Product> getProductCache(@PathVariable("pid") int id) throws ResourceNotFoundException {
		Product p =  service.getProductById(id);
		return ResponseEntity.ok().eTag(Long.toString(p.hashCode())).body(p);
	}
```

POSTMAN
GET http://localhost:8080/api/products/cache/4

Response:
ETag: "1870871232"

Body:
{
    "id": 4,
    "name": "Redme X-12",
    "price": 62000.0,
    "quantity": 98
}

---

Next Request:
POSTMAN
GET http://localhost:8080/api/products/cache/4

Headers:
ACCEPT:
CONTENT-TYPE:
If-none-Match: "1870871232"

Response:
304: not modified
Body: blank


Product.java
Using JPA @Version
@Version
private int ver;


```
@GetMapping("/cache/{pid}")
	public ResponseEntity<Product> getProductCache(@PathVariable("pid") int id) throws ResourceNotFoundException {
		Product p =  service.getProductById(id);
		return ResponseEntity.ok().eTag(Long.toString(p.getVer())).body(p);
	}

```

id name  price  qty    ver
1  A     3434   343     0

Any updates will keep incrementing version

Version column will also be used internally by JPA to handle concurreny issues

First Commit wins, by default last commit wins
User 1:
1  A     3434   343     0

User 1 purchases 43 products

update qty = qty - 43, ver = ver + 1 where id = 1 and ver  = 0 

User 1 Fails
User 2:
1  A     3434   343     0

User 2 purchases 3 products

update qty = qty - 3, ver = ver + 1 where id = 1 and ver = 0
1  A     3434   340     1
-------------------

* Server Side Caching


1) JPA first level Cache

@Transactional
public void doTask() {
    Product p1 = productDao.findById(1, Product.class).get(); // SELECT Query
    //
    Product p2 = productDao.findById(2, Product.class).get(); // SELECT Query

    ///
    Product p3 = productDao.findById(1, Product.class).get(); // gets from Cache --> available in PersistenceContext
}

2) JPA Second level Cache: --> cache @ application level used between users for multiple requests
Ehache, JBossSwarmCache, ...

3) Spring CacheManager

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-cache</artifactId>
		</dependency>
	
By Default ConcurrentMapCacheManager [ ConditionalBean ] is used by default --> In Memory.
Can't be used in Cluster environment, many instances running on docker, can't be used between micro-services

Steps:
1) enable caching in any Configuration file
@EnableCaching
@SpringBootApplication
public class OrderappApplication {

2) 
```
@Cacheable(value="productCache", key="#id")
	@GetMapping("/cache/{pid}")
	public Product getProductCache(@PathVariable("pid") int id)

    SPeL #
    Key will be product "id" : value will be the returned Product from method

    @CachePut(value="productCache", key="#id")
	@PutMapping("/{pid}")
	public Product updateProduct(@PathVariable("pid") int id, @RequestBody Product p) throws ResourceNotFoundException {
		service.updateProduct(id, p.getPrice());
		return this.getProduct(id);
	}
    // avoid
	@CacheEvict(value="productCache", key="#id")
	@DeleteMapping("/{id}")
	public String deleteProduct(@PathVariable("id") int id) {
		//
		return "Deleted product with id " + id;
	}
```

Schedule a Task to remove entries from cache.

CRON
https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/scheduling/support/CronExpression.html

```

@EnableCaching
@EnableScheduling
@SpringBootApplication
public class OrderappApplication {
	
	@Autowired
	CacheManager cacheManager;
	
	public static void main(String[] args) {
		SpringApplication.run(OrderappApplication.class, args);
	}

	//@Scheduled(fixedDelay = 2000)
	@Scheduled(cron ="0 0/30 * * * *")
	public void clearCache() {
		System.out.println("Cache Cleared!!!");
		cacheManager.getCacheNames().forEach(cache -> {
			cacheManager.getCache(cache).clear();
		});
	}
}

```

docker run --name -p 6379:6379 some-redis -d redis

<!-- Cache -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-cache</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-redis</artifactId>
		</dependency>
<!-- Cache End -->

application.properties
spring.redis.port=6379
spring.redis.host=127.0.0.1

Serialization is a process of replicating the state to a stream
public class Product implements Serializable{

Not Working: //TODO
```
@Bean
	public RedisCacheConfiguration cacheConfiguration() {
	    return RedisCacheConfiguration.defaultCacheConfig()
	      .entryTtl(Duration.ofMinutes(60))
	      .disableCachingNullValues()
	      .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
	}
```

Documentation of APIs:

1) RAML

config.yaml
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

2) Swagger --> OpenAPI Rest Document

<!-- https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-ui -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-ui</artifactId>
    <version>1.6.15</version>
</dependency>

http://localhost:8080/v3/api-docs

http://localhost:8080/swagger-ui/index.html

=============

Day 5

@ControllerAdvice and @ExceptionHandler for Global exception handling @Controller and @RestController

@Aspect --> is not aware of HttpRequest and HttpResponse

javax.validation.constraints 
@Min, @Max, @Range, @NotBlank, @Pattern, ....

@Validated, @Valid

==> MethodArgumentNotValidException

UnitTesting controller by mocking dependencies [Mockito]
jsonPath, Hamcrest, Junit

@WebMvcTest() ===> Spring Container only has Spring MVC loaded and not @Service, @Respository,...
MockMvc ==> CRUD operations using get(), post(), put(),...

@MockBean

----

Caching
* Cache-Control: max-age(36000)
* ETag will be sent as response from server along with data;
use If-None-Match header with Etag sent for further requests; Server sends new payload only if change in ETag value; else it sends 302 Not Modifed header with no payload

* Server - Side Caching:
Default --> ConcurrentMapCacheManager
@EnableCaching, @Cacheable, @CachePut, @CacheEvict
@EnableScheduling, @Scheduled {fixedRate, fixedDuration, CRON expression}

* Redis
once redis is configured; default "ConcurrentMapCacheManager" will not be available instead we will have RedisCacheManager

Redis Client:

NodeJS --> redis-commander

$ npx redis-commander

------

API documentation ==> RAML { declarative --> yaml}, OpenApi/Swagger {creates docs out of the box, just need to include dependency}

http://localhost:8080/v3/api-docs

http://localhost:8080/swagger-ui/index.html


=======================

RestTemplate:
Rest Template is used to create applications that consume RESTful Web Services.
Alternative -> WebClient [spring flux ], HttpClient
