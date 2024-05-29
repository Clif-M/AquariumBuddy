package com.clifm.se.capstone.aquariumbuddy.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.List;
import java.util.Objects;

@DynamoDBTable(tableName = "AquariumBuddy-Tanks")
public class Tank {
    private String name;
    private String userEmail;
    private String tankId;
    private List<Fish> fishList;
    private List<Log> logList;
    private Integer fishCount;

    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBHashKey(attributeName = "userEmail")
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @DynamoDBRangeKey(attributeName = "tankId")
    public String getTankId() {
        return tankId;
    }

    public void setTankId(String tankId) {
        this.tankId = tankId;
    }

    @DynamoDBAttribute(attributeName = "fishList")
    public List<Fish> getFishList() {
        return fishList;
    }

    public void setFishList(List<Fish> fishList) {
        this.fishList = fishList;
    }

    @DynamoDBAttribute(attributeName = "logList")
    public List<Log> getLogList() {
        return logList;
    }

    public void setLogList(List<Log> logList) {
        this.logList = logList;
    }

    @DynamoDBAttribute(attributeName = "fishCount")
    public Integer getFishCount() {
        return fishCount;
    }

    public void setFishCount(Integer fishCount) {
        this.fishCount = fishCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tank tank = (Tank) o;
        return Objects.equals(userEmail, tank.userEmail) && Objects.equals(tankId, tank.tankId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userEmail, tankId);
    }

}
