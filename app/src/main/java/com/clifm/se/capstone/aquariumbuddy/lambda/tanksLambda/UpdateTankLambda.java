package com.clifm.se.capstone.aquariumbuddy.lambda.tanksLambda;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.tankrequest.UpdateTankRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.tankresults.UpdateTankResult;
import com.clifm.se.capstone.aquariumbuddy.lambda.AuthenticatedLambdaRequest;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaActivityRunner;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaResponse;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class UpdateTankLambda
        extends LambdaActivityRunner<UpdateTankRequest, UpdateTankResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateTankRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateTankRequest> input, Context context) {
        return super.runActivity(() -> {
            UpdateTankRequest stagedRequest = input.fromBody(UpdateTankRequest.class);

            return input.fromUserClaims(claims -> UpdateTankRequest.builder()
                    .withTank(stagedRequest.getTank())
                    .withUserEmail(claims.get("email"))
                    .build());
            }, (request, serviceComponent) ->
                        serviceComponent.provideUpdateTankActivity().handleRequest(request)
        );
    }
}
