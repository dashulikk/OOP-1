package oop.lab2;

import oop.lab2.model.Paper;
import oop.lab2.parser.DOMParser;
import oop.lab2.parser.Parser;
import oop.lab2.parser.SAXParser;
import oop.lab2.parser.StAXParser;

import java.util.Comparator;
import java.util.List;

public class Lab2 {
    private final static String XMLFilePath = "/Users/dashulik/Library/Mobile Documents/com~apple~CloudDocs/KNU/5th semester/OOP/Lab2/XML/periodicals.xml";
    private final static String XSDFilePath = "/Users/dashulik/Library/Mobile Documents/com~apple~CloudDocs/KNU/5th semester/OOP/Lab2/XML/periodicals.xsd";

    public static void main(String[] args) {
        Parser parser = new SAXParser();
        List<Paper> papers = parser.validateAndParseXML(XMLFilePath, XSDFilePath);
        papers.sort(Comparator.comparingInt(Paper::getSize));
        papers.forEach(System.out::println);
    }


}
