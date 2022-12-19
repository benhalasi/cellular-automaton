package io.benph.sp.cellsim.grid;

public class Vector {
  public int x;
  public int y;
  public double length;
  public static Vector of() {
    return new Vector();
  }
  public static Vector of(int x, int y) {
    var v = Vector.of();
    v.x = x;
    v.y = y;
    return v;
  }
  public static Vector of(int x, int y, double length) {
    var v = Vector.of(x, y);
    v.length = length;
    return v;
  }
  @Override
  public String toString() {
    return String.format("[%d, %d] (%.2f)", x, y, length);
  }
}
