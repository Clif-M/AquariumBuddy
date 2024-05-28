package com.clifm.se.capstone.aquariumbuddy.activity.results.fishresults;

import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Fish;

import java.util.List;

public class GetFishResult {
    private final List<Fish> fish;

    private GetFishResult(List<Fish> fish) {
        this.fish = fish;
    }
    public List<Fish> getFish() {
        return fish;
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<Fish> fish;

        public Builder withFish(List<Fish> fish) {
            this.fish = fish;
            return this;
        }
        public GetFishResult build() {
            return new GetFishResult(fish);
        }
    }
}
