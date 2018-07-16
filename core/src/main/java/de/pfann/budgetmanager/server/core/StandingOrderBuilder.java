package de.pfann.budgetmanager.server.core;

import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.StandingOrder;
import de.pfann.budgetmanager.server.common.model.Tag;
import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.common.util.HashUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StandingOrderBuilder {

    private final AppUser user;
    private List<Tag> tags;
    private String amount;
    private String memo;
    private Date startTime;
    private Date endTime;
    private Date lastExecuted;
    private String strategy;

    private StandingOrderBuilder(AppUser aUser){
        user = aUser;
        tags = new ArrayList<>();
    }

    public static StandingOrderBuilder createBuilder(AppUser aAppUser){
        return new StandingOrderBuilder(aAppUser);
    }

    public StandingOrderBuilder withAmount(String aAmount){
        amount = aAmount;
        return this;
    }

    public StandingOrderBuilder withMemo(String aMemo){
        memo = aMemo;
        return this;
    }

    public StandingOrderBuilder withTag(String aTagName){
        Tag tag = new Tag(aTagName);
        tags.add(tag);
        return this;
    }

    public StandingOrderBuilder withStartTime(LocalDateTime aStarTime){
        startTime = DateUtil.asDate(aStarTime);
        return this;
    }

    public StandingOrderBuilder withEndTime(LocalDateTime aEndTime){
        endTime = DateUtil.asDate(aEndTime);
        return this;
    }

    public StandingOrderBuilder withLastExecuted(LocalDateTime aLastExecuted){
        lastExecuted = DateUtil.asDate(aLastExecuted);
        return this;
    }

    public StandingOrderBuilder withStrategy(String aStrategy){
        strategy = aStrategy;
        return this;
    }

    public StandingOrder build(){

        assertNotNull(amount);
        assertNotNull(memo);
        assertNotNull(strategy);

        StandingOrder standingOrder = new StandingOrder();
        standingOrder.setUser(user);
        standingOrder.setHash(HashUtil.getUniueHash());
        standingOrder.setAmount(amount);
        standingOrder.setMemo(memo);
        standingOrder.setTags(tags);
        standingOrder.setRotation_strategy(strategy);
        standingOrder.setLast_executed(getLastExecuted());
        standingOrder.setStart_at(getStartTime());
        standingOrder.setEnd_at(getEndTime());
        return standingOrder;
    }

    private void assertNotNull(String aValue) {
        if(aValue == null){
            throw new IllegalArgumentException();
        }
    }

    private Date getLastExecuted(){
        if(lastExecuted == null){
            lastExecuted = DateUtil.getMinimumDate();
        }
        return lastExecuted;
    }

    private Date getStartTime(){
        if(startTime == null){
            startTime = new Date();
        }
        return startTime;
    }

    private Date getEndTime(){
        if(endTime == null){
            endTime = DateUtil.getMaximumDate();
        }
        return endTime;
    }

}
