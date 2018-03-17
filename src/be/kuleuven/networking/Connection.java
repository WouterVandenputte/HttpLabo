package be.kuleuven.networking;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;

public class Connection
{
    private Socket socket;
    private URI uri;

    public Connection(Socket socket, URI uri)
    {
        setSocket(socket);
        setUri(uri);
    }

    private void setUri(URI uri)
    {
        this.uri = uri;
    }

    private void setSocket(Socket socket)
    {
        this.socket = socket;
    }

    public String downloadPath(String index, File location) throws IOException
    {
        if(socket.isConnected())
        {
            final FileOutputStream fileOutputStream = new FileOutputStream(location);
            final InputStream inputStream = socket.getInputStream();

            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            pw.println("GET / HTTP/1.1");
            pw.println("Host: " + uri.getHost());
            pw.println();
            pw.flush();
            // Header end flag.
            boolean headerEnded = false;

            byte[] bytes = new byte[2048];
            int length = 0;

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            int contentLength=0;
            while((line = bufferedReader.readLine()) != null)
            {
                while(!headerEnded)
                if(!headerEnded)
                {
                    if(line.toLowerCase().startsWith("content-length"))
                        contentLength = Integer.parseInt(line.replace("Content-Length: ",""));
                    else if(line.equals(""))
                        headerEnded=true;
                }
                else
                {
                    fileOutputStream.write(line.getBytes(),0,contentLength);
                    break;
                }
            }

           /* while (contentLength > 0) {
                // If the end of the header had already been reached, write the bytes to the file as normal.


                    // This locates the end of the header by comparing the current byte as well as the next 3 bytes
                    // with the HTTP header end "\r\n\r\n" (which in integer representation would be 13 10 13 10).
                    // If the end of the header is reached, the flag is set to true and the remaining data in the
                    // currently buffered byte array is written into the file.
                else {

                    for (int i = 0; i < 2045; i++) {
                        if (bytes[i] == 13 && bytes[i + 1] == 10 && bytes[i + 2] == 13 && bytes[i + 3] == 10) {
                            headerEnded = true;
                            fileOutputStream.write(bytes, i+4 , 2048-i-4);
                            break;
                        }
                    }
                }
            }*/
            inputStream.close();
            fileOutputStream.close();
        }
        return "";
    }

}