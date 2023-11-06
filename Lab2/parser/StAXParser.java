package oop.lab2.parser;

import oop.lab2.model.Booklet;
import oop.lab2.model.Journal;
import oop.lab2.model.Newspaper;
import oop.lab2.model.Paper;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StAXParser implements Parser {
    @Override
    public List<Paper> parseXML(String pathToXMLFile) throws SAXException, ParserConfigurationException, IOException, XMLStreamException {
        List<Paper> papers = new ArrayList<>();

        XMLInputFactory factory = XMLInputFactory.newFactory();
        XMLEventReader reader = factory.createXMLEventReader(new FileInputStream(pathToXMLFile));

        boolean isInsidePaper = false;
        String id = null, title = null, isMulticolor = null, isMonthly = null, type = null;
        int size = 0;

        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();

            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                if (startElement.getName().getLocalPart().equals("paper")) {
                    isInsidePaper = true;
                    id = startElement.getAttributeByName(new QName("id")).getValue();
                    isMulticolor =startElement.getAttributeByName(new QName("multicolor")).getValue();
                    size = Integer.parseInt(startElement.getAttributeByName(new QName("size")).getValue());
                } else if (isInsidePaper) {
                    switch (startElement.getName().getLocalPart()) {
                        case "title" -> title = reader.nextEvent().asCharacters().getData();
                        case "type" -> type = reader.nextEvent().asCharacters().getData();
                        case "monthly" -> isMonthly = reader.nextEvent().asCharacters().getData();
                    }
                }
            } else if (event.isEndElement()) {
                EndElement endElement = event.asEndElement();
                if (endElement.getName().getLocalPart().equals("paper") &&
                        isInsidePaper && (isMonthly != null && !isMonthly.isEmpty()) && (id != null && !id.isEmpty()) &&
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
        return papers;
    }
}
