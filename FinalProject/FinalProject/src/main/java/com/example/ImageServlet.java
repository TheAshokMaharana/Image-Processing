package com.example;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@MultipartConfig
public class ImageServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("file");
        String keyInput = request.getParameter("key");
        boolean encrypt = Boolean.parseBoolean(request.getParameter("encrypt"));
        String message = request.getParameter("message");
        String operation = request.getParameter("operation");

        if (filePart == null || keyInput == null || keyInput.trim().isEmpty()) {
            response.sendRedirect("index.jsp?error=Invalid input");
            return;
        }

        Integer key;
        try {
            key = Integer.parseInt(keyInput);
        } catch (NumberFormatException e) {
            response.sendRedirect("index.jsp?error=Invalid key");
            return;
        }

        File tempFile = File.createTempFile("upload", ".tmp");

        try (InputStream fileContent = filePart.getInputStream()) {
            byte[] imageData = fileContent.readAllBytes();
            ImageProcessor.writeFile(tempFile, imageData);

            if (operation.equals("simple")) {
                ImageProcessor.encryptDecryptImage(tempFile, key, encrypt);
            } else if (operation.equals("message")) {
                if (encrypt) {
                    ImageProcessor.embedMessage(tempFile, message);
                }
                ImageProcessor.encryptDecryptImage(tempFile, key, encrypt);
            }

            if (encrypt) {
                response.setContentType(filePart.getContentType());
                response.setHeader("Content-Disposition", "attachment; filename=\"processed_image.jpg\"");
                try (OutputStream out = response.getOutputStream()) {
                    out.write(ImageProcessor.readFile(tempFile));
                }
            } else {
                if (operation.equals("message")) {
                    String extractedMessage = ImageProcessor.extractMessage(tempFile);
                    request.setAttribute("message", extractedMessage);
                }
                request.setAttribute("imageURL", "data:image/jpeg;base64," + java.util.Base64.getEncoder().encodeToString(ImageProcessor.readFile(tempFile)));
                request.getRequestDispatcher("result.jsp").forward(request, response);
            }

        } catch (IOException e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp?error=Processing failed");
        } finally {
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
}
