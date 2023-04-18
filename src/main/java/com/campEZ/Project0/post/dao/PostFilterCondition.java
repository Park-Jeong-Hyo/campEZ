package com.campEZ.Project0.post.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class PostFilterCondition {
  private int startRec;         //시작레코드번호
  private int endRec;           //종료레코드번호

}