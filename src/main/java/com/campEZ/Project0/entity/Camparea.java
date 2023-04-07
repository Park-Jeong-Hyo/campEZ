package com.campEZ.Project0.entity;

import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Camparea{
  private int cnumber;
  private int area;
  private int capacitys;
}
