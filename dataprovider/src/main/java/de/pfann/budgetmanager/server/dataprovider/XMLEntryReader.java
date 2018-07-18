package de.pfann.budgetmanager.server.dataprovider;

import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.Entry;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class XMLEntryReader {



    public List<Entry> getEntries(String aPath) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        File file = new File(aPath);
        String filenameWithDocTyp = file.getName();
        String filename = filenameWithDocTyp.substring(0,filenameWithDocTyp.length()-4);
        String filenameDate =  filename.substring(6);
        String[] dateValues = filenameDate.split("-");

        Document document = builder.parse( new File(aPath) );


        String username = file.getAbsoluteFile().getParentFile().getName();
        int month = Integer.valueOf(dateValues[0]);
        int year = Integer.valueOf(dateValues[1]);


        AppUser user = new AppUser();
        user.setName(username);

        Node rootNode =document.getFirstChild();
        System.out.println(rootNode.getNodeName());
        List<Entry> entries = new LinkedList<>();

        NodeList nodeList = rootNode.getChildNodes();

        for(int i = 0; i < nodeList.getLength();i++){

            if(nodeList.item(i).getNodeName() == "entry"){
                Node node = nodeList.item(i);
                Entry entry = new Entry();
                List<Tag> tags = new LinkedList<Tag>();
                NodeList childNodes = node.getChildNodes();

                for(int x = 0; x < childNodes.getLength(); x++){
                    Node innerNode = childNodes.item(x);

                    if(innerNode.getNodeName() == "amount"){
                        entry.setAmount(innerNode.getTextContent());
                    }

                    if(innerNode.getNodeName() == "memo"){
                        entry.setMemo(innerNode.getTextContent());
                    }

                    if(innerNode.getNodeName() == "day"){
                        int day = Integer.valueOf(innerNode.getTextContent());
                        LocalDateTime localDateTime = LocalDateTime.of(year,month,day,0,0,0);
                        entry.setCreated_at(DateUtil.asDate(localDateTime));
                    }

                    if(innerNode.getNodeName() == "tags"){
                        NodeList tagNodeList = innerNode.getChildNodes();

                        for(int t = 0; t < tagNodeList.getLength(); t++){

                            if(tagNodeList.item(t).getNodeName() == "tag"){
                                Tag tag = new Tag(tagNodeList.item(t).getNodeName());
                                tags.add(tag);
                            }
                        }
                    }

                    entry.setHash(HashUtil.getUniueHash());
                    entry.setTags(tags);
                }

                System.out.println(entry.getTags().size());
                entries.add(entry);
            }
        }

        return entries;
    }


    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        XMLEntryReader reader = new XMLEntryReader();
        reader.getEntries("C:\\Users\\Johannes\\projects\\budgetmanager-server\\dataprovider\\src\\main\\resources\\johannes-1234\\entry_07-2018.xml");
    }

}
