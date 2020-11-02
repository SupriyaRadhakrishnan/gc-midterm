package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import library.LibraryApp;
import library.Media;

class SearchByDirectorTest {
		
		@Test 
		void testMultipleSearchResultsFound() throws IOException {
			String data = "Nolan";
			List<Media> movieActual = LibraryApp.searchByDirector(data);
			String expected = "The Dark Knight";
			String actual = movieActual.get(0).getTitle();
			assertEquals(expected, actual);
			 expected = "Inception";
			 actual = movieActual.get(1).getTitle();
			assertEquals(expected, actual);
	
			
		}
		@Test 
		void testSingleSearchResultsFound() throws IOException {
			String data = "Chaplin";
			List<Media> movieActual = LibraryApp.searchByDirector(data);
			String expected = "Modern Times";
			String actual = movieActual.get(0).getTitle();
			assertEquals(expected, actual);
			
		}
		@Test 
		void testNoSearchResultsFound() throws IOException {
			String data = "Supriya";
			List<Media> movieActual = LibraryApp.searchByDirector(data);
			assertTrue(movieActual.isEmpty());
			
		}
	}


