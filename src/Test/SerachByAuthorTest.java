package Test;

import static org.junit.jupiter.api.Assertions.*;

import library.Book;
import library.LibraryApp;
import library.Media;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.junit.jupiter.api.Test;


class SerachByAuthorTest {

	@Test 
	void testMulitipleSearchResultsFound() throws IOException {
		String data = "William";	
		List<Media> bookActual = LibraryApp.searchByAuthor(data);
		String expected = "Hamlet";
		String actual = bookActual.get(0).getTitle();
		assertEquals(expected, actual);
		expected = "Othello";
		actual = bookActual.get(1).getTitle();
		assertEquals(expected, actual);
		
	}
	@Test 
	void testSingleSearchResultsFound() throws IOException {
		String data = "Leo";
		List<Media> bookActual = LibraryApp.searchByAuthor(data);
		String expected = "War and Peace";
		String actual = bookActual.get(0).getTitle();
		assertEquals(expected, actual);
		
	}
	@Test 
	void testNoSearchResultsFound() throws IOException {
		String data = "Supriya";
		List<Media> bookActual = LibraryApp.searchByAuthor(data);
		assertTrue(bookActual.isEmpty());
		
	}
}
