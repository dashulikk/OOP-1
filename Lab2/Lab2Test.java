package oop.lab2;

import oop.lab2.model.*;
import oop.lab2.parser.DOMParser;
import oop.lab2.parser.Parser;
import oop.lab2.parser.SAXParser;
import oop.lab2.parser.StAXParser;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Lab2Test {
    private final static String XMLFilePath = "/Users/dashulik/Library/Mobile Documents/com~apple~CloudDocs/KNU/5th semester/OOP/Lab2/XML/periodicals.xml";
    private final static String XSDFilePath = "/Users/dashulik/Library/Mobile Documents/com~apple~CloudDocs/KNU/5th semester/OOP/Lab2/XML/periodicals.xsd";
    private final static List<Paper> expected = new ArrayList<>() {{
        add(new Booklet("d", true, 6, "Дендрологічний парк Софіївка", false));
        add(new Booklet("e", true, 7, "Київ", false));
        add(new Newspaper("a", true, 8, "Трибуна праці", false));
        add(new Newspaper("b", false, 16, "Сільські вісті",true));
        add(new Journal("c", true, 24, "Forbes", false));
        add(new Journal("f", true, 32, "БУЛЬВАР Гордона", true));
    }};

    @Test
    void DOMParser() {
        Parser parser = new DOMParser();
        List<Paper> papers = parser.validateAndParseXML(XMLFilePath, XSDFilePath);
        papers.sort(Comparator.comparingInt(Paper::getSize));
        assertEquals(papers, expected);
    }

    @Test
    void SAXParser() {
        Parser parser = new SAXParser();
        List<Paper> papers = parser.validateAndParseXML(XMLFilePath, XSDFilePath);
        papers.sort(Comparator.comparingInt(Paper::getSize));
        assertEquals(papers, expected);
    }

    @Test
    void StAXParser() {
        Parser parser = new StAXParser();
        List<Paper> papers = parser.validateAndParseXML(XMLFilePath, XSDFilePath);
        papers.sort(Comparator.comparingInt(Paper::getSize));
        assertEquals(papers, expected);
    }
}