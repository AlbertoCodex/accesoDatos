import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            doc.setXmlVersion("1.0");
            // Elemento clientes
            Element eClientes = doc.createElement("clientes");
            // Append a doc
            doc.appendChild(eClientes);

            // Elemento cliente
            addCliente(doc, eClientes, "78901234X", "NADALES", "44126", 24);
            addCliente(doc, eClientes, "89012345E", "ROJAS", "44126", -1);
            addCliente(doc, eClientes, "56789012B", "SAMPER", "29730", -1);
            addCliente(doc, eClientes, "12345678A", "Naranjo", "29110", 23, "Alberto", "9", "Alejandro", "10");
            addCliente(doc, eClientes, "87654321Z", "Nunez", "29110", 23, "Ana", "6");
            // Guardar documento XML
            saveXML(doc, "clientes.xml");
            // Mostrar el contenido del archivo
            showContent("clientes.xml");
        } catch (ParserConfigurationException | TransformerException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void addCliente(Document doc, Element eClientes, String dni, String apellidos, String cp, int edad, String... descendientes) {
        Element cliente = doc.createElement("cliente");
        cliente.setAttribute("DNI", dni);
        eClientes.appendChild(cliente);

        Element eApellidos = doc.createElement("apellidos");
        eApellidos.appendChild(doc.createTextNode(apellidos));
        cliente.appendChild(eApellidos);

        Element eCP = doc.createElement("CP");
        eCP.appendChild(doc.createTextNode(cp));
        cliente.appendChild(eCP);

        if (edad != -1) {
            cliente.setAttribute("Edad", Integer.toString(edad));
        }

        // Agregar descendientes
        for (int i = 0; i < descendientes.length; i += 2) {
            String nombre = descendientes[i];
            String edadDescendiente = descendientes[i + 1];

            Element descendiente = doc.createElement("descendiente");
            descendiente.setAttribute("Nombre", nombre);
            descendiente.setAttribute("Edad", edadDescendiente);
            cliente.appendChild(descendiente);
        }
    }
    private static void saveXML (Document doc, String fileName) throws TransformerException, IOException {
        // Creamos instancias
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        // Propiedades del fichero XML
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        // Creamos y escribimos(transformamos) el fichero
        FileOutputStream fos = new FileOutputStream(fileName);
        StreamResult result = new StreamResult(fos);
        transformer.transform(source, result);
        fos.close();
        System.out.println("El documento XML se ha guardado correctamente en " + fileName);
    }
    private static void showContent(String fileName) throws IOException {
        System.out.println("Contenido del archivo " + fileName + ":");
        File file = new File(fileName);
        if (file.exists()) {
            java.nio.file.Files.lines(file.toPath()).forEach(System.out::println);
        } else {
            System.out.println("El archivo no existe.");
        }
    }
}