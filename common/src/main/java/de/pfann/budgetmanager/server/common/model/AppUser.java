package de.pfann.budgetmanager.server.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class AppUser  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private boolean activated;

    @JsonIgnore
    private String encryptionText;

    public String getEncryptionText() {
        return encryptionText;
    }

    public void setEncryptionText(String encryptionText) {
        this.encryptionText = encryptionText;
    }

    public boolean isEncrypted() {
        return isEncrypted;
    }

    public void setEncrypted(boolean encrypted) {
        isEncrypted = encrypted;
    }

    @JsonIgnore
    private boolean isEncrypted;

    public AppUser() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActivated() {
        return activated;
    }

    public void activate() {
        this.activated = true;
    }

    public void deactivate() {
        this.activated = false;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", activated=" + activated +
                '}';
    }
}
