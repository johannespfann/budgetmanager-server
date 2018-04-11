package de.pfann.budgetmanager.server.persistens.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class TagTemplate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JsonIgnore
    private RotationEntry rotationEntry;


    public TagTemplate(){
        // default
    }

    public TagTemplate(String aName){
        name = aName;
    }

    /**
     * Setter
     */

    public void setName(String name) {
        this.name = name;
    }

    public void setRotationEntry(RotationEntry rotationEntry) {
        this.rotationEntry = rotationEntry;
    }

    /**
     * getter
     */


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public RotationEntry getRotationEntry() {
        return rotationEntry;
    }


    @Override
    public String toString() {
        return "TagTemplate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagTemplate)) return false;

        TagTemplate that = (TagTemplate) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
