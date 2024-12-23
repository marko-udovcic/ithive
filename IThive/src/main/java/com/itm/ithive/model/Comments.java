package com.itm.ithive.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id",  nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "blog_id", referencedColumnName = "id",  nullable = false)
    private Blog blog;

    @Column(name = "parent_id")
    private int parent;

//    @JsonFormat(pattern = "dd-MM-yyyy")
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    private int depth;

    private String text;
}
