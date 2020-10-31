package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import library.LibraryApp;

class IsOverDueTest {

	@Test
	public void testSameDate() {
		String dueDate = "10/31/2020";
		Assertions.assertFalse(LibraryApp.isOverDue(dueDate));
	}
	@Test
	public void testNotDue() {
		String dueDate = "11/1/2020";
		
		Assertions.assertFalse(LibraryApp.isOverDue(dueDate));
	}
	@Test
	public void testOverDue() {
		String dueDate = "10/01/2010";
		
		Assertions.assertTrue(LibraryApp.isOverDue(dueDate));
	}
	

}
