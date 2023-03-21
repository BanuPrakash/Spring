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

