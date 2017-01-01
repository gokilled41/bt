package gzhou;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.vitria.xquery.XQueryLib;

public class AppendXMLNodeTask extends Task {

    protected File src_ = null;
    protected File dest_ = null;
    protected String xpath_ = null;
    protected String type_ = "xml";
    protected String value_ = null;
    protected boolean allnodes_ = false;
    protected boolean replace_ = false;
    protected boolean noheader_ = false;

    public void setSrc(File src) {
        src_ = src;
    }

    public void setDest(File dest) {
        dest_ = dest;
    }

    public void setXpath(String xpath) {
        xpath_ = xpath;
    }

    public void setType(String type) {
        type_ = type;
    }

    public void setValue(String value) {
        value_ = value;
    }

    public void setAllnodes(boolean allnodes) {
        allnodes_ = allnodes;
    }

    public void setReplace(boolean replace) {
        replace_ = replace;
    }

    public void setNoheader(boolean noheader) {
        noheader_ = noheader;
    }

    @Override
    public void execute() throws BuildException {
        try {
            Document destDoc = XQueryLib.parseXML(new InputSource(new FileInputStream(dest_)), new Hashtable());

            Node[] parents = (Node[]) XQueryLib.getFromXPath(destDoc.getDocumentElement(), xpath_);

            if (allnodes_) {
                for (int i = 0; i < parents.length; i++) {
                    append(destDoc, parents[i]);
                }
            } else {
                append(destDoc, parents[0]);
            }

            String appendedDocString = XQueryLib.unparsePrettyXML(destDoc);

            FileOutputStream out = new FileOutputStream(dest_, false);
            out.write(getBytes(appendedDocString));
            out.close();

            log("handle file " + dest_);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private byte[] getBytes(String doc) {
        try {
            if (noheader_) {
                return doc.getBytes("UTF-8");
            } else {
                return XQueryLib.convertXMLStringToBytes("UTF-8", false, null, doc);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void append(Document destDoc, Node parent) {
        if (replace_) {
            Node node;
            while ((node = parent.getFirstChild()) != null) {
                parent.removeChild(node);
            }
        }

        parent.appendChild(getImportNode(destDoc));
    }

    private Node getImportNode(Document destDoc) {
        String content = getValue();
        Node importNode = null;
        if (type_.equals("xml")) {
            Document srcDoc = XQueryLib.parseXML(content);
            importNode = destDoc.importNode(srcDoc.getDocumentElement(), true);
        } else if (type_.equals("text")) {
            importNode = destDoc.createTextNode(content);
        } else if (type_.equals("cdata")) {
            importNode = destDoc.createCDATASection(content);
        }
        return importNode;
    }

    private String getValue() {
        if (src_ != null) {
            try {
                InputStream in = new FileInputStream(src_);
                byte[] bytes = new byte[in.available()];
                in.read(bytes);
                String textValue = new String(bytes);
                in.close();
                return textValue;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (value_ != null)
            return value_;
        return null;
    }
}
