package com.ivanmol.server;

import com.google.common.util.concurrent.RateLimiter;
import com.numbers.NumbersServiceGrpc;
import com.numbers.Service.NumbersRequest;
import com.numbers.Service.NumbersResponse;
import io.grpc.stub.StreamObserver;

public class NumbersServer extends NumbersServiceGrpc.NumbersServiceImplBase {
    @Override
    public void generateNumbersStream(NumbersRequest request, StreamObserver<NumbersResponse> responseObserver) {
        RateLimiter limiter = RateLimiter.create(2);
        for (int i = request.getFirstValue() + 1; i <= request.getLastValue(); i++) {
            limiter.acquire();
            NumbersResponse response = NumbersResponse.newBuilder().setNumber(i).build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }
}