package com.campEZ.Project0.members.dao;

import com.campEZ.Project0.entity.Members;
import com.campEZ.Project0.entity.Orders;
import com.campEZ.Project0.web.form.myPage.OrdersNameForm;
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
    sb.append("select mname, mid, phone, email, nickname, maddress, mtype, companyname, businessnumber ");
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
    sb.append("select mname, mid, phone, email, nickname, mtype, maddress ");
    sb.append(" from members ");
    sb.append(" where mid = :mid ");

    Map<String, String> param = Map.of("mid", mid);
    Members members = template.queryForObject(sb.toString(),param,new BeanPropertyRowMapper<>(Members.class));
    return members;
  }

  //예약조회(사업자)
  @Override
  public List<OrdersNameForm> orderFindB(String mid){
    StringBuffer sb = new StringBuffer();
    sb.append("select t2.cname, onumber, t1.cnumber, area, t1.mid, phone, headcount, checkin, checkout ");
    sb.append(" from orders t1, camping t2 ");
    sb.append(" where t1.cnumber = t2.cnumber and ");
    sb.append(" t2.mid = :mid ");

    Map<String, String> param = Map.of("mid", mid);
    List<OrdersNameForm> OrdersNameForm = template.query(sb.toString(),param,new BeanPropertyRowMapper<>(OrdersNameForm.class));
    return OrdersNameForm;
  }

  //예약조회(일반회원)
  @Override
  public List<Orders> orderFind(String mid){
    StringBuffer sb = new StringBuffer();
    sb.append("select * from orders where mid = :mid ");

    Map<String, String> param = Map.of("mid",mid);
    List<Orders> orders = template.query(sb.toString(),param,new BeanPropertyRowMapper<>(Orders.class));
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
    boolean isExist = false;
    String sql = "select count(mid) from members where mid = :mid ";

    Map<String, String> param = Map.of("mid", mid);
    log.info("param={}",param);
    Integer cnt = template.queryForObject(sql, param, Integer.class);
    isExist = (cnt > 0) ? true : false;
    return isExist;
  }

  // 닉네임 중복체크
  @Override
  public boolean nnIsExist(String nickname) {
    boolean isExist = false;
    String sql = "select count(nickname) from members where nickname = :nickname ";

    Map<String, String> param = Map.of("nickname", nickname);
    log.info("param={}",param);
    Integer cnt = template.queryForObject(sql, param, Integer.class);
    isExist = (cnt > 0) ? true : false;
    return isExist;
  }

  // 이메일 중복확인
  @Override
  public boolean mailIsExist(String email) {
    boolean isExist = false;
    String sql = "select count(email) from members where email = :email ";

    Map<String, String> param = Map.of("email", email);
    log.info("param={}",param);
    Integer cnt = template.queryForObject(sql, param, Integer.class);
    isExist = (cnt > 0) ? true : false;
    return isExist;
  }

  // 사업자 중복체크
  @Override
  public boolean bizIsExist(String businessnumber) {
    boolean isExist = false;
    String sql = "select count(nickname) from members where businessnumber = :businessnumber ";

    Map<String, String> param = Map.of("businessnumber", businessnumber);
    log.info("param={}",param);
    Integer cnt = template.queryForObject(sql, param, Integer.class);
    isExist = (cnt > 0) ? true : false;
    return isExist;
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
  
  // 비밀번호 찾기
  @Override
  public void changePasswd(String mid, String pw) {
    StringBuffer sql = new StringBuffer();
    sql.append("update members ");
    sql.append("   set pw = :pw ");
    sql.append(" where mid = :mid ");

    Map<String, String> param = Map.of("mid",mid,"pw",pw);
    template.update(sql.toString(),param);
  }

}
