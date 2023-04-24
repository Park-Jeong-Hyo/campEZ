package com.campEZ.Project0.uploadfile;

import com.campEZ.Project0.entity.UploadFile;
import com.campEZ.Project0.web.AttachFileType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UploadFileDAOImpl implements com.campEZ.Project0.uploadfile.UploadFileDAO {

  private final NamedParameterJdbcTemplate template;

  //업로드파일 등록-단건
  @Override
  public Long addFile(UploadFile uploadFile) {

    StringBuffer sb = addFileSql();
    SqlParameterSource param = new BeanPropertySqlParameterSource(uploadFile);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    template.update(sb.toString(),param,keyHolder,new String[]{"upnumber"});

    return keyHolder.getKey().longValue();
  }

  //업로드파일 등록-여러건
  @Override
  public void addFiles(List<UploadFile> uploadFiles) {

    StringBuffer sb = addFileSql();
    if(uploadFiles.size() == 1){
      addFile(uploadFiles.get(0));
//      SqlParameterSource param = new BeanPropertySqlParameterSource(uploadFiles.get(0));
//      template.update(sql.toString(),param);
    }else {
      SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(uploadFiles);
      //배치 처리 : 여러건의 갱신작업을 한꺼번에 처리하므로 단건처리할때보다 성능이 좋다.
      template.batchUpdate(sb.toString(), params);
    }
  }

  private StringBuffer addFileSql() {
    StringBuffer sb = new StringBuffer();
    sb.append("INSERT INTO uploadfile ( ");
    sb.append("  upnumber, ");
    sb.append("  code, ");
    sb.append("  rid, ");
    sb.append("  storename, ");
    sb.append("  uploadname, ");
    sb.append("  fsize, ");
    sb.append("  ftype ");
    sb.append(") VALUES ( ");
    sb.append("  upnumber_seq.nextval, ");
    sb.append("  :code, ");
    sb.append("  :rid, ");
    sb.append("  :storename, ");
    sb.append("  :uploadname, ");
    sb.append("  :fsize, ");
    sb.append("  :ftype ");
    sb.append(") ");
    return sb;
  }

  @Override
  //메인페이지 이미지 조회
  public List<UploadFile> findFileByCode(AttachFileType attachFileType){
    StringBuffer sb = new StringBuffer();

    sb.append("SELECT  ");
    sb.append("   upnumber, ");
    sb.append("   code, ");
    sb.append("   rid,  ");
    sb.append("   storename, ");
    sb.append("   uploadname,  ");
    sb.append("   fsize,  ");
    sb.append("   ftype,  ");
    sb.append("   cdate,  ");
    sb.append("   udate   ");
    sb.append("  FROM uploadfile  ");
    sb.append(" WHERE CODE = :code  ");
    sb.append(" order by upnumber desc  ");

    return template.query(
        sb.toString(),
        Map.of("code",attachFileType.name()),
        BeanPropertyRowMapper.newInstance(UploadFile.class));
  }


  //캠핑장 페이지 이미지 조회
  @Override
  public List<UploadFile> findFilesByCodeWithRid(AttachFileType attachFileType, int rid) {
    StringBuffer sb = new StringBuffer();

    sb.append("SELECT  ");
    sb.append("   upnumber, ");
    sb.append("   code, ");
    sb.append("   rid,  ");
    sb.append("   storename, ");
    sb.append("   uploadname,  ");
    sb.append("   fsize,  ");
    sb.append("   ftype,  ");
    sb.append("   cdate,  ");
    sb.append("   udate ");
    sb.append("  FROM uploadfile  ");
    sb.append(" WHERE CODE = :code  ");
    sb.append("   AND RID = :rid  ");

    return template.query(
        sb.toString(),
        Map.of("code",attachFileType.name(),"rid",rid),
        BeanPropertyRowMapper.newInstance(UploadFile.class));
  }

  // 첨부파일 참고번호로 조회
  public List<UploadFile> findFilesByRid(int rid){
    StringBuffer sb = new StringBuffer();
    sb.append(" select * ");
    sb.append(" from uploadfile ");
    sb.append(" where rid = :rid ");

    return template.query(
        sb.toString(),
        Map.of("rid",rid),
        BeanPropertyRowMapper.newInstance(UploadFile.class));
  }

  //첨부파일 조회
  @Override
  public Optional<UploadFile> findFileByUploadFileId(int upnumber) {
    StringBuffer sql = new StringBuffer();
    sql.append(" select * ");
    sql.append("  from uploadfile ");
    sql.append(" where upnumber = :upnumber ");

    UploadFile uploadFile = null;
    try {
      Map<String, Integer> param = Map.of("upnumber", upnumber);
      uploadFile = template.queryForObject(sql.toString(),param,BeanPropertyRowMapper.newInstance(UploadFile.class));
      return Optional.of(uploadFile);
    }catch (EmptyResultDataAccessException e){
      return Optional.empty();
    }
  }

  // 첨부파일 삭제 by rid
  @Override
  public int deleteFileByRid(int rid){
    StringBuffer sb = new StringBuffer();
    sb.append("delete from uploadfile ");
    sb.append(" where rid = :rid ");

    return template.update(sb.toString(), Map.of("rid",rid));
  }

  // 첨부파일 삭제 by uplaodfileId
  @Override
  public int deleteFileByUploadFileId(int upnumber) {
    StringBuffer sb = new StringBuffer();
    sb.append("delete from uploadfile ");
    sb.append(" where upnumber = :upnumber ");

    return template.update(sb.toString(), Map.of("upnumber",upnumber));
  }

  // 첨부파일 삭제 by code, rid
  @Override
  public int deleteFileByCodeWithRid(AttachFileType attachFileType, int rid) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from uploadfile ");
    sql.append(" where code = :code ");
    sql.append("   and rid = :rid ");

    return template.update(sql.toString(),Map.of("code",attachFileType.name(),"rid",rid));
  }

}
