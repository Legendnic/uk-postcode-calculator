package com.example.wcc.services;

import com.example.wcc.models.PostcodeRef;
import com.example.wcc.repositories.PostcodeRefRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.openapitools.model.DistanceRequest;
import org.openapitools.model.Postcodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

@Service
public class RefsService {
  private final PostcodeRefRepository postcodeRefRepository;
  Logger logger = Logger.getLogger("RefsService");

  @Autowired
  public RefsService(PostcodeRefRepository postcodeRefRepository) {
    this.postcodeRefRepository = postcodeRefRepository;
  }

  public List<PostcodeRef> getAllPostcodes(Integer page, Integer size) {

    PageRequest pageRequest =
      PageRequest.of(page != null && page > 0 ? page - 1 : 0, size != null ? size : 10);
    Page<PostcodeRef> postcodeRefs = postcodeRefRepository.findAll(pageRequest);
    return postcodeRefs.getContent();
  }

  public void uploadPostcodes(MultipartFile file) {
    try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
      CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()) {

      String[] line;
      List<PostcodeRef> postcodeRefs = new ArrayList<>();
      while ((line = csvReader.readNext()) != null) {
        String id = line[0] != null && !line[0].isEmpty() ? line[0] : null;
        String postcode = line[1] != null && !line[1].isEmpty() ? line[1] : null;
        String latitude = line[2] != null && !line[2].isEmpty() ? line[2] : null;
        String longitude = line[3] != null && !line[3].isEmpty() ? line[3] : null;

        if (id == null) {
          continue;
        }
        Optional<PostcodeRef> postcodeRef = this.postcodeRefRepository.findBySourceId(
          Integer.valueOf(id));

        BigDecimal latitude1 = latitude != null ? new BigDecimal(latitude) : null;
        BigDecimal longitude1 = longitude != null ? new BigDecimal(longitude) : null;
        if (postcodeRef.isPresent()) {
          PostcodeRef ref = postcodeRef.get();
          long checksum = checksum(id, postcode, latitude, longitude);
          if (checksum != ref.getChecksum()) {
            logger.log(
              java.util.logging.Level.INFO,
              "Updating postcode ref: " + postcode + " with checksum: " + checksum
            );
            ref.setPostcode(postcode);
            ref.setLatitude(latitude1);
            ref.setLongitude(longitude1);
            ref.setChecksum(checksum);
            postcodeRefs.add(ref);
          } else {
            logger.log(
              java.util.logging.Level.INFO,
              "Skipping postcode ref: " + postcode + " with checksum: " + checksum
            );
          }
        } else {
          PostcodeRef ref = new PostcodeRef();
          ref.setSourceId(Integer.parseInt(line[0]));
          ref.setPostcode(postcode);
          ref.setLatitude(latitude1);
          ref.setLongitude(longitude1);
          ref.setChecksum(checksum(id, postcode, latitude, longitude));
          postcodeRefs.add(ref);
        }

      }
      if (!postcodeRefs.isEmpty()) {
        postcodeRefRepository.saveAll(postcodeRefs);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
    }
  }

  public long checksum(String id, String postcode, String latitude, String longitude) {
    String combined = id + postcode + latitude + longitude;
    byte[] bytes = combined.getBytes();
    Checksum checksum = new CRC32();
    checksum.update(bytes, 0, bytes.length);
    return checksum.getValue();
  }

  public Optional<PostcodeRef> getPostcodeById(Integer id) {
    return postcodeRefRepository.findById(id);
  }

  public PostcodeRef updatePostcodeById(Integer id, Postcodes postcodes) {
    Optional<PostcodeRef> postcodeRef = postcodeRefRepository.findById(id);
    if (postcodeRef.isPresent()) {
      PostcodeRef ref = postcodeRef.get();
      ref.setPostcode(postcodes.getPostcode());
      ref.setLatitude(postcodes.getLatitude());
      ref.setLongitude(postcodes.getLongitude());
      ref.setChecksum(checksum(String.valueOf(ref.getSourceId()), postcodes.getPostcode(),
        String.valueOf(postcodes.getLatitude()), String.valueOf(postcodes.getLongitude())));
      return postcodeRefRepository.save(ref);
    }
    return null;
  }

  public HashMap<String, PostcodeRef> getPostcodeMap(DistanceRequest distanceRequest) {
    HashMap<String, PostcodeRef> postcodeMap = new HashMap<>();
    Optional<PostcodeRef> postcode1 = postcodeRefRepository.findByPostcode(
      distanceRequest.getPostcode1());
    Optional<PostcodeRef> postcode2 = postcodeRefRepository.findByPostcode(
      distanceRequest.getPostcode2());
    postcode1.orElseThrow(() -> new RuntimeException("Postcode1 not found"));
    postcode2.orElseThrow(() -> new RuntimeException("Postcode2 not found"));
    postcodeMap.put("postcode1", postcode1.get());
    postcodeMap.put("postcode2", postcode2.get());
    return postcodeMap;
  }
}
