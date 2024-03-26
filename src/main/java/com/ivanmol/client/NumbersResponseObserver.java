package com.ivanmol.client;

import com.numbers.Service;
import com.numbers.Service.NumbersResponse;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.atomic.AtomicInteger;

public class NumbersResponseObserver implements StreamObserver<NumbersResponse> {

    private final AtomicInteger resultHolder;

    public NumbersResponseObserver(AtomicInteger resultHolder) {
        this.resultHolder = resultHolder;
    }

    @Override
    public void onNext(NumbersResponse numbersResponse) {
        int number = numbersResponse.getNumber();
        System.out.println("    число от сервера: " + number);
        resultHolder.set(number);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {

    }
}
