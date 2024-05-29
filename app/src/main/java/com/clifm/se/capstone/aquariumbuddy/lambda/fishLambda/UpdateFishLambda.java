package com.clifm.se.capstone.aquariumbuddy.lambda.fishLambda;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.fishrequest.UpdateFishRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.fishresults.UpdateFishResult;
import com.clifm.se.capstone.aquariumbuddy.lambda.AuthenticatedLambdaRequest;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaActivityRunner;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaResponse;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class UpdateFishLambda
        extends LambdaActivityRunner<UpdateFishRequest, UpdateFishResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateFishRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateFishRequest> input, Context context) {
        return super.runActivity(() -> {
            UpdateFishRequest stagedRequest = input.fromBody(UpdateFishRequest.class);

            return input.fromUserClaims(claims -> UpdateFishRequest.builder()
                    .withFish(stagedRequest.getFish())
                    .withUserEmail(claims.get("email"))
                    .build());
            }, (request, serviceComponent) ->
                        serviceComponent.provideUpdateFishActivity().handleRequest(request)
        );
    }
}
