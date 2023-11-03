package com.epam.testProject.model;

public enum Type {
  SECTION("Section");

  Type(String name) {
    this.name = name;
  }

  private final String name;

  public String getName() {
    return name;
  }
}
