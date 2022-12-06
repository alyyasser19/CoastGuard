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
        return switch (strategy) {
            case "BF" -> breadthFirst(gridObj, visualize);
            case "DF" -> depthFirst(gridObj, visualize);
            case "ID" -> IterativeDeepening(gridObj, visualize);
            case "GR1" -> greedy(gridObj, visualize, 1);
            case "GR2" -> greedy(gridObj, visualize, 2);
            case "AS1" -> AStar(gridObj, visualize, 1);
            case "AS2" -> AStar(gridObj, visualize, 2);
            default -> "Invalid strategy";
        };
    }

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
    public State expand(State prevState) {
        String action = prevState.getOperator();
        //System.out.println("Expanding: " + action);

        //init the state
        Grid currentGrid = prevState.getGrid();
        ArrayList<String> plan = prevState.getPlan();
        int cgX;
        int cgY;
        int pathCost = prevState.getPathCost();
        int depth = prevState.getDepth();
        int numberNodesExpanded = prevState.getPlan().size();


        //perform the action
        currentGrid.performAction(action);
        int newDeaths = currentGrid.getDeaths();
        int newSavedPassengers = currentGrid.getSavedPassengers();
        int newCollectedBoxes = currentGrid.getCollectedBoxes();

        //update the state
        plan.add(action);
        pathCost++;
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
                numberNodesExpanded,
                prevState.getHeuristicValue()

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


    private static String depthFirst(Grid grid, boolean visualize) {
        //Set Initial Strings
        Search searchProblem = new CoastGuard();

        Grid currentGrid = grid;

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
                0,
                0
        );
        searchProblem.setInitState(rootState);
        searchProblem.getStateSpace().add(rootState);
        //Set Initial State
        State currentState = rootState;
        while (!currentGrid.checkGameOver()) {
            ArrayList<String> actions = currentGrid.getPossibleActions();
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
            currentState.setOperator(nextOperator);
            State nextState = searchProblem.expand(currentState);
            currentState = nextState;
            currentGrid = currentState.getGrid();
            searchProblem.getQueue().add(currentState);
            searchProblem.getStateSpace().add(currentState);
            if (visualize) {
                currentGrid.printGrid();
            }
        }

        String solution = stringifyState(currentState);
        if (visualize) {
            System.out.println("Solution: " + solution);
        }
        return solution;
    }

    private static String breadthFirst(Grid grid, boolean visualize) {
        //int state
        Search searchProblem = new CoastGuard();
        Grid currentGrid = grid;
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
                0,
                0
        );
        searchProblem.setInitState(rootState);
        searchProblem.getStateSpace().add(rootState);
        State currentState = rootState;


        //get possible actions
        ArrayList<String> actions = currentGrid.getPossibleActions();
        for (String action : actions) {
            State temp = rootState.copy();
            temp.setOperator(action);
            searchProblem.getQueue().add(temp);
            searchProblem.getStateSpace().add(temp);
        }
        while (!currentGrid.checkGameOver()) {
            currentState = searchProblem.expand(searchProblem.getQueue().remove(0));
            currentGrid = currentState.getGrid();
            actions = currentGrid.getPossibleActions();
            //get possible actions
            for (String action : actions) {
                State temp = currentState.copy();
                temp.setOperator(action);
                if (!searchProblem.getStateSpace().contains(temp)) {
                    searchProblem.getQueue().add(temp);
                    searchProblem.getStateSpace().add(temp);
                }
            }
            if (visualize) {
                currentGrid.printGrid();
            }
        }
        String solution = stringifyState(currentState);
        return solution;
    }

    //remove redundant states from queue from back to front
    private static void removeRedundantStates(ArrayList<State> queue) {
        for (int i = queue.size() - 1; i >= 0; i--) {
            State state = queue.get(i);
            for (int j = i - 1; j >= 0; j--) {
                State state2 = queue.get(j);
                if (state.compareStates(state2)) {
                    queue.remove(i);
                    break;
                }
            }
        }
    }

    //check if state is redundant
    private static boolean isRedundantState(State state, ArrayList<State> queue) {
        for (State state2 : queue) {
            if (state.compareStates(state2)) {
                return true;
            }
        }
        return false;
    }

    private static String IterativeDeepening(Grid grid, boolean visualize) {
        //int state
        Search searchProblem = new CoastGuard();
        Grid currentGrid = grid;
        int depthLimit = 0;
        boolean increaseDepth = true;
        State currentState = null;
        currentGrid = grid.copyGrid();
        currentState = new State(
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
                0,
                0
        );
        ArrayList<String> actions = new ArrayList<String>();
        while (!currentGrid.checkGameOver()) {
            if (increaseDepth) {
                currentGrid = grid.copyGrid();
                currentState = new State(
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
                        0,
                        0
                );
                searchProblem.getQueue().clear();
                searchProblem.getStateSpace().clear();
                searchProblem.setInitState(currentState);
                searchProblem.getStateSpace().add(currentState);
                actions = currentGrid.getPossibleActions();
                for (String action : actions) {
                    State temp = currentState.copy();
                    temp.setOperator(action);
                    searchProblem.getQueue().add(temp);
                    searchProblem.getStateSpace().add(temp);
                }
            }
            increaseDepth = false;
            if (currentState.getDepth() <= depthLimit) {
                currentState = searchProblem.expand(searchProblem.getQueue().remove(0));
                currentGrid = currentState.getGrid();
                actions = currentGrid.getPossibleActions();
                //get possible actions
                for (String action : actions) {
                    State temp = currentState.copy();
                    temp.setOperator(action);
                    if (!searchProblem.getStateSpace().contains(temp)) {
                        searchProblem.getQueue().add(temp);
                        searchProblem.getStateSpace().add(temp);
                    }
                }
            } else {
                increaseDepth = true;
                depthLimit++;
                System.out.println("Depth Limit: " + depthLimit);
            }
            if (visualize) {
                currentGrid.printGrid();
            }
        }
        String solution = stringifyState(currentState);
        return solution;
    }

    private static String greedy(Grid grid, boolean visualize, int i) {
        //The First Greedy Search uses the Manhattan Distance as the heuristic to find the closest ship if there is space if there isn't then it finds the closest station
        if (i == 1) {
            return greedyManhattan(grid, visualize);
        }
        //The Second Greedy searches for ship with max passengers
        else if (i == 2) {
            return greedyMaxPassengers(grid, visualize);
        }
        return null;
    }

    private static String greedyManhattan(Grid grid, boolean visualize) {
        //int state
        Search searchProblem = new CoastGuard();
        Grid currentGrid = grid;
        State rootState = new State(
                currentGrid,
                null,
                new ArrayList<String>(),
                "Init State",
                currentGrid.getCgX(),
                currentGrid.getCgY(),
                100,
                0,
                0,
                0,
                0,
                0,
                100
        );
        searchProblem.setInitState(rootState);
        searchProblem.getStateSpace().add(rootState);
        State currentState = rootState;

        ArrayList<String> curBestPath = grid.copyGrid().bestPathToShip();
        int curBestPathLength = curBestPath.size();

        //get possible actions
        ArrayList<String> actions = currentGrid.getPossibleActions();
        for (String action : actions) {
            State temp = rootState.copy();
            temp.setOperator(action);
            // find based on the index of this action if exists in the best path
            int index = curBestPath.indexOf(action);
            if (index != -1) {
                temp.setHeuristicValue(index + 1);
            } else {
                temp.setHeuristicValue(curBestPathLength + 1);
            }
            searchProblem.getQueue().add(temp);
            searchProblem.getStateSpace().add(temp);
        }

        while (!currentGrid.checkGameOver()) {
            currentState.setHeuristicValue(curBestPathLength + 1);
            //print plan
            //expand the state with the lowest path cost
            for (int i = 0; i < searchProblem.getQueue().size(); i++) {
                State state = searchProblem.getQueue().get(i);
                if (state.getHeuristicValue() < currentState.getHeuristicValue()) {
                    currentState = state;
                }
            }
            //clear the Queue and State Space
            searchProblem.getQueue().clear();
            searchProblem.getStateSpace().clear();
            currentState = searchProblem.expand(currentState);
            if(currentState.getPlan().size() == 50){
                break;
            }
            currentGrid = currentState.getGrid();

            if (currentGrid.getC() > 0 && !currentGrid.getS().isEmpty()) {
                curBestPath = currentGrid.copyGrid().bestPathToShip();
            } else {
                curBestPath = currentGrid.copyGrid().bestPathToStation();
            }
            curBestPathLength = curBestPath.size();
            actions = currentGrid.getPossibleActions();
            //get possible actions
            for (String action : actions) {
                State temp = currentState.copy();
                temp.setOperator(action);
                // find based on the index of this action if exists in the best path
                int index = curBestPath.indexOf(action);
                if (index != -1) {
                    temp.setHeuristicValue(index + 1);
                } else if (temp.getOperator().equals("pickup") || temp.getOperator().equals("drop") || temp.getOperator().equals("retrieve")) {
                    temp.setHeuristicValue(0);
                } else {
                    temp.setHeuristicValue(curBestPathLength + 1);
                }
                if (!searchProblem.getStateSpace().contains(temp)) {
                    searchProblem.getQueue().add(temp);
                    searchProblem.getStateSpace().add(temp);
                }
            }
            if (visualize) {
                currentGrid.printGrid();
            }
        }

        String solution = stringifyState(currentState);
        return solution;


    }

    private static String greedyMaxPassengers(Grid grid, boolean visualize){
        //
        //int state
        Search searchProblem = new CoastGuard();
        Grid currentGrid = grid;
        State rootState = new State(
                currentGrid,
                null,
                new ArrayList<String>(),
                "Init State",
                currentGrid.getCgX(),
                currentGrid.getCgY(),
                100,
                0,
                0,
                0,
                0,
                0,
                100
        );

        searchProblem.setInitState(rootState);
        searchProblem.getStateSpace().add(rootState);
        State currentState = rootState;

        int shipWithMaxPassengers = currentGrid.findShipWithMostPassengers();
        ArrayList<String> curBestPath = grid.copyGrid().bestPathToShip(shipWithMaxPassengers);
        int curBestPathLength = curBestPath.size();

        //get possible actions
        ArrayList<String> actions = currentGrid.getPossibleActions();
        for (String action : actions) {
            State temp = rootState.copy();
            temp.setOperator(action);
            // find based on the index of this action if exists in the best path
            int index = curBestPath.indexOf(action);
            if (index != -1) {
                temp.setHeuristicValue(index + 1);
            } else {
                temp.setHeuristicValue(curBestPathLength + 1);
            }
            searchProblem.getQueue().add(temp);
            searchProblem.getStateSpace().add(temp);
        }

while (!currentGrid.checkGameOver()) {
            currentState.setHeuristicValue(curBestPathLength + 1);
            //print plan
            //expand the state with the lowest path cost
            for (int i = 0; i < searchProblem.getQueue().size(); i++) {
                State state = searchProblem.getQueue().get(i);
                if (state.getHeuristicValue() < currentState.getHeuristicValue()) {
                    currentState = state;
                }
            }
            //clear the Queue and State Space
            searchProblem.getQueue().clear();
            searchProblem.getStateSpace().clear();
            currentState = searchProblem.expand(currentState);
            if(currentState.getPlan().size() == 50){
                break;
            }
            currentGrid = currentState.getGrid();

            if (currentGrid.getC() > 0 && !currentGrid.getS().isEmpty()) {
                shipWithMaxPassengers = currentGrid.findShipWithMostPassengers();
                curBestPath = currentGrid.copyGrid().bestPathToShip(shipWithMaxPassengers);
            } else {
                curBestPath = currentGrid.copyGrid().bestPathToStation();
            }
            curBestPathLength = curBestPath.size();
            actions = currentGrid.getPossibleActions();
            //get possible actions
            for (String action : actions) {
                State temp = currentState.copy();
                temp.setOperator(action);
                // find based on the index of this action if exists in the best path
                int index = curBestPath.indexOf(action);
                if (index != -1) {
                    temp.setHeuristicValue(index + 1);
                } else if (temp.getOperator().equals("pickup") || temp.getOperator().equals("drop") || temp.getOperator().equals("retrieve")) {
                    temp.setHeuristicValue(0);
                } else {
                    temp.setHeuristicValue(curBestPathLength + 1);
                }
                if (!searchProblem.getStateSpace().contains(temp)) {
                    searchProblem.getQueue().add(temp);
                    searchProblem.getStateSpace().add(temp);
                }
            }
            if (visualize) {
                currentGrid.printGrid();
            }
        }

        String solution = stringifyState(currentState);
        return solution;

    }
}


