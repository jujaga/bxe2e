package org.oscarehr.e2e.util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

/**
 * The Class E2EXSDValidator.
 */
public class E2EXSDValidator {
	private static final Logger log = Logger.getLogger(E2EXSDValidator.class.getName());
	private static final SAXParserFactory factory = SAXParserFactory.newInstance();
	static {
		factory.setNamespaceAware(true);
	}

	E2EXSDValidator() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Checks if xmlstring is a well formed XML.
	 *
	 * @param xmlstring the xmlstring
	 * @return true if well formed, false otherwise
	 */
	public static Boolean isWellFormedXML(final String xmlstring) {
		factory.setValidating(false);

		try {
			SAXParser parser = factory.newSAXParser();
			XMLReader reader = parser.getXMLReader();
			reader.setErrorHandler(new SimpleErrorHandler());
			reader.parse(new InputSource(new StringReader(xmlstring)));
			return true;
		} catch (Exception e) {
			log.warn(e.getMessage());
			return false;
		}
	}

	/**
	 * Checks if xmlstring is a valid XML.
	 *
	 * @param xmlstring the xmlstring
	 * @return true if valid, false otherwise
	 */
	public static Boolean isValidXML(final String xmlstring) {
		factory.setValidating(true);

		try {
			SAXParser parser = factory.newSAXParser();
			parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
			XMLReader reader = parser.getXMLReader();
			reader.setErrorHandler(new SimpleErrorHandler());
			reader.setEntityResolver(new E2EEntityResolver());
			reader.parse(new InputSource(new StringReader(xmlstring)));
			return true;
		} catch (Exception e) {
			log.warn(e.getMessage());
			return false;
		}
	}

	private static class SimpleErrorHandler implements ErrorHandler {
		public void warning(SAXParseException e) throws SAXException {
			throw new SAXException("(Parsing Warning) " + e.getMessage());
		}

		public void error(SAXParseException e) throws SAXException {
			throw new SAXException("(Parsing Error) " + e.getMessage());
		}

		public void fatalError(SAXParseException e) throws SAXException {
			throw new SAXException("(Parsing Fatal Error) " + e.getMessage());
		}
	}

	private static class E2EEntityResolver implements EntityResolver {
		public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
			// Grab only the filename from the full path
			String filename = new File(systemId).getName();

			// Prepend the correct path
			String correctedId = E2EXSDValidator.class.getResource("/e2e/" + filename).getPath();

			InputSource is = new InputSource(ClassLoader.getSystemResourceAsStream(correctedId)) {{
				setSystemId(correctedId);
			}};

			return is;
		}
	}
}
