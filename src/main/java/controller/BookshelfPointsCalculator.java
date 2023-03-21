package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

public class BookshelfPointsCalculator {
    private HashMap<String, ArrayList<ArrayList<int[]>>> adjacencyMap;
    private String[][] board;

    //mettere gli attributi static, cos� facendo non devo sempre passare adjacencyMap come parametro
    public BookshelfPointsCalculator(HashMap<String, ArrayList<ArrayList<int[]>>> adjacencyMap, String[][] board){
        this.adjacencyMap = adjacencyMap;
        this.board = board;
    }

    public void buildAdjacencyMap(){
        for(int r=0;r<board.length;r++) {
            for(int c=0;c<board[0].length;c++) {
                String cellValue = board[r][c];
                if(!cellValue.equals("")) {
                    //fai un nuovo gruppo di adiacenza e una nuova chiave
                    if(!adjacencyMap.containsKey(cellValue)) {
                        ArrayList<ArrayList<int[]>> adjacencyLists = new ArrayList<>();
                        ArrayList<int[]> adjacencyGroup = new ArrayList<>();
                        adjacencyGroup.add(new int[] {r,c});
                        adjacencyLists.add(adjacencyGroup);
                        adjacencyMap.put(cellValue, adjacencyLists);
                    }else {
                        addToAdjacencyGroup(cellValue, r, c, board, adjacencyMap);
                    }

                }
            }
        }
    }

    private void addToAdjacencyGroup(String cellValue, int r, int c,String[][] board,
                                            HashMap<String, ArrayList<ArrayList<int[]>>> adjacencyMap) {

        int lastCol = board[0].length-1;
        ArrayList<ArrayList<int[]>> adjacencyLists = adjacencyMap.get(cellValue);
        ArrayList<int[]> cellAboveRightAdjacencyGroup = new ArrayList<>();
        boolean backwardsLShapeFound = false;
        boolean adjacentToNothing = true;
        for(ArrayList<int[]> adjacencyGroup : adjacencyLists) {
            for(int[] adjacentCell : adjacencyGroup) {
                int adjacentCellRow = adjacentCell[0];
                int adjacentCellCol = adjacentCell[1];
                if(r!=0 && c!= lastCol) {
                    int cellAboveRightRow = r-1;
                    int cellAboveRightCol = c+1;
                    String cellAboveRightValue = board[cellAboveRightRow][cellAboveRightCol];
                    String cellRightValue = board[r][c+1];
                    if(cellValue.equals(cellAboveRightValue) && cellValue.equals(cellRightValue)) {
                        backwardsLShapeFound = true;
                        adjacentToNothing = false;
                        //get the adjacencyGroup of the cellAboveRightValue and add this cell
                        cellAboveRightAdjacencyGroup =
                                getCellAdjacencyGroup(cellAboveRightRow, cellAboveRightCol, cellAboveRightValue, adjacencyMap);
                    }
                }
                //forse al posto dell'if mettere un elseif
                if((r-adjacentCellRow==1 && c==adjacentCellCol) ^
                        (c-adjacentCellCol==1 && r==adjacentCellRow)) {
                    adjacentToNothing = false;
                    adjacencyGroup.add(new int[] {r,c});
                    //trovare un altro punto per mettere il return
                    return;
                }
            }

        }


        if(backwardsLShapeFound) {
            cellAboveRightAdjacencyGroup.add(new int[] {r,c});
        }
        if(adjacentToNothing) {
            //crea un nuovo gruppo di adiacenza
            ArrayList<int[]> adjacencyGroup = new ArrayList<>();
            adjacencyGroup.add(new int[] {r,c});
            adjacencyMap.get(cellValue).add(adjacencyGroup);
        }

    }

    private ArrayList<int[]> getCellAdjacencyGroup(int r, int c, String cellValue,
                                                          HashMap<String, ArrayList<ArrayList<int[]>>> adjacencyMap){

        ArrayList<int[]> toReturn = new ArrayList<int[]>();

        for(ArrayList<int[]> adjacencyGroup : adjacencyMap.get(cellValue)) {
            for(int[] adjacentCell : adjacencyGroup) {
                int adjacentCellRow = adjacentCell[0];
                int adjacentCellCol = adjacentCell[1];
                if(r==adjacentCellRow && c==adjacentCellCol) {
                    toReturn = adjacencyGroup;
                    break;
                }
            }
        }
        return toReturn;

    }

    //dopo eliminare il printAdjacencyMap
    
    private static void printAdjacencyMap(HashMap<String, ArrayList<ArrayList<int[]>>> adjacencyMap) {
        // TODO Auto-generated method stub
        for(Entry<String, ArrayList<ArrayList<int[]>>> mapEntry: adjacencyMap.entrySet()) {
            System.out.println(mapEntry.getKey()+"=>[");
            for(ArrayList<int[]> adjGrp : mapEntry.getValue()) {
                System.out.println(" [");
                for(int[] coords : adjGrp) {
                    System.out.println("  "+ Arrays.toString(coords));
                }
                System.out.println(" ]");
            }
            System.out.println("]");
        }
    }
    

    public int calculatePoints(HashMap<String, ArrayList<ArrayList<int[]>>> adjacencyMap){
        int points = 0;
        for(Entry<String, ArrayList<ArrayList<int[]>>> entry : adjacencyMap.entrySet()) {
            for(ArrayList<int[]> adjacencyGroup : entry.getValue()) {
                int adjacencyGroupSize = adjacencyGroup.size();
                if(adjacencyGroupSize==3) {
                    points+=2;
                }else if(adjacencyGroupSize==4) {
                    points+=3;
                }else if(adjacencyGroupSize==5) {
                    points+=5;
                }else if(adjacencyGroupSize>=6) {
                    points+=8;
                }
            }
        }
        return points;
    }

    
    public static void main(String[] args){
        HashMap<String, ArrayList<ArrayList<int[]>>> adjacencyMap = new HashMap<>();
        String[][] board = {
                {"R","R","B"},
                {"","B","B"},
                {"R","","B"}
        };
        BookshelfPointsCalculator pointsCalculator = new BookshelfPointsCalculator(adjacencyMap, board);
        pointsCalculator.buildAdjacencyMap();
        printAdjacencyMap(adjacencyMap);
        System.out.println(pointsCalculator.calculatePoints(adjacencyMap));
    }
    


}
