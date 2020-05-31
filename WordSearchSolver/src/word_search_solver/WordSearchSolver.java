package word_search_solver;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class WordSearchSolver {
	static int words_found = 0;
	public static void main(String[] args) throws IOException {
		int word_count = 0;
		
		String puzzle_fname = "puzzle-simpsons.txt";
		String words_fname = "words-simpsons.txt";
		
		Path currentPath = Paths.get(System.getProperty("user.dir"));
		Path puzzleFilePath = Paths.get(currentPath.toString(), "text", puzzle_fname);
		Path wordsFilePath = Paths.get(currentPath.toString(), "text", words_fname);
		
		ArrayList<String> puzzle = FileIO.readTextFileAsList(puzzleFilePath.toString());
		ArrayList<String> words = FileIO.readTextFileAsList(wordsFilePath.toString());
		
		char[][] grid = new char[puzzle.size()][puzzle.size()];
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
			words_found += stringContainsWord(text, words);
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
			words_found += stringContainsWord(text, words);
			
			text = "";
		}
		
		// Bottom right to top left
		for (int x_pos = puzzle.size() - 2; x_pos > 0; x_pos--) {
			for (int y = puzzle.size() - 1, x = x_pos; y >= 0; y--) {
				text += grid[y][x];
				if (x != 0) { x--; }
				else { break; }
			}
			words_found += stringContainsWord(text, words);
			text = "";
		}
		
		// Bottom left to top right
		for (int x_pos = 0; x_pos < puzzle.size() - 1; x_pos++) {
			for (int y = puzzle.size() - 1, x = x_pos; y > 0; y--) {
				text += grid[y][x];
				if (x < puzzle.size() - 1) { x++; }
				else { break; } 
			}
			words_found += stringContainsWord(text, words);
			text = "";
		}
		
		for (String line : puzzle) {
			words_found += stringContainsWord(line, words);
		}
		
		System.out.println(words_found + " of " + word_count + " words found");
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
