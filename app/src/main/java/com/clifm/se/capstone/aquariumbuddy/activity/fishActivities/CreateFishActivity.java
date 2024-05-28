package com.clifm.se.capstone.aquariumbuddy.activity.fishActivities;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.fishrequest.CreateFishRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.fishresults.CreateFishResult;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.FishDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Fish;

import java.util.UUID;
import javax.inject.Inject;

/**
 * Implementation of the CreateFishActivity for the Fish endpoint.
 *
 * This API allows the customer to interact with Fish objects in the database.
 */
public class CreateFishActivity {
    private final FishDao fishDao;

    /**
     * Instantiates a new CreateFishActivity object.
     *
     * @param fishDao FishDao to access the fish table.
     */
    @Inject
    public CreateFishActivity(FishDao fishDao) {
        this.fishDao = fishDao;
    }

    /**
     * This method handles the incoming request by creating a fish in the database.
     * <p>
     * It then returns the fish.
     *
     * @param createFishRequest request object containing the userEmail and fishId
     * @return GetFishResult result object containing the API defined
     * {@link com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Fish}
     */

    public CreateFishResult handleRequest(final CreateFishRequest createFishRequest) {
        Fish fish = createFishRequest.getFish();
        fish.setUserEmail(createFishRequest.getUserEmail());
        fish.setFishId(UUID.randomUUID().toString());
        fishDao.writeFish(fish);
        return CreateFishResult.builder()
                .withFish(fish)
                .build();
    }
}
