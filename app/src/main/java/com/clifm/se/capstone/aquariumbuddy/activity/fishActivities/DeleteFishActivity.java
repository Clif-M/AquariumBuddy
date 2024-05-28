package com.clifm.se.capstone.aquariumbuddy.activity.fishActivities;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.fishrequest.DeleteFishRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.fishresults.DeleteFishResult;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.FishDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Fish;

import javax.inject.Inject;

/**
 * Implementation of the DeleteFishActivity for the Fish endpoint.
 *
 * This API allows the customer to interact with Fish objects in the database.
 */
public class DeleteFishActivity {
    private final FishDao fishDao;

    /**
     * Instantiates a new DeleteFishActivity object.
     *
     * @param fishDao FishDao to access the fish table.
     */
    @Inject
    public DeleteFishActivity(FishDao fishDao) {
        this.fishDao = fishDao;
    }

    /**
     * This method handles the incoming request by deleting a fish from the database, if it exists.
     * <p>
     * It then returns the fish.
     * <p>
     *
     * @param deleteFishRequest request object containing the userEmail and fishId
     * @return DeleteFishResult result object containing the API defined
     * {@link com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Fish}
     */

    public DeleteFishResult handleRequest(final DeleteFishRequest deleteFishRequest) {
        Fish fish = fishDao.deleteFish(deleteFishRequest.getUserEmail(), deleteFishRequest.getFishId());
        return DeleteFishResult.builder()
                .withFish(fish)
                .build();
    }
}
