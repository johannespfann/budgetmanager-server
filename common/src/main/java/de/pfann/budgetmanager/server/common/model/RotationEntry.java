package de.pfann.budgetmanager.server.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.pfann.budgetmanager.server.common.util.HashUtil;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
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

    private Date start_at;

    private Date end_at;

    private Date last_executed;

    @Column(nullable = false)
    private String rotation_strategy;


    /**
     * to generate entry stuff
     */

    @Column(nullable = false)
    @Type(type="text")
    private String amount;

    @Type(type="text")
    private String memo;

    @OneToMany(mappedBy = "rotationEntry")
    private List<Tag> tags;


    public RotationEntry(){
        tags = new ArrayList<>();
    }

    public static RotationEntry generate(){
        RotationEntry rotationEntry = new RotationEntry();
        rotationEntry.setHash(HashUtil.getUniueHash());
        return rotationEntry;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public List<Tag> getTags() {return tags;}

    public void setTags(List<Tag> tags) {this.tags = tags;}

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
                ", tags=" + tags +
                '}';
    }
}
