package com.kaori02.bookdetailsclient.controller;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ClientController {
  private static final String BOOK_DETAILS_API = "http://localhost:8000/query";

  static RestTemplate restTemplate = new RestTemplate();

  @PostConstruct
  public static void main() {
    List<String> fields = new ArrayList<>();
    fields.add("id");
    fields.add("name");
    fields.add("pageCount");
    fields.add("author{firstName lastName}");

    callGetBookById("61c058045760ec26718a9bd7", fields);
    callGetAllBooks();
  }

  private static void callGetBookById(String bookId, List<String> fields) {
    StringBuilder fieldString = new StringBuilder();
    for (String field : fields) {
      fieldString.append(" ").append(field);
    }
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

    HttpEntity<String> entity = new HttpEntity<>("{bookById(id:\"" + bookId +"\"){" + fieldString+ "}}", headers);

    ResponseEntity<String> result = restTemplate.exchange(BOOK_DETAILS_API, HttpMethod.POST, entity, String.class);

    System.out.println(result.getBody());
  }

  private static void callGetAllBooks() {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

    HttpEntity<String> entity = new HttpEntity<>("{\n" +
        "  allBooks{\n" +
        "    id\n" +
        "    name\n" +
        "    author{\n" +
        "      lastName\n" +
        "    }\n" +
        "  }\n" +
        "}", headers);

    ResponseEntity<String> result = restTemplate.exchange(BOOK_DETAILS_API, HttpMethod.POST, entity, String.class);

    System.out.println(result.getBody());
  }
}
