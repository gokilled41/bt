package xtj;

import java.util.Map;

import org.w3c.dom.Document;

public interface DOMToJSONProcessor<T> {

    void initialize(MapOptions options) throws ConversionException;

    T generateJSON(Document document, boolean own) throws ConversionException;

}
