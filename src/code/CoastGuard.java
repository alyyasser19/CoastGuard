package code;

import code.classes.Grid;
import code.classes.State;
import java.util.ArrayList;

public class CoastGuard extends Search {

  public CoastGuard() {
    super();
    setOperators(getOperators());
  }

  public static String genGrid() {
    Grid grid = Grid.genGrid();
    String gridString = grid.generateGridString();
    return gridString;
  }

  public static String solve(String grid, String strategy, boolean visualize) {
    Grid gridObj = Grid.decodeString(grid);
    if (strategy.equals("BS")) {
      return breadthFirst(gridObj, visualize);
    } else if (strategy.equals("DF")) {
      return depthFirst(gridObj, visualize);
    } else if (strategy.equals("ID")) {
      return IterativeDeepening(gridObj, visualize);
    } else if (strategy.equals("GR1")) {
      return greedy(gridObj, visualize, 1);
    } else if (strategy.equals("GR2")) {
      return greedy(gridObj, visualize, 2);
    } else if (strategy.equals("AS1")) {
      return AStar(gridObj, visualize, 1);
    } else if (strategy.equals("AS2")) {
      return AStar(gridObj, visualize, 2);
    } else {
      return "Invalid strategy";
    }
  }

  // plan;deaths;retrieved;nodes
  // where:
  // – plan: the sequence of actions that lead to the goal (if such a sequence exists) separated by commas. For example: left,right,pickup,up,drop,down,retrieve.
  // (These are just all of the possible operator names.)
  // – deaths: number of passengers who have died in the solution starting from the
  // initial state to the found goal state.
  // – retrieved: number of black boxes successfully retrieved starting from the initial state to the found goal state.
  // – nodes: is the number of nodes chosen for expansion during the search.

  @Override
  public void printPath(State node) {
    ArrayList<String> plan = node.getPlan();
    int deaths = node.getDeaths();
    int retrieved = node.getCollectedBoxes();
    int nodes = node.getNumberNodesExpanded();
    System.out.println("Plan: " + plan);
    System.out.println("Deaths: " + deaths);
    System.out.println("Retrieved: " + retrieved);
    System.out.println("Nodes: " + nodes);
  }

  @Override
  public void printStateSpace() {
    for (State state : getStateSpace()) {
      System.out.println(state.getGrid().generateGridString());
    }
  }

  @Override
  public void printSolution(State node) {
    ArrayList<String> plan = node.getPlan();
    int deaths = node.getDeaths();
    int retrieved = node.getCollectedBoxes();
    int nodes = node.getNumberNodesExpanded();
    System.out.println("Plan: " + plan);
    System.out.println("Deaths: " + deaths);
    System.out.println("Retrieved: " + retrieved);
    System.out.println("Nodes: " + nodes);
  }

  @Override
  public State expand(State prevState, String action) {
    System.out.println("Expanding: " + action);

    //init the state
    Grid currentGrid = prevState.getGrid();
    ArrayList<String> plan = prevState.getPlan();
    int cgX = prevState.getCgX();
    int cgY = prevState.getCgY();
    int pathCost = prevState.getPathCost();
    int depth = prevState.getDepth();
    int savedPassengers = prevState.getSavedPassengers();
    int collectedBoxes = prevState.getCollectedBoxes();
    int deaths = prevState.getDeaths();
    int numberNodesExpanded = prevState.getNumberNodesExpanded();

    //perform the action
    int[] outcomes = currentGrid.performAction(action);
    int newDeaths = outcomes[0] != -1 ? +deaths + outcomes[0] : deaths;
    int newSavedPassengers = outcomes[1] != -1
      ? +savedPassengers + outcomes[1]
      : savedPassengers;
    int newCollectedBoxes = outcomes[2] != -1
      ? +collectedBoxes + outcomes[2]
      : collectedBoxes;

    //update the state
    plan.add(action);
    pathCost++;
    depth++;
    numberNodesExpanded++;
    cgX = currentGrid.getCgX();
    cgY = currentGrid.getCgY();

    State newState = new State(
      currentGrid,
      prevState,
      plan,
      action,
      cgX,
      cgY,
      pathCost,
      depth,
      newSavedPassengers,
      newCollectedBoxes,
      newDeaths,
      numberNodesExpanded
    );

    return newState;
  }

  public static String stringifyState(State node) {
    ArrayList<String> plan = node.getPlan();
    //print the plan
    String planString = "";
    for (String action : plan) {
      planString += action + ",";
    }
    System.out.println("Plan size: " + plan.size());
    planString = planString.substring(0, planString.length() - 1);
    int deaths = node.getDeaths();
    int retrieved = node.getCollectedBoxes();
    int nodes = node.getNumberNodesExpanded();
    return planString + ";" + deaths + ";" + retrieved + ";" + nodes;
  }

  private static String AStar(Grid grid, boolean visualize, int i) {
    return null;
  }

  private static String greedy(Grid grid, boolean visualize, int i) {
    return null;
  }

  private static String IterativeDeepening(Grid grid, boolean visualize) {
    return null;
  }

  private static String breadthFirst(Grid grid, boolean visualize) {
    return null;
  }

  private static String depthFirst(Grid grid, boolean visualize) {
    //Set Initial Strings
    Search searchProblem = new CoastGuard();
    String plan = "";
    int deaths = 0;
    int retrieved = 0;
    int nodes = 0;
    Grid currentGrid = grid;
    //Set Initial Grid
    String[][] gridArray = grid.getGrid();

    //set Root State
    State rootState = new State(
      currentGrid,
      null,
      new ArrayList<String>(),
      "Init State",
      currentGrid.getCgX(),
      currentGrid.getCgY(),
      0,
      0,
      0,
      0,
      0,
      0
    );
    searchProblem.setInitState(rootState);
    searchProblem.getQueue().add(rootState);
    searchProblem.getStateSpace().add(rootState);
    //Set Initial State
    State currentState = rootState;
    while (!grid.checkGameOver()) {
      ArrayList<String> actions = grid.getPossibleActions();
      String nextOperator = actions.get(0);
      //if the top action is a move, then randomly choose a move
      if (
        nextOperator.equals("left") ||
        nextOperator.equals("right") ||
        nextOperator.equals("up") ||
        nextOperator.equals("down")
      ) {
        nextOperator = actions.get((int) (Math.random() * actions.size()));
      }
      State nextState = searchProblem.expand(currentState, nextOperator);
      currentState = nextState;
      nodes++;
    }

    String solution = stringifyState(currentState);
    if (visualize) {
      System.out.println("Solution: " + solution);
    }
    return solution;
  }
}
