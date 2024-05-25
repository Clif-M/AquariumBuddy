package com.clifm.se.capstone.aquariumbuddy.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.Objects;

@DynamoDBTable(tableName = "AquariumBuddy-Logs")
public class Log {
    private String userEmail;
    private String logId;
    private String type;
    private String notes;

    @DynamoDBHashKey(attributeName = "userEmail")
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @DynamoDBRangeKey(attributeName = "logId")
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = "AquariumBuddy-LogsSortByType")
    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "AquariumBuddy-LogsSortByType", attributeName = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @DynamoDBAttribute(attributeName = "notes")
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Log log = (Log) o;
        return Objects.equals(userEmail, log.userEmail) && Objects.equals(logId, log.logId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userEmail, logId);
    }
}
