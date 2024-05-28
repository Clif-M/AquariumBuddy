package com.clifm.se.capstone.aquariumbuddy.activity.results.fishresults;

import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Fish;

public class DeleteFishResult {
    private final Fish fish;

    private DeleteFishResult(Fish fish) {
        this.fish = fish;
    }
    public Fish getFish() {
        return fish;
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Fish fish;

        public Builder withFish(Fish fish) {
            this.fish = fish;
            return this;
        }
        public DeleteFishResult build() {
            return new DeleteFishResult(fish);
        }
    }
}
