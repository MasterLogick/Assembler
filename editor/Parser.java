package editor;

import main.EditorFrame;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import settings.Colors;

import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    static HashMap<String, ParsePattern> parsePatterns = new HashMap<>();
    static HashMap<String, HashMap<String, String>> keyWords = new HashMap<>();

    public static void init() {
        File f = new File("C:\\Assembler\\src\\editor\\key_words.xml");//todo change file path
        addWords(f);
        addPattern("registers", new ParsePattern() {
            @Override
            public Color getColor(String type) {
                return Colors.REGISTER_COLOR;
            }

            @Override
            public boolean isWord(String type) {
                return true;
            }

            @Override
            public String getPattern(String type, String expression) {
                return expression;
            }
        });
        addPattern("commands", new ParsePattern() {
            @Override
            public Color getColor(String type) {
                return Colors.COMMANDS_COLOR;
            }

            @Override
            public boolean isWord(String type) {
                return true;
            }

            @Override
            public String getPattern(String type, String expression) {
                return expression;
            }
        });
        addPattern("utility", new ParsePattern() {
            @Override
            public Color getColor(String type) {
                switch (type) {
                    case "line_comment":
                    case "import":
                        return Colors.LINE_COMMENT_COLOR;
                    case "block_comment":
                        return Colors.BLOCK_COMMENT_COLOR;
                }
                return null;
            }

            @Override
            public boolean isWord(String type) {
                return false;
            }

            @Override
            public String getPattern(String type, String expression) {
                switch (type) {
                    case "line_comment":
                        return "//\\w+\n";
                    case "import":
                        return expression + "\\s\"[\\w]+\"";
                    case "block_comment":
                        return "/[\u002A]*[\u002A]/";
                }
                return null;
            }
        });
        //-------------TEST---------------
        /*
        System.out.println();
        for (Map.Entry<String,ParsePattern> entry:parsePatterns.entrySet()) {
            System.out.println(entry.getKey());
        }
        System.out.println();
        for (Map.Entry<String, HashMap<String, String>> entr:keyWords.entrySet()) {
            System.out.println(entr.getKey());
            System.out.println();
            for (Map.Entry<String,String> entry:entr.getValue().entrySet()) {
                System.out.println(entry.getKey()+" "+entry.getValue());
            }
            System.out.println();
        }*/
    }

    public static void addPattern(String type, ParsePattern pp) {
        parsePatterns.put(type, pp);
    }

    public static void addWords(File f) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        Document doc = null;
        try {
            db = dbf.newDocumentBuilder();
            doc = db.parse(f);
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
        NodeList root = doc.getChildNodes().item(0).getChildNodes();
        for (int j = 0; j < root.getLength(); j++) {
            Node node = root.item(j);
            NodeList words = node.getChildNodes();
            for (int i = 0; i < words.getLength(); i++) {
                Node word = words.item(i);
                if (word.getNodeName().equals("expression")) {
                    if (keyWords.get(node.getNodeName()) == null) {
                        keyWords.put(node.getNodeName(), new HashMap<>());
                    }
                    keyWords.get(node.getNodeName()).put(word.getTextContent(), word.getAttributes().item(0).getTextContent());
                    /*System.out.println(word.getAttributes().item(0).getTextContent());
                    System.out.println(word.getTextContent());*/
                }
            }
        }
    }

    static void parse() {
        //System.out.println("test");
        StyledDocument sd = EditorFrame.getEditor().getStyledDocument();
        SimpleAttributeSet sas = new SimpleAttributeSet();
        StyleConstants.setForeground(sas,Colors.MAIN_FOREGROUND_COLOR);
        sd.setCharacterAttributes(0, sd.getLength(), sas, false);
        for (Map.Entry<String, HashMap<String, String>> entry : keyWords.entrySet()) {
            ParsePattern pp = parsePatterns.get(entry.getKey());
            for (Map.Entry<String, String> value : entry.getValue().entrySet()) {
                parse(pp.getPattern(value.getValue(), value.getKey()), pp.getColor(value.getValue()), pp.isWord(value.getKey()));
            }
        }
        sd.setCharacterAttributes(sd.getLength(), 1, sas, false);
    }

    private static void parse/*Patten*/(String pattern, Color color, boolean flag) {
        StyledDocument sd = EditorFrame.getEditor().getStyledDocument();
        String text = null;
        try {
            text = sd.getText(0, sd.getLength()).toLowerCase();
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        Matcher m = null;
        m = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(text);
        while (m.find()) {
            SimpleAttributeSet sas = new SimpleAttributeSet();
            StyleConstants.setForeground(sas, color);
            /*if (flag) {
                sd.setCharacterAttributes(m.start(), m.end() - m.start(), sas, false);
            } else {*/
                sd.setCharacterAttributes(m.start(), m.end() - m.start(), sas, false);
            /*}*/
        }
    }
}
