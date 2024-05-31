package com.clifm.se.capstone.aquariumbuddy.activity.requests.logrequest;

public class GetLogsByTypeRequest {
    private final String tankId;
    private final String type;

    private GetLogsByTypeRequest(String tankId, String type) {
        this.tankId = tankId;
        this.type = type;
    }

    public String getTankId() {
        return tankId;
    }

    public String getType() {
        return type;
    }

    //CHECKSTYLE:OFF:BUILDER
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String tankId;
        private String type;

        public Builder withTankId(String tankId) {
            this.tankId = tankId;
            return this;
        }

        public Builder withType(String Type) {
            this.type = type;
            return this;
        }

        public GetLogsByTypeRequest build() {
            return new GetLogsByTypeRequest(tankId, type);
        }
    }
}
