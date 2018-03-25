package de.pfann.budgetmanager.server.rotationjobs;

import de.pfann.budgetmanager.server.model.RotationEntry;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Examiner {

    private List<RotationEntryPattern> patterns;

    private Date examineDate;

    public boolean executeable(RotationEntry aRotationEntry){

        for(RotationEntryPattern pattern : patterns){

            if(pattern.isValidPattern(aRotationEntry)){

                if(pattern.isExecutable(examineDate,aRotationEntry)){
                    return true;
                }

            }
        }

        return false;
    }

    private Examiner(List<RotationEntryPattern> aPattern, Date aExamineDate){
        patterns = aPattern;
        examineDate = aExamineDate;
    }


    public static ExaminerBuilder builder(){
        return new ExaminerBuilder();
    }

    public static class ExaminerBuilder{

        private List<RotationEntryPattern> pattern;

        private Date date;

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

        public ExaminerBuilder forDate(Date aDate){
            date = aDate;
            return this;
        }

        public Examiner build(){
            assertNotNull(date);
            assertNotEmpty(pattern);
            return new Examiner(pattern,date);
        }

        private void assertNotEmpty(List<RotationEntryPattern> pattern) throws IllegalArgumentException {
            if(pattern == null || pattern.size() > 0){
                throw new IllegalArgumentException("No pattern to validate found!");
            }
        }

        private void assertNotNull(Date date) throws IllegalArgumentException{
            if(date == null){
                throw new IllegalArgumentException("No date found!");
            }
        }


    }



}
