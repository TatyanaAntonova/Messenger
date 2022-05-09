package demo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

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
