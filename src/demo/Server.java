package demo;

import java.io.*;
import java.net.Socket;

public class Server extends Thread {
    Socket socket;
    BufferedReader reader;
    BufferedWriter writer;

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
                ServerMain.serverList.remove(this);
                break;
            }

            System.out.println(message);

            for (Server server : ServerMain.serverList) {
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

