package com.example.report_generation.report_generation.models;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;
    private String username;
    @Transient
    private List<UserData> data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<UserData> getData() {
        return data;
    }

    public void setData(List<UserData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        String user = "UserId:" + this.id;
        user += "\n[";
        for (UserData userData : data) {
            user += userData.toString();
            user += "\n";
        }
        user += "]";
        return user;
    }
}
