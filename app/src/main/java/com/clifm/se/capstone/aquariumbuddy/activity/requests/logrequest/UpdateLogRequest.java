package com.clifm.se.capstone.aquariumbuddy.activity.requests.logrequest;

import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Log;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = UpdateLogRequest.Builder.class)
public class UpdateLogRequest {
    private Log log;
    private String userEmail;
    private UpdateLogRequest(Log log, String userEmail) {
        this.log = log;
        this.userEmail = userEmail;
    }

    public Log getLog() {
        return log;
    }

    public String getUserEmail() {
        return userEmail;
    }

    //CHECKSTYLE:OFF:BUILDER
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private Log log;
        private String userEmail;

        public Builder withLog(Log log) {
            this.log=log;
            return this;
        }

        public Builder withUserEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public UpdateLogRequest build() {
            return new UpdateLogRequest(log, userEmail);
        }
    }
}
