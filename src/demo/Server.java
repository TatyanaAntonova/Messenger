package demo;

import java.io.*;
import java.net.Socket;

public class Server extends Thread {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    Server(Socket socket) {
        this.socket = socket;

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        start();
    }

    @Override
    public void run() {
        while (true) {
            String message = null;
            try {
                message = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (message.equals("exit")) {
                ServerMain.getServerList().remove(this);
                break;
            }

            System.out.println(message);

            for (Server server : ServerMain.getServerList()) {
                server.send(message);
            }
        }
    }

    private void send(String msg) {
        try {
            writer.write(msg);
            writer.newLine();
            writer.flush();
        } catch (IOException ignored) {
        }
    }
}

