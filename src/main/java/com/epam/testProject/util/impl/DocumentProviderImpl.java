package com.epam.testProject.util.impl;

import static com.epam.testProject.exception.errorcode.CommonErrorCode.FAILED_PARSE_CONTENT;

import com.epam.testProject.exception.ServiceException;
import com.epam.testProject.util.DocumentProvider;
import java.io.IOException;
import java.net.URL;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

@Component
public class DocumentProviderImpl implements DocumentProvider {

  @Override
  public Document getDocument(String path) {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    Document document;
    try {
      document = factory.newDocumentBuilder().parse(new URL(path).openStream());
      document.getDocumentElement().normalize();
    } catch (SAXException | IOException | ParserConfigurationException e) {
      throw new ServiceException(FAILED_PARSE_CONTENT);
    }
    return document;
  }
}
