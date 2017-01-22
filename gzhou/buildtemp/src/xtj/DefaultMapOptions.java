package xtj;

import java.util.Map;

import org.w3c.dom.Document;

public class DefaultMapOptions implements MapOptions{
	private String info;
	
	private ConversionMethod conversionMethod;
	
	private String templateContent;
	private String templateSeparator;
	private boolean validateJson;
	
	private Document mappingContent;
	
	private boolean keepRoot;
	
	private String retainNamespaces;
	private String retainPrefixes;
	private boolean groupNamespaces;
	private String namespaceGroupName;
	private String namespacePrefix;	
	
	private boolean keepComment;
	private String commentPrefix;
	
	private boolean keepProcessingInstructions;
	private String processingInstructionPrefix;
	
	private boolean stringContentAsNode;
	private boolean cdataAsText;
	private String cdataName;
	private String textName;
	
	private boolean groupAttribute;
	private String attributeGroupName;
	private String attributePrefix;
	private RetainNamespacePrefix attributeRetainNS;
	
	private boolean groupElement;
	private String elementGroupName;
	private String elementPrefix;
	private RetainNamespacePrefix elementRetainNS;
	
	public DefaultMapOptions(Map<String, Object> context) {
		StringBuilder infoBuilder = new StringBuilder();
		
		conversionMethod = (ConversionMethod)processEnumerativeOption(context, MapOptions.CONVERTION_METHOD, ConversionMethod.document, infoBuilder);

		templateContent = processStringOption(context, MapOptions.TEMPLATE_CONTENT, null, infoBuilder);
		templateSeparator = processStringOption(context, MapOptions.TEMPLATE_SEPERATOR, "$$", infoBuilder);
		validateJson = processBooleanOption(context, MapOptions.VALIDATE_JSON_STRING, false, infoBuilder); 
		
		//mappingContent = processStringOption(context, MapOptions.MAPPING_CONTENT, "", infoBuilder);
		//mappingContent = processGeneralOption(context, MapOptions.MAPPING_CONTENT, null, infoBuilder);
		Document mapping = (Document)context.get(MapOptions.MAPPING_CONTENT);
		if (mapping == null) {
			mappingContent = null;
		} else {
			mappingContent = mapping;
		}
		
		
		keepRoot = processBooleanOption(context, MapOptions.KEEP_ROOT, true, infoBuilder);

		retainNamespaces = processStringOption(context, MapOptions.RETAIN_NAMESPACES, "", infoBuilder);
		retainPrefixes = processStringOption(context, MapOptions.RETAIN_PREFIXES, "xml xsi", infoBuilder);
		groupNamespaces = processBooleanOption(context, MapOptions.GROUP_NAMESPACE, false, infoBuilder);
		namespaceGroupName = processStringOption(context, MapOptions.NAMESPACE_GROUP_NAME, "@@nsmap", infoBuilder);
		namespacePrefix = processStringOption(context, MapOptions.NAMESPACE_PREFIX, "#", infoBuilder);
		
		keepComment = processBooleanOption(context, MapOptions.KEEP_COMMENT, false, infoBuilder);
		commentPrefix = processStringOption(context, MapOptions.COMMENT_PREFIX, "comment()", infoBuilder);
		
		keepProcessingInstructions = processBooleanOption(context, MapOptions.KEEP_PROCESSING_INSTRUCTION, false, infoBuilder);
		processingInstructionPrefix = processStringOption(context, MapOptions.PROCESSING_INSTRUCTION_PREFIX, "pi#", infoBuilder);
		
		stringContentAsNode = processBooleanOption(context, MapOptions.STRING_CONTENT_AS_NODE, false, infoBuilder);
		cdataAsText = processBooleanOption(context, MapOptions.CDATA_AS_TEXT, true, infoBuilder);
		cdataName = processStringOption(context, MapOptions.CDATA_NAME, "cdata()", infoBuilder);
		textName = processStringOption(context, MapOptions.TEXT_NAME, "text()", infoBuilder);
		
		groupAttribute = processBooleanOption(context, MapOptions.GROUP_ATTRIBUTE, false, infoBuilder);
		attributeGroupName = processStringOption(context, MapOptions.ATTRIBUTE_GROUP_NAME, "@@attributes", infoBuilder);
		attributePrefix = processStringOption(context, MapOptions.ATTRIBUTE_PREFIX, "@", infoBuilder);
		attributeRetainNS = (RetainNamespacePrefix)processEnumerativeOption(context, MapOptions.ATTRIBUTE_RETAIN_NAMESPACE_PREFIX, RetainNamespacePrefix.dependent, infoBuilder);
		
		groupElement = processBooleanOption(context, MapOptions.GROUP_ELEMENT, false, infoBuilder);
		elementGroupName = processStringOption(context, MapOptions.ELEMENT_GROUP_NAME, "@@contents", infoBuilder);
		elementPrefix = processStringOption(context, MapOptions.ELEMENT_PREFIX, "", infoBuilder);
		elementRetainNS = (RetainNamespacePrefix)processEnumerativeOption(context, MapOptions.ELEMENT_RETAIN_NAMESPACE_PREFIX, RetainNamespacePrefix.dependent, infoBuilder);
		
		info = infoBuilder.toString();
	}
	
	private String processStringOption(Map<String, Object> options, String key, String defaultValue, StringBuilder builder) {
		String value = (String)options.get(key);
		builder.append("String option [").append(key).append("] ");
		if (value == null) {
			builder.append("use default [").append(defaultValue).append("]\n");
			return defaultValue;
		} else {
			builder.append("use input [").append(value).append("] default [").append(defaultValue).append("]\n");
			return value;
		}
	}
	
	private boolean processBooleanOption(Map<String, Object> options, String key, boolean defaultValue, StringBuilder builder) {
		Object rawValue = options.get(key);
		builder.append("Boolean option [").append(key).append("] ");
		if (rawValue == null) {
			builder.append("use default [").append(defaultValue).append("]\n");
			return defaultValue;
		} else {
			boolean value;
			if (rawValue instanceof Boolean) {
				value = (Boolean)rawValue;
			} else {
				value = Boolean.valueOf((String)rawValue);
			}
			builder.append("use input [").append(rawValue).append("] default [").append(defaultValue).append("]\n");
			return value;
		}
	}
	
	private Enum processEnumerativeOption(Map<String, Object> options, String key, Enum defaultValue, StringBuilder builder) {
		String value = (String)options.get(key);
		Enum result;
		
		builder.append("Enum option [").append(key).append("] ");
		if (value == null) {
			builder.append("use default [").append(defaultValue).append("] ");
			result = defaultValue;
		} else {
			builder.append("use input [").append(value).append("] defaul [").append(defaultValue).append("] ");
			result = Enum.valueOf(defaultValue.getDeclaringClass(), value);
		}
		
		builder.append("all recognizable values {");
		for (Object item: defaultValue.getDeclaringClass().getEnumConstants()) {
			builder.append(item).append(",");
		}
		builder.append("}\n");
		
		return result;
	}
	
	private <T> T processGeneralOption(Map<String, Object> options, String key, T defaultValue, StringBuilder builder) {
		T value = (T)options.get(key);
		builder.append("String option [").append(key).append("] ");
		if (value == null) {
			builder.append("use default [").append(defaultValue).append("]\n");
			return defaultValue;
		} else {
			builder.append("use input [").append(value).append("] default [").append(defaultValue).append("]\n");
			return value;
		}
	}
	
	@Override
	public ConversionMethod conversionMethod() {
		return conversionMethod;
	}

	@Override
	public String templateSeperator() {
		return templateSeparator;
	}

	@Override
	public String templateContent() {
		return templateContent;
	}
	
	@Override
	public boolean validateJson() {
		return validateJson;
	}

	@Override
	public Document mappingContent() {
		return mappingContent;
	}
	
	@Override
	public boolean groupAttributes() {
		return groupAttribute;
	}

	@Override
	public String attributeGroupName() {
		return attributeGroupName;
	}

	@Override
	public String attributePrefix() {        
		return attributePrefix;
	}	

	@Override
	public RetainNamespacePrefix attributeRetainNamespacePrefix() {
		return attributeRetainNS;
	}
	
	@Override
	public boolean groupElements() {
		return groupElement;
	}
	
	@Override
	public String elementsGroupName() {
		return elementGroupName;
	}

	@Override
	public String elementPrefix() {
		return elementPrefix;
	}
	
	@Override
	public RetainNamespacePrefix elementRetainNamespacePrefix() {
		return elementRetainNS;
	}

	@Override
	public boolean bigDecimalExact() {
		return false;
	}

	@Override
	public boolean stringContentAsNode() {
		return stringContentAsNode;
	}

	@Override
	public String retainNamespaces() {
		return retainNamespaces;
	}
	
	@Override
	public String retainPrefixes() {
		return retainPrefixes;
	}

	@Override
	public boolean groupNamespaces() {
		return groupNamespaces;
	}

	@Override
	public String namespaceGroupName() {
		return namespaceGroupName;
	}

	@Override
	public String namespacePrefix() {
		return namespacePrefix;
	}

	@Override
	public boolean keepRoot() {
		return keepRoot;
	}

	@Override
	public boolean keepComment() {
		return keepComment;
	}

	@Override
	public boolean keepProcessingInstruction() {
		return keepProcessingInstructions;
	}

	@Override
	public String commentName() {
		return commentPrefix;
	}

	@Override
	public String processingInstructionPrefix() {
		return processingInstructionPrefix;
	}

	@Override
	public String textName() {
		return textName;
	}

	@Override
	public boolean cdataAsText() {
		return cdataAsText;
	}

	@Override
	public String cdataName() {
		return cdataName;
	}

	@Override
	public String info() {
		return info;
	}
}
