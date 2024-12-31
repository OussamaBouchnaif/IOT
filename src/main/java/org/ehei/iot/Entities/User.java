package org.ehei.iot.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User{
    public User(String username, String password, Long telegramId, String email, Role role) {
        this.username = username;
        this.password = password;
        this.telegramId = telegramId;
        this.email = email;
        this.role = role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private Long telegramId;
    private String email;
    @ManyToOne
    @JoinColumn(name = "id_role")
    private Role role;
}
