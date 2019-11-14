package com.learn.springboot.dataProvider.model;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import java.util.ArrayList;
import java.util.List;

@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Entity
@Table(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String username;
    private String password;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private AppUserActivities activities;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AppUserActivities getActivities() {
        return activities;
    }

    public void setActivities(AppUserActivities activities) {
        this.activities = activities;
    }




    public static class AppUserActivities {
        private List<Long> recentLogins = new ArrayList<>();

        public List<Long> getRecentLogins() {
            return recentLogins;
        }

        public void setRecentLogins(List<Long> recentLogins) {
            this.recentLogins = recentLogins;
        }

        public void addLogin() {
            if (recentLogins.size()>=10) {
                recentLogins.remove(0);
            }
            recentLogins.add( System.currentTimeMillis() );
        }
    }
}