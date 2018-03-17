package be.kuleuven;

import be.kuleuven.networking.Connection;
import be.kuleuven.networking.HttpClient;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args)
    {
        NetworkHelper helper = new NetworkHelper();
        String command = args[0];
        if(command != null && !command.isEmpty())
        {
            switch (command.toUpperCase())
            {
                case "GET":
                    helper.handleGet(args[1]);
                    break;
                default: break;
            }
        }
    }
}
