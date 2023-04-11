package com.campEZ.Project0.camping.svc;

import com.campEZ.Project0.camping.dao.CampingDAO;
import com.campEZ.Project0.camping.dao.CampingFilterCondition;
import com.campEZ.Project0.entity.Camparea;
import com.campEZ.Project0.entity.Camping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CampingSVCImpl implements CampingSVC{

  private final CampingDAO campingDAO;

  @Override
  public List<Object> campingSave(Camping camping, Camparea camparea) {
    return campingDAO.campingSave(camping, camparea);
  }

  @Override
  public int campingUpdate(Camping camping, Camparea camparea, int cnumber) {
    return campingDAO.campingUpdate(camping, camparea, cnumber);
  }

  @Override
  public int campingDelete(int cnumber) {
    return campingDAO.campingDelete(cnumber);
  }

  @Override
  public Optional<Camping> campingDetail(int cnumber) {
    return campingDAO.campingDetail(cnumber);
  }
  @Override
  public List<Camping> campingFindByManagerMid(String mid){return campingDAO.campingFindByManagerMid(mid);}

  @Override
  public List<Camping> campingSearch(CampingFilterCondition campingFilterCondition) {
    return campingDAO.campingSearch(campingFilterCondition);
  }
}
