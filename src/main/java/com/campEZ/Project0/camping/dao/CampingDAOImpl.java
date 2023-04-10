package com.campEZ.Project0.camping.dao;

import com.campEZ.Project0.entity.Camparea;
import com.campEZ.Project0.entity.Camping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Repository
public class CampingDAOImpl implements CampingDAO{
  private final NamedParameterJdbcTemplate template;

  //캠핑장 등록, 캠핑장 구역 등록
  @Override
  public List<Object> campingSave(Camping camping, Camparea camparea) {
    //캠핑장 등록 로직
    StringBuffer sql = new StringBuffer();
    sql.append("INSERT INTO CAMPING ");
    sql.append("( cnumber, mid, cname, caddress, camptel, ctype, operdate, ");
    sql.append("homepage, ctitle, ctext, priceweekday, priceweekend, toilet, mart ) ");
    sql.append("VALUES ");
    sql.append("( cnumber_seq.nextval, :mid, :cname, :caddress, :camptel, :ctype, :operdate, ");
    sql.append(":homepage, :ctitle, :ctext, :priceweekday, :priceweekend, :toilet, :mart ) ");

    KeyHolder keyHolder = new GeneratedKeyHolder();
    SqlParameterSource param = new BeanPropertySqlParameterSource(camping);
    template.update(sql.toString(), param, keyHolder, new String[]{"cnumber"});
    int cnumber = keyHolder.getKey().intValue();
    camping.setCnumber(cnumber);

    //캠핑장 구역 등록 로직
    StringBuffer sql2 = new StringBuffer();
    sql2.append("INSERT INTO CAMPAREA ");
    sql2.append("(cnumber, area, capacitys) ");
    sql2.append("VALUES ");
    sql2.append("(:cnumber, :area, :capacitys) ");

    SqlParameterSource param2 = new BeanPropertySqlParameterSource(camparea);
    //camping의 키 cnumber(외래키)를 가져와서 camparea에 삽입
    camparea.setCnumber(camping.getCnumber());
    template.update(sql2.toString(), param2);
    List<Object> list = List.of(camping, camparea);
    return list;
  }
  //캠핑장 수정
  // 리턴값으로 수정된 row의 갯수 1이 반환됨.
  @Override
  public int campingUpdate(Camping camping, Camparea camparea, int cnumber) {
    StringBuffer sql = new StringBuffer();
    sql.append("UPDATE CAMPING ");
    sql.append("SET cname = :cname, caddress = :caddress, camptel = :camptel, ctype = :ctype, operdate = :operdate, homepage = :homepage, ");
    sql.append("ctitle = :ctitle, ctext = :ctext, priceweekday= :priceweekday, priceweekend = :priceweekend, toilet = :toilet, mart = :mart ");
    sql.append("WHERE cnumber = :cnumber ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("cname", camping.getCname())
        .addValue("caddress", camping.getCaddress())
        .addValue("camptel", camping.getCamptel())
        .addValue("ctype", camping.getCtype())
        .addValue("operdate", camping.getOperdate())
        .addValue("homepage", camping.getHomepage())
        .addValue("ctitle", camping.getCtitle())
        .addValue("ctext", camping.getCtext())
        .addValue("priceweekday", camping.getPriceweekday())
        .addValue("priceweekend", camping.getPriceweekend())
        .addValue("toilet", camping.getToilet())
        .addValue("mart", camping.getMart())
        .addValue("cnumber", cnumber);

    //캠핑장 구역 수용인원(capaticys) 수정
    //특이사항: 캠핑장 구역(area)을 수정하는 로직은 없음.
    StringBuffer sql2 = new StringBuffer();
    sql2.append("UPDATE CAMPAREA SET ");
    sql2.append("capacitys = :capacitys ");
    sql2.append("WHERE cnumber = :cnumber AND area = :area ");

    SqlParameterSource param2 = new MapSqlParameterSource()
        .addValue("capacitys", camparea.getCapacitys())
        .addValue("area", camparea.getArea())
        .addValue("cnumber", cnumber);
    template.update(sql2.toString(), param2);
    //반환은 camping의 수정 결과만
    return template.update(sql.toString(), param);
  }
  //캠핑장 삭제
  @Override
  public int campingDelete(int cnumber) {
    StringBuffer sql = new StringBuffer();
    sql.append("DELETE FROM CAMPING ");
    sql.append("WHERE cnumber = :cnumber ");

    Map<String, Integer> param = Map.of("cnumber", cnumber);
    return template.update(sql.toString(), param);
  }
  //캠핑장 조회
  //캠핑장 정보를 리턴
  @Override
  public Optional<Camping> campingDetail(int cnumber) {
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT * FROM CAMPING ");
    sql.append("WHERE cnumber = :cnumber ");

    try{
      Map<String, Integer> param = Map.of("cnumber", cnumber);
      Camping camping = template.queryForObject(sql.toString(), param, BeanPropertyRowMapper.newInstance(Camping.class));
      return Optional.of(camping);
    } catch(EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

    //  내 캠핑장 조회
      public List<Camping> campingFindByManagerMid(String mid){
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM CAMPING ");
        sql.append("WHERE mid = :mid ");
          Map<String, String> param = Map.of("mid", mid);
          List<Camping> MyCampingList = template.query(sql.toString(), param, BeanPropertyRowMapper.newInstance(Camping.class));
          return MyCampingList;
      }

  //캠핑장 검색
  //매개변수: 캠핑장 검색 조건, 캠핑장 종류, 주소, 이름 (시간 되면 조건도 추가 예정)
  @Override
  public List<Camping> campingSearch(CampingFilterCondition campingFilterCondition) {
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT cnumber, ctype, caddress, cname ");
    sql.append("FROM CAMPING ");
    sql.append("WHERE ");
    //동적 쿼리
    sql = dynamicQuery(campingFilterCondition, sql);
    SqlParameterSource param = new BeanPropertySqlParameterSource(campingFilterCondition);
    //결과 리스트 반환
    List<Camping> list = null;
    list = template.query(sql.toString(), param, new BeanPropertyRowMapper<>(Camping.class));
    return list;
  }

  //동적 쿼리 메서드
  private StringBuffer dynamicQuery(CampingFilterCondition campingFilterCondition, StringBuffer sql) {
    sql.append("ctype = '" + campingFilterCondition.getCampingType() + "' AND ");
    sql.append("caddress like '%" + campingFilterCondition.getCampingRegion() +"%' AND ");
    sql.append("cname like '%" + campingFilterCondition.getCampingKeyword() + "%' " );
    return sql;
  }
}
