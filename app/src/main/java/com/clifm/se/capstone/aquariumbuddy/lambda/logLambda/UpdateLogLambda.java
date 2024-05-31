package com.clifm.se.capstone.aquariumbuddy.lambda.logLambda;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.logrequest.UpdateLogRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.logresults.UpdateLogResult;
import com.clifm.se.capstone.aquariumbuddy.lambda.AuthenticatedLambdaRequest;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaActivityRunner;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaResponse;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class UpdateLogLambda
        extends LambdaActivityRunner<UpdateLogRequest, UpdateLogResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateLogRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateLogRequest> input, Context context) {
        return super.runActivity(() -> {
            UpdateLogRequest stagedRequest = input.fromBody(UpdateLogRequest.class);

            return input.fromUserClaims(claims -> UpdateLogRequest.builder()
                    .withLog(stagedRequest.getLog())
                    .withUserEmail(claims.get("email"))
                    .build());
            }, (request, serviceComponent) ->
                        serviceComponent.provideUpdateLogActivity().handleRequest(request)
        );
    }
}
