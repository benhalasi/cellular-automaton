package io.benph.sp.cellsim.grid;

import java.util.ArrayList;
import java.util.List;

public class Utils {

  public static List<Vector> getNCircle (double radius) {
    List<Vector> res = new ArrayList<>();
    for ( int x = 0; x <= radius; x++) {
      for ( int y = 0; Math.sqrt(x * x + y * y) <= radius; y++ ) {
        var l = Math.sqrt(x * x + y * y);
        if( x > 0 && y > 0 ){
          res.add(Vector.of( x,  y, l));
          res.add(Vector.of( x, -y, l));
          res.add(Vector.of(-x,  y, l));
          res.add(Vector.of(-x, -y, l));
        }
        if( x == 0 && y > 0 ){
          res.add(Vector.of( x,  y, l));
          res.add(Vector.of( x, -y, l));
        }
        if( x > 0 && y == 0 ){
          res.add(Vector.of( x,  y, l));
          res.add(Vector.of(-x,  y, l));
        }
      }
    }
    return res;
  }
}
