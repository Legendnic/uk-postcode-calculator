package com.example.wcc.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name = "postcode_refs")
public class PostcodeRef {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "postcode_refs_seq")
  @SequenceGenerator(name = "postcode_refs_seq", sequenceName = "postcode_refs_seq", allocationSize = 1)
  private Integer id;

  private Integer sourceId;
  private String postcode;
  private BigDecimal latitude;
  private BigDecimal longitude;

  private Long checksum;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getSourceId() {
    return sourceId;
  }

  public void setSourceId(Integer sourceId) {
    this.sourceId = sourceId;
  }

  public String getPostcode() {
    return postcode;
  }

  public void setPostcode(String postcode) {
    this.postcode = postcode;
  }

  public BigDecimal getLatitude() {
    return latitude;
  }

  public void setLatitude(BigDecimal latitude) {
    this.latitude = latitude;
  }

  public BigDecimal getLongitude() {
    return longitude;
  }

  public void setLongitude(BigDecimal longitude) {
    this.longitude = longitude;
  }

  public Long getChecksum() {
    return checksum;
  }

  public void setChecksum(Long checksum) {
    this.checksum = checksum;
  }
}
