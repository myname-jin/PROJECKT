/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

/**
 *
 * @author adsd3
 */
import java.io.*;
import java.net.Socket;
import java.util.*;

public class UserFileWatcher extends Thread {
    private final String filePath;
    private final Socket socket;
    private final BufferedWriter out;
    private final List<String> knownLines = new ArrayList<>();

    public UserFileWatcher(String filePath, Socket socket) throws IOException {
        this.filePath = filePath;
        this.socket = socket;
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        initKnownLines();
    }

    private void initKnownLines() throws IOException {
        File file = new File(filePath);
        if (!file.exists()) file.createNewFile();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                knownLines.add(line);
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(2000);
                List<String> currentLines = new ArrayList<>();
                try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        currentLines.add(line);
                    }
                }

                for (String cur : currentLines) {
                    if (!knownLines.contains(cur)) {
                        System.out.println("🆕 감지된 회원가입: " + cur);
                        out.write("REGISTER:" + cur);
                        out.newLine();
                        out.flush();
                        knownLines.add(cur);
                    }
                }
            } catch (Exception e) {
                System.out.println("❌ 감시 중단: " + e.getMessage());
                break;
            }
        }
    }
}