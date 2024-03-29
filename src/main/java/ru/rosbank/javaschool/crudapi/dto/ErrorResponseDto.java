package ru.rosbank.javaschool.crudapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class ErrorResponseDto {
  private final int status;
  private final String message;
  private final String translated;
  private final Map<String, List<String>> errors;
}
