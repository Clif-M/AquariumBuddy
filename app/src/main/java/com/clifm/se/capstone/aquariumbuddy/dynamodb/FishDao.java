package com.clifm.se.capstone.aquariumbuddy.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Fish;
import com.clifm.se.capstone.aquariumbuddy.exceptions.FishNotFoundException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FishDao {
    private final DynamoDBMapper mapper;

    @Inject
    public FishDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public Fish getSingleFish(String userEmail, String fishId) {
        Fish fish = mapper.load(Fish.class, userEmail, fishId);
        if (fish == null) {
            throw new FishNotFoundException(String.format("Could not find fish with userEmail %s and fishId %s.", userEmail, fishId));
        }
        return fish;
    }

}
