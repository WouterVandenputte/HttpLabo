package be.kuleuven.networking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URI;

public abstract class HttpClient
{
    public static Connection connect(URI uri) throws IOException
    {
        Socket socket = new Socket(uri.getHost(), 80);
        return new Connection(socket, uri);
    }
}