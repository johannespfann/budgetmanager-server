package de.pfann.budgetmanager.server.persistens.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Run implements Comparable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private LocalDate executed_at;

    public Run(){
        executed_at = LocalDate.now();
    }

    public Run(LocalDate aExecuted_at){
        executed_at = aExecuted_at;
    }

    public long getId() {
        return id;
    }

    public LocalDate getExecuted_at() {
        return executed_at;
    }

    @Override
    public int compareTo(Object object) {

        if(object instanceof Run){
            return executed_at.compareTo(((Run) object).executed_at);
        }

        throw new IllegalArgumentException();
    }
}
