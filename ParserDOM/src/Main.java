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
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final String file = "prueba.xml";
    public static void main(String[] args) {
        parser();
    }
    public static void parser() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringComments(true);
        dbf.setIgnoringElementContentWhitespace(true);

        try {
            System.out.println("Parser DOM");
            File inputFile = new File(file);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            muestraNodo(doc);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    public static void muestraNodo(Node nodo) {
        switch (nodo.getNodeType()) {
            case Node.DOCUMENT_NODE:
                System.out.println("Nodo Documento");
                muestraNodoDocumento(nodo);
                break;
            case Node.ELEMENT_NODE:
                System.out.println("Nodo Elemento");
                muestraNodoElemento(nodo);
                break;
            case Node.TEXT_NODE:
                if(nodo.getNodeValue().trim().length() == 0) {
                    return;
                }
                System.out.println("Nodo texto");
                muestraNodoTexto(nodo);
                break;
            default:
                System.out.println("Otro tipo de nodo");
        }
        NodeList hijos = nodo.getChildNodes();
        System.out.println("****************************");
        for (int i=0; i < hijos.getLength(); i++) {
            muestraNodo(hijos.item(i));
        }
    }
    public static void muestraNodoDocumento(Node nodo) {
        System.out.println(nodo.getNodeName());
        System.out.println(file);
    }

    public static void muestraNodoElemento(Node nodo) {
        System.out.println(nodo.getNodeName());
        NamedNodeMap NNN = nodo.getAttributes();
        if (NNN.getLength() > 0) {
            for (int i = 0; i < NNN.getLength();i++) {
                System.out.println(NNN.item(i));
            }
        }
    }

    public static void muestraNodoTexto(Node nodo) {
        System.out.println(nodo.getNodeValue());
    }
}