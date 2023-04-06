package com.campEZ.Project0.common.uploadFile.dao;

import com.campEZ.Project0.entity.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class uploadFileDAOImpl implements uploadFileDAO{

  private final NamedParameterJdbcTemplate template;

  //업로드 파일 등록(하나)
  @Override
  public int fileSaveSingle(UploadFile uploadFile) {
    StringBuffer sql = fileSaveSql();
    SqlParameterSource param = new BeanPropertySqlParameterSource(uploadFile);
    KeyHolder keyholder = new GeneratedKeyHolder();
    template.update(sql.toString(), param, keyholder, new String[]{"upnumber"});
    return keyholder.getKey().intValue();
  }

  @Override
  public void fileSaveMultiple(List<UploadFile> uploadFiles) {
  }

  //업로드 파일 sql구문
  private StringBuffer fileSaveSql() {
    StringBuffer sql = new StringBuffer();
    sql.append("INSERT INTO UPLOADFILE ");
    sql.append("(upnumber, position, reference, storename, uploadname, fsize, ftype) ");
    sql.append("(upnumber_seq.netxtval, :position, :reference, :storename, :uploadname, :fsize, :ftype) ");
    return sql;
  }
}
