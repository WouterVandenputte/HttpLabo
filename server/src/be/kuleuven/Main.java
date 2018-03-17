package be.kuleuven;

import be.kuleuven.networking.Attachment;
import be.kuleuven.networking.ConnectionHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open();
        String host = "localhost";
        int port = 80;
        InetSocketAddress sAddr = new InetSocketAddress(host, port);
        server.bind(sAddr);
        System.out.format("Server is listening at %s%n", sAddr);
        Attachment attach = new Attachment();
        attach.server = server;
        server.accept(attach, new ConnectionHandler());
        Thread.currentThread().join();
    }
}
