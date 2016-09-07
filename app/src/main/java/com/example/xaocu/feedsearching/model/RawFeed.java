package com.example.xaocu.feedsearching.model;

/**
 * Created by Iurii Kushyk on 06.09.2016.
 */
public class RawFeed implements BaseFeed {
  private String abbreviation;

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getAbbreviation() {
    return abbreviation;
  }

  public void setAbbreviation(String abbreviation) {
    this.abbreviation = abbreviation;
  }

  private String fullName;
}
