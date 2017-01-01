package gzhou;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.vitria.xquery.XQueryLib;

public class ArraySupportTest {

    public static void main(String[] args) throws Exception {
        String newitems = "C:\\workspace\\buildtemp\\src\\xml\\Lib1.xml";
        InputStream in = new FileInputStream(newitems);
        byte[] bytes = new byte[in.available()];
        in.read(bytes);
        in.close();
        Document doc = XQueryLib.parseXML(bytes);

        System.out.println(XQueryLib.unparsePrettyXML(doc.getDocumentElement()));

        Document[] newdocs = new Document[4];
        for (int i = 0; i < newdocs.length; i++) {
            String file = "C:\\workspace\\buildtemp\\src\\xml\\newitems" + (5 - (i + 1)) + ".xml";
            InputStream in2 = new FileInputStream(file);
            byte[] bytes2 = new byte[in2.available()];
            in2.read(bytes2);
            in2.close();
            Document doc2 = XQueryLib.parseXML(bytes2);
            newdocs[i] = doc2;
        }

        // remove old nodes
        Node[] nodes = XQueryLib.getFromXPath(doc.getDocumentElement(), "items/item");
        Node parent = (Node) XQueryLib.getFirstFromXPath(doc.getDocumentElement(), getParentPath("items/item"));
        for (int i = 0; i < nodes.length; i++) {
            parent.removeChild(nodes[i]);
        }

        // add new nodes
        for (int i = 0; i < newdocs.length; i++) {
            Node importedNode = doc.importNode(newdocs[i].getDocumentElement(), true);
            parent.appendChild(importedNode);
        }

        System.out.println(XQueryLib.unparsePrettyXML(doc.getDocumentElement()));

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        db.newDocument();

        nodes = XQueryLib.getFromXPath(doc.getDocumentElement(), "items/item");

        Document[] docs = new Document[nodes.length];
        for (int i = 0; i < docs.length; i++) {
            Document d = db.newDocument();
            Node node = nodes[i];
            d.appendChild(d.importNode(node, true));
            docs[i] = d;
        }

        for (int i = 0; i < docs.length; i++) {
            System.out.println();
            System.out.println(XQueryLib.unparsePrettyXML(docs[i]));
        }
    }

    private static String getParentPath(String path) {
        if (path.indexOf("/") == -1)
            return ".";
        int index = path.lastIndexOf("/");
        return path.substring(0, index);
    }
}