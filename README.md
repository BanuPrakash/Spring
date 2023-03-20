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
1) @Component
2) @Repository
3) @Service
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

