/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import clientapp.controllers.BoardController;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class MatchRecorder {

    FileWriter recordWriter;

    public MatchRecorder(String Player1NAme, String Player2NAme, boolean turn) {
        String Time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH_mm"));
        String directoryPath = "../../Recordings";
        String filePath = directoryPath + "/" + Player1NAme + " VS " + Player2NAme + "_" + Time + ".txt";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Recorings dir created");
            }
        }
            File file = new File(filePath);
            System.out.println("Absolute Path: " + file.getAbsolutePath());
            try {
                file.createNewFile();
                recordWriter = new FileWriter(file);
                recordWriter.write(Player1NAme + "\n" + Player2NAme + "\n" + (turn ? "1" : "0") + "\n");
            } catch (IOException ex) {
                System.out.println("error in File creation" + ex.getMessage());
            }
        }
    

    protected void recordPlay(int position) {
        try {
            recordWriter.write(String.valueOf(position));
        } catch (IOException ex) {
            System.out.println("error writing in a file");
        }
    }

    public void closeRecordFile() {
        try {
            recordWriter.close();
        } catch (IOException e) {
            System.out.println("Failed to close Recordwriter ");
        }

    }

    public static void readRecordFile(File file) {

        try (BufferedReader reader = new BufferedReader(new FileReader(file.getCanonicalPath()))) {

            String line;
            BoardController.gameReplay = new ArrayList<>();
            BoardController.gameReplay.add(reader.readLine());
            BoardController.gameReplay.add(reader.readLine());
            BoardController.gameReplay.add(reader.readLine());
            line = reader.readLine();

            for (char c : line.toCharArray()) {
                BoardController.gameReplay.add(String.valueOf(c));
            }

        } catch (FileNotFoundException ex) {

            System.out.println("error reading the file");
        } catch (IOException ex) {
            System.out.println("error in IO of the file");
        }

    }

}
