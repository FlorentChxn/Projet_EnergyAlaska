package fr.sorbonne_u.components.hem2026.bases.parser;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;
import java.util.ArrayList;
import java.util.List;

// -----------------------------------------------------------------------------
/**
 * La classe <code>ControlAdapterParserXML</code> est un parseur d'un fichier XML décrivant un adaptateur de contrôle.
 * Il lit le fichier XML et crée un objet <code>ControlAdapterDescriptor</code> qui contient les informations nécessaires pour générer la classe de l'adaptateur de contrôle.
 * 
 * <p><strong>Description</strong></p>
 * 
 * <p>
 * Le parseur extrait les informations sur les imports requis, les variables d'instance et les méthodes de l'adaptateur de contrôle.
 * Pour chaque méthode, il crée un objet <code>MethodeDescriptor</code> qui contient le nom, les modificateurs, le type de retour, les paramètres, les exceptions et le corps de la méthode.
 * </p>
 * 
 * <p>Created on : 2026-10-29</p>
 * 
 * @author	<a href="mailto:florent.chen94@gmail.com">Florent CHEN</a>
 */

public class ControlAdapterParserXML {
    
    // -------------------------------------------------------------------------
    // Constants and variables
    // -------------------------------------------------------------------------
    
    // namespace for the XML files describing control adapters
    public static final String nameSpace = "http://www.sorbonne-universite.fr/alasca/control-adapter";
    // db is the document builder used to parse XML files
    protected DocumentBuilder db;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------
    public ControlAdapterParserXML() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        try {
            this.db = dbf.newDocumentBuilder();
        } catch (Exception e) {
            throw new Exception("Error while creating the document builder", e);
        }
    }

    public ControlAdapterDescriptor parse( String xmlFilePath ) throws Exception {
        if (xmlFilePath == null || xmlFilePath.isEmpty()) throw new IllegalArgumentException("xmlFilePath must not be empty");
        Document doc = null;
        try {
            doc = this.db.parse(xmlFilePath);
            Element root = doc.getDocumentElement();
            if (root == null || !("control-adapter".equals(root.getLocalName()))) throw new Exception("Invalid root element in XML file: " + xmlFilePath);
            
            NodeList consumptions = root.getElementsByTagNameNS(nameSpace, "consumption");
            if (consumptions.getLength() == 0) throw new Exception("No consumption elements found in XML file: " + xmlFilePath);
            Element consumption = (Element) consumptions.item(0); // Par défaut : on ne s'intéresse qu'à la première consommation et donc pas grave si il y en a plusieurs
            
            List<String> importsRequired = new ArrayList<String>();
            List<InstanceVariableDescriptor> variables = new ArrayList<InstanceVariableDescriptor>();
            List<MethodeDescriptor> operations = new ArrayList<MethodeDescriptor>();
            
            for (Element element : listChildren(root)) {
                String name = element.getLocalName();
                if ("required".equals(name)) {
                    importsRequired.add(element.getTextContent());
                } else if ("instance-var".equals(name)) {
                    variables.add(new InstanceVariableDescriptor(element));
                } else if ("internal".equals(name) || isMethode(name)) {
                    operations.add(parseMethode(element));
                }
            }
            return new ControlAdapterDescriptor(
                    root.getAttribute("uid"),
                    root.getAttribute("offered"),
                    Double.valueOf(consumption.getAttribute("min")),
                    Double.parseDouble(consumption.getAttribute("nominal")),
                    Double.valueOf(consumption.getAttribute("max")),
                    importsRequired,
                    variables,
                    operations);
        } catch (Exception e) {
            throw new Exception("Error while parsing the XML file: " + xmlFilePath, e);
        }
    }
    
    // -------------------------------------------------------------------------
    // Methods
    // -------------------------------------------------------------------------
    public static List<Element> listChildren (Element parent) {
        List<Element> result = new ArrayList<Element>();
        NodeList liste = parent.getChildNodes();
        for (int i = 0; i < liste.getLength(); i++) {
            Node node = liste.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) result.add((Element) node);
        }
        return result;
    }

    public static MethodeDescriptor parseMethode (Element element) {
        List<MethodeDescriptor.ParameterDescriptor> parameters = new ArrayList<MethodeDescriptor.ParameterDescriptor>();
        List<String> thrown = new ArrayList<String>();
        Element body = null;
        for (Element child : listChildren(element)) {
            if ("parameter".equals(child.getLocalName())) {
                parameters.add(new MethodeDescriptor.ParameterDescriptor( child.getAttribute("type"), child.getAttribute("name")));
            }
            else if ("thrown".equals(child.getLocalName())) {
                thrown.add(child.getTextContent());
            }
            else if ("body".equals(child.getLocalName())) {
                body = child;
            }
        }
        if ("internal".equals(element.getLocalName())) {
            return new MethodeDescriptor(element.getAttribute("name"), element.getAttribute("modifiers"), element.getAttribute("type"), parameters, thrown,body.getAttribute("equipmentRef"), body.getTextContent());
        }
        return new MethodeDescriptor(element.getLocalName(),null,null, parameters, thrown, body.getAttribute("equipmentRef"), body.getTextContent());
    }

    public static boolean isMethode(String name) {
        return "maxMode".equals(name) || "upMode".equals(name) || "downMode".equals(name) || "currentMode".equals(name) || "setMode".equals(name) || "currentMode".equals(name) || "getModeConsumption".equals(name) || "suspended".equals(name) || "suspend".equals(name) || "resume".equals(name) || "emergency".equals(name);
    }
}
