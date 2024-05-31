package com.clifm.se.capstone.aquariumbuddy.dynamodb;

import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Log;
import com.clifm.se.capstone.aquariumbuddy.exceptions.LogNotFoundException;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * Accesses data for a Log using {@link Log} to interact with the model in DynamoDB.
 */
@Singleton
public class LogDao {
    private final DynamoDBMapper mapper;

    /**
     * Instantiates a LogDao object.
     *
     * @param mapper the {@link DynamoDBMapper} used to interact with the Log table
     */
    @Inject
    public LogDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Retrieves a Log by userEmail and logId.
     *
     * If not found, throws LogNotFoundException.
     *
     * @param userEmail The userEmail to look up
     * @param logId The logId to look up
     * @return The corresponding Log if found
     */
    public Log getSingleLog(String userEmail, String logId) {
        Log log = mapper.load(Log.class, userEmail, logId);
        if (log == null) {
            throw new LogNotFoundException(String.format(
                    "Could not find log with userEmail %s and logId %s.", userEmail, logId));
        }
        return log;
    }

    /**
     * Retrieves all logs for user by userEmail.
     *
     * If not found, throws LogNotFoundException.
     *
     * @param userEmail The userEmail to look up
     * @return The corresponding Log if found
     */
    public List<Log> getLogs(String userEmail) {
        Log log = new Log();
        log.setUserEmail(userEmail);
        DynamoDBQueryExpression<Log> queryExpression = new DynamoDBQueryExpression<Log>()
                .withHashKeyValues(log);
        return mapper.query(Log.class, queryExpression);
    }

    /**
     * Retrieves a List of logs by tankId Using a GSI Index.
     *
     * If not found, throws LogNotFoundException.
     *
     * @param tankId The tankId to look up
     *
     * @return The corresponding List of Logs if found
     */
    public List<Log> getLogsforTank(String tankId) {
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":tankId", new AttributeValue(tankId));
        DynamoDBQueryExpression<Log> queryExpression = new DynamoDBQueryExpression<Log>()
                .withIndexName("AquariumBuddy-LogsSortByType")
                .withConsistentRead(false)
                .withKeyConditionExpression("tankId = :tankId")
                .withExpressionAttributeValues(valueMap);
        return mapper.query(Log.class, queryExpression);
    }

    /**
     * Retrieves a List of logs by tankId and flavor Using a GSI Index.
     *
     * If not found, throws LogNotFoundException.
     *
     * @param tankId The tankId to look up
     * @param flavor The flavor to look up
     * @return The corresponding List of Logs if found
     */
    public List<Log> getLogsByType(String tankId, String flavor) {
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":tankId", new AttributeValue(tankId));
        valueMap.put(":flavor", new AttributeValue(flavor));
        DynamoDBQueryExpression<Log> queryExpression = new DynamoDBQueryExpression<Log>()
                .withIndexName("AquariumBuddy-LogsSortByType")
                .withConsistentRead(false)
                .withKeyConditionExpression("tankId = :tankId and flavor = :flavor")
                .withExpressionAttributeValues(valueMap);
        return mapper.query(Log.class, queryExpression);
    }


    /**
     * Saves provided Log to DynamoDB to create or update DynamoDB record.
     *
     * @param log The Log to be saved
     */
    public void writeLog(Log log) {
        mapper.save(log);
    }

    /**
     * Removes the requested Log from DynamoDB, if present.
     *
     * @param userEmail The userEmail to look up
     * @param logId The logId to look up
     * @return The corresponding Log if found
     */
    public Log deleteLog(String userEmail, String logId) {
        Log log = mapper.load(Log.class, userEmail, logId);
        mapper.delete(log);
        return log;
    }

}
