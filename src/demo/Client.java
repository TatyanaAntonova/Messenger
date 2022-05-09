package demo;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final int PORT = 4004;
    private String name;
    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;

    public Client(String name) {
        this.name = name;

        try {
            socket = new Socket("localhost", PORT);
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        new SendMessage(writer, name).start();
        new ReadMessage(reader, name).start();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to chat! Write your name, please!");

        new Client(scanner.nextLine());

        System.out.println("Write your message!");
    }
}
