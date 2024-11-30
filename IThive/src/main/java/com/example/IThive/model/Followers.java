package com.example.IThive.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Followers{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "follower_id", referencedColumnName = "id",  nullable = false)
    private Users follower;

    @ManyToOne
    @JoinColumn(name = "followed_id", referencedColumnName = "id",  nullable = false)
    private Users followed;

    @CreationTimestamp
    @Column(name = "date_following", updatable = false)
    private LocalDateTime date_following;

    public LocalDateTime getDateFollowing() {
        return date_following;
    }

    public void setDateFollowing(LocalDateTime date_following) {
        this.date_following = date_following;
    }

}