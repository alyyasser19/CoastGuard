import code.CoastGuard;
import code.classes.Grid;

public class App {

    public static void main(String[] args) throws Exception {
        String grid0 = "5,6;50;0,1;0,4,3,3;1,1,1;";
        String grid1 = "6,6;52;2,0;2,4,4,0,5,4;2,1,19,4,2,6,5,0,8;";
        String grid2 = "7,5;40;2,3;3,6;1,1,10,4,5,2;";
        String grid3 = "8,5;60;4,6;2,7;3,4,37,3,5,93,4,0,40;";
        String grid4 = "5,7;63;4,2;6,2,6,3;0,0,17,0,2,73,3,0,30;";
        // String grid5 = "5,5;69;3,3;0,0,0,1,1,0;0,3,78,1,2,2,1,3,14,4,4,9;";
        // String grid6 = "7,5;86;0,0;1,3,1,5,4,2;1,1,42,2,5,99,3,5,89;";
        //String grid7 = "6,7;82;1,4;2,3;1,1,58,3,0,58,4,2,72;";
        // String grid8 =
        //   "6,6;74;1,1;0,3,1,0,2,0,2,4,4,0,4,2,5,0;0,0,78,3,3,5,4,3,40;";
        //String grid9 ="7,5;100;3,4;2,6,3,5;0,0,4,0,1,8,1,4,77,1,5,1,3,2,94,4,3,46;";
        String grid10 =
                "10,6;59;1,7;0,0,2,2,3,0,5,3;1,3,69,3,4,80,4,7,94,4,9,14,5,2,39;";

        String solution = CoastGuard.solve(grid0, "GR1", false);
        Grid grid = Grid.decodeString(grid0);
        grid.printGrid();
        grid.performAction("down");
        grid.performAction("down");

        System.out.println(grid.findClosestWreck());
        System.out.println(grid.getCgX());
        System.out.println(grid.getCgY());
//        grid.printGrid();
//        System.out.println(grid.bestPathToStation());
//        grid.performAction("right");
//        grid.printGrid();
//System.out.println(grid.bestPathToStation());
//        grid.performAction("right");
//        grid.printGrid();
//        System.out.println(grid.bestPathToStation());
//        grid.performAction("right");
//        grid.printGrid();
//        System.out.println(grid.bestPathToStation());
//        grid.performAction("right");
//        grid.printGrid();
//        System.out.println(grid.bestPathToStation());
//        grid.performAction("right");
//        grid.printGrid();
//        System.out.println(grid.bestPathToStation());


    }
}
