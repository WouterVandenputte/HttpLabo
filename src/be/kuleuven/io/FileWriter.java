package be.kuleuven.io;

import java.io.*;
import java.net.URI;

public class FileWriter
{
    public static File writeTextFile(URI path, String content) throws IOException {

        File file = new File(path);
        if(!file.exists())
            file.createNewFile();

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(path.getPath()), "utf-8"))) {
            writer.write(content);
        }
        return file;
    }
}
