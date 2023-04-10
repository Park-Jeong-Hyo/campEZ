package com.campEZ.Project0.camping.svc;

import com.campEZ.Project0.camping.dao.CampingFilterCondition;
import com.campEZ.Project0.entity.Camparea;
import com.campEZ.Project0.entity.Camping;

import java.util.List;
import java.util.Optional;

public interface CampingSVC {
  List<Object> campingSave(Camping camping, Camparea camparea);
  int campingUpdate(Camping camping, Camparea camparea, int cnumber);
  int campingDelete(int cnumber);
  Optional<Camping> campingDetail(int cnumber);
 List<Camping> campingFindByManagerMid(String mid);
  List<Camping> campingSearch(CampingFilterCondition campingFilterCondition);
}
