package de.pfann.budgetmanager.server.common.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Run implements Comparable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private LocalDateTime executed_at;

    public Run(){
        executed_at = LocalDateTime.now();
    }

    public Run(LocalDateTime aExecuted_at){
        executed_at = aExecuted_at;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getExecuted_at() {
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
