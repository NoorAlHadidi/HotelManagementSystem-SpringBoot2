package com.brightskies.hotelsystem.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.stereotype.Component;

@Component
@Entity
public class User {
    @Id
    private int id;
    private String name;
    private String email;
    private String phone;
}
