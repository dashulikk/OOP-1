package oop.lab2.parser;

import oop.lab2.model.Booklet;
import oop.lab2.model.Journal;
import oop.lab2.model.Newspaper;
import oop.lab2.model.Paper;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SAXParser implements Parser {
    private static List<Paper> papers;

    @Override
    public List<Paper> parseXML(String pathToXMLFile) throws SAXException, ParserConfigurationException, IOException {
        papers = new ArrayList<>();

        SAXParserFactory factory = SAXParserFactory.newInstance();
        javax.xml.parsers.SAXParser parser = factory.newSAXParser();

        XMLHandler handler = new XMLHandler();
        parser.parse(new File(pathToXMLFile), handler);

        return papers;
    }

    private static class XMLHandler extends DefaultHandler {
        boolean isInsidePaper;
        String id, title, isMulticolor, isMonthly, type, previousElementName;
        int size;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equals("paper")) {
                id = attributes.getValue("id");
                isMulticolor = attributes.getValue("multicolor");
                size = Integer.parseInt(attributes.getValue("size"));
                isInsidePaper = true;
            }
            previousElementName = qName;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String information = new String(ch, start, length);

            information = information.replace("\n", "").trim();

            if (!information.isEmpty() && isInsidePaper)
                switch (previousElementName) {
                    case "title" -> title = information;
                    case "monthly" -> isMonthly = information;
                    case "type" -> type = information;
                }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if ( isInsidePaper && (isMonthly != null && !isMonthly.isEmpty()) && (id != null && !id.isEmpty()) &&
                    (title != null && !title.isEmpty() && (isMulticolor != null && !isMulticolor.isEmpty())) &&
                    (type != null && !type.isEmpty()) && size != 0) {
                boolean multicolor = Boolean.parseBoolean(isMulticolor);
                boolean monthly = Boolean.parseBoolean(isMonthly);
                papers.add(
                        switch (type) {
                            case "newspaper" -> new Newspaper(id, multicolor, size, title, monthly);
                            case "journal" -> new Journal(id, multicolor, size, title, monthly);
                            case "booklet" -> new Booklet(id, multicolor, size, title, monthly);
                            default -> throw new SAXException("Unknown paper type: " + type);
                });
                isInsidePaper = false;
                id = null;
                title = null;
                isMulticolor = null;
                isMonthly = null;
                type = null;
                size = 0;
            }
        }
    }
}
