package de.pfann.budgetmanager.server.dataprovider;

import de.pfann.budgetmanager.server.common.model.StandingOrder;
import de.pfann.budgetmanager.server.common.model.Tag;
import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.common.util.HashUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class XMLStandingOrderReader {

    public List<StandingOrder> getStandingOrders(File file) throws ParserConfigurationException, IOException, SAXException {
        List<StandingOrder> standingOrders = new LinkedList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();


        String filenameWithDocTyp = file.getName();

        System.out.println(filenameWithDocTyp);

        Document document = builder.parse( file );

        Node rootNode =document.getFirstChild();
        NodeList nodeList = rootNode.getChildNodes();

        for(int i = 0; i < nodeList.getLength();i++){

            if(nodeList.item(i).getNodeName() == "standingorder"){
                Node node = nodeList.item(i);
                NodeList childNodes = node.getChildNodes();

                StandingOrder standingOrder = new StandingOrder();
                standingOrder.setHash(HashUtil.getUniueHash());
                System.out.println(node.getNodeName());

                for(int x = 0; x < childNodes.getLength(); x++){

                    Node innerNode = childNodes.item(x);
                    if(innerNode.getNodeName() == "amount"){
                        standingOrder.setAmount(innerNode.getTextContent());
                    }

                    if(innerNode.getNodeName() == "memo"){
                        standingOrder.setMemo(innerNode.getTextContent());
                    }

                    if(innerNode.getNodeName() == "rotation_strategy"){
                        standingOrder.setRotation_strategy(innerNode.getTextContent());
                    }

                    if(innerNode.getNodeName() == "start_at"){
                        LocalDateTime startTime = LocalDateTime.parse(innerNode.getTextContent());
                        standingOrder.setStart_at(DateUtil.asDate(startTime));
                    }

                    if(innerNode.getNodeName() == "last_executed"){
                        LocalDateTime lastExecuted = LocalDateTime.parse(innerNode.getTextContent());
                        standingOrder.setLast_executed(DateUtil.asDate(lastExecuted));
                    }

                    if(innerNode.getNodeName() == "end_at"){
                        LocalDateTime endDate = LocalDateTime.parse(innerNode.getTextContent());
                        standingOrder.setEnd_at(DateUtil.asDate(endDate));
                    }

                    if(innerNode.getNodeName() == "tags"){
                        NodeList tagNodeList = innerNode.getChildNodes();
                        List<Tag> tags = new LinkedList<Tag>();

                        for(int t = 0; t < tagNodeList.getLength(); t++){

                            if(tagNodeList.item(t).getNodeName() == "tag"){
                                Tag tag = new Tag(tagNodeList.item(t).getTextContent());
                                tags.add(tag);

                            }
                        }

                        standingOrder.setTags(tags);
                    }
                }


                standingOrders.add(standingOrder);
            }
        }
        return standingOrders;
    }

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        XMLStandingOrderReader standingOrderReader = new XMLStandingOrderReader();
        File file = new File("C:\\Users\\Johannes\\projects\\budgetmanager-server\\dataprovider\\src\\main\\resources\\johannes-1234\\standingorder.xml");
        standingOrderReader.getStandingOrders(file);
    }
}
