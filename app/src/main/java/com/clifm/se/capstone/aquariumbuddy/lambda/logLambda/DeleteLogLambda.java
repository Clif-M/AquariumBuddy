package com.clifm.se.capstone.aquariumbuddy.lambda.logLambda;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.logrequest.DeleteLogRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.logresults.DeleteLogResult;
import com.clifm.se.capstone.aquariumbuddy.lambda.AuthenticatedLambdaRequest;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaActivityRunner;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaResponse;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class DeleteLogLambda
        extends LambdaActivityRunner<DeleteLogRequest, DeleteLogResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteLogRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteLogRequest> input, Context context) {
        return super.runActivity(() -> {
            DeleteLogRequest stagedRequest = input.fromUserClaims(claims ->
                    DeleteLogRequest.builder()
                            .withUserEmail(claims.get("email"))
                            .build());
            return input.fromPath(path -> DeleteLogRequest.builder()
                    .withUserEmail(stagedRequest.getUserEmail())
                    .withLogId(path.get("logId"))
                    .build());
            }, (request, serviceComponent) ->
                        serviceComponent.provideDeleteLogActivity().handleRequest(request)
        );
    }
}
