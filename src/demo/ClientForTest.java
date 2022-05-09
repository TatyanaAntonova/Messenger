package demo;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ClientForTest {
    private static final int PORT = 4004;
    private String name;
    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;

    public ClientForTest(String name) {
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

        new ClientForTest(scanner.nextLine());

        System.out.println("Write your message!");
    }

    public class ReadMessage extends Thread {
        private BufferedReader reader;
        private String communicatorName;

        public ReadMessage(BufferedReader reader, String communicatorName) {
            this.reader = reader;
            this.communicatorName = communicatorName;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String message = reader.readLine();
                    System.out.println(message);
                }
            } catch (IOException e) {
                System.out.println(communicatorName + " said bye!");
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class SendMessage extends Thread {
        private BufferedWriter writer;
        private Scanner scanner = new Scanner(System.in);
        private String communicatorName;

        public SendMessage(BufferedWriter writer, String communicatorName) {
            this.writer = writer;
            this.communicatorName = communicatorName;
        }

        private String getCurrentDateTime() {
            Date dateNow = new Date();
            SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd hh:mm a");
            return formatForDateNow.format(dateNow);
        }

        private void write(String message) {
            try {
                writer.write(message);
                writer.newLine();
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            String message;

            while (true) {
                message = scanner.nextLine();

                if (message.equals("exit")) break;

                write(getCurrentDateTime() + " " + communicatorName + ": " + message);
            }

            write(getCurrentDateTime() + " " + communicatorName + " disconnected!");
            write(message);

            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
