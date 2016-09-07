package com.example.xaocu.feedsearching.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Iurii Kushyk on 06.09.2016.
 */
public class OnlineFeed implements BaseFeed{
  @JsonIgnore
  private String date;

  private String volume;
  private String ex_dividend;
  private String split_ratio;
  private String adj_open;
  private String adj_high;
  private String adj_low;
  private String adj_close;
  private String adj_volume;
  private String open;
  private String high;
  private String low;
  private String close;


  public String getOpen() {
    return open;
  }

  public void setOpen(String open) {
    this.open = open;
  }

  public String getLow() {
    return low;
  }

  public void setLow(String low) {
    this.low = low;
  }

  public String getClose() {
    return close;
  }

  public void setClose(String close) {
    this.close = close;
  }

  public String getHigh() {
    return high;
  }

  public void setHigh(String high) {
    this.high = high;
  }

  public boolean isFull() {
    return open != null && high != null && close != null && low != null;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getEx_dividend() {
    return ex_dividend;
  }

  public void setEx_dividend(String ex_dividend) {
    this.ex_dividend = ex_dividend;
  }

  public String getSplit_ratio() {
    return split_ratio;
  }

  public void setSplit_ratio(String split_ratio) {
    this.split_ratio = split_ratio;
  }

  public String getAdj_open() {
    return adj_open;
  }

  public void setAdj_open(String adj_open) {
    this.adj_open = adj_open;
  }

  public String getAdj_high() {
    return adj_high;
  }

  public void setAdj_high(String adj_high) {
    this.adj_high = adj_high;
  }

  public String getAdj_low() {
    return adj_low;
  }

  public void setAdj_low(String adj_low) {
    this.adj_low = adj_low;
  }

  public String getAdj_close() {
    return adj_close;
  }

  public void setAdj_close(String adj_close) {
    this.adj_close = adj_close;
  }

  public String getAdj_volume() {
    return adj_volume;
  }

  public void setAdj_volume(String adj_volume) {
    this.adj_volume = adj_volume;
  }

  public String getVolume() {
    return volume;
  }

  public void setVolume(String volume) {
    this.volume = volume;
  }
}
