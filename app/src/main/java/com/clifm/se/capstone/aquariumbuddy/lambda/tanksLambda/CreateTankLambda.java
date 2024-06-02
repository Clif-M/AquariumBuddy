package com.clifm.se.capstone.aquariumbuddy.lambda.tanksLambda;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.tankrequest.CreateTankRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.tankresults.CreateTankResult;
import com.clifm.se.capstone.aquariumbuddy.lambda.AuthenticatedLambdaRequest;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaActivityRunner;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaResponse;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class CreateTankLambda
        extends LambdaActivityRunner<CreateTankRequest, CreateTankResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreateTankRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateTankRequest> input, Context context) {
        return super.runActivity(() -> {
            CreateTankRequest stagedRequest = input.fromBody(CreateTankRequest.class);

            return input.fromUserClaims(claims -> CreateTankRequest.builder()
                    .withTank(stagedRequest.getTank())
                    .withUserEmail(claims.get("email"))
                    .build());
            }, (request, serviceComponent) ->
                        serviceComponent.provideCreateTankActivity().handleRequest(request)
        );
    }
}
