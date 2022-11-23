package code;

import java.util.ArrayList;

import code.classes.State;

public abstract class Search {
    private String[] operators;
    private State initState;
    private ArrayList<State> stateSpace = new ArrayList<State>();
    private ArrayList<State> queue = new ArrayList<State>();
    private String goalState;

    public Search() {
        this.operators = new String[] { "up", "down", "right", "left", "pickup","drop","retrieve" };
        this.initState = null;
        this.goalState = "G";
    }

    public String[] getOperators() {
        return operators;
    }

    public void setOperators(String[] operators) {
        this.operators = operators;
    }

    public State getInitState() {
        return initState;
    }

    public void setInitState(State initState) {
        this.initState = initState;
    }

    public ArrayList<State> getStateSpace() {
        return stateSpace;
    }

    public void setStateSpace(ArrayList<State> stateSpace) {
        this.stateSpace = stateSpace;
    }

    public ArrayList<State> getQueue() {
        return queue;
    }

    public String getGoalState() {
        return goalState;
    }

    public void setGoalState(String goalState) {
        this.goalState = goalState;
    }

    public abstract State expand(State prevState, String action);

    public abstract void printPath(State node);

    public abstract void printStateSpace();

    public abstract void printSolution(State node);

}
