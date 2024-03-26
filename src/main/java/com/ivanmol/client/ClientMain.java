package com.ivanmol.client;


public class ClientMain {

    public static void main(String[] args) throws InterruptedException {
        NumbersClient client = new NumbersClient("localhost", 9090);
        client.generateNumbers(0, 30);
        client.shutdown();
    }
}
