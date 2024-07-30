package com.brightskies.hotelsystem.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.stereotype.Component;

@Component
@Entity
public class User {
    @Id
    private Integer id;
    private String name;
    private String email;
    private String phone;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
