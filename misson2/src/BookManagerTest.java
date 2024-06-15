
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
	/**
	 * Testing AddBook() Add books that have id 1, 2 and 3 and check it throws an error if trying to add books that already existing id 
	 * @throws RuntimeException
	 */
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
	/**
	 * Testing SearchBook() test full scan search, and check if it throws an error if you try to find an id that doesn't exist
	 * @throws RuntimeException
	 */
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
	/**
	 * Testing BinarySearchBook() test binary search, and check if it throws an error if you try to find an id that doesn't exist
	 * @throws RuntimeException
	 */
	@Test
	void testSearchBS() throws RuntimeException {
		System.out.println("\n=== Binaray Search Test Begin ===");
		System.out.println("Add book id 1, 2 to bookshelf");
		manager.AddBook(book1);
		manager.AddBook(book2);

		assertNotNull(manager.BinarySearchBook(1));
		System.out.println("Find book id 1 : " + manager.BinarySearchBook(1));
		assertNotNull(manager.BinarySearchBook(2));
		System.out.println("Find book id 2 : " + manager.BinarySearchBook(2));
		assertNull(manager.BinarySearchBook(3));
		System.out.println("Book id 3 not exist, null return : " + manager.BinarySearchBook(3));

		System.out.println("*** Pass the Binary Search Book Test ***");
	}
	/**
	 * Testing RemoveBook() test remove book, and check if it throws an error if you try to remove a book that doesn't exist
	 * @throws RuntimeException
	 */
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

	long runSearch(int id) {
		long start = System.nanoTime();
		manager.SearchBook(id);
		long end = System.nanoTime();

		return end - start;
	}

	long runBinarySearch(int id) {
		long start = System.nanoTime();
		manager.BinarySearchBook(id);
		long end = System.nanoTime();

		return end - start;
	}

	double calImprovement(long time1, long time2) {
		double improvement = (time1 - time2) / (time1 + 1e-10) * 100;
		return improvement;
	}
	/**
	 * Performance test Measure running time in ns, make 1000000 dummy data 
	 * For full scan and binary search, measure the time taken to search for the front,mid and end index, respectively
	 */
	@Test
	void peformanceTestOnSearch() {
		System.out.println("\n=== Search Book Performance Test Begin ===");
		// Make Dummy,
		// Uniqueness is proved, add the Book directly
		manager = new BookManager();
		int numDummy = 1000000;
		for (int id = 1; id <= numDummy; ++id) {
			Book newBook = new Book(id, "Book" + id, "Author" + id, 2000);
			manager.getBookshelf().add(newBook);
		}

		long searchFrontRet = runSearch(10);
		long searchMidRet = runSearch(Math.floorDiv(numDummy, 2));
		long searchEndRet = runSearch(numDummy - 10);

		long biSearchFrontRet = runBinarySearch(10);
		long biSearchMidRet = runBinarySearch(Math.floorDiv(numDummy, 2));
		long biSearchEndRet = runBinarySearch(999990);

		System.out.println("[[Result on Search]]\n" + "Front : " + searchFrontRet + "ns\n" + "Mid : " + searchMidRet
				+ "ns\n" + "End : " + searchEndRet + "ns\n");
		System.out.println("[[Result on BinarySearch]]\n" + "Front : " + biSearchFrontRet + "ns\n" + "Mid : "
				+ biSearchMidRet + "ns\n" + "End : " + runBinarySearch(999990) + "ns\n");

		System.out.println("[[Improvement]]\n" + "Front : " + calImprovement(searchFrontRet, biSearchFrontRet) + "%\n"
				+ "Mid : " + calImprovement(searchMidRet, biSearchMidRet) + "%\n" + "End : "
				+ calImprovement(searchEndRet, biSearchEndRet) + "%\n");

		System.out.println("*** End the Search Book Performance Test ***");

	}

}