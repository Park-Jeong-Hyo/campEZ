package com.campEZ.Project0.web.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response<T> {
  private Header header;
  private T data;

  @Data
  @AllArgsConstructor
  public static class Header {
    private String rtcd;
    private String rtmsg;
  }

  public static <T> Response<T> createRestResponse(String rtcd, String rtmsg, T data){
    return new Response<>(new Header(rtcd,rtmsg),data);
  }
}