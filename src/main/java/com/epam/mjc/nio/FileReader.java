package com.epam.mjc.nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class FileReader {

    public Profile getDataFromFile(File file) {
        String fileToString = mapFileToString(file);
        return mapStringToProfile(fileToString);
    }

    private Profile mapStringToProfile(String s) {
        System.out.println(s);
        String name = s.substring(("Name: ").length(),s.indexOf("Age: "));
        String age = s.substring(s.indexOf("Age: ") + "Age: ".length(), s.indexOf("Email: "));
        String email = s.substring(s.indexOf("Email") + "Email: ".length(), s.indexOf("Phone: "));
        String phone = s.substring(s.indexOf("Phone") + "Phone: ".length());
        System.out.println(name);
        System.out.println(age);
        System.out.println(email);
        System.out.println(phone);
        return new Profile(name,Integer.parseInt(age),email,Long.parseLong(phone));
    }

    private String mapFileToString(File file) {
        StringBuilder content = new StringBuilder();
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");) {
            FileChannel channel = randomAccessFile.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
            int read = channel.read(buffer);
            while (read != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    char c = (char) buffer.get();
                    if (c != '\r' && c != '\n') {
                        content.append(c);
                    }
                }
                buffer.clear();
                read = channel.read(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(content);
    }
}
