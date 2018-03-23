package de.pfann.budgetmanager.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

    @Column(nullable = false)
    private Date start_at;

    @Column(nullable = false)
    private Date end_at;

    @Column(nullable = false)
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="rotation_tag",
            joinColumns=@JoinColumn(name="ROTATIONENTRY_ID", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="TAG_ID", referencedColumnName="id"))
    private List<Tag> tags;

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

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
