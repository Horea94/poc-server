package com.example.app.photos.data;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public enum ResizeType {
  ORIGINAL("original", "", -1, "_orig", 1),
  SMALL_W("small_w", "w", 300, "_sw", 1 << 1),
  MEDIUM_W("medium_w", "w", 500, "_mw", 1 << 2),
  LARGE_W("large_w", "w", 700, "_lw", 1 << 3),
  SMALL_H("small_h", "h", 300, "_sh", 1 << 4),
  MEDIUM_H("medium_h", "h", 500, "_mh", 1 << 5),
  LARGE_H("large_h", "h", 700, "_lh", 1 << 6);

  private final String name;
  private final String type;
  private final int size;
  private final String suffix;
  private final int flag;

  ResizeType(String name, String type, int size, String suffix, int flag) {
    this.name = name;
    this.type = type;
    this.size = size;
    this.suffix = suffix;
    this.flag = flag;
  }

  public static List<ResizeType> getAllWidthTypes() {
    List<ResizeType> result = new ArrayList<>();
    result.add(SMALL_W);
    result.add(MEDIUM_W);
    result.add(LARGE_W);
    return result;
  }

  public static List<ResizeType> getAllHeightTypes() {
    List<ResizeType> result = new ArrayList<>();
    result.add(SMALL_H);
    result.add(MEDIUM_H);
    result.add(LARGE_H);
    return result;
  }
}
