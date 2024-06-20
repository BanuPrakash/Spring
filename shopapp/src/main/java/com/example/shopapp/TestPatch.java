package com.example.shopapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.aop.framework.ProxyFactory;

public class TestPatch {

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

