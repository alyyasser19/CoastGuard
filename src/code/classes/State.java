package code.classes;

import java.util.*;

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
        grid.printGrid();
    }

    //copy State
    public State copy() {
        Grid gridCopy = grid.copyGrid();
        State parentCopy = this;
        ArrayList<String> planCopy = new ArrayList<String>();
        //copy plan
        for (String s : plan) {
            planCopy.add(s);
        }
        String operatorCopy = operator;
        int cgXCopy = cgX;
        int cgYCopy = cgY;
        int pathCostCopy = pathCost;
        int depthCopy = depth + 1;
        int savedPassengersCopy = savedPassengers;
        int collectedBoxesCopy = collectedBoxes;
        int deathsCopy = deaths;
        int numberNodesExpandedCopy = numberNodesExpanded;
        State stateCopy = new State(gridCopy, parentCopy, planCopy, operatorCopy, cgXCopy, cgYCopy, pathCostCopy, depthCopy,
                savedPassengersCopy, collectedBoxesCopy, deathsCopy, numberNodesExpandedCopy);
        return stateCopy;
    }

    public String stateID(){
        return "Operator: " + this.operator+ " " + "cgX: " + this.cgX +" " + "cgY: " + this.cgY +" "+"depth: " + this.depth +" " +"numberNodesExpanded: " + this.numberNodesExpanded + " "+ "Plan Size: " + this.plan.size();
    }

    //Compare two states to see if they are the same based on cgx, cgy, and deaths
    public boolean compareStates(State state){
        if(this.cgX == state.cgX && this.cgY == state.cgY && this.deaths == state.deaths && this.operator == state.operator){
            return true;
        }
        return false;
    }
//toString
    @Override
    public String toString() {
        return "State [cgX=" + cgX + ", cgY=" + cgY + ", deaths=" + deaths
                + ", operator="
                + operator + ", pathCost=" + pathCost + "]";
    }


    @Override
    public boolean equals(Object state) {
        State x = (State) state;
        return cgX == x.getCgX() && cgY == x.getCgY() && collectedBoxes == x.getCollectedBoxes() &&
                deaths == x.getDeaths() && operator.equals(x.getOperator());
    }



    @Override
    public int hashCode() {
        return Objects.hash(cgX, cgY, deaths, operator);
    }
}
