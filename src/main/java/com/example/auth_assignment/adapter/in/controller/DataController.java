package com.example.auth_assignment.adapter.in.controller;

import java.util.HashMap;
import java.util.Map;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.auth_assignment.adapter.in.dto.DataRequest;

@RestController
@RequestMapping("/data")
public class DataController {

    // USER และ ADMIN สามารถเข้าถึงได้

    @GetMapping("/view")
    public ResponseEntity<?> viewData() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "viewData");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
        // Logic สำหรับการดูข้อมูล
    }

    // USER และ ADMIN สามารถเข้าถึงได้

    @PostMapping("/create")
    public ResponseEntity<?> createData(@RequestBody @Valid DataRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "createData");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
        
        // Logic สำหรับการสร้างข้อมูล
    }

    // เฉพาะ ADMIN เท่านั้นที่สามารถเข้าถึงได้

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editData(@PathVariable Long id, @RequestBody @Valid DataRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "editData");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
        // Logic สำหรับการแก้ไขข้อมูล
    }

    // เฉพาะ ADMIN เท่านั้นที่สามารถเข้าถึงได้

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteData(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "deleteData");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
        // Logic สำหรับการลบข้อมูล
    }
}