package xtj;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.xml.XMLConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

import xtj.MapOptions.RetainNamespacePrefix;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JSONBuilder implements DOMResponder {

    private MapOptions mapper = null;

    private JsonNode result = null;

    private JsonNodeFactory jsonNodeFactory = null;
    private Stack<StackItem> stack = new Stack<StackItem>();

    private NSManager nsManager = null;

    private static final Log logger = LogFactory.getLog(JSONBuilder.class);

    private String stackContent() {
        StringBuilder info;
        if (stack == null) {
            info = new StringBuilder("Stack[]=>null");
        } else {
            info = new StringBuilder("Stack[" + stack.size() + "]=>\n");
            for (StackItem item : stack) {
                info.append("    ").append(item.parent.toString()).append("\n");
            }
        }
        return info.toString();
    }

    private void debugBefore(String title, Node currentNode) {
        if (!logger.isDebugEnabled()) {
            return;
        }

        String info = "Before processing " + title + (title.isEmpty() ? "" : " ") + currentNode.getNodeName() + "\n"
                + stackContent();
        logger.debug(info);
    }

    private void debugAfter(String title, Node currentNode) {
        if (!logger.isDebugEnabled()) {
            return;
        }

        String info = "After processing " + title + (title.isEmpty() ? "" : " ") + currentNode.getNodeName() + "\n"
                + stackContent();
        logger.debug(info);
    }

    public JSONBuilder(MapOptions mapper) {
        this.mapper = mapper;

        nsManager = new NSManager(mapper.retainNamespaces(), mapper.retainPrefixes());
        jsonNodeFactory = JsonNodeFactory.withExactBigDecimals(mapper.bigDecimalExact());
    }

    public JsonNode getResult(boolean stripOuter) {
        if (stripOuter) {
            if (!mapper.keepRoot()) {
                return result; //already stripped
            }
            if (result.isObject()) {
                if (result.size() != 1) {
                    // bug?
                    return null;
                }
                return result.iterator().next();
            } else {
                // bug?
                return null;
            }
        } else {
            return result;
        }
    }

    @Override
    public boolean startDocument(Document document) {
        debugBefore("enter", document);
        debugAfter("enter", document);
        return true;
    }

    @Override
    public void endDocument(Document document) {
        debugBefore("leave", document);
        debugAfter("leave", document);
    }

    @Override
    public boolean enterElement(Element element) {
        debugBefore("enter", element);

        nsManager.pushContext();

        JsonNode node = jsonNodeFactory.objectNode();

        StackItem item = new StackItem(node);
        stack.push(item);

        debugAfter("enter", element);
        return true;
    }

    @Override
    public void leaveElement(Element element) {
        debugBefore("leave", element);

        String nodeName = mapElementName(element);
        StackItem top = stack.pop();

        if (mapper.groupAttributes()) {
            if (!top.attributes.isEmpty()) {
                ObjectNode attributesNode = jsonNodeFactory.objectNode();
                for (StringPair pair : top.attributes) {
                    attributesNode.put(pair.first, pair.second);
                }

                String attributesName = mapper.attributeGroupName();
                ((ObjectNode) top.parent).put(attributesName, attributesNode);

                top.attributes.clear();
            }
        }

        // add namespaces
        Map<String, String> nsmap = nsManager.currentContext();
        if (!nsmap.isEmpty()) {
            ObjectNode nsParent = (ObjectNode) top.parent;
            if (mapper.groupNamespaces()) {
                ObjectNode nsNode = jsonNodeFactory.objectNode();
                String nsNodeName = mapper.namespaceGroupName();
                nsParent.put(nsNodeName, nsNode);
                nsParent = nsNode;
            }
            for (String prefix : nsmap.keySet()) {
                String ns = nsmap.get(prefix);
                nsParent.put(mapper.namespacePrefix() + prefix, ns);
            }
        }

        // process children
        List<NamedJsonNode> tempNodes = new LinkedList<NamedJsonNode>();
        List<JsonNode> sameNameItems = new LinkedList<JsonNode>();
        String sameName = null;

        for (int i = 0; i < top.children.size(); i++) {
            NamedJsonNode namedNode = top.children.get(i);
            String name = namedNode.name;
            JsonNode node = namedNode.jsonNode;

            if (name.equals(sameName)) {
                sameNameItems.add(node);
            } else {
                int size = sameNameItems.size();
                if (size == 0) {
                    // this is first node, no need to construct array
                } else if (size == 1) {
                    // can only be added as object node
                    //((ObjectNode)top.parent).set(sameName, sameNameItems.get(0));
                    tempNodes.add(new NamedJsonNode(sameName, sameNameItems.get(0)));
                } else {
                    // can be collapsed to an array
                    ArrayNode arrayNode = jsonNodeFactory.arrayNode();
                    for (JsonNode inner : sameNameItems) {
                        arrayNode.add(inner);
                    }
                    //((ObjectNode)top.parent).set(sameName, arrayNode);
                    tempNodes.add(new NamedJsonNode(sameName, arrayNode));
                }
                sameNameItems.clear();

                sameName = name;
                sameNameItems.add(node);
            }
        }

        if (!sameNameItems.isEmpty()) {
            // process last items
            if (sameNameItems.size() == 1) {
                // can only be added as object node
                //((ObjectNode)top.parent).set(sameName, sameNameItems.get(0));
                tempNodes.add(new NamedJsonNode(sameName, sameNameItems.get(0)));
            } else {
                // can be collapsed to an array
                ArrayNode arrayNode = jsonNodeFactory.arrayNode();
                for (JsonNode inner : sameNameItems) {
                    arrayNode.add(inner);
                }
                //((ObjectNode)top.parent).set(sameName, arrayNode);
                tempNodes.add(new NamedJsonNode(sameName, arrayNode));
            }
        }

        // process temp nodes, this maybe add another array layer when encountering discrete same name node
        if (needArrayLayerAndProcessRange(tempNodes)) {
            ArrayNode arrayLayer = jsonNodeFactory.arrayNode();
            for (int i = 0; i < tempNodes.size(); i++) {
                NamedJsonNode item = tempNodes.get(i);
                ObjectNode arrayItem = jsonNodeFactory.objectNode();
                arrayItem.put(item.name, item.jsonNode);
                arrayLayer.add(arrayItem);
            }
            String contentName = mapper.elementsGroupName();
            ((ObjectNode) top.parent).set(contentName, arrayLayer);
        } else {
            for (int i = 0; i < tempNodes.size(); i++) {
                NamedJsonNode item = tempNodes.get(i);
                ((ObjectNode) top.parent).set(item.name, item.jsonNode);
            }
        }

        // add this node to parent
        StackItem parent = null;
        if (stack.isEmpty()) {
            // get to top level

            // collapse single text() node to a JSON string
            if (top.parent.size() == 1 && top.parent.get(mapper.textName()) != null) {
                top.parent = top.parent.get(mapper.textName());
            }

            if (!mapper.keepRoot()) {
                result = top.parent;
            } else {
                ObjectNode rootNode = jsonNodeFactory.objectNode();
                rootNode.put(nodeName, top.parent);
                result = rootNode;
            }

        } else {
            parent = stack.peek();

            JsonNode thisNode = top.parent;
            if (!mapper.stringContentAsNode()) {
                // collapse single text() node to a JSON string
                if (thisNode.size() == 1 && thisNode.get(mapper.textName()) != null) {
                    thisNode = thisNode.get(mapper.textName());
                }
            }
            parent.appendChild(nodeName, thisNode);
        }

        nsManager.popContext();

        debugAfter("leave", element);
    }

    private boolean needArrayLayerAndProcessRange(List<NamedJsonNode> nodes) {
        Map<String, Integer> checker = new HashMap<String, Integer>();

        boolean containJump = false;
        for (int i = 0; i < nodes.size(); i++) {
            NamedJsonNode node = nodes.get(i);

            Integer previous = checker.get(node.name);
            if (previous == null) {
                checker.put(node.name, i);
            } else {
                int diff = i - previous;
                if (diff != 1) {
                    containJump = true;
                }
            }

            if (node.name.equals(mapper.textName())) {
                JsonNode subnodes = node.jsonNode;
                if (subnodes.isArray()) {
                    node.jsonNode = combineTextNodes((ArrayNode) subnodes);
                }
            }
        }

        if (mapper.groupElements()) {
            return true;
        } else {
            return containJump;
        }
    }

    private JsonNode combineTextNodes(ArrayNode textArray) {
        StringBuilder totalText = new StringBuilder();
        Iterator<JsonNode> iterator = textArray.elements();
        while (iterator.hasNext()) {
            JsonNode textSubNode = iterator.next();
            totalText.append(textSubNode.asText());
        }
        JsonNode textNode = jsonNodeFactory.textNode(totalText.toString());
        return textNode;
    }

    @Override
    public void attribute(Attr attribute) {
        debugBefore("", attribute);

        String localName = getLocalName(attribute);
        String prefix = getPrefix(attribute);

        if (localName.equals("xmlns") || prefix != null && prefix.equals("xmlns")) {
            // process name space declaration
            String namespace = attribute.getValue();
            if (localName.equals("xmlns")) {
                if (nsManager.isRetained(null, namespace)) {
                    nsManager.addMapping(null, namespace);
                }
            } else {
                if (nsManager.isRetained(localName, namespace)) {
                    nsManager.addMapping(localName, namespace);
                }
            }
        } else {
            String name = mapAttributeName(prefix, localName);
            String value = attribute.getValue();

            StackItem top = stack.peek();
            if (mapper.groupAttributes()) {
                top.appendAttribute(name, value);
            } else {
                ((ObjectNode) top.parent).put(name, value);
            }
        }

        debugAfter("", attribute);
    }

    @Override
    public void text(Text text) {
        debugBefore("", text);

        String name = mapper.textName();
        String value = text.getData();

        if (value.trim().isEmpty()) {
            // remove empty text
            return;
        }

        StackItem top = stack.peek();
        //((ObjectNode)top.parent).put(name, value);		
        top.appendChild(name, jsonNodeFactory.textNode(value));

        debugAfter("", text);
    }

    @Override
    public void cdataSection(CDATASection cdata) {
        debugBefore("", cdata);

        if (mapper.cdataAsText()) {
            text(cdata);

            debugAfter("", cdata);
            return;
        }

        String name = mapper.cdataName();
        String value = cdata.getData();

        if (value.trim().isEmpty()) {
            // remove empty text
            debugAfter("", cdata);
            return;
        }

        StackItem top = stack.peek();
        //((ObjectNode)top.parent).put(name, value);		
        top.appendChild(name, jsonNodeFactory.textNode(value));

        debugAfter("", cdata);
    }

    @Override
    public void comment(Comment comment) {
        debugBefore("", comment);

        if (!mapper.keepComment()) {
            debugAfter("", comment);
            return;
        }

        String name = mapper.commentName();
        String value = comment.getData();

        StackItem top = stack.peek();
        top.appendChild(name, jsonNodeFactory.textNode(value));

        debugAfter("", comment);
    }

    @Override
    public void processingInstruction(ProcessingInstruction processingInstruction) {
        debugBefore("", processingInstruction);

        if (!mapper.keepProcessingInstruction()) {
            debugAfter("", processingInstruction);
            return;
        }

        String target = processingInstruction.getTarget();
        String name = mapper.processingInstructionPrefix() + target;
        String value = processingInstruction.getData();

        StackItem top = stack.peek();
        top.appendChild(name, jsonNodeFactory.textNode(value));

        debugAfter("", processingInstruction);
    }

    private String mapElementName(Element element) {
        String localName = getLocalName(element);
        String prefix = getPrefix(element);

        String eleName = localName;
        if (keepPrefix(prefix, mapper.elementRetainNamespacePrefix())) {
            if (prefix != null && !prefix.isEmpty())
                eleName = prefix + ":" + localName;
        }

        return mapper.elementPrefix() + eleName;
    }

    private String mapAttributeName(String prefix, String localName) {
        String attrName = localName;
        if (keepPrefix(prefix, mapper.attributeRetainNamespacePrefix())) {
            if (prefix != null)
                attrName = prefix + ":" + localName;
        }

        return mapper.attributePrefix() + attrName;
    }

    private boolean keepPrefix(String prefix, RetainNamespacePrefix retainPrefix) {
        switch (retainPrefix) {
        case all:
            return true;
        case none:
            return false;
        case dependent:
            String ns = nsManager.getPrefix(prefix);
            return ns != null;
        }
        return false;
    }

    private String getLocalName(Node node) {
        String localName = node.getLocalName();
        if (localName == null) {
            localName = node.getNodeName();
            int pos = localName.indexOf(":");
            if (pos >= 0) {
                localName = localName.substring(pos + 1);
            }
        }
        return localName;
    }

    private String getPrefix(Node node) {
        String nodeName = node.getNodeName();
        int pos = nodeName.indexOf(":");
        if (pos >= 0) {
            return nodeName.substring(0, pos);
        }
        String nsuri = node.getNamespaceURI();
        if (nsuri != null && !XMLConstants.NULL_NS_URI.equals(nsuri)) {
            return "";
        }
        return null;
    }

    private static class StackItem {
        private JsonNode parent;
        private List<NamedJsonNode> children = new LinkedList<NamedJsonNode>();
        private List<StringPair> attributes = new LinkedList<StringPair>();

        public StackItem(JsonNode parent) {
            this.parent = parent;
        }

        public void appendChild(String name, JsonNode child) {
            NamedJsonNode namedNode = new NamedJsonNode(name, child);
            children.add(namedNode);
        }

        public void appendAttribute(String name, String value) {
            StringPair stringPair = new StringPair(name, value);
            attributes.add(stringPair);
        }
    }

    private static class NamedJsonNode {
        public String name;
        public JsonNode jsonNode;

        public NamedJsonNode(String name, JsonNode jsonNode) {
            this.name = name;
            this.jsonNode = jsonNode;
        }
    }

    private static class StringPair {
        public String first;
        public String second;

        public StringPair(String first, String second) {
            this.first = first;
            this.second = second;
        }
    }
}
