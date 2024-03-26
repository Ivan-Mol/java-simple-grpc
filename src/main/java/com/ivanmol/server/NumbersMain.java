package com.ivanmol.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class NumbersMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(9090)
                .addService(new NumbersServer())
                .build();
        server.start();
        server.awaitTermination();
    }
}