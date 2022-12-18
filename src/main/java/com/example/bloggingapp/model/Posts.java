package com.example.bloggingapp.model;

import org.hibernate.annotations.CreationTimestamp;


import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "posts")
public class Posts {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY  )
    @Column(name = "id")
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "body", nullable = false)
    private String body;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", updatable = false)
    private Date creationDate;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private Users users;

    @Column(name = "is_approved")
    private boolean isApproved;

    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<Comments> comments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Users getUser() {
        return users;
    }

    public void setUser(Users user) {
        this.users = user;
    }

    public Collection<Comments> getComments() {
        return comments;
    }

    public void setComments(Collection<Comments> comments) {
        this.comments = comments;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}
