package de.pfann.budgetmanager.server.persistenscouchdb.file;

import java.io.*;
import java.util.Scanner;

public class FileUtil {

    public static void createDirectory(String aPath) {
        File directory =  new File(aPath);
        directory.mkdirs();
    }


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

    public static void readFile(String aFileName){
        File standingOrderFile = new File(aFileName);
        try {
            standingOrderFile.createNewFile();
            FileReader fileReader = new FileReader(standingOrderFile);
            fileReader.read();
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
