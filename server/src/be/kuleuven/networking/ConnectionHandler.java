package be.kuleuven.networking;

import be.kuleuven.ReadWriteHandler;
import be.kuleuven.networking.Attachment;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ConnectionHandler implements  CompletionHandler<AsynchronousSocketChannel, Attachment> {

    @Override
    public void completed(AsynchronousSocketChannel client, Attachment attach) {
        try {
            SocketAddress clientAddr = client.getRemoteAddress();
            System.out.format("Accepted a  connection from  %s%n", clientAddr);
            attach.server.accept(attach, this);
            ReadWriteHandler rwHandler = new ReadWriteHandler();
            Attachment newAttach = new Attachment();
            newAttach.server = attach.server;
            newAttach.client = client;
            newAttach.buffer = ByteBuffer.allocate(2048);
            newAttach.isRead = true;
            newAttach.clientAddr = clientAddr;

            ByteBuffer out = ByteBuffer.allocateDirect(2048);

            client.write(out);
            client.read(newAttach.buffer, newAttach, rwHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void failed(Throwable e, Attachment attach) {
        System.out.println("Failed to accept a  connection.");
        e.printStackTrace();
    }
}