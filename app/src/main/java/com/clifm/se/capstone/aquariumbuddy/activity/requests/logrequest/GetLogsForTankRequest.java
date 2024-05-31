package com.clifm.se.capstone.aquariumbuddy.activity.requests.logrequest;

public class GetLogsForTankRequest {
    private final String tankId;

    private GetLogsForTankRequest(String tankId) {
        this.tankId = tankId;
    }

    public String getTankId() {
        return tankId;
    }

    //CHECKSTYLE:OFF:BUILDER
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String tankId;

        public Builder withTankId(String tankId) {
            this.tankId = tankId;
            return this;
        }

        public GetLogsForTankRequest build() {
            return new GetLogsForTankRequest(tankId);
        }
    }
}
