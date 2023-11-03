package com.epam.testProject.util.impl;

import com.epam.testProject.model.Book;
import com.epam.testProject.model.Section;
import com.epam.testProject.model.Type;
import com.epam.testProject.util.DocumentParser;
import com.epam.testProject.util.DocumentProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Component
@AllArgsConstructor
public class DocumentParserImpl implements DocumentParser {

  private final static String INSTRUCTION = "content-link";
  private final static String TAG_NAME = "title";
  private final static int INDEX_FOR_LINK = 6;
  private final DocumentProvider provider;

  @Override
  public Book parseToBook(String bookUrl) {
    List<Section> sections = new ArrayList<>();
    parseAllPagesToList(provider.getDocument(bookUrl), bookUrl, sections);
    Book newBook = new Book();
    newBook.setId(UUID.randomUUID().toString());
    newBook.setSections(sections);
    return newBook;
  }

  private void parseAllPagesToList(Document document, String path,
      List<Section> sections) {

    NodeList nList = document.getChildNodes();
    String contentPath = getInstructionContent(document.getDocumentElement(), INSTRUCTION);

    if (contentPath.isEmpty()) {
      addSections(nList, sections);
    } else {
      addSections(nList, sections);
      String newPath = getNewPath(path, contentPath);
      parseAllPagesToList(provider.getDocument(newPath), newPath, sections);
    }
  }

  private void addSections(NodeList nList, List<Section> list) {
    for (int temp = 0; temp < nList.getLength(); temp++) {
      Node node = nList.item(temp);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        Element eElement = (Element) node;
        list.add(new Section(Type.SECTION,
            eElement.getElementsByTagName(TAG_NAME).item(0).getTextContent()));
      }
    }
  }

  private String getInstructionContent(Node node, String instructionName) {
    NodeList list = node.getChildNodes();
    for (int i = 0; i < list.getLength(); i++) {
      Node currentNode = list.item(i);
      String content = getInstructionContent(currentNode, instructionName);
      if (!content.isEmpty()) {
        return content;
      }
    }
    if (node.getNodeType() == Node.PROCESSING_INSTRUCTION_NODE && node.getNodeName()
        .equals(instructionName)) {
      return getAddressFromLink(node.getTextContent());
    }
    return "";
  }

  private String getAddressFromLink(String path) {
    return path.substring(INDEX_FOR_LINK, path.length() - 1);
  }

  private String getNewPath(String path, String contentPath) {
    int index = path.lastIndexOf("/");
    return path.substring(0, index + 1).concat(contentPath);
  }
}
