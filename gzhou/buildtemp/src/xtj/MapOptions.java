package xtj;

import org.w3c.dom.Document;


public interface MapOptions {
	static final String LOGGER_PREFIX = "$__#_LOGGER_PREFIX";
	static final String COMPONENT_NAME = "$__#_COMPONENT_NAME";
	
	static final String CONVERTION_METHOD = "conversion_method";
	
	static final String TEMPLATE_SEPERATOR = "template_seperator";
	static final String TEMPLATE_CONTENT = "template_content";
	static final String VALIDATE_JSON_STRING = "validate_json_string";
	
	static final String MAPPING_CONTENT = "mapping_content";
	
	static final String RETAIN_NAMESPACES = "retain_namespaces";
	static final String RETAIN_PREFIXES = "retain_prefixes";
	
	static final String NAMESPACE_PREFIX = "namespace_prefix";
	static final String GROUP_NAMESPACE = "group_namespace";
	static final String NAMESPACE_GROUP_NAME = "namespace_group_name";	
	
	static final String KEEP_ROOT = "keep_root";
	
	static final String KEEP_COMMENT = "keep_comment";
	static final String COMMENT_PREFIX = "comment_name";
	
	static final String KEEP_PROCESSING_INSTRUCTION = "keep_processing_instruction";
	static final String PROCESSING_INSTRUCTION_PREFIX = "processing_instruction_prefix";
	
	static final String STRING_CONTENT_AS_NODE = "string_content_as_node";
	static final String CDATA_AS_TEXT = "cdata_as_text";
	static final String CDATA_NAME = "cdada_name";
	
	static final String TEXT_NAME = "text_name";
	
	static final String GROUP_ATTRIBUTE = "group_attribute";
	static final String ATTRIBUTE_GROUP_NAME = "attribute_group_name";
	static final String ATTRIBUTE_PREFIX = "attribute_prefix";
	static final String ATTRIBUTE_RETAIN_NAMESPACE_PREFIX = "retain_attribute_namespace_prefix";
	
	static final String GROUP_ELEMENT = "group_element";
	static final String ELEMENT_GROUP_NAME = "element_group_name";
	static final String ELEMENT_PREFIX = "element_prefix";
	static final String ELEMENT_RETAIN_NAMESPACE_PREFIX = "retain_element_namespace_prefix";
	
	enum ConversionMethod {
		document,
		template,
		mapping,
	}
	
	enum RetainNamespacePrefix {
		all,
		dependent,
		none;
	}
	
	ConversionMethod conversionMethod();
	
	String templateSeperator();
	String templateContent();
	boolean validateJson();
	
	Document mappingContent();
	
	boolean groupAttributes();
	String attributeGroupName();
	String attributePrefix();
	RetainNamespacePrefix attributeRetainNamespacePrefix();
	
	boolean groupElements();
	String elementsGroupName();	
	String elementPrefix();
	RetainNamespacePrefix elementRetainNamespacePrefix();
	
	boolean bigDecimalExact();
	boolean stringContentAsNode();
	
	String retainNamespaces();
	String retainPrefixes();
	boolean groupNamespaces();
	String namespaceGroupName();
	String namespacePrefix();
	
	boolean keepRoot();
	
	boolean keepComment();	
	boolean keepProcessingInstruction();
	
	String commentName();
	String processingInstructionPrefix();
	
	String textName();
	boolean cdataAsText();
	String cdataName();
	
	String info();
}
