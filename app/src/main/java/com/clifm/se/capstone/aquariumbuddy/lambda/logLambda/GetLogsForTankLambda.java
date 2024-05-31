package com.clifm.se.capstone.aquariumbuddy.lambda.logLambda;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.logrequest.GetLogsForTankRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.logresults.GetLogsForTankResult;
import com.clifm.se.capstone.aquariumbuddy.lambda.AuthenticatedLambdaRequest;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaActivityRunner;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaResponse;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetLogsForTankLambda
        extends LambdaActivityRunner<GetLogsForTankRequest, GetLogsForTankResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetLogsForTankRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetLogsForTankRequest> input, Context context) {
        return super.runActivity(
            () -> input.fromPath(path ->
                    GetLogsForTankRequest.builder()
                            .withTankId(path.get("tankId"))
                            .build()),
            (request, serviceComponent) ->
                    serviceComponent.provideGetLogsForTankActivity().handleRequest(request)
        );
    }
}
