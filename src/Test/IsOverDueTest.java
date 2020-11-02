package Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import library.LibraryApp;

class IsOverDueTest {

	@Test
	public void testSameDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		String dueDate = dateFormat.format(date);		
		Assertions.assertFalse(LibraryApp.isOverDue(dueDate));
	}
	@Test
	public void testNotDue() {
		String dueDate = "11/1/2021";
		
		Assertions.assertFalse(LibraryApp.isOverDue(dueDate));
	}
	@Test
	public void testOverDue() {
		String dueDate = "10/01/2010";
		
		Assertions.assertTrue(LibraryApp.isOverDue(dueDate));
	}
	

}
