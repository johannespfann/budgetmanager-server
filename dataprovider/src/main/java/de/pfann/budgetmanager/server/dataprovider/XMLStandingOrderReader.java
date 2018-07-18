package de.pfann.budgetmanager.server.dataprovider;

import de.pfann.budgetmanager.server.common.model.StandingOrder;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class XMLStandingOrderReader {

    public List<StandingOrder> getStandingOrders(String aPath) throws ParserConfigurationException, IOException, SAXException {
        List<StandingOrder> standingOrders = new LinkedList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        File file = new File(aPath);
        String filenameWithDocTyp = file.getName();

        System.out.println(filenameWithDocTyp);

        Document document = builder.parse( new File(aPath) );


        return standingOrders;
    }

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        XMLStandingOrderReader standingOrderReader = new XMLStandingOrderReader();
        standingOrderReader.getStandingOrders("C:\\Users\\Johannes\\projects\\budgetmanager-server\\dataprovider\\src\\main\\resources\\johannes-1234\\standingorder.xml");
    }
}
