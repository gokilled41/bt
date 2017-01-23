package xtj;

import org.w3c.dom.Document;

import com.fasterxml.jackson.databind.JsonNode;

public class DocumentProcessor implements DOMToJSONProcessor<JsonNode> {
    private DOMResponder domResponder;
    private DOMWalker domWalker;
    private MapOptions mapper;

    @Override
    public JsonNode generateJSON(Document document) throws ConversionException {
        JsonNode result = (JsonNode) domWalker.walk(document, domResponder);
        return result;
    }

    @Override
    public void initialize(MapOptions options) {
        this.domResponder = new JSONBuilder(options);
        this.domWalker = new DOMWalker();
        this.mapper = options;
    }

}
