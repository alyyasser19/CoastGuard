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
        System.out.println("Expanding: " + action);

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
                System.out.println(currentGrid.generateGridString());
            }
        }

        String solution = stringifyState(currentState);
        if (visualize) {
            System.out.println("Solution: " + solution);
        }
        return solution;
    }

    private static String breadthFirst(Grid grid, boolean visualize) {
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
                0
        );
        searchProblem.setInitState(rootState);
        searchProblem.getStateSpace().add(rootState);
        searchProblem.getQueue().add(rootState);
        //Set Initial State
        State currentState = rootState;
        ArrayList<String> actions = currentGrid.getPossibleActions();
        for (String action : actions) {
            State temp = currentState.copy();
            temp.setOperator(action);
            searchProblem.getQueue().add(temp);
            searchProblem.getStateSpace().add(temp);
        }

        while(!currentGrid.checkGameOver()){
            currentState = searchProblem.expand(searchProblem.getQueue().remove(0));
            currentGrid = currentState.getGrid();
            actions = currentGrid.getPossibleActions();
            for (String action : actions) {
                State temp = currentState.copy();
                temp.setOperator(action);
                searchProblem.getQueue().add(temp);
                searchProblem.getStateSpace().add(temp);
            }
            removeRedundantStates(searchProblem.getQueue());
        }

        String solution = stringifyState(currentState);
        if (visualize) {
            System.out.println("Solution: " + solution);
        }
        return solution;

    }

    //remove redundant states from queue from back to front
    private static void removeRedundantStates(ArrayList<State> queue){
        for(int i = queue.size() - 1; i >= 0; i--){
            State state = queue.get(i);
            for(int j = i - 1; j >= 0; j--){
                State state2 = queue.get(j);
                if(state.compareStates(state2)){
                    queue.remove(i);
                    break;
                }
            }
        }
    }

    //check if state is redundant
    private static boolean isRedundantState(State state, ArrayList<State> queue){
        for(State state2 : queue){
            if(state.compareStates(state2)){
                return true;
            }
        }
        return false;
    }
}
