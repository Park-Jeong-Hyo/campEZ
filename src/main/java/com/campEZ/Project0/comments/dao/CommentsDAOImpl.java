package com.campEZ.Project0.comments.dao;

import com.campEZ.Project0.entity.Comments;
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

import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CommentsDAOImpl implements CommentsDAO{
  
  private final NamedParameterJdbcTemplate template;

  //댓글 작성
  @Override
  public Comments commentsSave(Comments comments) {
    //sql구문 작성
    StringBuffer sql = new StringBuffer();
    sql.append("INSERT INTO COMMENTS ");
    sql.append("(conumber, pnumber, nickname, cotext) ");
    sql.append("VALUES ");
    sql.append("(conumber_seq.nextval, :pnumber, :nickname, :cotext) ");
    // 파라미터 설정, id값 설정
    SqlParameterSource param = new BeanPropertySqlParameterSource(comments);
    KeyHolder keyholder = new GeneratedKeyHolder();
    // db 업데이트, id값을 dto에 추가한 후 리턴
    template.update(sql.toString(), param, keyholder, new String[]{"conumber"});
    int conumber = keyholder.getKey().intValue();
    comments.setConumber(conumber);
    return comments;
  }

  //댓글 수정
  //리턴 값으로 수정된 열의 갯수가 온다.
  @Override
  public int commentsUpdate(int conumber, Comments comments) {
    StringBuffer sql = new StringBuffer();
    sql.append("UPDATE COMMENTS ");
    sql.append("SET    cotext  = :cotext, ");
    sql.append("WHERE conumber = :conumber ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("cotext", comments.getCotext())
        .addValue("conumber", conumber);

    return template.update(sql.toString(), param);
  }

  //수동적 댓글 삭제(게시글 삭제될 경우 댓글도 같이 삭제됨)
  @Override
  public int commentsDeletePassive(int pnumber) {
    StringBuffer sql = new StringBuffer();
    sql.append("DELETE FROM COMMENTS ");
    sql.append("WHERE pnumber = :pnumber");

    Map<String, Integer> param = Map.of("pnumber", pnumber);
    return template.update(sql.toString(),param);
  }

  //능동적 댓글 삭제(댓글 작성자가 댓글 삭제)
  @Override
  public int commentsDeleteActive(int conumber) {
    StringBuffer sql = new StringBuffer();
    sql.append("DELETE FROM COMMENTS ");
    sql.append("WHERE conumber = :conumber");

    Map<String, Integer> param = Map.of("conumber", conumber);
    return template.update(sql.toString(), param);
  }

  //댓글 조회
  @Override
  public Optional<Comments> commentsDetail(int conumber) {
    StringBuffer sql = new StringBuffer();
    //쿼리가 conumber, pnumber을 묻고 있지 않기 때문에 결과값이 conumber, pnumber가 0이 됨.
    sql.append("SELECT nickname, cotext, udate ");
    sql.append("FROM COMMENTS ");
    sql.append("WHERE conumber = :conumber");

    try {
      Map<String, Integer> param = Map.of("conumber", conumber);
      Comments comments = template.queryForObject(sql.toString(),param, BeanPropertyRowMapper.newInstance(Comments.class));
      return Optional.of(comments);
    } catch(EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }
}
