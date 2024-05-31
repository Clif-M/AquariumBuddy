package com.clifm.se.capstone.aquariumbuddy.activity.requests.logrequest;

public class GetSingleLogRequest {
    private final String userEmail;
    private final String logId;

    private GetSingleLogRequest(String userEmail, String logId) {
        this.userEmail = userEmail;
        this.logId = logId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getLogId() {
        return logId;
    }

    //CHECKSTYLE:OFF:BUILDER
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String userEmail;
        private String logId;

        public Builder withUserEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public Builder withLogId(String logId) {
            this.logId = logId;
            return this;
        }

        public GetSingleLogRequest build() {
            return new GetSingleLogRequest(userEmail, logId);
        }
    }
}
