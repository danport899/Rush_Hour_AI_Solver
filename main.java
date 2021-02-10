package p1;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;


public class main {
	
	final static byte gridSize = 6;
	final static byte cellSize = 80;
	final static byte successPoint = 4;
	final static boolean watchProcess = false;
	
	
	static Puzzle_GUI GUI;
	
	static List<Vehicle> vehicles = new ArrayList<Vehicle>();
	static Map<Short, Byte > blockHash = new HashMap<Short, Byte>();
	
	static List<Coordinate> unseenConfigs = new ArrayList<Coordinate>();	
	static Map<Long, Boolean > seenConfigs = new HashMap<Long, Boolean>();
	static Map<Long, List<Coordinate> > IDseenConfigs = new HashMap<Long, List<Coordinate>>();
	
	static List<Coordinate> baseCoordinates = new ArrayList<Coordinate>();
	static List<Coordinate> possibleActions = new ArrayList<Coordinate>();
	
	static List<Coordinate> solutionSteps = new ArrayList<Coordinate>();
	
	static int moveNumber = 0;
	static int maxSize = 0;
	static long gridHash = 0;
	static Coordinate currentCoordinate;
	
	
	public static void main(String args[]) throws InterruptedException {
		
		
		
		//initEasy();
		initEasy();
		
		initUI();
		
		
		displayGrid();
		
		long timeStart = System.currentTimeMillis();
		
		//Get current layout in baseCoordinates, Hash the Layout, store in in seen layouts
		baseCoordinates = getBaseCoordinates();
		gridHash = getTotalGridHash(baseCoordinates);
		
		//System.out.println(possibleActions.size() + " possible moves");
		//System.out.println();
		//Add them to the unseenMoves list
		
		breadthFirstSearch();
		
		//depthFirstSearch();
		
		//iterativeDeepeningSearch();
		
		long timeEnd = System.currentTimeMillis();
		
		displayGrid();	
		//printResults(moveNumber);
		System.out.println("Total Time: " + (timeEnd - timeStart)/1000.0 + " seconds");
		System.out.println("Total Size (Max Size of Unseen Configs): " + maxSize);
		if(seenConfigs.size() != 0) System.out.println("Length of Seen Configs: " + seenConfigs.size());
		else System.out.println("Length of Seen Configs: " + IDseenConfigs.size());
		
		
		
		while(currentCoordinate.parentCoordinate!=null) {
			//displayGrid();
			//reconstructLayout(currentCoordinate);
			solutionSteps.add(0,currentCoordinate);
			currentCoordinate = currentCoordinate.parentCoordinate;
		}
		
		System.out.println("Total Steps to Completion " + solutionSteps.size());
		for(Coordinate c: solutionSteps) {
			reconstructLayout(c);
			GUI.constructGrid(vehicles);
			TimeUnit.MILLISECONDS.sleep(1500);
			
		}
	}
	
	
	public static void initEasy() {
		// Create all 36 slots by their hash code.
		refreshGrid();
		
		
		
		//                          id | byte x |  byte y | direction | size
Vehicle victoryCar = new Vehicle( (byte) 0, (byte) 0 , (byte)3, false,  false, Color.RED);
		for(short num: getCoordinateHashCodeList(victoryCar)) {
			blockHash.put(num, (byte)0);
		}
		vehicles.add(victoryCar);
		
		Vehicle v1 = new Vehicle( (byte) 1, (byte)0 , (byte)4, true,  false, Color.BLUE);
		for(short num: getCoordinateHashCodeList(v1)) {
			blockHash.put(num, (byte)1);
		}
		vehicles.add(v1);
		
		Vehicle v2 = new Vehicle( (byte) 2, (byte)1 , (byte)4, true,  false, Color.CYAN);
		for(short num: getCoordinateHashCodeList(v2)) {
			blockHash.put(num, (byte)2);
		}
		vehicles.add(v2);
		
		Vehicle v3 = new Vehicle((byte) 3, (byte)2 , (byte)0, true,  false, Color.GREEN);
		for(short num: getCoordinateHashCodeList(v3)) {
			blockHash.put(num, (byte)3);
		}
		vehicles.add(v3);
		
		Vehicle v4 = new Vehicle( (byte)4,  (byte)2 , (byte)2, true,  false, Color.ORANGE);
		for(short num: getCoordinateHashCodeList(v4)) {
			blockHash.put(num, (byte)4);
		}
		vehicles.add(v4);
		
		Vehicle v5 = new Vehicle((byte) 5,  (byte)2 , (byte)4, false,  false, Color.YELLOW);
		for(short num: getCoordinateHashCodeList(v5)) {
			blockHash.put(num, (byte)5);
		}
		vehicles.add(v5);
		
		Vehicle v6 = new Vehicle((byte) 6,  (byte)2 , (byte)5, false,  false, Color.WHITE);
		for(short num: getCoordinateHashCodeList(v6)) {
			blockHash.put(num, (byte)6);
		}
		vehicles.add(v6);
		
		Vehicle v7 = new Vehicle((byte)7,(byte)3 , (byte)1, true,  true, Color.PINK);
		for(short num: getCoordinateHashCodeList(v7)) {
			blockHash.put(num, (byte)7);
		}
		vehicles.add(v7);
		
		Vehicle v8 = new Vehicle((byte) 8, (byte)4 , (byte)1, true,  false, Color.MAGENTA);
		for(short num: getCoordinateHashCodeList(v8)) {
			blockHash.put(num, (byte)8);
		}
		vehicles.add(v8);
		
		Vehicle v9 = new Vehicle( (byte)9, (byte)4 , (byte)4, true,  false, Color.BLACK);
		for(short num: getCoordinateHashCodeList(v9)) {
			blockHash.put(num, (byte)9);
		}
		vehicles.add(v9);
		
		
		
		
		
		
		
	}
	
	public static void initExpert() {
	refreshGrid();
		
		
		
		//                          id | byte x |  byte y | direction | size
Vehicle victoryCar = new Vehicle( (byte) 0, (byte) 0 , (byte)3, false,  false, Color.RED);
		for(short num: getCoordinateHashCodeList(victoryCar)) {
			blockHash.put(num, (byte)0);
		}
		vehicles.add(victoryCar);
		
		Vehicle v1 = new Vehicle( (byte) 1, (byte)0 , (byte)0, false,  true, Color.BLUE);
		for(short num: getCoordinateHashCodeList(v1)) {
			blockHash.put(num, (byte)1);
		}
		vehicles.add(v1);
		
		Vehicle v2 = new Vehicle( (byte) 2, (byte)0 , (byte)2, false,  false, Color.CYAN);
		for(short num: getCoordinateHashCodeList(v2)) {
			blockHash.put(num, (byte)2);
		}
		vehicles.add(v2);
		
		Vehicle v3 = new Vehicle((byte) 3, (byte)0 , (byte)4, true,  false, Color.GREEN);
		for(short num: getCoordinateHashCodeList(v3)) {
			blockHash.put(num, (byte)3);
		}
		vehicles.add(v3);
		
		Vehicle v4 = new Vehicle( (byte)4,  (byte)1 , (byte)1, false,  false, Color.MAGENTA);
		for(short num: getCoordinateHashCodeList(v4)) {
			blockHash.put(num, (byte)4);
		}
		vehicles.add(v4);
		
		Vehicle v5 = new Vehicle((byte) 5,  (byte)1 , (byte)5, false,  false, Color.YELLOW);
		for(short num: getCoordinateHashCodeList(v5)) {
			blockHash.put(num, (byte)5);
		}
		vehicles.add(v5);
		
		Vehicle v6 = new Vehicle((byte) 6,  (byte)2 , (byte)2, true,  false, Color.WHITE);
		for(short num: getCoordinateHashCodeList(v6)) {
			blockHash.put(num, (byte)6);
		}
		vehicles.add(v6);
		
		Vehicle v7 = new Vehicle((byte)7,(byte)2 , (byte)4, false,  false, Color.DARK_GRAY);
		for(short num: getCoordinateHashCodeList(v7)) {
			blockHash.put(num, (byte)7);
		}
		vehicles.add(v7);
		
		Vehicle v8 = new Vehicle((byte) 8, (byte)3 , (byte)0, true,  false, Color.RED.darker().darker());
		for(short num: getCoordinateHashCodeList(v8)) {
			blockHash.put(num, (byte)8);
		}
		vehicles.add(v8);
		
		Vehicle v9 = new Vehicle( (byte)9, (byte)3 , (byte)2, false,  false, Color.ORANGE);
		for(short num: getCoordinateHashCodeList(v9)) {
			blockHash.put(num, (byte)9);
		}
		vehicles.add(v9);
		
		Vehicle v10 = new Vehicle( (byte)10, (byte)3 , (byte)5, false,  true, Color.PINK);
		for(short num: getCoordinateHashCodeList(v10)) {
			blockHash.put(num, (byte)10);
		}
		vehicles.add(v10);
		
		Vehicle v11 = new Vehicle( (byte)11, (byte)4 , (byte)3, true,  false, Color.LIGHT_GRAY);
		for(short num: getCoordinateHashCodeList(v11)) {
			blockHash.put(num, (byte)11);
		}
		vehicles.add(v11);
		
		Vehicle v12 = new Vehicle( (byte)12, (byte)5 , (byte)0, true,  true, Color.BLACK);
		for(short num: getCoordinateHashCodeList(v12)) {
			blockHash.put(num, (byte)12);
		}
		vehicles.add(v12);
		
		
		
		
		
		
	}
	
	public static void initUI() {
		GUI = new Puzzle_GUI(vehicles);
		GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUI.setSize(gridSize*cellSize, gridSize*cellSize);
		GUI.setVisible(true);
	}
	public static void breadthFirstSearch() throws InterruptedException {
		
		seenConfigs.put(gridHash, true);
		
		
		//Collect all current possible moves
		possibleActions = tallyPossibleMoves();
		
		for(Coordinate c: possibleActions) {
			c.basePositionList = baseCoordinates;	
			c.depth = 1;
			unseenConfigs.add(c);
		}

		while(vehicles.get(0).axisPoints.get(0) != successPoint) {
			
			if(unseenConfigs.size() > maxSize) maxSize = unseenConfigs.size();
			currentCoordinate = unseenConfigs.get(0);
			reconstructLayout(currentCoordinate);
					
			moveVehicle(currentCoordinate);
			if(watchProcess) {
				GUI.constructGrid(vehicles);
				TimeUnit.MILLISECONDS.sleep(1000);
			}
			
			//if(counter == 5) break;
			//displayGrid();
			unseenConfigs.remove(0);
			//displayVehiclePositions();
			baseCoordinates = getBaseCoordinates();
			gridHash = getTotalGridHash(baseCoordinates);
			
			if(seenConfigs.containsKey(gridHash)) {
				continue;
			}
			
			seenConfigs.put(gridHash, true);	
			
			
			possibleActions = tallyPossibleMoves();
			//System.out.println();
			
		
			for(Coordinate c: possibleActions) {
				//displayGrid();
				if(lookAheadHash(c)) continue;		
				c.basePositionList = baseCoordinates;
				c.parentCoordinate = currentCoordinate;
				c.depth = currentCoordinate.depth + 1;
				unseenConfigs.add(c);
				
			}
			//System.out.println(" Unseen Configs length: " + unseenConfigs.size());	
			//System.out.println("--------------------------------");
			//System.out.println("Current Depth: "+ currentCoordinate.depth);
			//displayGrid();	
					
		
		}
		
	}
	
	public static void depthFirstSearch() throws InterruptedException {
		seenConfigs.put(gridHash, true);
		
		//Collect all current possible moves
		possibleActions = tallyPossibleMoves();
		
		for(Coordinate c: possibleActions) {
			c.basePositionList = baseCoordinates;
			//c.parentMove=moveNumber;
			c.depth = 1;
			unseenConfigs.add(c);
		}
		
		while(vehicles.get(0).axisPoints.get(0) != successPoint) {
			if(unseenConfigs.size() > maxSize) maxSize = unseenConfigs.size();
			currentCoordinate = unseenConfigs.get(0);
			reconstructLayout(unseenConfigs.get(0));
			unseenConfigs.remove(0);
			
			moveVehicle(currentCoordinate);
			if(watchProcess) {
				GUI.constructGrid(vehicles);
				TimeUnit.MILLISECONDS.sleep(1000);
			}
			
			baseCoordinates = getBaseCoordinates();
			//displayGrid();
			//displayVehiclePositions();
			gridHash = getTotalGridHash(baseCoordinates);
			
			//System.out.println(gridHash);
			if(seenConfigs.containsKey(gridHash)) {		
				//System.out.println("Repeat found ^^^. Eliminating Branch\n\n");			
				//displayGrid();
				continue;
			}
			
			seenConfigs.put(gridHash, true);	
			
			possibleActions = tallyPossibleMoves();
			//System.out.println();
			
			
			for(Coordinate c: possibleActions) {
				if(lookAheadHash(c)) continue;		
				c.basePositionList = baseCoordinates;
				c.parentCoordinate = currentCoordinate;
				unseenConfigs.add(0,c);
				
			}
			//System.out.println(" Unseen Configs length: " + unseenConfigs.size());		
			//System.out.println("--------------------------------");		
			
			//displayGrid();			
		
		}
	}
	
	public static void iterativeDeepeningSearch() throws InterruptedException {
		
		int maxDepth = 0;
		
		long startingHash = gridHash;
		
		possibleActions = tallyPossibleMoves();
		for(Coordinate c: possibleActions) {
			c.basePositionList = baseCoordinates;
			//c.parentMove=moveNumber;
			c.depth = 0;
			unseenConfigs.add(c);
		}

		IDseenConfigs.put(gridHash, possibleActions);
		
		currentCoordinate = unseenConfigs.get(0);
		
		while(vehicles.get(0).axisPoints.get(0) != successPoint) {
			
			if(unseenConfigs.size() > maxSize) maxSize = unseenConfigs.size();
			
			currentCoordinate = unseenConfigs.get(0);
			unseenConfigs.remove(0);
			
			if(currentCoordinate.depth < maxDepth -1) {
				//System.out.println("Skipped");
				
	
				unseenConfigs.addAll(0,IDseenConfigs.get(currentCoordinate.gridLayoutHash));
				
				if(unseenConfigs.size() == 0) {
					unseenConfigs.addAll(IDseenConfigs.get(startingHash));
					maxDepth++;	
				}
				continue;
			}

			reconstructLayout(currentCoordinate);
			//displayGrid();
			moveVehicle(currentCoordinate);
			if(watchProcess) {
				GUI.constructGrid(vehicles);
				TimeUnit.MILLISECONDS.sleep(1000);
			}
			//displayGrid();
			baseCoordinates = getBaseCoordinates();
			gridHash = getTotalGridHash(baseCoordinates);
		
			
		
			if(IDseenConfigs.containsKey(gridHash) && currentCoordinate.depth == maxDepth) {
				if(unseenConfigs.size() == 0) {
					unseenConfigs.addAll(IDseenConfigs.get(startingHash));
					maxDepth++;	
				}
				//System.out.println("Already Seen. Removed");
				
				continue;
			}
			
			currentCoordinate.gridLayoutHash = gridHash;
			
			
			if(currentCoordinate.parentCoordinate != null && currentCoordinate.depth == maxDepth)IDseenConfigs.get(currentCoordinate.parentCoordinate.gridLayoutHash).add(0,currentCoordinate);
			
			if(currentCoordinate.depth == maxDepth-1) {
				possibleActions = tallyPossibleMoves();
				for(Coordinate c: possibleActions) {
					c.parentCoordinate = currentCoordinate;
					c.basePositionList = baseCoordinates;
					c.depth = currentCoordinate.depth +1;
					unseenConfigs.add(0,c);		
				}
				
				
			}	
			
			if(unseenConfigs.size() == 0) {
				unseenConfigs.addAll(IDseenConfigs.get(startingHash));
				maxDepth++;
			}
			
			if(!IDseenConfigs.containsKey(gridHash))IDseenConfigs.put(gridHash, new ArrayList<Coordinate>());
		
		}
		
	}
	
	
	
	public static void refreshGrid() {
		for(int i = 0; i < gridSize; i ++) {
			for(int j = 0; j < gridSize; j ++) {
				short code = (short)(i*31 + j*41);
				blockHash.put(code,(byte)-1);
			}
		}
		
	}
	
	public static void displayGrid() {
		for(byte i = 5; i >= 0; i --) {
			for(byte j = 0; j < 6; j ++) {
				short code = getBlockCode(j,i);
				if(blockHash.get(code) != -1) System.out.print(blockHash.get(code) + "  ");
				else System.out.print(".  ");
				
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
	}
	
	public static void reconstructLayout( Coordinate coordinate) {
		refreshGrid();
		List<Coordinate> basePositions = coordinate.basePositionList;
		byte baseX, baseY;
		for(int i = 0; i < vehicles.size(); i ++) {
			Vehicle currentVehicle = vehicles.get(i);
			baseX = basePositions.get(i).x;
			baseY = basePositions.get(i).y;
			if(currentVehicle.direction) {
				currentVehicle.lockedPoint = baseX;
				currentVehicle.axisPoints.set(0,baseY);
				currentVehicle.axisPoints.set(1,(byte)(baseY+1));
				if(currentVehicle.size) currentVehicle.axisPoints.set(2,(byte)(baseY+2));
				for(byte b: currentVehicle.axisPoints) {
					blockHash.put(getBlockCode(baseX, b), currentVehicle.id);
				}
			}
			else {
				currentVehicle.lockedPoint = baseY;
				currentVehicle.axisPoints.set(0,baseX);
				currentVehicle.axisPoints.set(1, (byte)(baseX+1));
				if(currentVehicle.size) currentVehicle.axisPoints.set(2,(byte)(baseX+2));
				for(byte b: currentVehicle.axisPoints) {
					blockHash.put(getBlockCode(b, baseY), currentVehicle.id);
				}
			}
			
		}
	}
	
	public static List<Coordinate> getBaseCoordinates() {
		
		
		List<Coordinate> coordinateList = new ArrayList<Coordinate>();
		for(Vehicle v: vehicles) {
			if(v.direction) {
				Coordinate coordinate = new Coordinate(v.lockedPoint, v.axisPoints.get(0), v.id);
				coordinateList.add(coordinate);
				
			}
			else {
				Coordinate coordinate = new Coordinate(v.axisPoints.get(0), v.lockedPoint, v.id);
				coordinateList.add(coordinate);
			}
		}
		
		return coordinateList;
	}
	
	public static List<Coordinate> tallyPossibleMoves() {
		byte x, y;
		List<Coordinate> coordinates = new ArrayList<Coordinate>();
		for(Vehicle vehicle: vehicles) {

			if(vehicle.direction) {
				 y = vehicle.axisPoints.get(0);
				 x = vehicle.lockedPoint;
				 if(y > 0 && blockHash.get(getBlockCode(x,(byte)(y-1))) == -1) {
					 //System.out.println(vehicle.id+ " can move down");
					 Coordinate nextCoordinate = new Coordinate(x, y, vehicle.id);
					 coordinates.add(nextCoordinate);
					 
				 }
				 y = vehicle.axisPoints.get(vehicle.axisPoints.size()-1);
				 if( y < 5 && blockHash.get(getBlockCode(x,(byte)(y+1))) == -1) {
					 //System.out.println(vehicle.id + " can move up");
					 Coordinate nextCoordinate = new Coordinate(x, y, vehicle.id);
					 coordinates.add(nextCoordinate);
				 }
				 
			}
			else {
				
				 x = vehicle.axisPoints.get(0);
				 y = vehicle.lockedPoint;
				
				 if( x > 0 && blockHash.get(getBlockCode((byte)(x-1),y)) == -1) {
					 
					 //System.out.println(vehicle.id + " can move left to " + (x-1) + "," + y);					
					 Coordinate nextCoordinate = new Coordinate(x, y, vehicle.id);
					 coordinates.add(nextCoordinate);
				 }
				 x = vehicle.axisPoints.get(vehicle.axisPoints.size()-1);
				 if( x < 5 && blockHash.get(getBlockCode((byte)(x+1),y)) == -1) {
					 
					 //System.out.println(vehicle.id + " can move right to " + (x+1) + "," + y);
					 Coordinate nextCoordinate = new Coordinate(x, y, vehicle.id);
					 coordinates.add(nextCoordinate);
				 }
				
			}
			
			
		}
		
		return coordinates;
		
	}
	
	public static List<Short> getCoordinateHashCodeList( Vehicle vehicle) {
		
		byte x, y;
		List<Short> coordinateHashes = new ArrayList<Short>();
		
		if(vehicle.direction) {
			 x = vehicle.lockedPoint;
			 for(byte b: vehicle.axisPoints) {			 
				 coordinateHashes.add(getBlockCode(x,b));
			 }
		}
		
		else {
			 y = vehicle.lockedPoint;
			 for(byte b: vehicle.axisPoints) {
				 coordinateHashes.add(getBlockCode(b,y));
			 }
		}
		
		return coordinateHashes;
			
	}
	
	public static void moveVehicle(Coordinate coordinate){
		
		Vehicle vehicle = vehicles.get(coordinate.id);
		byte x = coordinate.x, y = coordinate.y;
		short code;
		if(vehicle.direction) {
			if(vehicle.axisPoints.get(0) == y) {
				code = getBlockCode(x, vehicle.axisPoints.get(vehicle.axisPoints.size()-1));
				blockHash.put(code,(byte)-1);
				code = getBlockCode(x, (byte)(y -1));
				blockHash.put(code,vehicle.id);
				for(byte i = 0; i < vehicle.axisPoints.size() ; i ++) {			
					vehicle.axisPoints.set(i, (byte)(vehicle.axisPoints.get(i) - 1));
				}
				//System.out.println(vehicle.id + " to " + x + ", " + (y-1));
			}
			else {
				code = getBlockCode(x, vehicle.axisPoints.get(0));
				blockHash.put(code,(byte) -1);
				code = getBlockCode(x, (byte)(y +1));
				blockHash.put(code,vehicle.id);
				for(byte i = 0; i < vehicle.axisPoints.size() ; i ++) {			
					vehicle.axisPoints.set(i, (byte)(vehicle.axisPoints.get(i) + 1));
				}
				//System.out.println(vehicle.id + " to " + x + ", " + (y+1));
			}
		}
		else {
			if(vehicle.axisPoints.get(0) == x) {
				code = getBlockCode(vehicle.axisPoints.get(vehicle.axisPoints.size()-1), y);
				blockHash.put(code,(byte) -1);
				code = getBlockCode((byte)(x -1), y );
				blockHash.put(code,vehicle.id);
				for(byte i = 0; i < vehicle.axisPoints.size() ; i ++) {			
					vehicle.axisPoints.set(i, (byte)(vehicle.axisPoints.get(i) - 1));
				}
				//System.out.println(vehicle.id + " to " + (x-1) + ", " + y);
			}
			else {
				code = getBlockCode(vehicle.axisPoints.get(0), y);
				blockHash.put(code,(byte) -1);
				code = getBlockCode((byte)(x +1), y );
				blockHash.put(code,vehicle.id);
				for(byte i = 0; i < vehicle.axisPoints.size() ; i ++) {			
					vehicle.axisPoints.set(i, (byte)(vehicle.axisPoints.get(i) + 1));
				}
				//System.out.println(vehicle.id + " to " + (x+1) + ", " + y);
			}
		}
		
		vehicles.set(coordinate.id, vehicle);
	}
	
	public static short getBlockCode(byte x, byte y) {
		
		return (short) (x * 31 + y * 41);
	}
	
	
	
	public static long getTotalGridHash(List<Coordinate> baseCoordinates) {
		
		long hashValue = 0;
		byte exp = (byte)baseCoordinates.size();
		exp++;
		
		for(Coordinate c: baseCoordinates) {
			hashValue += getBlockCode(c.x,c.y) * Math.pow(13, exp);
			exp--;
		}
		return hashValue;
		
		
	}
	
	public static boolean lookAheadHash(Coordinate c) {
		//System.out.println("Car #" + c.id);	
		long gridHash;
		Vehicle vehicle = vehicles.get(c.id);
		//System.out.println("Old Car Coordinate: " + baseCoordinates.get(c.id).x + "," + baseCoordinates.get(c.id).y + " ");
		if(vehicle.direction) {
			if(vehicle.axisPoints.get(0) == c.y) {
				baseCoordinates.get(c.id).y --;
				gridHash = (getTotalGridHash(baseCoordinates));
				baseCoordinates.get(c.id).y ++;
				
			}
			else {
				baseCoordinates.get(c.id).y++;
				gridHash = (getTotalGridHash(baseCoordinates));
				baseCoordinates.get(c.id).y--;
			}
		}
		
		else {
			if(vehicle.axisPoints.get(0) == c.x) {
				baseCoordinates.get(c.id).x--;
				gridHash = (getTotalGridHash(baseCoordinates));
				baseCoordinates.get(c.id).x++;
			}
			
			else {
				baseCoordinates.get(c.id).x++;
				gridHash = (getTotalGridHash(baseCoordinates));
				baseCoordinates.get(c.id).x--;
			}
		}

		if(seenConfigs.containsKey(gridHash)) {
			return true;
		}
		return false;
		
	}
	
	public static void displayVehiclePositions() {
		byte x,y;
		for(Vehicle v: vehicles) {
			System.out.print("Vehicle " + v.id + " at : ");
			if(v.direction) {
				x = v.lockedPoint;
				for(byte b: v.axisPoints) {
					System.out.print("("+ x + "," + b + ")  ");
				}
			}
			else {
				y = v.lockedPoint;
				for(byte b: v.axisPoints) {
					System.out.print("("+ b + "," + y + ")  ");
				}
			}
			System.out.println();
		}
	}
	
	public static void printResults(int moveNumber) {
		if(moveNumber == 0) return;
		reconstructLayout(unseenConfigs.get(moveNumber));
		displayGrid();
		//printResults(unseenConfigs.get(moveNumber).parentMove);
	}
	

}


