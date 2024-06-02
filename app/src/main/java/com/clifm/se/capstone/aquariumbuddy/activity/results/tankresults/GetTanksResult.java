package com.clifm.se.capstone.aquariumbuddy.activity.results.tankresults;

import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Tank;

import java.util.List;

public class GetTanksResult {
    private final List<Tank> tanks;

    private GetTanksResult(List<Tank> tanks) {
        this.tanks = tanks;
    }
    public List<Tank> getTank() {
        return tanks;
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<Tank> tanks;

        public Builder withTank(List<Tank> tanks) {
            this.tanks = tanks;
            return this;
        }
        public GetTanksResult build() {
            return new GetTanksResult(tanks);
        }
    }
}
