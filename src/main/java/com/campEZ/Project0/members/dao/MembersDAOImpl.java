package com.campEZ.Project0.members.dao;

import com.campEZ.Project0.entity.Members;
import com.campEZ.Project0.entity.Orders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MembersDAOImpl implements MembersDAO {

  private final NamedParameterJdbcTemplate template;

  //회원등록
  @Override
  public Members memSave(Members members){
    StringBuffer sb = new StringBuffer();
    sb.append("insert into members(mname,mid,pw,phone,email,nickname,maddress,mtype,companyname,businessnumber) ");
    sb.append("values( ");
    sb.append(":mname, ");
    sb.append(":mid, ");
    sb.append(":pw, ");
    sb.append(":phone, ");
    sb.append(":email, ");
    sb.append(":nickname, ");
    sb.append(":maddress, ");
    sb.append(":mtype, ");
    sb.append(":companyname, ");
    sb.append(":businessnumber) ");


    SqlParameterSource param = new BeanPropertySqlParameterSource(members);
    template.update(sb.toString(),param);


    return members;
  }

  //회원수정
  @Override
  public void memUpdate(String mid, Members members){
    StringBuffer sb = new StringBuffer();
    sb.append("update members ");
    sb.append("   set nickname = :nickname, ");
    sb.append("       pw = :pw, ");
    sb.append("       phone = :phone, ");
    sb.append("       email = :email, ");
    sb.append("       maddress = :maddress, ");
    sb.append("       companyname = :companyname, ");
    sb.append("       businessnumber = :businessnumber ");
    sb.append(" where mid = :mid ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("nickname",members.getNickname())
        .addValue("pw",members.getPw())
        .addValue("phone",members.getPhone())
        .addValue("email",members.getEmail())
        .addValue("maddress",members.getMaddress())
        .addValue("companyname",members.getCompanyname())
        .addValue("businessnumber",members.getBusinessnumber())
        .addValue("mid",mid);

    template.update(sb.toString(),param);
  }

  //회원탈퇴
  @Override
  public void memDelete(String mid){
    StringBuffer sb = new StringBuffer();
    sb.append("delete from members ");
    sb.append(" where mid = :mid ");

    Map<String, String> param = Map.of("mid", mid);
    template.update(sb.toString(), param);
  }

  //회원조회(사업자)
  @Override
  public Members memFindB(String mid){
    StringBuffer sb = new StringBuffer();
    sb.append("select mname, mid, phone, email, nickname, maddress, companyname, businessnumber ");
    sb.append(" from members ");
    sb.append(" where mid = :mid ");

    Map<String, String> param = Map.of("mid", mid);
    Members members = template.queryForObject(sb.toString(),param,new BeanPropertyRowMapper<>(Members.class));
    return members;
  }

  //회원조회(일반회원)
  @Override
  public Members memFindN(String mid){
    StringBuffer sb = new StringBuffer();
    sb.append("select mname, mid, phone, email, nickname, maddress ");
    sb.append(" from members ");
    sb.append(" where mid = :mid ");

    Map<String, String> param = Map.of("mid", mid);
    Members members = template.queryForObject(sb.toString(),param,new BeanPropertyRowMapper<>(Members.class));
    return members;
  }

  //예약조회(사업자)
  @Override
  public Orders orderFindB(int cnumber){
    StringBuffer sb = new StringBuffer();
    sb.append("select * from orders where cnumber = :cnumber ");

    Map<String, Integer> param = Map.of("cnumber", cnumber);
    Orders orders = template.queryForObject(sb.toString(),param,new BeanPropertyRowMapper<>(Orders.class));
    return orders;
  }

  //예약조회(일반회원)
  @Override
  public Orders orderFindN(String mid){
    StringBuffer sb = new StringBuffer();
    sb.append("select * from orders where mid = :mid ");

    Map<String, String> param = Map.of("mid",mid);
    Orders orders = template.queryForObject(sb.toString(),param,new BeanPropertyRowMapper<>(Orders.class));
    return orders;
  }

  //아이디찾기
  @Override
  public Optional<String> idFind(String mname,String phone,String email){
    StringBuffer sb = new StringBuffer();
    sb.append("select mid from members ");
    sb.append(" where email = :email and phone = :phone and mname = :mname ");

    Map<String, String> param = Map.of("mname",mname,"phone",phone,"email", email);

    List<String> result = template.query(
        sb.toString(),
        param,
        (rs, rowNum)-> rs.getNString("mid")

    );

    return (result.size() == 1) ? Optional.of(result.get(0)) : Optional.empty();
  }

  //비밀번호찾기
  @Override
  public Optional<String> pwFind(String mid, String phone, String email){
    StringBuffer sb = new StringBuffer();
    sb.append("select pw from members ");
    sb.append(" where mid = :mid and email = :email and phone = :phone   ");

    Map<String, String> param = Map.of("mid",mid,"phone",phone,"email", email);

    List<String> result = template.query(
        sb.toString(),
        param,
        (rs, rowNum)->rs.getNString("pw")
    );

    return (result.size() == 1) ? Optional.of(result.get(0)) : Optional.empty();



  }

  //회원유무
  @Override
  public boolean isExist(String mid){
    String sql = "select count(mid) from members where mid = :mid ";

    Map<String, String> param = Map.of("mid", mid);

    Integer cnt = template.queryForObject(sql, param, Integer.class);
    return cnt == 1 ? true : false;
  }

  //로그인
  @Override
  public Optional<Members> login(String mid, String pw){
    StringBuffer sql = new StringBuffer();
    sql.append("select mid, mtype, nickname");
    sql.append(" from members");
    sql.append(" where mid = :mid and pw = :pw ");

    Map<String, String> param = Map.of("mid", mid,"pw",pw);
    List<Members> list = template.query(
        sql.toString(),
        param,
        BeanPropertyRowMapper.newInstance(Members.class)
    );

    return list.size() == 1 ? Optional.of(list.get(0)) : Optional.empty();
  }


}
