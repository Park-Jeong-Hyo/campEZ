package com.campEZ.Project0.members.svc;

import com.campEZ.Project0.entity.BusinessStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class BusinessApiSVC {

  private final String SERVICE_KEY = "siDhb0EnBxC6r9NydVSmaBEw0Cnh%2B75T3ISGeBxC2nZYTZhVt0h31ztvLU3CW%2BZ1NB5MkkIJQTsJpaZt%2BKcUxQ%3D%3D";

  //사업자등록 상태 조회
  public Map<String,String> getPublicData(String businessNo) {
    Map<String, String> map = new HashMap<>();
    try {
//  public static void main(String[] args) throws IOException, URISyntaxException {

      UriComponents complexUrl = UriComponentsBuilder
          .fromUriString("http://api.odcloud.kr/api/nts-businessman/v1/status")
//      .uriVariables(Map.of("nickname", "dailycode"))
          .queryParam("serviceKey", SERVICE_KEY)
          .queryParam("resultType", "JSON")
          .build();  //encode() 해주면 toUriString 사용시 한글에 대한 URL 엔코딩도 해준다

      // request url
      String url = complexUrl.toString();

      // create an instance of RestTemplate
      RestTemplate restTemplate = new RestTemplate();

      // create headers
      HttpHeaders headers = new HttpHeaders();

      // set `Content-Type` and `Accept` headers
      headers.setContentType(MediaType.APPLICATION_JSON);
      headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

      // build the request
//    String body = "{ \"b_no\": [\"6212002123\"]}";
      HttpEntity<String> request = new HttpEntity<>(businessNo, headers);

      // make an HTTP GET request with headers
      ResponseEntity<String> response = restTemplate.exchange(
          new URI(url),
          HttpMethod.POST,
          request,
          String.class    //응답타입
      );

      // check response
      if (response.getStatusCode() == HttpStatus.OK) {
        log.info("Request Successful={}", response.getBody());
        //1)  json포맷 문자열 => 자바객체
        ObjectMapper objectMapper = new ObjectMapper();
        BusinessStatus businessStatus = objectMapper.readValue(response.getBody(), BusinessStatus.class);
        log.info("businessStatusChk={}", businessStatus);
        //2)
        if (businessStatus.getStatus_code().equals("OK")) {

          switch (businessStatus.getData().get(0).getB_stt_cd()) {
            case "01": //계속사업자
              //사업자번호
              String b_no = businessStatus.getData().get(0).getB_no();
              map.put("01", "계속사업자");
              break;
            case "02": //휴업자
              map.put("02", "휴업자");
              break;
            case "03": //폐업자
              map.put("03", "폐업자");
              break;
            default: //기타
              map.put("00", "기타");
              break;
          }
        }
      } else {
        log.info("Request Failed={}", response.getStatusCode());
      }
    } catch (Exception e) {
      log.info(e.getMessage());
    }

    return map;
  }

//    if(res.status_code == 'OK'){
//      switch(res.data[0].b_stt_cd){ //납세자 상태
//        case "01": //계속사업자
//          console.log('계속');
//          document.querySelector('.item.item2').classList.remove('hidden');
//
//          //진위확인 파라미터
//          businessTrulyRequestParm.b_no = res.data[0].b_no;  //사업자 등록번호
//
//          break;
//        case "02": //휴업자
//          break;
//        case "03": //폐업자
//          break;
//        default :
//          throw new Error(`${res.data[0].tax_type}`);
//      }
//    }else{
//      throw new Error(`${res.description}`);
//    }

  //사업자등록정보 진위확인
  public String getPublicData2(String json) {

    try {
      UriComponents complexUrl = UriComponentsBuilder
          .fromUriString("http://api.odcloud.kr/api/nts-businessman/v1/validate")
//        .uriVariables(Map.of("nickname", "dailycode"))
          .queryParam("serviceKey", SERVICE_KEY)
          .queryParam("resultType", "JSON")
          .build();  //encode() 해주면 toUriString 사용시 한글에 대한 URL 엔코딩도 해준다

      // request url
      String url = complexUrl.toString();

      // create an instance of RestTemplate
      RestTemplate restTemplate = new RestTemplate();

      // create headers
      HttpHeaders headers = new HttpHeaders();

      // set `Content-Type` and `Accept` headers
      headers.setContentType(MediaType.APPLICATION_JSON);
      headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

      // build the request
      HttpEntity<String> request = new HttpEntity<>(json, headers);

      // make an HTTP GET request with headers
      ResponseEntity<String> response = restTemplate.exchange(
          new URI(url),
          HttpMethod.POST,
          request,
          String.class    //응답타입
      );

      // check response
      if (response.getStatusCode() == HttpStatus.OK) {
        log.info("Request Successful={}", response.getBody());
        return response.getBody();
      } else {
        log.info("Request Failed={}", response.getStatusCode());
      }
    } catch (Exception e) {
      log.info(e.getMessage());
    }

    return null;
  }
}
