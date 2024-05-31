package com.clifm.se.capstone.aquariumbuddy.lambda.logLambda;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.logrequest.GetSingleLogRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.logresults.GetSingleLogResult;
import com.clifm.se.capstone.aquariumbuddy.lambda.AuthenticatedLambdaRequest;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaActivityRunner;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaResponse;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetSingleLogLambda
        extends LambdaActivityRunner<GetSingleLogRequest, GetSingleLogResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetSingleLogRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetSingleLogRequest> input, Context context) {
        return super.runActivity(() -> {
            GetSingleLogRequest stagedRequest = input.fromUserClaims(claims ->
                    GetSingleLogRequest.builder()
                            .withUserEmail(claims.get("email"))
                            .build());
            return input.fromPath(path -> GetSingleLogRequest.builder()
                    .withUserEmail(stagedRequest.getUserEmail())
                    .withLogId(path.get("logId"))
                    .build());
            }, (request, serviceComponent) ->
                        serviceComponent.provideGetSingleLogActivity().handleRequest(request)
        );
    }
}
