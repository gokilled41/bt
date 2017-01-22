package xtj;

import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

public interface DOMResponder {
	boolean startDocument(Document document);
	void endDocument(Document document);
	
	boolean enterElement(Element element);
	void leaveElement(Element element);
	
	void attribute(Attr attribute);
	
	void text(Text text);
	void cdataSection(CDATASection cdata);
	
	void comment(Comment comment);
	void processingInstruction(ProcessingInstruction processingInstruction);
	
	Object getResult(boolean stripOuter);
}
