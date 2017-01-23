package xtj;

import java.io.ByteArrayInputStream;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

public class DOMWalker {

    public DOMWalker() {
    }

    public Object walk(Node starting, DOMResponder responder) {
        if (starting.getNodeType() != Node.ELEMENT_NODE && starting.getNodeType() != Node.DOCUMENT_NODE) {
            // trigger initialization with element node
            Element resultElement = getContainerElement();
            Node cloneNode = resultElement.getOwnerDocument().importNode(starting, true);
            resultElement.appendChild(cloneNode);

            visit(resultElement, responder);
            Object result = responder.getResult(true);
            resultElement.removeChild(cloneNode);
            return result;
        } else {
            visit(starting, responder);
            return responder.getResult(false);
        }
    }

    private void visit(Node node, DOMResponder responder) {
        boolean proceed;
        switch (node.getNodeType()) {
        //case Node.ATTRIBUTE_NODE:
        case Node.CDATA_SECTION_NODE:
            responder.cdataSection((CDATASection) node);
            break;
        case Node.TEXT_NODE:
            responder.text((Text) node);
            break;
        case Node.COMMENT_NODE:
            responder.comment((Comment) node);
            break;
        case Node.DOCUMENT_NODE:
            proceed = responder.startDocument((Document) node);
            if (!proceed)
                break;
            visit(((Document) node).getDocumentElement(), responder);

            break;
        case Node.ELEMENT_NODE:
            proceed = responder.enterElement((Element) node);
            if (!proceed)
                break;
            visitAttributes(node.getAttributes(), responder);
            visitChildren(node.getChildNodes(), responder);
            responder.leaveElement((Element) node);
            break;
        case Node.ENTITY_REFERENCE_NODE:
            EntityReference entityReference = (EntityReference) node;
            Node entity = entityReference.getChildNodes().item(0);
            visit(entity, responder);
            break;
        case Node.PROCESSING_INSTRUCTION_NODE:
            responder.processingInstruction((ProcessingInstruction) node);
            break;
        default:
            break;
        }
    }

    private void visitAttributes(NamedNodeMap attributes, DOMResponder responder) {
        List<Attr> nsAttrs = new LinkedList<Attr>();
        List<Attr> normalAttrs = new LinkedList<Attr>();

        for (int i = 0; i < attributes.getLength(); i++) {
            Attr attr = (Attr) attributes.item(i);
            String attrName = attr.getName();
            if (attrName.startsWith("xmlns")) {
                nsAttrs.add(attr);
            } else {
                normalAttrs.add(attr);
            }
            //responder.attribute(attr);
        }

        // namespace first
        for (Attr attr : nsAttrs) {
            responder.attribute(attr);
        }
        for (Attr attr : normalAttrs) {
            responder.attribute(attr);
        }
    }

    private void visitChildren(NodeList children, DOMResponder responder) {
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            visit(child, responder);
        }
    }

    private static final String containerXML = "<result></result>";
    private static Element resultElement;

    private static Element getContainerElement() {
        if (resultElement == null) {
            try {
                DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder;

                docBuilder = docBuilderFactory.newDocumentBuilder();

                ByteArrayInputStream input = new ByteArrayInputStream(containerXML.getBytes("UTF-8"));
                Document resultDoc = docBuilder.parse(input);

                resultElement = resultDoc.getDocumentElement();
            } catch (Exception exp) {
                throw new RuntimeException(exp);
            }
        }
        return resultElement;
    }

    public static String nodeType(int nodeType) {
        switch (nodeType) {
        case Node.ELEMENT_NODE:
            return "element";
        case Node.ATTRIBUTE_NODE:
            return "attribute";
        case Node.TEXT_NODE:
            return "text";
        case Node.CDATA_SECTION_NODE:
            return "cdata";
        case Node.ENTITY_REFERENCE_NODE:
            return "entityReference";
        case Node.ENTITY_NODE:
            return "entity";
        case Node.PROCESSING_INSTRUCTION_NODE:
            return "processingInstruction";
        case Node.COMMENT_NODE:
            return "comment";
        case Node.DOCUMENT_NODE:
            return "document";
        case Node.DOCUMENT_TYPE_NODE:
            return "documentType";
        case Node.DOCUMENT_FRAGMENT_NODE:
            return "documentFragment";
        case Node.NOTATION_NODE:
            return "notation";
        default:
            return "unknown";
        }
    }
}
