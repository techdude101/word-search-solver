package word_search_solver;

/**
 * @author Tech
 *
 */
public class Word {
	// Variables
	private String _word;
	private boolean _found;
	private int _startPosX;
	private int _startPosY;
	private Direction _direction;
	
	// Constructor
	public Word(String word) {
		this._word = word;
	}
	
	// Methods
	public String get() {
		return this._word;
	}
	
	public void setFound(boolean b) {
		_found = b;
	}
	
	public boolean isFound() {
		return this._found;
	}
	
	public void setStartPos(int x, int y) {
		this._startPosX = x;
		this._startPosY = y;
	}
	

	public int[] getStartPos() {
		int[] pos = new int[] { this._startPosX, this._startPosY };
		return pos;
	}
	
	public void setDirection(Direction d) {
		_direction = d;
	}
	
	public Direction getDirection() {
		return this._direction;
	}
}
