package com.example.wcc.controllers;

import com.example.wcc.models.PostcodeRef;
import com.example.wcc.services.RefsService;
import org.openapitools.api.RefsApi;
import org.openapitools.model.Postcodes;
import org.openapitools.model.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class RefsController implements RefsApi {

  private final RefsService refsService;

  @Autowired
  public RefsController(RefsService refsService) {
    this.refsService = refsService;
  }

  @Override
  public ResponseEntity<SuccessResponse> getPostcodes(Integer page, Integer size) {
    SuccessResponse successResponse = new SuccessResponse();
    successResponse.setData(refsService.getAllPostcodes(page, size));
    return ResponseEntity.ok(successResponse);
  }

  @Override
  public ResponseEntity<SuccessResponse> uploadPostcodes(MultipartFile file) {
    SuccessResponse successResponse = new SuccessResponse();
    refsService.uploadPostcodes(file);
    successResponse.setData(file.getName());
    return ResponseEntity.ok(successResponse);
  }

  @Override
  public ResponseEntity<SuccessResponse> getPostcodeById(Integer id) {
    SuccessResponse successResponse = new SuccessResponse();
    successResponse.setData(refsService.getPostcodeById(id));
    return ResponseEntity.ok(successResponse);
  }

  @Override
  public ResponseEntity<SuccessResponse> updatePostcodeById(Integer id, Postcodes postcodes) {
    PostcodeRef postcodeRef = this.refsService.updatePostcodeById(id, postcodes);
    SuccessResponse successResponse = new SuccessResponse();
    successResponse.setData(postcodeRef);
    return ResponseEntity.ok(successResponse);
  }


}
