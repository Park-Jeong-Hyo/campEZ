package com.campEZ.Project0.post.dao;

import com.campEZ.Project0.entity.Post;
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

@Slf4j
@Repository
@RequiredArgsConstructor
public class PostDAOImpl implements PostDAO{
  private final NamedParameterJdbcTemplate template;

  //게시글 목록
  // 현재 공지사항(ptype = 'n')일 경우만 구현했으나
  // 컨트롤러에서 where ptype 부분이 입력이 가능한가 아닌가 따라서 추후 수정 혹은 추가 예정
  @Override
  public List<Post> postList() {
    StringBuffer sql = new StringBuffer();
    sql.append("select pnumber, nickname, ptitle, udate, ptext, ptype ");
    sql.append("from post ");

    List<Post> list = template.query(
        sql.toString(),
        BeanPropertyRowMapper.newInstance(Post.class)
    );
    return list;
  }

  //게시글 작성
  //게시글 작성 후 작성한 게시글로 넘어가게 하려함.
  //생성된 게시글 번호를 전달 함으로써 게시글 조회가 가능하게 하려함.
  @Override
  public int postSave(Post post) {
    StringBuffer sql = new StringBuffer();
    sql.append("INSERT INTO POST ( pnumber, nickname, ptitle, ptext, ptype) ");
    sql.append("VALUES ( pnumber_seq.nextval, :nickname, :ptitle, :ptext, :ptype) ");

    SqlParameterSource param = new BeanPropertySqlParameterSource(post);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    template.update(sql.toString(), param, keyHolder, new String[]{"pnumber"});
    int pnumber = keyHolder.getKey().intValue();
    post.setPnumber(pnumber);
    return pnumber;
  }

  //게시글 수정
  //게시글 번호와 정보를 받아서 수정
  //리턴값이 1인 경우 수정 성공 (row를 하나 수정)
  @Override
  public int postUpdate(int pnumber, Post post) {
    StringBuffer sql = new StringBuffer();
    sql.append("UPDATE POST ");
    sql.append("SET    ptitle = :ptitle, ");
    sql.append("        ptext = :ptext ");
    sql.append("WHERE pnumber = :pnumber ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("ptitle", post.getPtitle())
        .addValue("ptext", post.getPtext())
        .addValue("pnumber", pnumber);
    return template.update(sql.toString(), param);

    }

  //게시글 삭제
  //게시글 번호를 참조해서 게시글을 삭제
  //리턴값이 1일 경우 삭제 성공 (row를 하나 삭제)
  @Override
  public int postDelete(int pnumber) {
    StringBuffer sql = new StringBuffer();
    sql.append("DELETE FROM POST ");
    sql.append("WHERE pnumber = :pnumber ");

    Map<String, Integer> param = Map.of("pnumber", pnumber);
    return template.update(sql.toString(), param);
  }

  //게시글 상세조회
  //게시글 번호를 참조해서 게시글 조회
  //null 값을 가질 수 있는 객체를 감싸는 컨테이너 클래스 optional을 사용,
  //nullpointerException을 방지하기 위함.
  @Override
  public Optional<Post> postDetail(int pnumber) {
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT * FROM POST ");
    sql.append("WHERE pnumber = :pnumber ");
    try {
      Map<String, Integer> param = Map.of("pnumber", pnumber);

      Post post = template.queryForObject(
              sql.toString(),
              param,
              BeanPropertyRowMapper.newInstance(Post.class)
      );
      //Optional.of(value): value가 null이 아닌 값으로 Optional객체 생성
      //null이면 nullpointerException 발생
      return Optional.of(post);
    } catch (EmptyResultDataAccessException e) {
      //Optional.of(value)빈 optional객체 생성
      return Optional.empty();
    }
  }
}
