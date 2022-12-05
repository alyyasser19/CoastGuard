package code;

import java.util.ArrayList;
import java.util.HashSet;

import code.classes.State;

public abstract class Search {
    private String[] operators;
    private State initState;
    private HashSet<State> stateSpace = new HashSet<State>();
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

    public ArrayList<State> getQueue() {
        return queue;
    }

    public String getGoalState() {
        return goalState;
    }

    public HashSet<State> getStateSpace() {
        return stateSpace;
    }

    public void setStateSpace(HashSet<State> stateSpace) {
        this.stateSpace = stateSpace;
    }

    public void setGoalState(String goalState) {
        this.goalState = goalState;
    }

    public abstract State expand(State prevState);

    public abstract void printPath(State node);

    public abstract void printStateSpace();

    public abstract void printSolution(State node);



}
