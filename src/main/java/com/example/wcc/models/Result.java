package com.example.wcc.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name = "results")
public class Result {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "result_seq")
  @SequenceGenerator(name = "result_seq", sequenceName = "result_seq", allocationSize = 1)
  private Integer id;

  private String postcode1;
  private String postcode2;

  @Column(columnDefinition = "TEXT")
  private String distance;
  private Integer refPostcodeId1;
  private Integer refPostcodeId2;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getPostcode1() {
    return postcode1;
  }

  public void setPostcode1(String postcode1) {
    this.postcode1 = postcode1;
  }

  public String getPostcode2() {
    return postcode2;
  }

  public void setPostcode2(String postcode2) {
    this.postcode2 = postcode2;
  }

  public String getDistance() {
    return distance;
  }

  public void setDistance(String distance) {
    this.distance = distance;
  }

  public Integer getRefPostcodeId1() {
    return refPostcodeId1;
  }

  public void setRefPostcodeId1(Integer refPostcodeId1) {
    this.refPostcodeId1 = refPostcodeId1;
  }

  public Integer getRefPostcodeId2() {
    return refPostcodeId2;
  }

  public void setRefPostcodeId2(Integer refPostcodeId2) {
    this.refPostcodeId2 = refPostcodeId2;
  }
}
