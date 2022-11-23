package code.classes;

import java.util.ArrayList;

public class State {
    Grid grid;
    State parent;
    ArrayList<String> plan;
    String operator;
    int cgX;
    int cgY;
    int pathCost;
    int depth;
    int savedPassengers;
    int collectedBoxes;
    int deaths;
    int numberNodesExpanded;

    public State(Grid grid, State parent, ArrayList<String> plan, String operator, int cgX, int cgY, int pathCost,
            int depth, int savedPassengers, int collectedBoxes, int deaths, int numberNodesExpanded) {
        this.grid = grid;
        this.parent = parent;
        this.plan = plan;
        this.operator = operator;
        this.cgX = cgX;
        this.cgY = cgY;
        this.pathCost = pathCost;
        this.depth = depth;
        this.savedPassengers = savedPassengers;
        this.collectedBoxes = collectedBoxes;
        this.deaths = deaths;
        this.numberNodesExpanded = numberNodesExpanded;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public State getParent() {
        return parent;
    }

    public void setParent(State parent) {
        this.parent = parent;
    }

    public ArrayList<String> getPlan() {
        return plan;
    }

    public void setPlan(ArrayList<String> plan) {
        this.plan = plan;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
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

    public int getPathCost() {
        return pathCost;
    }

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getSavedPassengers() {
        return savedPassengers;
    }

    public void setSavedPassengers(int savedPassengers) {
        this.savedPassengers = savedPassengers;
    }

    public int getCollectedBoxes() {
        return collectedBoxes;
    }

    public void setCollectedBoxes(int collectedBoxes) {
        this.collectedBoxes = collectedBoxes;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getNumberNodesExpanded() {
        return numberNodesExpanded;
    }

    public void setNumberNodesExpanded(int numberNodesExpanded) {
        this.numberNodesExpanded = numberNodesExpanded;
    }

    public void printStateInfo() {
        System.out.println("State Info:");
        System.out.println("Operator: " + this.operator);
        System.out.println("Path Cost: " + this.pathCost);
        System.out.println("Depth: " + this.depth);
        System.out.println("Saved Passengers: " + this.savedPassengers);
        System.out.println("Collected Boxes: " + this.collectedBoxes);
        System.out.println("Deaths: " + this.deaths);
        System.out.println("Number Nodes Expanded: " + this.numberNodesExpanded);
    }
}
