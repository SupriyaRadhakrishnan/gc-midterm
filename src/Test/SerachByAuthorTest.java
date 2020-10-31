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
	void testSerachResultsFound() throws IOException {
		String data = "William";
		System.setIn(new ByteArrayInputStream(data.getBytes()));	
		List<Media> bookActual = LibraryApp.searchByAuthor();
		String expected = "Hamlet";
		String actual = bookActual.get(0).getTitle();
		assertEquals(expected, actual);
		expected = "Othello";
		actual = bookActual.get(1).getTitle();
		assertEquals(expected, actual);
		
	}
}
