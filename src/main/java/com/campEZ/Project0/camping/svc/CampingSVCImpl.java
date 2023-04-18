package com.campEZ.Project0.camping.svc;

import com.campEZ.Project0.camping.dao.CampingDAO;
import com.campEZ.Project0.camping.dao.CampingFilterCondition;
import com.campEZ.Project0.entity.Camparea;
import com.campEZ.Project0.entity.Camping;
import com.campEZ.Project0.entity.UploadFile;
import com.campEZ.Project0.uploadfile.UploadFileSVC;
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
  private final UploadFileSVC uploadFileSVC;
  @Override
  public Camping campingSave(Camping camping) {
    return campingDAO.campingSave(camping);
  }

  @Override
  public Camping campingSave(Camping camping, List<UploadFile> uploadFiles) {
    int cnumber = campingSave(camping).getCnumber();
    if (uploadFiles.size() > 0) {
      uploadFiles.stream().forEach(file->file.setRid(cnumber));
      uploadFileSVC.addFiles(uploadFiles);
    }

    return camping;
  }

  @Override
  public Camparea campareaSave(Camparea camparea) {
    return campingDAO.campareaSave(camparea);
  }
  @Override
  public int campingUpdate(Camping camping, int cnumber) {
    return campingDAO.campingUpdate(camping, cnumber);
  }

  @Override
  public int campareaUpdate(Camparea camparea) {
    return campingDAO.campareaUpdate(camparea);
  }

  @Override
  public int campingDelete(int cnumber) {
    return campingDAO.campingDelete(cnumber);
  }

  @Override
  public int campareaDelete(int area) {
    return campingDAO.campareaDelete(area);
  }

  @Override
  public Optional<Camping> campingDetail(int cnumber) {
    return campingDAO.campingDetail(cnumber);
  }

  @Override
  public Optional<List<Camparea>> campareaDetail(int cnumber) {
    return campingDAO.campareaDetail(cnumber);
  }

  @Override
  public List<Camping> campingFindByManagerMid(String mid){return campingDAO.campingFindByManagerMid(mid);}

  @Override
  public List<Camping> campingSearch(CampingFilterCondition campingFilterCondition) {
    return campingDAO.campingSearch(campingFilterCondition);
  }
}
