package com.campEZ.Project0.orders.dao;

import com.campEZ.Project0.entity.Orders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OrdersDAOImpl implements OrdersDAO{

  private final NamedParameterJdbcTemplate template;

  //예약
  //예약
  @Override
  public Orders order(Orders orders) {
    StringBuffer sb = new StringBuffer();
    sb.append("insert into ORDERS (onumber,cnumber,area,mid,phone,headcount,checkin,checkout) ");
    sb.append("values(onumber_seq.nextval, :cnumber, :area, :mid, :phone, :headcount, :checkin, :checkout) ");

    SqlParameterSource param = new BeanPropertySqlParameterSource(orders);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    template.update(sb.toString(),param,keyHolder,new String[]{"onumber"});


    long onumber = keyHolder.getKey().longValue();
    return orders;
  }

  //예약 취소
  @Override
  public int orDelete(int onumber) {
    String sb = "delete from orders where onumber = :onumber ";
    return template.update(sb, Map.of("onumber",onumber));
  }

  //캠프에리어 테이블에서 캠핑장번호로 구역 가져오기
  public List<Integer> campingFindByCnumber(int cnumber){
    StringBuffer sql = new StringBuffer();
    sql.append("select area from camparea ");
    sql.append("where cnumber = :cnumber ");
    Map<String, Integer> param = Map.of("cnumber", cnumber);
    List<Integer> campareas = template.queryForList(sql.toString(), param, Integer.class); //멀티행 단일컬럼
    return campareas;
  }

  //캠프에리어 테이블에서 캠핑장번호, 구역으로 인원 가져오기
  public Integer campingAreaChange(int cnumber, int area){
    StringBuffer sql = new StringBuffer();
    sql.append("select capacitys from camparea ");
    sql.append("where cnumber = :cnumber and area = :area ");

    Map<String, Integer> param = Map.of("cnumber", cnumber,"area",area);
    Integer campareas = template.queryForObject(sql.toString(), param, Integer.class);

    return campareas;
  }

  //캠핑테이블에서 캠핑장번호로 소개글 가져오기
  public String campingCtext(int cnumber){
    StringBuffer sql = new StringBuffer();
    sql.append("select ctext from camping ");
    sql.append("where cnumber = :cnumber ");

    Map<String, Integer> param = Map.of("cnumber", cnumber);
    String camping = template.queryForObject(sql.toString(), param, String.class);

    return camping;
  }
}
