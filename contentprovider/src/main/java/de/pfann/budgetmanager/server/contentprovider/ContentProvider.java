package de.pfann.budgetmanager.server.contentprovider;

import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.Entry;
import de.pfann.budgetmanager.server.common.model.RotationEntry;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ContentProvider {

    private Document document;
    private File file;

    public ContentProvider(final String aPath){
        file = new File(aPath);
        SAXBuilder builder = new SAXBuilder();

        try {
            document = builder.build(file);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        XMLOutputter fmt = new XMLOutputter();
        try {
            fmt.output(document, System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // https://javabeginners.de/XML/XML-Datei_lesen.php
    }

    public List<RotationEntry> getRotationEntries(){
        return null;
    }

    public List<Entry> getEntries(){
        return null;
    }

    public AppUser getUser(){
        return null;
    }

    private void load(){

    }



}
