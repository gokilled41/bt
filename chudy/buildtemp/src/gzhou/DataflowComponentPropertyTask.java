package gzhou;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.vitria.dataflow.util.DOMUtil;
import com.vitria.dataflow.dmsdk.framework.util.Util;

public class DataflowComponentPropertyTask extends Task {

    private String componentXML_;

    private String moduleId_;
    private String componentType_;

    public void setComponentXML(String componentXML) {
        componentXML_ = componentXML;
    }

    @Override
    public void execute() throws BuildException {

        try {
            String xml = DOMUtil.getStringFromFile(componentXML_);
            Document doc = DOMUtil.getDocument(xml);
            Node root = doc.getDocumentElement();
            init(root);
            Node[] nodes = DOMUtil.getNodes(root, "/component/properties/property");
            for (Node node : nodes) {
                logNode(node);
            }
        } catch (Exception e) {
            throw new BuildException("Cannot execute: dataflow component property task.", e);
        }

    }

    private void init(Node root) throws Exception {
        initModuleId(root);
        initComponentType(root);
    }

    private void initModuleId(Node root) throws Exception {
        String label = DOMUtil.getAttribute(root, "@label");
        moduleId_ = label.substring(0, label.indexOf(' ')).toLowerCase();
    }

    private void initComponentType(Node root) throws Exception {
        String type = DOMUtil.getAttribute(root, "@type");
        componentType_ = type.toLowerCase();
    }

    private void logNode(Node node) throws Exception {
        String name = DOMUtil.getAttribute(node, "@name");
        String label = DOMUtil.getAttribute(node, "@label");
        String description = DOMUtil.getAttribute(node, "@description");
        if (description == null)
            description = DOMUtil.getAttribute(node, "@desc");
        String type = DOMUtil.getAttribute(node, "@type");
        String defaultValue = DOMUtil.getAttribute(node, "@value");
        String optional = DOMUtil.getAttribute(node, "@optional");
        String hidden = DOMUtil.getAttribute(node, "@hidden");
        String constraints = DOMUtil.getAttribute(node, "@constraints");
        String enums = DOMUtil.getAttribute(node, "@enums");

        logOneProperty(name, label, description, type, defaultValue, optional, hidden, constraints, enums);
    }

    private void logOneProperty(String name, String label, String description, String type, String defaultValue,
            String optional, String hidden, String constraints, String enums) throws Exception {
        String msg = "    {0}-{1}-{2}.{3}={4}";
        log(Util.format(msg, moduleId_, componentType_, name, "name", toString(name)));
        log(Util.format(msg, moduleId_, componentType_, name, "label", toString(label)));
        log(Util.format(msg, moduleId_, componentType_, name, "description", toString(description)));
        log(Util.format(msg, moduleId_, componentType_, name, "type", toString(type)));
        log(Util.format(msg, moduleId_, componentType_, name, "default", toString(defaultValue)));
        log(Util.format(msg, moduleId_, componentType_, name, "optional", toYesOrNo(optional)));
        log(Util.format(msg, moduleId_, componentType_, name, "hidden", toYesOrNo(hidden)));
        log(Util.format(msg, moduleId_, componentType_, name, "constraints", toString(constraints)));
        log(Util.format(msg, moduleId_, componentType_, name, "enums", toString(enums)));
        log("");
    }

    private String toString(String s) {
        if (s != null) {
            return s;
        }
        return "";
    }

    private String toYesOrNo(String optional) {
        if (optional != null) {
            if (optional.equals("true"))
                return "Y";
        }
        return "N";
    }

}
