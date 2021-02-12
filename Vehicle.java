package p1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Vehicle {
	
	Color color;
	boolean direction;
	boolean size;
	byte id;
	byte lockedPoint;
	List<Byte> axisPoints = new ArrayList<Byte>();
	
	
	public Vehicle(byte id, byte x, byte y, boolean direction, boolean size, Color color) {
		// direction false = horizontal. true = vertical;
		// size false = 2(car); true = 3(bus)
		this.id = id;
		this.color = color;
		this.direction = direction;
		this.size = size;
		if(direction) {
			lockedPoint =x;
			axisPoints.add(y);
			axisPoints.add((byte)(y+1));
			if(size) axisPoints.add((byte)(y+2));
			
		}
		else {
			lockedPoint = y;
			axisPoints.add(x);
			axisPoints.add((byte)(x+1));
			if(size) axisPoints.add((byte)(x+2));
		}
		
	}

}
