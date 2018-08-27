package de.pfann.budgetmanager.server.persistenscouchdb.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

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

    public static String convertToString(File aFile){
        String content = "";
        try {
            content =  new Scanner(aFile).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return content;
    }


}
