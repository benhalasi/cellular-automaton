package io.benph.sp.cellsim.grid;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;


public class Grid <C extends Cell> {

  private final int width;
  private final int height;
  private final boolean finite;

  private final List<List<Point<C>>> grid;

  public Grid(int w, int h, boolean finite) {
    this.width = w;
    this.height = h;
    this.finite = finite;

    this.grid = new ArrayList<>(h);
  }

  public void init (BiFunction<Integer, Integer, C> cellSupplier) {
    init(grid, cellSupplier);
  }

  private void init(List<List<Point<C>>> grid, BiFunction<Integer, Integer, C> cellSupplier) {
    for ( int y = 0; y < height; y++ ){
      var row = new ArrayList<Point<C>>(width);
      grid.add(row);
      for ( int x = 0; x < width; x++ ){
        var aCell = cellSupplier.apply(x, y);
        var bCell = cellSupplier.apply(x, y);
        row.add(new Point<C>(aCell, bCell, x, y));
      }
    }
  }

  public void calcNeighbours (
    double radius,
    Function<Integer, C[]> arraySupplier,
    Consumer<Integer> clbk
  ) {
    var nVectors = Utils.getNCircle(radius);

    double[] dists = new double[nVectors.size()];
    for (int vi = 0; vi < nVectors.size(); ++vi) {
      dists[vi] = nVectors.get(vi).length;
    }

    AtomicInteger c = new AtomicInteger(1);
    grid.parallelStream()
    .forEach(row -> {
      for (var p : row) {
        C[] ans = arraySupplier.apply(nVectors.size());
        C[] bns = arraySupplier.apply(nVectors.size());
        for (int vi = 0; vi < nVectors.size(); ++vi) {
          var v = nVectors.get(vi);
          Point<C> n = getNeighbour(p.x, p.y, v);
          if (n == null) continue;
          ans[vi] = n.a;
          bns[vi] = n.b;
        }
        p.ans = ans;
        p.bns = bns;
        p.dists = dists;
      }
      clbk.accept(c.getAndIncrement());
    });
  }

  public Point<C> getNeighbour (int cx, int cy, Vector delta) {
    int x = cx + delta.x;
    int y = cy + delta.y;
    if (x >= width || x < 0){
      if(finite) return null;
      x = (x+width) % width;
    }
    if (y >= height || y < 0){
      if(finite) return null;
      y = (y+height) % height;
    }
    return grid.get(y).get(x);
  }

  public List<List<Point<C>>> getGrid() {
    return grid;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public boolean isFinite() {
    return finite;
  }
}
