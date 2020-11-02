package Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import library.LibraryApp;
import library.Media;

public class SearchByKeywordTest {
	@Test 
	void testMultipleSearchResultsFound() throws IOException {
		String data = "the";
		List<Media> movieActual = LibraryApp.searchByKeyword(data);
		assertEquals(8, movieActual.size());
	}
	@Test 
	void testSingleMovieSearchResultsFound() throws IOException {
		String data = "Parasite";
		List<Media> movieActual = LibraryApp.searchByKeyword(data);
		assertEquals(1, movieActual.size());
		
	}
	@Test 
	void testSingleBookSearchResultsFound() throws IOException {
		String data = "Othello";
		List<Media> bookActual = LibraryApp.searchByKeyword(data);
		assertEquals(1, bookActual.size());
		
	}
	@Test 
	void testSingleNoSearchResultsFound() throws IOException {
		String data = "Tim Stetter";
		List<Media> bookActual = LibraryApp.searchByKeyword(data);
		assertTrue(bookActual.isEmpty());
		
	}
}
