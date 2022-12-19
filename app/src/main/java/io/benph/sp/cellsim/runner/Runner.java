package io.benph.sp.cellsim.runner;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import io.benph.sp.cellsim.grid.Cell;
import io.benph.sp.cellsim.grid.Grid;
import io.benph.sp.cellsim.grid.Point.State;

public class Runner<C extends Cell> {

  private final Path path;
  private final Grid<C> grid;
  private final Algorithm<C> algo;
  private State state = State.A;

  private int fc = 1;
  private final Map<Integer, Map<Long, Integer>> frames = new LinkedHashMap<>();

  private BufferedImage image;
  private Graphics2D graphics;
  private static final Color shade = new Color(0f, 0f, 0f, .4f);

  public Runner(Grid<C> grid, Algorithm<C> algo, Path path) {
    this.grid = grid;
    this.algo = algo;
    this.path = path;

    this.image = new BufferedImage(grid.getWidth(), grid.getHeight(), BufferedImage.TYPE_INT_RGB);
    this.graphics = image.createGraphics();
  }

  public void iterate() {
    Stream<C> iteration = grid.getGrid().parallelStream()
        .flatMap(row -> row.stream())
        .map(p -> {
          C current = p.get(state);
          C next = p.get(state.opposite());
          algo.iterate(
              current, next, p.getNs(state), p.dists);
          return next;
        });

    var frame = algo.export(iteration, grid.getWidth());
    frames.put(fc++, frame);
    state = state.opposite();
  }

  public void initDestination() throws IOException {
    if (!path.toFile().exists()) {
      Files.createDirectories(path);
    } else {
      for (var f : path.toFile().listFiles()) {
        if (f.isFile()) {
          f.delete();
        }
      }
    }
  }

  public void dump() throws IOException {
    frames.entrySet().stream()
        .forEach(e -> dump(e.getKey(), e.getValue()));
    frames.clear();
  }

  private void dump(int i, Map<Long, Integer> frame) {
    graphics.setColor(shade);
    graphics.fillRect(0, 0, grid.getWidth(), grid.getHeight());
    frame.entrySet()
        .forEach(f -> {
          int x = (int) (f.getKey() % grid.getWidth());
          int y = (int) (f.getKey() / grid.getWidth());
          image.setRGB(x, y, f.getValue());
        });
    try {
      ImageIO.write(image, "bmp", path.resolve("f-" + i + ".bmp").toFile());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
