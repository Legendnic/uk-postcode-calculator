package com.example.wcc.controllers;

import com.example.wcc.models.PostcodeRef;
import com.example.wcc.services.CalculationService;
import com.example.wcc.services.RefsService;
import com.example.wcc.services.ResultService;
import org.openapitools.api.DistanceApi;
import org.openapitools.model.DistanceRequest;
import org.openapitools.model.DistanceResponse;
import org.openapitools.model.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class DistanceController implements DistanceApi {

  private final CalculationService calculationService;
  private final RefsService refsService;

  private final ResultService resultService;

  @Autowired
  public DistanceController(CalculationService calculationService, RefsService refsService,
    ResultService resultService) {
    this.calculationService = calculationService;
    this.refsService = refsService;
    this.resultService = resultService;
  }

  @Override
  public ResponseEntity<SuccessResponse> calculateDistance(DistanceRequest distanceRequest) {
    HashMap<String, PostcodeRef> postcodeRefs = refsService.getPostcodeMap(distanceRequest);
    double distance = calculationService.initCalculation(postcodeRefs.get("postcode1"),
      postcodeRefs.get("postcode2"));

    DistanceResponse distanceResponse = calculationService.getResponse(distance, postcodeRefs);

    resultService.saveResult(distanceRequest, distanceResponse, postcodeRefs);
    SuccessResponse successResponse = new SuccessResponse();
    successResponse.setData(distanceResponse);
    return ResponseEntity.ok(successResponse);
  }
}
