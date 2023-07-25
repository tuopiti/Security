package com.piti.java.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/brand")
public class BrandController {
	
	@PreAuthorize("hasAuthority('brand:read')")
    @GetMapping
    public String get() {
        return "GET:: brand controller";
    }
	
	@PreAuthorize("hasAuthority('brand:write')")
    @PostMapping
    public String post() {
        return "POST:: brand controller";
    }
    @PutMapping
    public String put() {
        return "PUT:: brand controller";
    }
    @DeleteMapping
    public String delete() {
        return "DELETE:: brand controller";
    }
    
}
