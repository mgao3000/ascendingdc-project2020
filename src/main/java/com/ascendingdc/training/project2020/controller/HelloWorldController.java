package com.ascendingdc.training.project2020.controller;//package com.ascending.training.controller;

import com.ascendingdc.training.project2020.entity.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
//@Controller
@RequestMapping(value = "/world")
public class HelloWorldController {
//    @Autowired
//    private Logger logger;
    private Logger logger = LoggerFactory.getLogger(getClass());

//    @ResponseBody
    @GetMapping(value="hello", produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(value = "hello", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String Hello() {
        logger.info("=== From Hello() === ");
        return "Hello";
    }

    @RequestMapping(value = "hello", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String HelloForPost() {
        logger.info("=== From Hello() === ");
        return "Hello";
    }

    @GetMapping(value="hello/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String greetingWithHello(@PathVariable("name")String name123) {
        logger.info("=== From greetingWithHello(), the input value of PathVariable('name') is: {}", name123);
        return "Hello " + name123;
    }

    @GetMapping(value="hi", produces = MediaType.APPLICATION_JSON_VALUE)
    public String Hi() {
        logger.info("=== From Hi() === ");
        return "Hi";
    }

    @GetMapping(value="hi/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String greetingWithHi(@PathVariable("name")String name) {
        logger.info("=== From greetingWithHi(), the input value of PathVariable('name') is: {}", name);
        return "Hi " + name;
    }

//    @GetMapping(value="helloWorld",produces = MediaType.APPLICATION_JSON_VALUE)
//    public String greetingWithHiUsingSingleRequestParam123(@RequestParam("name123")String name) {
//        logger.info("=== From greetingWithHiUsingSingleRequestParam(), the input value of RequestParam('name') is: {}", name);
//        return "Hi " + name;
//    }
//
//    @GetMapping(value="q",produces = MediaType.APPLICATION_JSON_VALUE)
//    public String greetingWithHiUsingSingleRequestParamLocationm123(@RequestParam("location123")String name) {
//        logger.info("=== From greetingWithHiUsingSingleRequestParam(), the input value of RequestParam('name') is: {}", name);
//        return "Hi " + name;
//    }

    @GetMapping(value="hi", params={"name"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public String greetingWithHiUsingSingleRequestParam(@RequestParam("name")String name) {
        logger.info("=== From greetingWithHiUsingSingleRequestParam(), the input value of RequestParam('name') is: {}", name);
        return "Hi " + name;
    }

    @GetMapping(value="hi", params={"name", "location"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public String greetingWithHiUsingMultipleRequestParam(@RequestParam("name")String name,
                                                          @RequestParam("location")String location) {
        logger.info("=== From greetingWithHiUsingMultipleRequestParam(), the input value of PathVariable('name') is: {} and PathVariable('location') is: {}",
                name, location);
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("Hi ")
                .append(name)
                .append("\n")
                .append("Are you from ")
                .append(location)
                .append("?");
        return strBuilder.toString();
    }

    @PostMapping(value = "hi/departments", produces = MediaType.APPLICATION_JSON_VALUE)
    public Department createDepartment(@RequestBody Department department) {
        logger.info("=== for create department call with HttpMethod.post, input department = {}", department);
        department.setId(33333L);
        return department;
    }

    @PutMapping(value = "hi/departments", produces = MediaType.APPLICATION_JSON_VALUE)
    public Department updateDepartment(@RequestBody Department department) {
        logger.info("=== for update department call with HttpMethod.put, input department = {}", department);
        department.setName(department.getName() + "_update_test");
        department.setLocation(department.getLocation() + "_update_test");
        department.setDescription(department.getDescription() + "_update_test");
        return department;
    }

    @PatchMapping(value = "hi/departments", produces = MediaType.APPLICATION_JSON_VALUE)
    public Department updateDepartmentPartially(@RequestBody Department department) {
        logger.info("=== for update department partially call with HttpMethod.post, input department = {}", department);
        department.setLocation(department.getLocation() + "_update_only_partially_test");
        return department;
    }

    @DeleteMapping(value = "delete/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteTest(@PathVariable("name") String name) {
        logger.info("###@@@$$$ deleteTest call, input name ={}", name);
        return "Delete Test looks GOOD!, the value of input pathVariable 'name' is: " + name;
    }

    @GetMapping("/headertest")
    public String greeting(@RequestHeader("accept-language") String language) {
        return "The item 'accept-language' in headers has a value: " + language;
    }

}



