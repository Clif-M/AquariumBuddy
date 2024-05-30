package com.clifm.se.capstone.aquariumbuddy.dynamodb;

import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Tank;
import com.clifm.se.capstone.aquariumbuddy.exceptions.TankNotFoundException;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Accesses data for a Tank using {@link Tank} to interact with the model in DynamoDB.
 */
@Singleton
public class TankDao {
    private final DynamoDBMapper mapper;

    /**
     * Instantiates a TankDao object.
     *
     * @param mapper the {@link DynamoDBMapper} used to interact with the Tank table
     */
    @Inject
    public TankDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Retrieves a Tank by userEmail and tankId.
     *
     * If not found, throws TankNotFoundException.
     *
     * @param userEmail The userEmail to look up
     * @param tankId The tankId to look up
     * @return The corresponding Tank if found
     */
    public Tank getSingleTank(String userEmail, String tankId) {
        Tank tank = mapper.load(Tank.class, userEmail, tankId);
        if (tank == null) {
            throw new TankNotFoundException(String.format(
                    "Could not find tank with userEmail %s and tankId %s.", userEmail, tankId));
        }
        return tank;
    }


    /**
     * Saves provided Tank to DynamoDB to create or update DynamoDB record.
     *
     * @param tank The Tank to be saved
     */
    public void writeTank(Tank tank) {
        mapper.save(tank);
    }

    /**
     * Removes the requested Tank from DynamoDB, if present.
     *
     * @param userEmail The userEmail to look up
     * @param tankId The tankId to look up
     * @return The corresponding Tank if found
     */
    public Tank deleteTank(String userEmail, String tankId) {
        Tank tank = mapper.load(Tank.class, userEmail, tankId);
        mapper.delete(tank);
        return tank;
    }

}
