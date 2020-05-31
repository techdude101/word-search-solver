package word_search_solver;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class WordSearchSolver {
	static char[][] grid;
	static int words_found = 0;
	
	public static void main(String[] args) throws IOException {
		int word_count = 0;
		
		String puzzle_fname = "puzzle-simpsons.txt";
		String words_fname = "words-simpsons.txt";
		
		Path currentPath = Paths.get(System.getProperty("user.dir"));
		Path puzzleFilePath = Paths.get(currentPath.toString(), "text", puzzle_fname);
		Path wordsFilePath = Paths.get(currentPath.toString(), "text", words_fname);
		
		ArrayList<String> puzzle = FileIO.readTextFileAsList(puzzleFilePath.toString());
		ArrayList<String> words_text = FileIO.readTextFileAsList(wordsFilePath.toString());
		ArrayList<Word> words = new ArrayList<Word>();
		
		// Store words in Word objects
		for (String w : words_text) {
			//Remove spaces for names like SIDESHOW BOB
			words.add(new Word(w.replaceAll("\\s+",  "")));
		}
		
		grid = new char[puzzle.size()][puzzle.size()];
//		char[][] grid = new char[puzzle.size()][puzzle.size()];
		word_count = words.size();
		
		String text = "";
		
		// Populate the 2d array
		for (int y = 0; y < puzzle.size(); y++) {
			for (int x = 0; x < puzzle.size(); x++) {
				grid[y][x] = puzzle.get(y).charAt(x);
			}
		}
		
		// Get columns
		for (int y = 0; y < puzzle.size(); y++) {
			for (int x = 0; x < puzzle.size() - 1; x++) {
				text += grid[x][y];
			}
			words_found += stringContainsWord(text, words_text);
			for (String word : words_text) {
				word = word.replaceAll("\\s+",  "");
				int pos = text.indexOf(word);
				
				if (pos == -1) {
					pos = text.indexOf(reverseString(word));
				}
				if (pos != -1) {
					// Update Word
					for (Word w : words) {
						if (w.get().equals(word)) {
							w.setFound(true);
							w.setDirection(Direction.TOP_TO_BOTTOM);
							w.setStartPos(y, pos);
						}
					}
//					System.out.println(word + " found at " + (y + 1) + "," + (pos + 1));
				}
			}
			text = "";
		}
		
		// Get diagonals
		// Top left to bottom right
		
		for (int x_pos = 0; x_pos < puzzle.size() - 1; x_pos++) {
			for (int y = 0, x = x_pos; y < puzzle.size(); y++) {
				text += grid[y][x];
				if (x < puzzle.size() - 1) { x++; }
				else { break; } 
			}
			words_found += stringContainsWord(text, words_text);
			for (String word : words_text) {
				word = word.replaceAll("\\s+",  "");
				int pos = text.indexOf(word);
				
				if (pos == -1) {
					pos = text.indexOf(reverseString(word));
				}
				if (pos != -1) {
					// Update Word
					for (Word w : words) {
						if (w.get().equals(word)) {
							w.setFound(true);
							w.setDirection(Direction.DIAGONAL_L_R_DOWN);
							w.setStartPos(pos, x_pos);
						}
					}
//					System.out.println(word + " found at " + (pos) + "," + (x_pos));
//					System.out.println("Diagonal " + Direction.DIAGONAL_L_R_DOWN);
				}
			}
			text = "";
		}
		
		// Bottom right to top left
		for (int x_pos = puzzle.size() - 2; x_pos > 0; x_pos--) {
			for (int y = puzzle.size() - 1, x = x_pos; y >= 0; y--) {
				text += grid[y][x];
				if (x != 0) { x--; }
				else { break; }
			}
			words_found += stringContainsWord(text, words_text);
			for (String word : words_text) {
				word = word.replaceAll("\\s+",  "");
				int pos = text.indexOf(word);
				
				if (pos == -1) {
					pos = text.indexOf(reverseString(word));
				}
				if (pos != -1) {
					// Update Word
					for (Word w : words) {
						if (w.get().equals(word)) {
							w.setFound(true);
							w.setDirection(Direction.DIAGONAL_R_L_UP);
							w.setStartPos(puzzle.size() - pos - 1, x_pos + pos);
						}
					}
					System.out.println(word + " found at " + (puzzle.size() - pos) + "," + (x_pos + pos + 1));
					System.out.println("Diagonal " + Direction.DIAGONAL_R_L_UP);
				}
			}
			text = "";
		}
		
		// Bottom left to top right
		for (int x_pos = 0; x_pos < puzzle.size() - 1; x_pos++) {
			for (int y = puzzle.size() - 1, x = x_pos; y > 0; y--) {
				text += grid[y][x];
				if (x < puzzle.size() - 1) { x++; }
				else { break; } 
			}
			words_found += stringContainsWord(text, words_text);
			for (String word : words_text) {
				word = word.replaceAll("\\s+",  "");
				int pos = text.indexOf(word);
				
				if (pos == -1) {
					pos = text.indexOf(reverseString(word));
				}
				if (pos != -1) {
					// Update Word
					for (Word w : words) {
						if (w.get().equals(word)) {
							w.setFound(true);
							w.setDirection(Direction.DIAGONAL_L_R_UP);
							w.setStartPos(puzzle.size() - pos - 1, x_pos + pos);
						}
					}
//					System.out.println(word + " found at " + (puzzle.size() - pos) + "," + (x_pos + pos + 1));
//					System.out.println("Diagonal " + Direction.DIAGONAL_L_R_UP);
				}
			}
			text = "";
		}
		
		// Rows
		for (int y = 0; y < puzzle.size(); y++) {
			words_found += stringContainsWord(puzzle.get(y), words_text);
			for (String word : words_text) {
				word = word.replaceAll("\\s+",  "");
				int pos = puzzle.get(y).indexOf(word);
				
				if (pos == -1) {
					pos = puzzle.get(y).indexOf(reverseString(word));
				}
				if (pos != -1) {
					// Update Word
					for (Word w : words) {
						if (w.get().equals(word)) {
							w.setFound(true);
							w.setDirection(Direction.LEFT_TO_RIGHT);
							w.setStartPos(pos, y);
						}
					}
//					System.out.println(word + " found at " + (y + 1) + "," + (pos + 1));
				}
			}
		}
		
		System.out.println(words_found + " of " + word_count + " words found");
		
		System.out.println();
		displayPuzzle(puzzle.size(), puzzle);
		System.out.println();
		
		for (Word w : words) {
			if (w.isFound() ) {
				int[] pos = w.getStartPos();
//				System.out.println(w.get() + " found at " + pos[0] + "," + pos[1]);
				
				resetPuzzle(puzzle.size(), puzzle);
				replaceWord(w, puzzle.size());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				displayPuzzle(puzzle.size(), puzzle);
				System.out.println();
				System.out.println("Found " + w.get());
				System.out.println();
			} else { System.out.println("Not found " + w.get()); }
		}
		
	}
	
	public static void replaceWord(Word w, int size) {
		if ( w.get().length() == 0) { return; }
		int[] startPos = w.getStartPos();
		int x = startPos[0];
		int y = startPos[1];
		
		switch (w.getDirection()) {
			case TOP_TO_BOTTOM:
				for (int i = 0; i < w.get().length(); i++) {
					grid[y + i][x] = ' ';
				}
				break;
			case LEFT_TO_RIGHT:
				for (int i = 0; i < w.get().length(); i++) {
					if (x + i > (size - 1)) { break; }
					grid[y][x + i] = ' ';
				}
				break;	
			case DIAGONAL_L_R_UP:
				for (int i = 0; i < w.get().length(); i++) {
					grid[x - i][y + i] = ' ';
				}
				break;
			case DIAGONAL_R_L_UP:
				for (int i = 0; i < w.get().length(); i++) {
					grid[x - i][y - i] = ' ';
				}
				break;
			case DIAGONAL_L_R_DOWN:
				for (int i = 0; i < w.get().length(); i++) {
					grid[x + i][y + i] = ' ';
				}
				break;
			default:
				break;
		}
	}

	public static void resetPuzzle(int size, ArrayList<String> lines) {
		// Populate the 2d array
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				grid[y][x] = lines.get(y).charAt(x);
			}
		}
	}
	
	public static void displayPuzzle(int size, ArrayList<String> lines) {
		// Populate the 2d array
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				System.out.print(grid[y][x]);
			}
			System.out.println();
		}
	}
	
	public static int[] stringStartPos(String s, String word) {
		return new int[] {0, 0 }; 
	}
	
	public static int stringContainsWord(String s, ArrayList<String> list) {
		int foundWords = 0;
		for (String word : list) {
			word = word.replaceAll("\\s+",  "");
			if (s.contains(word) || reverseString(s).contains(word)) { 
				foundWords++; 
			}
		}
		return foundWords;
	}
	
	public static String reverseString(String s) {
		StringBuilder sb = new StringBuilder(s);
		return sb.reverse().toString();
	}
}
