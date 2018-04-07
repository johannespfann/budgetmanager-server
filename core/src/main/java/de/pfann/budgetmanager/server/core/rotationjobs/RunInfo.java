package de.pfann.budgetmanager.server.core.rotationjobs;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class RunInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Run run;

    @Column(nullable = false)
    private LocalDate start_at;

    @Column(nullable = false)
    private LocalDate end_at;

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
            start_at = LocalDate.now();
        }
    }

    public void stop(){
        if(end_at == null){
            end_at = LocalDate.now();
        }
    }

    public LocalDate getStart_at() {
        return start_at;
    }

    public LocalDate getEnd_at() {
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
}
