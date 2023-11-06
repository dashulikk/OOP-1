package oop.lab2.parser;

import oop.lab2.model.Paper;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.List;

public interface Parser {
    default boolean isValidXML(String pathToXMLFile, String pathToXSDFile) {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File(pathToXSDFile));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(pathToXMLFile)));
        } catch (SAXException | IOException e) {
            System.out.println("Exception: " + e.getMessage());
            return false;
        }
        return true;
    }

    List<Paper> parseXML(String pathToXMLFile) throws SAXException, ParserConfigurationException, IOException, XMLStreamException;

    default List<Paper> validateAndParseXML(String pathToXMLFile, String pathToXSDFile) {
        if (isValidXML(pathToXMLFile, pathToXSDFile)) {
            try {
                return parseXML(pathToXMLFile);
            } catch (SAXException | ParserConfigurationException | IOException | XMLStreamException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.printf("XML file '%s' is invalid according to schema '%s'\n", pathToXMLFile, pathToXSDFile);
            return null;
        }
    }
}
