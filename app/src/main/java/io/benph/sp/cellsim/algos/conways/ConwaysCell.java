package io.benph.sp.cellsim.algos.conways;

import io.benph.sp.cellsim.grid.Cell;

public class ConwaysCell extends Cell {

  private boolean alive;

  public ConwaysCell(int x, int y) {
    super(x, y);
  }

  public ConwaysCell(int x, int y, boolean isAlive) {
    super(x, y);
    this.alive = isAlive;
  }

  public boolean isAlive() {
    return alive;
  }

  public void setAlive(boolean alive) {
    this.alive = alive;
  }
}
