package com.itm.ithive.model;

import com.itm.ithive.model.Enums.Role;
import com.itm.ithive.model.Enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data //generate getter,setter,toString,equals,hashCode
@AllArgsConstructor
@NoArgsConstructor //empty constructor
@Builder //for construction object , use only when you have a lot of parameters
public class Users {
    @Id
    @UuidGenerator
    private String id;
    private String username;

    @Column(name = "first_name", nullable = false, length = 70)
    private String firstname;
  
    @Lob //big data like TEXT
    @Column(name = "about_me", nullable = true)
    private String aboutMe;

    @Column(name = "company_name", length = 100, nullable = true)
    private String companyName;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING) //map enum as string in DB
    @Column(name = "role", nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING) //map enum as string in DB
    @Column(name = "status", nullable = false)
    private Status status;
  
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastname;
}
