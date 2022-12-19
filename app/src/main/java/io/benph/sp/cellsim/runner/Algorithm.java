package io.benph.sp.cellsim.runner;

import java.util.Map;
import java.util.stream.Stream;

import io.benph.sp.cellsim.grid.Cell;

public interface Algorithm <C extends Cell> {
  public void iterate (
    C origin,
    C next,
    C[] neighbours,
    double[] dists
  );

  public Map<Long, Integer> export (
    Stream<C> iteration, int width
  );
}
