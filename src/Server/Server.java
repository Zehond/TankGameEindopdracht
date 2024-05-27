package Server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private Match match;
    private final int MILISECONDS_PER_FRAME =  1000000000 / 20;

    public Server() throws IOException {
        serverSocket = new ServerSocket(1234);
        System.out.println("SERVER STARTED");
    }

    public void start() throws IOException, ClassNotFoundException {
        long startTime = System.nanoTime();
        long currentTime = System.nanoTime();
        while (true){
            Socket player1 = serverSocket.accept();
            Socket player2 = serverSocket.accept();

            match = new Match(player1, player2);

            while (true) {
//                long whenShouldNextTickRun = startTime + MILISECONDS_PER_FRAME;
//                if (currentTime < whenShouldNextTickRun) {
//                    long sleepTime = (whenShouldNextTickRun - currentTime) / 1000000;  // Convert to milliseconds
//                    if (sleepTime > 0) {
//                        try {
//                            Thread.sleep(sleepTime);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    continue;
//                }
                try {
                    System.out.println("match.update()");
                    match.update();
                }catch (IOException | ClassNotFoundException e){
                    e.printStackTrace();
                    continue;
                }
//                startTime = System.nanoTime();
            }
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.start();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
