package controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

class BookshelfPointsCalculatorTest {

	@Test
	void backwardsLShapeTest() {
		HashMap<String, ArrayList<ArrayList<int[]>>> adjacencyMap = new HashMap<>();
        String[][] board = {
                {"R","R","B"},
                {"R","B","B"},
                {"R","","B"}
        };
        BookshelfPointsCalculator pointsCalculator = new BookshelfPointsCalculator(adjacencyMap, board);
        pointsCalculator.buildAdjacencyMap();
        int points = pointsCalculator.calculatePoints(adjacencyMap);
        assertEquals(6, points);
	}
	
	@Test
	void adjacentToNothingTest() {
		HashMap<String, ArrayList<ArrayList<int[]>>> adjacencyMap = new HashMap<>();
        String[][] board = {
                {"R","R","B"},
                {"R","G","B"},
                {"","R","B"}
        };
        BookshelfPointsCalculator pointsCalculator = new BookshelfPointsCalculator(adjacencyMap, board);
        pointsCalculator.buildAdjacencyMap();
        int points = pointsCalculator.calculatePoints(adjacencyMap);
        assertEquals(4, points);
	}
	
	@Test
	void groupOfFiveTest() {
		HashMap<String, ArrayList<ArrayList<int[]>>> adjacencyMap = new HashMap<>();
        String[][] board = {
                {"R","R","B"},
                {"R","G","B"},
                {"R","R","B"}
        };
        BookshelfPointsCalculator pointsCalculator = new BookshelfPointsCalculator(adjacencyMap, board);
        pointsCalculator.buildAdjacencyMap();
        int points = pointsCalculator.calculatePoints(adjacencyMap);
        assertEquals(7, points);
	}
	
	@Test
	void groupOfSixTest() {
		HashMap<String, ArrayList<ArrayList<int[]>>> adjacencyMap = new HashMap<>();
        String[][] board = {
                {"R","R","B"},
                {"R","G","B"},
                {"R","R","B"},
                {"","R",""}
        };
        BookshelfPointsCalculator pointsCalculator = new BookshelfPointsCalculator(adjacencyMap, board);
        pointsCalculator.buildAdjacencyMap();
        int points = pointsCalculator.calculatePoints(adjacencyMap);
        assertEquals(10, points);
	}

}
