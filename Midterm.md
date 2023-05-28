
1. Which of the following HTTP methods can be mapped to a method annotated with @RequestMapping?
    a. GET 
    b. PUT
    c. POST
    d. DELETE

2. What are the types of dependency injection supported by Spring IoC Container ? (Choose all that apply) *
    A. field-based injection
    B. constructor injection
    C. interface-based injection
    D. setter injection

3. can you name some advantages of dependency injection?

4. What is fluent API?

5. What is the difference between RequestMapping and GetMapping?

6. Please explain the difference between @BeforeAll and @BeforeEach in Junit 5.

7. As we have learned, Stub is a fake class that comes with pre-programmed return values. It’s injected into the class under test to give you absolute control over what’s being tested as input.
   We can also create a Mock class which is a fake class too. For example:
   @Mock
   private Major mockMajor;

   or
   Major mockMajor = org.mockito.Mockito.mock(Major.class)

   Can you tell the difference between a stub class and a mock class?

8. What does @Autowired do in Spring / Spring Boot application?

9. What does @Component do in Spring / Spring Boot application?

10. Please explain the difference between @Component and @Service in Spring / Spring Boot

11. Explain the difference between @RestController and @Controller

12. In Spring / Spring Boot framework, what @Qualifier annotation is used for?

13. 13. Which of the following HTTP request method can be used to create a new entity, but not modify an existing one?
    a. GET
    b. PUT
    c. POST
    d. DELETE

14. Please explain what @Bean annotation does in the following class:

    @Configuration
    public class SampleProjectConfiguration {
       @Bean
       public SampleService createBean(String name) {
            SampleService sampleService = new SampleService(name);
            sampleService.setCounter(10);
            return sampleService;
       }
    }

    public class SampleService {
       private String serviceName;
       private int counter;
       public SampleService(String serviceName) {
          this.serviceName = serviceName;
       }
       public void setCounter(int counterValue) {
          this.counter = counterValue;
       }
    }

15. Please refactor the following class to be a singleton class.
    public class StudentService {
        private String studentName;
        private int id;
        private String email;

        public String sayHello(String name) {
            return “Hi, “ + name;
        }  
    }  






















