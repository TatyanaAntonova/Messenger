package demo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class ServerMain {
    public static final int PORT = 4004;
    private static ServerSocket server;
    private static LinkedList<Server> serverList = new LinkedList<>();

    ServerMain() {
        try {
            server = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LinkedList<Server> getServerList() {
        return serverList;
    }
    
    public static void main(String[] args) {
        
        System.out.println("Server starts working!");
        new ServerMain();

        try {
            while (true) {
                Socket socket = server.accept();

                serverList.add(new Server(socket));
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
