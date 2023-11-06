package oop.lab2.parser;

import oop.lab2.model.Booklet;
import oop.lab2.model.Journal;
import oop.lab2.model.Newspaper;
import oop.lab2.model.Paper;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DOMParser implements Parser {
    @Override
    public List<Paper> parseXML(String pathToXMLFile) throws SAXException, ParserConfigurationException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(pathToXMLFile));

        List<Paper> papers = new ArrayList<>();

        NodeList paperElements = document.getDocumentElement().getElementsByTagName("paper");
        for (int i = 0; i < paperElements.getLength(); i++) {
            Node paperNode = paperElements.item(i);

            NamedNodeMap attributes = paperNode.getAttributes();
            NodeList childNodes = paperNode.getChildNodes();
            Map<String, String> elementValues = new HashMap<>();
            for (int j = 0; j < childNodes.getLength(); j++) {
                Node paperElement = childNodes.item(j);
                elementValues.put(paperElement.getNodeName(), paperElement.getTextContent());
            }

            String id = attributes.getNamedItem("id").getNodeValue();
            boolean isMulticolor = Boolean.parseBoolean(attributes.getNamedItem("multicolor").getNodeValue());
            int size = Integer.parseInt(attributes.getNamedItem("size").getNodeValue());
            String title = elementValues.get("title");
            boolean isMonthly = Boolean.parseBoolean(elementValues.get("monthly"));

            papers.add(switch (elementValues.get("type")) {
                case "newspaper" -> new Newspaper(id, isMulticolor, size, title, isMonthly);
                case "journal" -> new Journal(id, isMulticolor, size, title, isMonthly);
                case "booklet" -> new Booklet(id, isMulticolor, size, title, isMonthly);
                default -> throw new RuntimeException("Invalid type of paper element: " + elementValues.get("type"));
            });
        }

        return papers;
    }
}
