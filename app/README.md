# Cellular Automaton Library

![Main Page](/readme/conways.bmp)

## How-to

Create cellular grid

```java
import io.benph.sp.cellsim.grid.Grid;
import io.benph.sp.cellsim.algos.conways.ConwaysCell;

var grid = new Grid<ConwaysCell>(w, h, canGoThroughEdges);
grid.init(x, y -> new ConwaysCell(x, y, isAlive));
```

Run simulation

```java
import io.benph.sp.cellsim.runner.Runner;
import io.benph.sp.cellsim.algos.conways.Conways;

var runner = new Runner<>(grid, new Conways(), "~/cellular-automaton/export");

// run one iteration
runner.iterate();

// export current state as bmp
runner.dump();
```

checkout this [working example](/app/src/main/java/io/benph/sp/cellsim/App.java)

You can try your custom algorithm by implementing [Algorithm](/app/src/main/java/io/benph/sp/cellsim/runner/Algorithm.java) and extending [Cell](/app/src/main/java/io/benph/sp/cellsim/grid/Cell.java) classes.
This is how [Conways](/app/src/main/java/io/benph/sp/cellsim/algos/conways/Conways.java) and [TriColorConways](/app/src/main/java/io/benph/sp/cellsim/algos/tcc/TriColorConways.java) algorithms were implemented.
