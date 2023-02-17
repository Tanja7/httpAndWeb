package ru.netology;

import java.io.BufferedOutputStream;

public interface Handler {
     default void handle(Request request, BufferedOutputStream responseStream) {

    }
}
