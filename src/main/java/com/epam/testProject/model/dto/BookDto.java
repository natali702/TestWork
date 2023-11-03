package com.epam.testProject.model.dto;

import com.epam.testProject.model.Section;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

  String id;
  List<Section> sections;
}
