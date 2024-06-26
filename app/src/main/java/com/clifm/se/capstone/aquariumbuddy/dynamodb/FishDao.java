package com.clifm.se.capstone.aquariumbuddy.dynamodb;

import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Fish;
import com.clifm.se.capstone.aquariumbuddy.exceptions.FishNotFoundException;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Accesses data for a Fish using {@link Fish} to interact with the model in DynamoDB.
 */
@Singleton
public class FishDao {
    private final DynamoDBMapper mapper;

    /**
     * Instantiates a FishDao object.
     *
     * @param mapper the {@link DynamoDBMapper} used to interact with the Fish table
     */
    @Inject
    public FishDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Retrieves a Fish by userEmail and fishId.
     *
     * If not found, throws FishNotFoundException.
     *
     * @param userEmail The userEmail to look up
     * @param fishId The fishId to look up
     * @return The corresponding Fish if found
     */
    public Fish getSingleFish(String userEmail, String fishId) {
        Fish fish = mapper.load(Fish.class, userEmail, fishId);
        if (fish == null) {
            throw new FishNotFoundException(String.format(
                    "Could not find fish with userEmail %s and fishId %s.", userEmail, fishId));
        }
        return fish;
    }

    /**
     * Retrieves a List of Fish by userEmail.
     *
     * If not found, throws FishNotFoundException.
     *
     * @param userEmail The userEmail to look up
     *
     * @return The corresponding List Fish if found
     */
    public List<Fish> getFish(String userEmail) {
        Fish fish = new Fish();
        fish.setUserEmail(userEmail);
        DynamoDBQueryExpression<Fish> queryExpression = new DynamoDBQueryExpression<Fish>()
                .withHashKeyValues(fish);
        return mapper.query(Fish.class, queryExpression);
    }


    /**
     * Saves provided Project to DynamoDB to create or update DynamoDB record.
     *
     * @param fish The Fish to be saved
     */
    public void writeFish(Fish fish) {
        mapper.save(fish);
    }

    /**
     * Removes the requested fish from DynamoDB, if present.
     *
     * @param userEmail The userEmail to look up
     * @param fishId The fishId to look up
     *
     */
    public void deleteFish(String userEmail, String fishId) {
        Fish fish = new Fish();
        fish.setFishId(fishId);
        fish.setUserEmail(userEmail);
        mapper.delete(fish);
    }

}
