package de.pfann.budgetmanager.server.dataprovider;

import de.pfann.budgetmanager.server.common.model.AppUser;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XMLUserReader {

    public AppUser getUser(String aPath) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        File file = new File(aPath);
        String filenameWithDocTyp = file.getName();

        System.out.println(filenameWithDocTyp);

        Document document = builder.parse( new File(aPath) );

        AppUser appUser = new AppUser();

        return appUser;
    }

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        XMLUserReader userReader = new XMLUserReader();
        userReader.getUser("C:\\Users\\Johannes\\projects\\budgetmanager-server\\dataprovider\\src\\main\\resources\\johannes-1234\\user.xml");
    }
}
