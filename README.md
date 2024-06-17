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