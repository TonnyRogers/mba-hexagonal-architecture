package com.example.demo;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.infrastructure.DemoApplication;

@ActiveProfiles("test")
@SpringBootTest(classes = DemoApplication.class)
public abstract class IntegrationTest {

}
