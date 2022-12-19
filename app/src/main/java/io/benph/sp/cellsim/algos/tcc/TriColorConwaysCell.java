package io.benph.sp.cellsim.algos.tcc;

import java.awt.Color;

import io.benph.sp.cellsim.grid.Cell;

public class TriColorConwaysCell extends Cell {

  private Color color = new Color(0f, 0f, 0f);

  public TriColorConwaysCell(int x, int y) {
    super(x, y);
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public boolean isAlive () {
    return color.getRGB() != 0;
  }
}
