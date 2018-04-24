package de.pfann.budgetmanager.server.persistens.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
public class SubTotalEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String hash;

    @Column(nullable = false)
    private double amount;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private AppUser appUser;

    private Date created_at;

    @Type(type="text")
    private String memo;

    public SubTotalEntry(){
        // default
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
