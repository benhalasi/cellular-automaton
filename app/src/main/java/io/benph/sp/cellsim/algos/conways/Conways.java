package io.benph.sp.cellsim.algos.conways;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.benph.sp.cellsim.runner.Algorithm;

public class Conways implements Algorithm<ConwaysCell> {

  @Override
  public void iterate (
    ConwaysCell origin,
    ConwaysCell next,
    ConwaysCell[] neighbours,
    double[] dists
  ) {
    int population = 0;
    for (var neighbour : neighbours) {
      if ( neighbour != null && neighbour.isAlive() ) population ++;
    }

    if ( population == 3 ){
      next.setAlive(true);
    } else
    if ( origin.isAlive() && population == 2 ) {
      next.setAlive(true);
    } else {
      next.setAlive(false);
    }
  }

  @Override
  public Map<Long, Integer> export(Stream<ConwaysCell> iteration, int width) {
    return iteration.parallel()
    .filter(c -> c.isAlive())
    .collect(Collectors.toMap(c -> Math.multiplyFull(c.y, width) + c.x, c -> 16777215));
  }
}
