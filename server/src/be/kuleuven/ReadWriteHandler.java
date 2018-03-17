package be.kuleuven;

import be.kuleuven.networking.Attachment;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
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
                System.out.format("Client at  %s  says: %s%n", attach.clientAddr,
                        msg);
                attach.isRead = false; // It is a write
                attach.buffer.rewind();

            } else {
                Charset cs = Charset.forName("UTF-8");
                byte[] data = "hey".getBytes(cs);
                attach.buffer.put(data);
                attach.buffer.flip();

                ByteBuffer bb = ByteBuffer.allocateDirect(2048);
                bb.put(data);

                try {
                    attach.client.write(bb).get();
                    attach.client.close();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //attach.client.write(attach.buffer, attach, this);
                attach.isRead = true;
                attach.buffer.clear();
                attach.client.read(attach.buffer, attach, this);
            }
        }

        @Override
        public void failed(Throwable e, Attachment attach) {
            e.printStackTrace();
        }
}
