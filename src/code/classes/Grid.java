package code.classes;

import java.util.ArrayList;

public class Grid {
  private String[][] grid;
  private int M; // columns
  private int N; // rows
  private int maxC; //max capacity
  private int C; // capacity
  private int cgX; //coast guard x
  private int cgY; //coast guard y
  private int[][] i; //stations coordinates
  private ArrayList<Ship> s = new ArrayList<Ship>(); //ships coordinates
  private boolean gameOver = false;

  public Grid(int m, int n) {
    M = m;
    N = n;
    this.grid = new String[N][M];
  }

  public String[][] getGrid() {
    return grid;
  }

  public void setGrid(String[][] grid) {
    this.grid = grid;
  }

  public int getM() {
    return M;
  }

  public void setM(int m) {
    M = m;
  }

  public int getN() {
    return N;
  }

  public void setN(int n) {
    N = n;
  }

  public int getMaxC() {
    return maxC;
  }

  public void setMaxC(int maxC) {
    this.maxC = maxC;
  }

  public int getC() {
    return C;
  }

  public void setC(int c) {
    C = c;
  }

  public int getCgX() {
    return cgX;
  }

  public void setCgX(int cgX) {
    this.cgX = cgX;
  }

  public int getCgY() {
    return cgY;
  }

  public void setCgY(int cgY) {
    this.cgY = cgY;
  }

  public int[][] getI() {
    return i;
  }

  public void setI(int[][] i) {
    this.i = i;
  }

  public ArrayList<Ship> getS() {
    return s;
  }

  public void setS(ArrayList<Ship> s) {
    this.s = s;
  }

  public boolean getGameStatus() {
    if (this.gameOver) {
      System.out.println("Game Over");
      return true;
    } else {
      return false;
    }
  }

  public void printGrid() {
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < M; j++) {
        System.out.print(grid[i][j] + " ");
      }
      System.out.println();
    }
  }

  public void printStations() {
    for (int i = 0; i < this.i.length; i++) {
      System.out.println(
        "Station " + (i + 1) + ": " + this.i[i][0] + " " + this.i[i][1]
      );
    }
  }

  public void printShips() {
    for (int i = 0; i < this.s.size(); i++) {
      System.out.println(
        "Ship " +
        (i + 1) +
        ": " +
        this.s.get(i).getX() +
        " " +
        this.s.get(i).getY()
      );
    }
  }

  public void printPassengers() {
    for (int i = 0; i < this.s.size(); i++) {
      System.out.println(
        "Ship Passengers" +
        (i + 1) +
        ": " +
        this.s.get(i).getRemainingPassengers()
      );
    }
  }

  public void printCoastGuard() {
    System.out.println("Coast Guard: " + this.cgX + " " + this.cgY);
  }

  public void printCapacity() {
    System.out.println("Capacity: " + this.C);
  }

  public void printGridSize() {
    System.out.println("Grid Size: " + this.M + " " + this.N);
  }

  public void printGridInfo() {
    printGridSize();
    printCapacity();
    printCoastGuard();
    printStations();
    printShips();
    printPassengers();
  }

  public static Grid genGrid() {
    //Generate between 5 and 15 locations
    int m = (int) ((Math.random() * (15 - 5)) + 5);
    int n = (int) ((Math.random() * (15 - 5)) + 5);

    Grid grid = new Grid(m, n);

    //Capacity between 30 and 100
    int c = (int) ((Math.random() * (100 - 30)) + 30);
    grid.setMaxC(c);
    grid.setC(c);

    //Coast Guard
    int cgX = (int) (Math.random() * m);
    int cgY = (int) (Math.random() * n);
    grid.setCgX(cgX);
    grid.setCgY(cgY);

    // Generate N stations between 1 and 40% of the grid size
    int[][] i = new int[(int) (Math.random() * (m * n) * 0.4) + 1][2];
    grid.setI(i);
    for (int j = 0; j < i.length; j++) {
      int x = (int) (Math.random() * m);
      int y = (int) (Math.random() * n);
      int[] station = { x, y };
      grid.getI()[j] = station;
      grid.getGrid()[y][x] = "Station";
    }

    // Generate N ships between 1 and 40% of the grid size and each ship has a random number of passengers between 0 and 100
    for (int j = 0; j < (int) (Math.random() * (m * n) * 0.4) + 1; j++) {
      int x = (int) (Math.random() * m);
      int y = (int) (Math.random() * n);

      // If the ship is on a station, generate a new position
      while (grid.getGrid()[y][x] == "Station") {
        x = (int) (Math.random() * m);
        y = (int) (Math.random() * n);
      }

      int p = (int) (Math.random() * 100);
      Ship ship = new Ship(x, y, p);
      grid.getGrid()[y][x] = "Ship";
      grid.getS().add(ship);
    }

    return grid;
  }

  public String generateGridString() {
    String gridString = "";
    gridString += this.M + "," + this.N + ";";
    gridString += this.C + ";";
    gridString += this.cgX + "," + this.cgY + ";";
    for (int i = 0; i < this.i.length; i++) {
      gridString += this.i[i][1] + "," + this.i[i][0] + ",";
    }
    gridString += ";";
    for (int i = 0; i < this.s.size(); i++) {
      gridString +=
        this.s.get(i).getY() +
        "," +
        this.s.get(i).getX() +
        "," +
        this.s.get(i).getRemainingPassengers() +
        ",";
    }
    gridString = gridString.substring(0, gridString.length() - 1);
    gridString += ";";
    return gridString;
  }

  public static Grid decodeString(String grid) {
    String[] gridInfo = grid.split(";");
    String[] gridDimensions = gridInfo[0].split(",");
    int m = Integer.parseInt(gridDimensions[0]);
    int n = Integer.parseInt(gridDimensions[1]);
    Grid g = new Grid(m, n);
    g.setC(Integer.parseInt(gridInfo[1]));
    g.setMaxC(Integer.parseInt(gridInfo[1]));
    String[] cg = gridInfo[2].split(",");
    g.setCgX(Integer.parseInt(cg[1]));
    g.setCgY(Integer.parseInt(cg[0]));
    String[] stations = gridInfo[3].split(",");
    int[][] i = new int[stations.length / 2][2];
    g.setI(i);
    for (int j = 0; j < stations.length; j += 2) {
      int[] station = {
        Integer.parseInt(stations[j]),
        Integer.parseInt(stations[j+1]),
      };
      g.getI()[j / 2] = station;
      g.getGrid()[Integer.parseInt(stations[j])][Integer.parseInt(
          stations[j + 1]
        )] =
        "Station";
    }
    String[] ships = gridInfo[4].split(",");
    for (int j = 0; j < ships.length; j += 3) {
      int x = Integer.parseInt(ships[j+1]);
      int y = Integer.parseInt(ships[j]);
      int p = Integer.parseInt(ships[j + 2]);
      Ship ship = new Ship(x, y, p);
      g.getGrid()[y][x] = "Ship";
      g.getS().add(ship);
    }
    return g;
  }

  public void moveAgent(String direction) {
    switch (direction) {
      case "up":
        if (cgY > 0) {
          cgY--;
        }
        break;
      case "down":
        if (cgY < N - 1) {
          cgY++;
        }
        break;
      case "left":
        if (cgX > 0) {
          cgX--;
        }
        break;
      case "right":
        if (cgX < M - 1) {
          cgX++;
        }
        break;
    }
  }

  public void pickUpPassengers() {
    for (int i = 0; i < s.size(); i++) {
      if (s.get(i).getX() == cgX && s.get(i).getY() == cgY) {
        if (s.get(i).getRemainingPassengers() > 0) {
          if (s.get(i).getRemainingPassengers() > C) {
            s
              .get(i)
              .setRemainingPassengers(s.get(i).getRemainingPassengers() - C);
            System.out.println("Capacity: " + C);
            System.out.println("Max Capacity: " + maxC);
            System.out.println("Picked up " + C + " passengers");
            C = 0;
          } else {
            System.out.println("Capacity: " + C);
            System.out.println("Max Capacity: " + maxC);
            C -= s.get(i).getRemainingPassengers();
            s.get(i).setRemainingPassengers(0);
            System.out.println("Picked up " + (maxC - C) + " passengers");
          }
        }
      }
    }
  }

  public int dropOffPassengers() {
    if (grid[cgY][cgX] == "Station") {
      int savedPassengers = maxC - C;
      C = maxC;

      System.out.println("Dropped off " + savedPassengers + " passengers");

      return savedPassengers;
    }
    return 0;
  }

  public int damageShips() {
    int deaths = 0;
    for (int i = 0; i < s.size(); i++) {
      if (!s.get(i).isWrecked() && s.get(i).getRemainingPassengers() > 0) {
        s.get(i).setRemainingPassengers(s.get(i).getRemainingPassengers() - 1);
        System.out.println(
          "Ship " +
          i +
          " has " +
          s.get(i).getRemainingPassengers() +
          " remaining passengers"
        );
        deaths++;
      }
      if (
        s.get(i).getRemainingPassengers() == 0 &&
        !s.get(i).isWrecked() &&
        !s.get(i).isDestroyed()
      ) {
        grid[s.get(i).getY()][s.get(i).getX()] = "Wreck";
        s.get(i).setWrecked(true);
        System.out.println("Ship " + i + " has been wrecked");
      }
      if (s.get(i).isWrecked() && !s.get(i).isDestroyed()) {
        s.get(i).damageBox();
        if (s.get(i).getBoxDamage() == 20) {
          grid[s.get(i).getY()][s.get(i).getX()] = "Destroyed";
          s.remove(i);
          System.out.println("Ship " + i + " has been destroyed");
        }
      }
    }
    return deaths;
  }

  public int retrieveBox() {
    for (int i = 0; i < s.size(); i++) {
      if (s.get(i).getX() == cgX && s.get(i).getY() == cgY) {
        if (s.get(i).isWrecked() && !s.get(i).isDestroyed()) {
          System.out.println("Retrieved box");
          grid[s.get(i).getY()][s.get(i).getX()] = "Destroyed";
          s.remove(i);
          return 1;
        }
      }
    }
    return 0;
  }

  //if all ships are destroyed, return true
  public boolean checkGameOver() {
    if (s.size() == 0 && C == maxC) {
      return true;
    }
    return false;
  }

  public ArrayList<String> getPossibleActions() {
    ArrayList<String> actions = new ArrayList<String>();
    for (int i = 0; i < s.size(); i++) {
      if (s.get(i).getX() == cgX && s.get(i).getY() == cgY) {
        if (
          s.get(i).getRemainingPassengers() > 0 &&
          C > 0 &&
          !s.get(i).isWrecked()
        ) {
          actions.add("pickup");
        }
      }
    }
    if (grid[cgY][cgX] == "Station" && C < maxC) {
      actions.add("drop");
    }
    if (grid[cgY][cgX] == "Wreck") {
      actions.add("retrieve");
    }
    if (cgY > 0) {
      actions.add("up");
    }
    if (cgX > 0) {
      actions.add("left");
    }
    if (cgX < M - 1) {
      actions.add("right");
    }
    if (cgY < N - 1) {
      actions.add("down");
    }

    //print all possible actions
    System.out.println("Possible actions: ");
    for (int i = 0; i < actions.size(); i++) {
      System.out.print(actions.get(i) + " ");
    }
    System.out.println();
    return actions;
  }

  public int[] performAction(String action) {
    // [0] = Deaths, [1] = saved, [2] = collected boxes;
    System.out.println("Performing action: " + action);
    int[] outcomes = new int[3];
    //fill outcomes with -1
    for (int i = 0; i < outcomes.length; i++) {
      outcomes[i] = -1;
    }
    switch (action) {
      case "up":
        moveAgent("up");
        break;
      case "down":
        moveAgent("down");
        break;
      case "left":
        moveAgent("left");
        break;
      case "right":
        moveAgent("right");
        break;
      case "pickup":
        pickUpPassengers();
        break;
      case "drop":
        outcomes[1] = dropOffPassengers();
        break;
      case "retrieve":
        outcomes[2] = retrieveBox();
        break;
    }
    outcomes[0] = damageShips();

    return outcomes;
  }
}
