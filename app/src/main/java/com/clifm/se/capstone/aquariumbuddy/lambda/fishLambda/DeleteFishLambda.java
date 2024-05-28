package com.clifm.se.capstone.aquariumbuddy.lambda.fishLambda;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.fishrequest.DeleteFishRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.fishresults.DeleteFishResult;
import com.clifm.se.capstone.aquariumbuddy.lambda.AuthenticatedLambdaRequest;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaActivityRunner;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaResponse;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class DeleteFishLambda
        extends LambdaActivityRunner<DeleteFishRequest, DeleteFishResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteFishRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteFishRequest> input, Context context) {
        return super.runActivity(() -> {
            DeleteFishRequest stagedRequest = input.fromUserClaims(claims ->
                    DeleteFishRequest.builder()
                            .withUserEmail(claims.get("email"))
                            .build());
            return input.fromPath(path -> DeleteFishRequest.builder()
                    .withUserEmail(stagedRequest.getUserEmail())
                    .withFishId(path.get("fishId"))
                    .build());
            }, (request, serviceComponent) ->
                        serviceComponent.provideDeleteFishActivity().handleRequest(request)
        );
    }
}
