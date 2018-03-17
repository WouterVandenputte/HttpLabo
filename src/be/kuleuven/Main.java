package be.kuleuven;

import be.kuleuven.networking.HttpClient;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args)
    {
        String command = args[0];
        if(command != null && !command.isEmpty())
        {
            switch (command.toUpperCase())
            {
                case "GET":
                    handleGet(args[1]);
                    break;
                default: break;
            }
        }
    }

    private static void handleGet(String url)
    {

                if(url != null && !url.isEmpty())
                {
                    try {
                        URI uri = new URI(url);
                        File file = new File(uri.getHost()+ ".html");
                        file.createNewFile();
                        String content = HttpClient.connect(uri).downloadPath("index",file);
                        System.out.println("Downloaded " + url + " " + file.getAbsolutePath());
                        new FileWriter(new File(uri.getPath())).write(content);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
    }
}
