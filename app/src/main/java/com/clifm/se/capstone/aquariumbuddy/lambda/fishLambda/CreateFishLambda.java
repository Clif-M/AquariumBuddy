package com.clifm.se.capstone.aquariumbuddy.lambda.fishLambda;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.fishrequest.CreateFishRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.fishresults.CreateFishResult;
import com.clifm.se.capstone.aquariumbuddy.lambda.AuthenticatedLambdaRequest;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaActivityRunner;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaResponse;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class CreateFishLambda
        extends LambdaActivityRunner<CreateFishRequest, CreateFishResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreateFishRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateFishRequest> input, Context context) {
        return super.runActivity(() -> {
            CreateFishRequest stagedRequest = input.fromBody(CreateFishRequest.class);

            return input.fromUserClaims(claims -> CreateFishRequest.builder()
                    .withFish(stagedRequest.getFish())
                    .withUserEmail(claims.get("email"))
                    .build());
            }, (request, serviceComponent) ->
                        serviceComponent.provideCreateFishActivity().handleRequest(request)
        );
    }
}
