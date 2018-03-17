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

    public Socket getSocket() {
        return socket;
    }

    public URI getUri() {
        return uri;
    }

    public boolean isConnected()
    {
        return socket.isConnected();
    }

    public OutputStream getOutputStream() throws IOException
    {
        return socket.getOutputStream();
    }

    public InputStream getInputStream() throws IOException
    {
        return socket.getInputStream();
    }

    public String getHost()
    {
        return uri.getHost();
    }
}