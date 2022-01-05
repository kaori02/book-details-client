package com.kaori02.bookdetailsclient.controller;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
public class ClientController {
  private static final String BOOK_DETAILS_API = "http://localhost:8000/query";

  static RestTemplate restTemplate = new RestTemplate();

  @PostConstruct
  public static void main() {
    callGetBookById();
  }

  private static void callGetBookById() {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

    HttpEntity<String> entity = new HttpEntity<>("{bookById(id:\"61c058045760ec26718a9bd8\"){id name pageCount author{firstName lastName}}}", headers);

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

    System.out.println(result);
  }
}
