package de.pfann.budgetmanager.server.core.rotationjobs;

import de.pfann.budgetmanager.server.common.util.LogUtil;
import de.pfann.budgetmanager.server.persistens.model.RotationEntry;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Examiner {

    private List<RotationEntryPattern> patterns;

    private LocalDate examineDate;

    public boolean executeable(RotationEntry aRotationEntry){

        LogUtil.info(this.getClass(),"  [DecideExecution]");
        if(aRotationEntry.getLast_executed() != null) {
            LogUtil.info(this.getClass(), "     [LastExecuted:] " + aRotationEntry.getLast_executed().toString());
        }
        else{
            LogUtil.info(this.getClass(), "     [LastExecuted:]" + " was null");
        }
        LogUtil.info(this.getClass(), "     [ExamineForDay: ]" +  examineDate);

        for(RotationEntryPattern pattern : patterns){

            if(pattern.isValidPattern(aRotationEntry)){

                if(pattern.isExecutable(examineDate,aRotationEntry)){
                    LogUtil.info(this.getClass(),"     [Was for today] " + examineDate);

                    return true;
                }

            }
        }
        LogUtil.info(this.getClass(),"     [Was not for today] " + examineDate);
        return false;
    }

    private Examiner(List<RotationEntryPattern> aPattern, LocalDate aExamineDate){
        patterns = aPattern;
        examineDate = aExamineDate;
    }


    public static ExaminerBuilder builder(){
        return new ExaminerBuilder();
    }

    public static class ExaminerBuilder{

        private List<RotationEntryPattern> pattern;

        private LocalDate date;

        public ExaminerBuilder(){
            pattern = new LinkedList<>();
        }

        public ExaminerBuilder withPattern(RotationEntryPattern aPattern){
            pattern.add(aPattern);
            return this;
        }

        public ExaminerBuilder withPattern(List<RotationEntryPattern> aPattern){
            pattern.addAll(aPattern);
            return this;
        }

        public ExaminerBuilder forDate(LocalDate aDate){
            date = aDate;
            return this;
        }

        public Examiner build(){
            assertNotNull(date);
            assertNotEmpty(pattern);
            return new Examiner(pattern,date);
        }

        private void assertNotEmpty(List<RotationEntryPattern> pattern) throws IllegalArgumentException {
            if(pattern == null || pattern.size() <= 0){
                throw new IllegalArgumentException("No pattern to validate found!");
            }
        }

        private void assertNotNull(LocalDate date) throws IllegalArgumentException{
            if(date == null){
                throw new IllegalArgumentException("No date found!");
            }
        }


    }



}
