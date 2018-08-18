package de.pfann.budgetmanager.server.dataprovider;

import de.pfann.budgetmanager.server.common.model.AppUser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XMLUserReader {

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        XMLUserReader userReader = new XMLUserReader();
        File file = new File("C:\\Users\\Johannes\\projects\\budgetmanager-server\\dataprovider\\src\\main\\resources\\johannes-1234\\user.xml");
        userReader.getUser(file, "johannes-1234");
    }

    public AppUser getUser(File aFile, String aName){
        System.out.println("XMLReader: " + aFile.getName());
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = null;
        try {
            document = builder.parse(aFile);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AppUser appUser = new AppUser();
        Node rootNode = document.getFirstChild();
        NodeList nodeList = rootNode.getChildNodes();


        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            System.out.println(node.getNodeName());

            if (node.getNodeName() == "password") {
                appUser.setPassword(node.getTextContent());
            }

            if (node.getNodeName() == "encryptionText") {
                appUser.setEncryptionText(node.getTextContent());
            }

            if (node.getNodeName() == "emails") {
                NodeList tagNodeList = node.getChildNodes();
                for (int t = 0; t < tagNodeList.getLength(); t++) {
                    if (tagNodeList.item(t).getNodeName() == "email") {
                        appUser.setEmail(tagNodeList.item(t).getTextContent());
                    }
                }
            }
        }
        return appUser;
    }
}
