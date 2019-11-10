package de.pfann.budgetmanager.server.persistenscouchdb.file;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
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


    public static String readContent(File aFile){
        String content = "";
        try {
            content =  new Scanner(aFile).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return content;
    }


    public static List<File> listDirectories(String aPath) {
        File baseDirectory = new File(aPath);
        List<File> directories = new LinkedList<>();
        for (final File fileEntry : baseDirectory.listFiles()) {
            if (fileEntry.isDirectory()) {
                directories.add(fileEntry);
            }
        }
        return directories;
    }

    public static List<File> listFiles(String aPath) {
        File baseDirectory = new File(aPath);
        List<File> files = new LinkedList<>();
        for (final File fileEntry : baseDirectory.listFiles()) {
            if (fileEntry.isFile()) {
                files.add(fileEntry);
            }
        }
        return files;
    }


}
