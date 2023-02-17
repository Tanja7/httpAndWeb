package ru.netology;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int port = 9999;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(64);

    public void start() {
        // стартуем сервер
        try (var serverSocket = new ServerSocket(port)) {
            //  принимаем подключения
            while (true) {
                // обработка одного подключения
                var socket = serverSocket.accept();
                var thread = new Handler(socket);
                threadPool.submit(thread);
    }
            } catch(IOException e){
                e.getMessage();
            }
        }
    }
