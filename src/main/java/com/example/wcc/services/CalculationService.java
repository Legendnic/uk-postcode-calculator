package com.example.wcc.services;

import com.example.wcc.models.PostcodeRef;
import org.openapitools.model.DistanceResponse;
import org.openapitools.model.PostcodeResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CalculationService {
  private final static double EARTH_RADIUS = 6371; // radius in kilometers
  private double calculateDistance(double latitude, double longitude, double latitude2, double longitude2) {
    // Using Haversine formula! See Wikipedia;
    double lon1Radians = Math.toRadians(longitude);
    double lon2Radians = Math.toRadians(longitude2);
    double lat1Radians = Math.toRadians(latitude);
    double lat2Radians = Math.toRadians(latitude2);
    double a = haversine(lat1Radians, lat2Radians) + Math.cos(lat1Radians) * Math.cos(lat2Radians) * haversine(lon1Radians, lon2Radians);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return (EARTH_RADIUS * c);
  }
  private double haversine(double deg1, double deg2) { return square(Math.sin((deg1 - deg2) / 2.0));
  }
  private double square(double x) { return x * x;
  }

  public double initCalculation(PostcodeRef data1, PostcodeRef data2) {
    return calculateDistance(
      data1.getLatitude()!=null ? data1.getLatitude().doubleValue() : 0,
      data1.getLongitude()!=null ? data1.getLongitude().doubleValue() : 0,
      data2.getLatitude()!=null ? data2.getLatitude().doubleValue() : 0,
      data2.getLongitude()!=null ? data2.getLongitude().doubleValue() : 0
    );
  }

  public DistanceResponse getResponse(double distance, HashMap<String, PostcodeRef>  postcodeRefs) {
    DistanceResponse distanceResponse = new DistanceResponse();
    distanceResponse.setDistance(distance + " km");
    PostcodeResponse postcodeResponse1 = new PostcodeResponse();
    postcodeResponse1.setPostcode(postcodeRefs.get("postcode1").getPostcode());
    postcodeResponse1.setLatitude(postcodeRefs.get("postcode1").getLatitude());
    postcodeResponse1.setLongitude(postcodeRefs.get("postcode1").getLongitude());
    distanceResponse.setPostcode1(postcodeResponse1);

    PostcodeResponse postcodeResponse2 = new PostcodeResponse();
    postcodeResponse2.setPostcode(postcodeRefs.get("postcode2").getPostcode());
    postcodeResponse2.setLatitude(postcodeRefs.get("postcode2").getLatitude());
    postcodeResponse2.setLongitude(postcodeRefs.get("postcode2").getLongitude());

    distanceResponse.setPostcode2(postcodeResponse2);
    return distanceResponse;
  }
}
