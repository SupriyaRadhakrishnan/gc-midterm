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
		void testMultipleSerachResultsFound() throws IOException {
			String data = "Nolan";
			System.setIn(new ByteArrayInputStream(data.getBytes()));
			List<Media> movieActual = LibraryApp.searchByDirector();
			String expected = "The Dark Knight";
			String actual = movieActual.get(0).getTitle();
			assertEquals(expected, actual);
			 expected = "Inception";
			 actual = movieActual.get(1).getTitle();
			assertEquals(expected, actual);
	
			
		}
	}


