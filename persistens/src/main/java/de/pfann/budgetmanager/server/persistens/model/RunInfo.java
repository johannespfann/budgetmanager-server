package de.pfann.budgetmanager.server.persistens.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class RunInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Run run;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private LocalDateTime start_at;

    @Column(nullable = false)
    private LocalDateTime end_at;

    @Column(nullable = false)
    private String identifier;

    public RunInfo(){
        // default;
    }

    public RunInfo(Run aRun, String aIdentifier){
        run = aRun;
        identifier = aIdentifier;
    }

    public void start(){
        if(start_at == null){
            start_at = LocalDateTime.now();
        }
    }

    public void stop(String aState){
        if(end_at == null){
            state = aState;
            end_at = LocalDateTime.now();
        }
    }

    public LocalDateTime getStart_at() {
        return start_at;
    }

    public LocalDateTime getEnd_at() {
        return end_at;
    }

    public long getId() {
        return id;
    }

    public Run getRun() {
        return run;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getState() { return state; }

}
