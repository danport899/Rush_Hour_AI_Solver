package p1;
//
import java.util.ArrayList;
import java.util.List;

public class Coordinate{
	
	byte x, y;
	byte id;
	List<Coordinate> basePositionList = new ArrayList<Coordinate>();
	long gridLayoutHash = 0;
	Coordinate parentCoordinate;
	int depth;
	
    public Coordinate(byte x, byte y) {
    	this.x = x;
    	this.y = y;
    }
    public Coordinate(byte x, byte y, byte id) {
    	this.x = x;
    	this.y = y;
    	this.id = id;
    }
    
}
