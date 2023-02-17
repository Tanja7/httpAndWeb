package ru.netology;

public class Main {

  public static void main(String[] args) {
    Server server = new Server();
    server.start();

//    // добавление хендлеров (обработчиков)
//      server.addHandler("GET", "/messages", new Handler() {
//        public void handle(Request request, BufferedOutputStream responseStream) {
//          // TODO: handlers code
//        }
//      });
//      server.addHandler("POST", "/messages", new Handler() {
//        public void handle(Request request, BufferedOutputStream responseStream) {
//          // TODO: handlers code
//        }
//      });


  }
}
//В итоге на запрос типа GET на путь "/messages" будет вызван первый обработчик,
// а на запрос типа POST и путь "/messages" будет вызван второй.
//
//Как вы видите, Handler — функциональный интерфейс всего с одним методом.
// Он может быть заменён на lambda.
//
//Request — это класс, который проектируете вы сами. Для нас важно, чтобы он содержал:
//
//метод запроса, потому что на разные методы можно назначить один и тот же хендлер;
//заголовки запроса;
//тело запроса, если есть.
//BufferedOutputStream берётся путём заворачивания OutputStream  socket:
// new BufferedOutputStream(socket.getOutputStream()).

