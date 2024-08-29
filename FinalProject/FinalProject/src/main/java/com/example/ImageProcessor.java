package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ImageProcessor {

    private static final int MESSAGE_LENGTH = 1024;

    public static byte[] readFile(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            return fis.readAllBytes();
        }
    }

    public static void writeFile(File file, byte[] data) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data);
        }
    }

    public static void encryptDecryptImage(File file, int key, boolean encrypt) throws IOException {
        byte[] image = readFile(file);

        // Perform XOR operation for encryption or decryption
        for (int i = 0; i < image.length; i++) {
            image[i] ^= key;
        }

        writeFile(file, image);
    }

    public static void embedMessage(File file, String message) throws IOException {
        byte[] image = readFile(file);
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        byte[] result = new byte[image.length + MESSAGE_LENGTH];

        System.arraycopy(image, 0, result, 0, image.length);
        System.arraycopy(messageBytes, 0, result, image.length, messageBytes.length);

        for (int i = image.length + messageBytes.length; i < result.length; i++) {
            result[i] = 0;
        }

        writeFile(file, result);
    }

    public static String extractMessage(File file) throws IOException {
        byte[] image = readFile(file);
        int length = Math.min(image.length, MESSAGE_LENGTH);
        byte[] messageBytes = new byte[length];

        System.arraycopy(image, image.length - length, messageBytes, 0, length);

        return new String(messageBytes, StandardCharsets.UTF_8).trim();
    }
}
