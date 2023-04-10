package com.campEZ.Project0.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.URLEncoder;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String redirectUrl = null;
    String requestURI = request.getRequestURI();
    log.info("requestUrl={}", requestURI);

    //URI에 쿼리스트링인지 인지 아닌지 확인하는 로직
    //쿼리스트링이 있을 경우
    if(request.getQueryString() != null) {
      //URI에 한글이 있으면 깨질 수도 있기 때문에 인코딩
      String queryString = URLEncoder.encode(request.getQueryString(), "UTF-8");
      StringBuffer sb = new StringBuffer();
      redirectUrl = sb.append(requestURI)
              .append("&")
              .append(queryString)
              .toString();
    } else {
      //쿼리스트링이 없을 경우
      redirectUrl = requestURI;
    }
    //세션 체크
    //getSession(true)면 생성, 반환O , getSession(false)면 생성X, 반환O
    HttpSession session = request.getSession(false);

    //세션 없으면 로그인화면으로 리다이렉트
    if(session == null) {
      response.sendRedirect("/login");
      return false;
    }
    //true일 경우 컨트롤러 진행, false일 경우 작업중단, 컨트롤러 진행 X
    return true;
  }
}
