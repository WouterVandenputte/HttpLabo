package be.kuleuven;

import be.kuleuven.networking.Connection;
import be.kuleuven.networking.HttpClient;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

public class NetworkHelper
{

    private HttpClient client = new HttpClient();

    public Connection connect(String url) throws IOException, URISyntaxException {
        URI uri = new URI(url);
        Socket socket = new Socket(uri.getHost(), 80);
        return new Connection(socket, uri);
    }

    public void handleGet(String host)
    {
        try {
            Connection connection = connect(host);
            client.saveToFile(connection,"index");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


}
