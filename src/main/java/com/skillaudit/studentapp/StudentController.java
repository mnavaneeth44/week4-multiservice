package com.skillaudit.studentapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String VISIT_KEY = "visit_count";

    // Home endpoint - tracks visits via Redis
    @GetMapping("/")
    public String home() {
        Long visits = redisTemplate.opsForValue().increment(VISIT_KEY);
        return "<html><body style='font-family:Arial;max-width:600px;margin:50px auto;text-align:center;background:#0f0f1a;color:white;'>"
             + "<h1 style='color:#63b3ed'>Student Registry</h1>"
             + "<p>Spring Boot + PostgreSQL + Redis</p>"
             + "<div style='background:#1a1a2e;padding:20px;border-radius:10px;margin:20px 0'>"
             + "<h2 style='color:#68d391'>Page Visits: " + visits + "</h2>"
             + "<p>Counter stored in Redis!</p></div>"
             + "<p>✅ Spring Boot Running</p>"
             + "<p>✅ PostgreSQL Connected</p>"
             + "<p>✅ Redis Connected</p>"
             + "<p><a href='/api/students' style='color:#63b3ed'>View All Students</a></p>"
             + "</body></html>";
    }

    // Get all students from PostgreSQL
    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return ((ListCrudRepository<Student, Long>) studentRepository).findAll();
    }

    // Add a new student
    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        return ((CrudRepository<Student, Long>) studentRepository).save(student);
    }

    // Health check
    @GetMapping("/health")
    public String health() {
        return "{\"status\":\"ok\",\"services\":[\"spring-boot\",\"postgresql\",\"redis\"]}";
    }
}