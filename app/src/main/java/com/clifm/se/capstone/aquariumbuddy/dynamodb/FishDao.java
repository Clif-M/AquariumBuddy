package com.clifm.se.capstone.aquariumbuddy.dynamodb;

import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Fish;
import com.clifm.se.capstone.aquariumbuddy.exceptions.FishNotFoundException;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

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
     * If not found, throws TaskNotFoundException.
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
     * Saves provided Project to DynamoDB to create or update DynamoDB record.
     *
     * @param fish The Fish to be saved
     */
    public void writeFish(Fish fish) {
        mapper.save(fish);
    }

    /**
     * Removes the provided Project from DynamoDB, if present.
     *
     * @param fish The Project to be deleted
     */
    public void deleteFish(Fish fish) {
        mapper.delete(fish);
    }

}
