package com.clifm.se.capstone.aquariumbuddy.activity.fishActivities;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.fishrequest.UpdateFishRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.fishresults.UpdateFishResult;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.FishDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Fish;
import com.clifm.se.capstone.aquariumbuddy.exceptions.FishNotFoundException;

import javax.inject.Inject;

/**
 * Implementation of the UpdateFishActivity for the Fish endpoint.
 *
 * This API allows the customer to interact with Fish objects in the database.
 */
public class UpdateFishActivity {
    private final FishDao fishDao;

    /**
     * Instantiates a new UpdateFishActivity object.
     *
     * @param fishDao FishDao to access the fish table.
     */
    @Inject
    public UpdateFishActivity(FishDao fishDao) {
        this.fishDao = fishDao;
    }

    /**
     * This method handles the incoming request by updating a fish in the database.
     * <p>
     * It then returns the fish.
     *
     * @param updateFishRequest request object containing the fish object
     * @return UpdateFishResult result object containing the API defined
     * {@link com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Fish}
     */

    public UpdateFishResult handleRequest(final UpdateFishRequest updateFishRequest) {
        Fish fish = updateFishRequest.getFish();
        fish.setUserEmail(updateFishRequest.getUserEmail());
        try {
            fishDao.getSingleFish(fish.getUserEmail(), fish.getFishId());
        } catch (FishNotFoundException e) {
            throw new FishNotFoundException("Fish can not be updated. Fish does not exist in database.");
        }
        fishDao.writeFish(fish);
        return UpdateFishResult.builder()
                .withFish(fish)
                .build();
    }
}
