package be.kuleuven;

import be.kuleuven.networking.Attachment;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class ReadWriteHandler implements CompletionHandler<Integer, Attachment>
{
        @Override
        public void completed(Integer result, Attachment attach) {
            if (result == -1) {
                try {
                    attach.client.close();
                    System.out.format("Stopped   listening to the   client %s%n",
                            attach.clientAddr);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return;
            }

            if (attach.isRead) {
                attach.buffer.flip();
                int limits = attach.buffer.limit();
                byte bytes[] = new byte[limits];
                attach.buffer.get(bytes, 0, limits);
                Charset cs = Charset.forName("UTF-8");
                String msg = new String(bytes, cs);
                System.out.format("Client at  %s  says: %s%n", attach.clientAddr, msg);
                handleRequest(msg,attach);
                attach.isRead = false; // It is a write
                attach.buffer.rewind();
            } else {
                attach.client.write(attach.buffer, attach, this);
                attach.isRead = true;
                attach.buffer.clear();
                attach.client.read(attach.buffer, attach, this);
            }
        }

        @Override
        public void failed(Throwable e, Attachment attach) {
            e.printStackTrace();
        }

        private void handleRequest(String msg, Attachment attachment)
        {
            if(msg.startsWith("GET"))
            {
                attachment.buffer.clear();
                //attachment.buffer.putInt(200);
                attachment.buffer.put("Hallo".getBytes(Charset.forName("UTF-8")));
                attachment.client.write(attachment.buffer);
                try {
                    attachment.client.shutdownOutput();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(msg.startsWith("POST"))
            {

                attachment.client.read(attachment.buffer);
                byte[] d = attachment.buffer.array();
                int a = 0;
            }
        }
}
