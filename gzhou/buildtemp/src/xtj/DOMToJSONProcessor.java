package xtj;

import org.w3c.dom.Document;

public interface DOMToJSONProcessor<T> {

    void initialize(MapOptions options) throws ConversionException;

    T generateJSON(Document document) throws ConversionException;

}
