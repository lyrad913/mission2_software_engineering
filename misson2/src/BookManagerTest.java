import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookManagerTest {

	private BookManager manager;
	private Book book1, book2, book3;

	@BeforeEach
	void setUp() throws RuntimeException {
		manager = new BookManager();
		book1 = new Book(1, "자바 기초", "Jane", 2021);
		book2 = new Book(2, "소프트웨어 공학", "Tom", 2014);
		book3 = new Book(3, "분산 컴퓨팅", "Yoon", 2024);
	}

	@Test
	void testAddBook() throws RuntimeException {
		System.out.println("\n=== Add Book Test Begin ===");
		System.out.println("Add book id 1, 2, 3 to bookshelf");

		System.out.println("Before Book id 1 add : " + manager.SearchBook(1));
		manager.AddBook(book1);
		assertNotNull(manager.SearchBook(1));
		System.out.println("After Book id 1 Added : " + manager.SearchBook(1));

		System.out.println("Before Book id 2 add : " + manager.SearchBook(2));
		manager.AddBook(book2);
		assertNotNull(manager.SearchBook(2));
		System.out.println("After Book id 2 Added : " + manager.SearchBook(2));

		System.out.println("Before Book id 3 add : " + manager.SearchBook(3));
		manager.AddBook(book3);
		assertNotNull(manager.SearchBook(3));
		System.out.println("After Book id 3 Added : " + manager.SearchBook(3));

		Assertions.assertThrows(BookManager.BookExistException.class, () -> {
			manager.AddBook(book1);
		});
		System.out.println("Book 1 already Exist.");

		System.out.println("*** Pass the Add Book Test ***");

	}

	@Test
	void testSearchBook() throws RuntimeException {
		System.out.println("\n=== Search Book Test Begin ===");
		System.out.println("Add book id 1, 2 to bookshelf");
		manager.AddBook(book1);
		manager.AddBook(book2);

		assertNotNull(manager.SearchBook(1));
		System.out.println("Find book id 1 : " + manager.SearchBook(1));
		assertNotNull(manager.SearchBook(2));
		System.out.println("Find book id 2 : " + manager.SearchBook(2));
		assertNull(manager.SearchBook(3));
		System.out.println("Book id 3 not exist, null return : " + manager.SearchBook(3));

		System.out.println("*** Pass the Search Book Test ***");
	}

	@Test
	void testRemoveBook() throws RuntimeException {
		System.out.println("\n=== Remove Book Test Begin ===");
		System.out.println("Add book id 1, 2 to bookshelf");
		manager.AddBook(book1);
		manager.AddBook(book2);

		System.out.println("Before Book id 1 Remove : " + manager.SearchBook(1));
		manager.RemoveBook(1);
		assertNull(manager.SearchBook(1));
		System.out.println("After Book id 1 Removed : " + manager.SearchBook(1));

		Assertions.assertThrows(BookManager.BookNOTFindException.class, () -> {
			manager.RemoveBook(1);
		});
		System.out.println("Cannot find Book 1");

		Assertions.assertThrows(BookManager.BookNOTFindException.class, () -> {
			manager.RemoveBook(3);
		});
		System.out.println("Cannot find Book 3");

		System.out.println("*** Pass the Remove Book Test ***");
	}

}