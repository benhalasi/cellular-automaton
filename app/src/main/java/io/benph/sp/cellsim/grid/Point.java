package io.benph.sp.cellsim.grid;

public class Point <C extends Cell> {
  public final int x;
  public final int y;
  public final C a;
  public final C b;
  public C[] ans;
  public C[] bns;
  public double[] dists;

  public Point(C a, C b, int x, int y) {
    this.a = a;
    this.b = b;
    this.x = x;
    this.y = y;
  }

  public C get(State state){
    if ( state == State.A ) return a;
    return b;
  }

  public C[] getNs(State state){
    if ( state == State.A ) return ans;
    return bns;
  }

  public static final class Neighbour <C extends Cell> {
    public final C cell;
    public final double dist;
    public Neighbour(C cell, double dist) {
      this.cell = cell;
      this.dist = dist;
    }
  }

  public static enum State {
    A, B;

    public State opposite () {
      return oppositeOf(this);
    }

    public static State oppositeOf(State state) {
      if( state == A ) return B;
      return A;
    }
  }
}
