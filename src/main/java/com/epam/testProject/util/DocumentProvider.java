package com.epam.testProject.util;

import org.w3c.dom.Document;

public interface DocumentProvider {

  /**
   * Returns document from existing source path
   *
   * @param path String source path
   * @return document
   */
  Document getDocument(String path);

}
