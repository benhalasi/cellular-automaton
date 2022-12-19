package io.benph.sp.cellsim.algos.tcc;

import java.awt.Color;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.benph.sp.cellsim.runner.Algorithm;

public class TriColorConways implements Algorithm<TriColorConwaysCell> {

  // private final int capacity = 383;

  private final double overPopulation;
  private final double acceptablePopulation;
  private final double optimalPopulation;
  private final double underPopulation;

  public TriColorConways(int maxNumberOfNeighbours, double radius) {
    overPopulation       = maxNumberOfNeighbours * 4 / 8;
    optimalPopulation    = maxNumberOfNeighbours * 2 / 8 +1;
    acceptablePopulation = maxNumberOfNeighbours * 1 / 8 +1;
    underPopulation      = 0;

    System.out.println(
      "mns: " + maxNumberOfNeighbours + "\n"
    + "ovp:" +  overPopulation + "\n"
    + "opp:" +  optimalPopulation + "\n"
    + "acp:" +  acceptablePopulation + "\n"
    + "unp:" +  underPopulation
    );
  }

  @Override
  public void iterate(
    TriColorConwaysCell origin,
    TriColorConwaysCell next,
    TriColorConwaysCell[] neighbours,
    double[] dists
  ) {
    boolean or = origin.getColor().getRed() > 0;
    int r = 0;
    boolean og = origin.getColor().getGreen() > 0;
    int g = 0;
    boolean ob = origin.getColor().getBlue() > 0;
    int b = 0;

    for (var n : neighbours) {
      if ( n != null && n.isAlive() ) {
        r += (n.getColor().getRed() > 0)? 1 : 0;
        g += (n.getColor().getGreen() > 0)? 1 : 0;
        b += (n.getColor().getBlue() > 0)? 1 : 0;
      };
    }

    var rx = iterateColor(or, r)? r : 0;
    var gx = iterateColor(og, g)? g : 0;
    var bx = iterateColor(ob, b)? b : 0;

    r = g = b = 0;

    if ( rx > gx && rx > bx ){
      // r = 255;
      next.setColor(mix(.5d, origin.getColor(), 255, 0, 0));
    }

    if ( gx > rx && gx > bx ){
      // g = 255;
      next.setColor(mix(.5d, origin.getColor(), 0, 255, 0));
    }

    if ( bx > rx && bx > gx ){
      // b = 255;
      next.setColor(mix(.5d, origin.getColor(), 0, 0, 255));
    }

    if ( rx == gx && gx == bx ){
      next.setColor(Color.BLACK);
    } else {
      // next.setColor(mix(.5d, origin.getColor(), r, g, b));
    }

  }

  private boolean iterateColor (
    boolean o, int population
  ) {
    if ( optimalPopulation <= population && population < overPopulation ){
      return true;
    }
    if ( o && acceptablePopulation <= population && population < optimalPopulation  ) {
      return true;
    }
    return false;
  }

  public static Color mix(double percent, Color a, int r, int g, int b) {
    return new Color(
      (int) (a.getRed() * percent + r * (1.0 - percent)),
      (int) (a.getGreen() * percent + g * (1.0 - percent)),
      (int) (a.getBlue() * percent + b * (1.0 - percent))
    );
  }

  @Override
  public Map<Long, Integer> export(Stream<TriColorConwaysCell> iteration, int width) {
    return iteration.parallel()
    .filter(c -> ! c.getColor().equals(Color.BLACK))
    .collect(Collectors.toMap(
      c -> Math.multiplyFull(c.y, width) + c.x,
      c -> c.getColor().getRGB()
      ));
  }

}
