package com.clifm.se.capstone.aquariumbuddy.activity.requests.logrequest;

public class GetLogsByTypeRequest {
    private final String tankId;
    private final String flavor;

    private GetLogsByTypeRequest(String tankId, String flavor) {
        this.tankId = tankId;
        this.flavor = flavor;
    }

    public String getTankId() {
        return tankId;
    }

    public String getType() {
        return flavor;
    }

    //CHECKSTYLE:OFF:BUILDER
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String tankId;
        private String flavor;

        public Builder withTankId(String tankId) {
            this.tankId = tankId;
            return this;
        }

        public Builder withType(String flavor) {
            this.flavor = flavor;
            return this;
        }

        public GetLogsByTypeRequest build() {
            return new GetLogsByTypeRequest(tankId, flavor);
        }
    }
}
