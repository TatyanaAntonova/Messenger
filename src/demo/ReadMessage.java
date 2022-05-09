package demo;

import java.io.BufferedReader;
import java.io.IOException;

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
