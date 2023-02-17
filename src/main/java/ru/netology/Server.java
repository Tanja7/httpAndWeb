package ru.netology;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final int port;

    // допустимые файлы
    final List<String> validPaths = List.of("/index.html", "/spring.svg", "/spring.png",
            "/resources.html", "/styles.css", "/app.js", "/links.html", "/forms.html",
            "/classic.html", "/events.html", "/events.js");

    public Server(int port) {
        this.port = port;
    }

    ExecutorService threadPool = Executors.newFixedThreadPool(64);

    public void start() throws IOException {
        // стартуем сервер
        try (final ServerSocket serverSocket = new ServerSocket(port)) {
            //  принимаем подключения
            while (true) {
                try (
                        final var socket = serverSocket.accept();
                        final var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        final var out = new BufferedOutputStream(socket.getOutputStream());
                ) {
                    // обработка одного подключения
                    threadPool.submit(() -> {
                        try {
                            connectionProcessing(in, out);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    });
                    //  connectionProcessing(in, out);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void connectionProcessing(BufferedReader in, BufferedOutputStream out) throws IOException {

        // read only request line for simplicity (читаем только строку запроса для простоты)
        // must be in form (должно быть в форме) GET /path HTTP/1.1
        final var requestLine = in.readLine();
        final var parts = requestLine.split(" ");

        if (parts.length == 3) {
            Request request = new Request(parts[0], parts[1]);
            // файл пути
            final var path = parts[1];
            // если допустимые файлы не содержат указанный
            if (!validPaths.contains(path)) {
                out.write((
                        "HTTP/1.1 404 Not Found\r\n" +
                                "Content-Length: 0\r\n" +
                                "Connection: close\r\n" +
                                "\r\n"
                ).getBytes());
                out.flush();
            } else {
                // путь к файлу
                final Path filePath = Path.of(".", "public", path);
                // если файл распознается какой-либо из реализаций, возвращается тип содержимого.
                final var mimeType = Files.probeContentType(filePath);

                // special case for classic
                if (path.equals("/classic.html")) {
                    final String template = Files.readString(filePath);
                    final byte[] content = template.replace(
                            "{time}",
                            LocalDateTime.now().toString()
                    ).getBytes();
                    out.write((
                            "HTTP/1.1 200 OK\r\n" +
                                    "Content-Type: " + mimeType + "\r\n" +
                                    "Content-Length: " + content.length + "\r\n" +
                                    "Connection: close\r\n" +
                                    "\r\n"
                    ).getBytes());
                    out.write(content);
                    out.flush();

                } else {

                    final long length = Files.size(filePath);
                    out.write((
                            "HTTP/1.1 200 OK\r\n" +
                                    "Content-Type: " + mimeType + "\r\n" +
                                    "Content-Length: " + length + "\r\n" +
                                    "Connection: close\r\n" +
                                    "\r\n"
                    ).getBytes());
                    Files.copy(filePath, out);
                    out.flush();
                }
            }
        }
    }

}













