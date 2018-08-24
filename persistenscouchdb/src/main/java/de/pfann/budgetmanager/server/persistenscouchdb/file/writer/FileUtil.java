package de.pfann.budgetmanager.server.persistenscouchdb.file.writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {

    public static void createFile(String aFileName, String aInput){
        File standingOrderFile = new File(aFileName);
        try {
            standingOrderFile.createNewFile();
            FileWriter fileWriter = new FileWriter(standingOrderFile);
            fileWriter.write(aInput);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
