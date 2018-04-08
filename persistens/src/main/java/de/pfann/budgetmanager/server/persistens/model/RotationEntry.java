package de.pfann.budgetmanager.server.persistens.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.pfann.budgetmanager.server.persistens.model.AppUser;
import de.pfann.budgetmanager.server.persistens.model.Category;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class RotationEntry  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String hash;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private AppUser user;

    private Date start_at;

    private Date end_at;

    private Date last_executed;

    @Column(nullable = false)
    private String rotation_strategy;


    /**
     * to generate entry stuff
     */

    @Column(nullable = false)
    private double amount;

    private String memo;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;

    private String tags;


    public RotationEntry(){
    }


    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Date getStart_at() {
        return start_at;
    }

    public void setStart_at(Date start_at) {
        this.start_at = start_at;
    }

    public String getRotation_strategy() {
        return rotation_strategy;
    }

    public void setRotation_strategy(String rotation_strategy) {
        this.rotation_strategy = rotation_strategy;
    }

    public Date getEnd_at() {
        return end_at;
    }

    public void setEnd_at(Date end_at) {
        this.end_at = end_at;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTags() {return tags;}

    public void setTags(String tags) {this.tags = tags;}

    public Date getLast_executed() {
        return last_executed;
    }

    public void setLast_executed(Date last_executed) {
        this.last_executed = last_executed;
    }


    @Override
    public String toString() {
        return "RotationEntry{" +
                "hash='" + hash + '\'' +
                ", user=" + user +
                ", start_at=" + start_at +
                ", end_at=" + end_at +
                ", last_executed=" + last_executed +
                ", rotation_strategy='" + rotation_strategy + '\'' +
                ", amount=" + amount +
                ", memo='" + memo + '\'' +
                ", category=" + category +
                ", tags='" + tags + '\'' +
                '}';
    }
}
