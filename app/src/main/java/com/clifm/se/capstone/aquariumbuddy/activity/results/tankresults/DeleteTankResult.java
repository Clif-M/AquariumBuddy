package com.clifm.se.capstone.aquariumbuddy.activity.results.tankresults;

import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Tank;

public class DeleteTankResult {
    private final Tank tank;

    private DeleteTankResult(Tank tank) {
        this.tank = tank;
    }
    public Tank getTank() {
        return tank;
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Tank tank;

        public Builder withTank(Tank tank) {
            this.tank = tank;
            return this;
        }
        public DeleteTankResult build() {
            return new DeleteTankResult(tank);
        }
    }
}
