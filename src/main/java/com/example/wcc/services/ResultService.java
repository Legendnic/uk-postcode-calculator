package com.example.wcc.services;

import com.example.wcc.models.PostcodeRef;
import com.example.wcc.models.Result;
import com.example.wcc.repositories.ResultRepository;
import org.openapitools.model.DistanceRequest;
import org.openapitools.model.DistanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ResultService {
  private final ResultRepository resultRepository;

  @Autowired
  public ResultService(ResultRepository resultRepository) {
    this.resultRepository = resultRepository;
  }

  public void saveResult(DistanceRequest request, DistanceResponse response, HashMap<String, PostcodeRef> postcodeRefs) {
    try {
      Result result = new Result();
      result.setPostcode1(request.getPostcode1());
      result.setPostcode2(request.getPostcode2());
      result.setDistance(response.getDistance());
      result.setRefPostcodeId1(postcodeRefs.get("postcode1").getId());
      result.setRefPostcodeId2(postcodeRefs.get("postcode2").getId());
      resultRepository.save(result);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e.getCause());
    }
  }
}
