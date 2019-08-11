package de.pfann.budgetmanager.server.persistenscouchdb.util;

import de.pfann.budgetmanager.server.persistenscouchdb.file.FileUtil;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileTest {

    @Test
    public void test() {

        //FileUtil.createFile("test.txt","hallo");

        File standingOrderFile = new File("C:\\Users\\jopf8\\projects\\budgetmanager-server\\persistenscouchdb\\test.txt");
        try {
            standingOrderFile.createNewFile();
            FileReader fileReader = new FileReader(standingOrderFile);
            BufferedReader br = new BufferedReader(fileReader);

            String zeile = "";

            while( (zeile = br.readLine()) != null )
            {
                System.out.println(zeile);
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
