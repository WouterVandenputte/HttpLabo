package be.kuleuven.networking;

import java.io.*;
import java.net.*;

public class HttpClient
{
    public void saveToFile(Connection connection, String pageName)
    {
        if(connection != null)
        {
            try {
                File file = new File(connection.getHost()+ ".html");
                file.createNewFile();
                String content = contentOfPage(connection,pageName);
                System.out.println("Server responded:  " + content);
                new FileWriter(formatUriToFileName(connection.getUri())).append(content).flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String formatUriToFileName(URI uri)
    {
        return uri.getHost() + ".html";
    }

    private String contentOfPage(Connection connection, String page) throws IOException
    {
        if(connection.isConnected())
        {
            OutputStream output = connection.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            writer.println("GET / HTTP/1.1");
            writer.println("Host: " + connection.getHost());
            writer.println("Accept: text/html");
            //writer.println("Connection: close");
            writer.println();

            InputStream input = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            StringBuilder builder = new StringBuilder();

            String inputLine;
            while (!(inputLine = reader.readLine()).equals(""))
                builder.append(inputLine);

            return builder.toString();
        }
        return "hallo";
    }
}