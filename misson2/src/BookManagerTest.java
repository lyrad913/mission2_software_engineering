
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
	 * Testing AddBook() Add books that have id 1, 2 and 3 and check it throws an
	 * error if trying to add books that already existing id
	 * 
	 * @throws RuntimeException
	 */
	@Test
	void testAddBook() throws RuntimeException {
		System.out.println("\n=== Add Book Test Begin ===");
		System.out.println("Add book id 1, 2, 3 to bookshelf");

		System.out.println("Before Book id 1 add : " + manager.searchBook(1));
		manager.addBook(book1);
		assertNotNull(manager.searchBook(1));
		System.out.println("After Book id 1 Added : " + manager.searchBook(1));

		System.out.println("Before Book id 2 add : " + manager.searchBook(2));
		manager.addBook(book2);
		assertNotNull(manager.searchBook(2));
		System.out.println("After Book id 2 Added : " + manager.searchBook(2));

		System.out.println("Before Book id 3 add : " + manager.searchBook(3));
		manager.addBook(book3);
		assertNotNull(manager.searchBook(3));
		System.out.println("After Book id 3 Added : " + manager.searchBook(3));

		Assertions.assertThrows(BookManager.BookExistException.class, () -> {
			manager.addBook(book1);
		});
		System.out.println("Book 1 already Exist.");

		System.out.println("*** Pass the Add Book Test ***");

	}

	/**
	 * Testing SearchBook() test full scan search, and check if it throws an error
	 * if you try to find an id that doesn't exist
	 * 
	 * @throws RuntimeException
	 */
	@Test
	void testSearchBook() throws RuntimeException {
		System.out.println("\n=== Search Book Test Begin ===");
		System.out.println("Add book id 1, 2 to bookshelf");
		manager.addBook(book1);
		manager.addBook(book2);

		assertNotNull(manager.searchBook(1));
		System.out.println("Find book id 1 : " + manager.searchBook(1));
		assertNotNull(manager.searchBook(2));
		System.out.println("Find book id 2 : " + manager.searchBook(2));
		assertNull(manager.searchBook(3));
		System.out.println("Book id 3 not exist, null return : " + manager.searchBook(3));

		System.out.println("*** Pass the Search Book Test ***");
	}

	/**
	 * Testing BinarySearchBook() test binary search, and check if it throws an
	 * error if you try to find an id that doesn't exist
	 * 
	 * @throws RuntimeException
	 */
	@Test
	void testSearchBS() throws RuntimeException {
		System.out.println("\n=== Binaray Search Test Begin ===");
		System.out.println("Add book id 1, 2 to bookshelf");
		manager.addBook(book1);
		manager.addBook(book2);

		assertNotNull(manager.binarySearchBook(1));
		System.out.println("Find book id 1 : " + manager.binarySearchBook(1));
		assertNotNull(manager.binarySearchBook(2));
		System.out.println("Find book id 2 : " + manager.binarySearchBook(2));
		assertNull(manager.binarySearchBook(3));
		System.out.println("Book id 3 not exist, null return : " + manager.binarySearchBook(3));

		System.out.println("*** Pass the Binary Search Book Test ***");
	}

	/**
	 * Testing RemoveBook() test remove book, and check if it throws an error if you
	 * try to remove a book that doesn't exist
	 * 
	 * @throws RuntimeException
	 */
	@Test
	void testRemoveBook() throws RuntimeException {
		System.out.println("\n=== Remove Book Test Begin ===");
		System.out.println("Add book id 1, 2 to bookshelf");
		manager.addBook(book1);
		manager.addBook(book2);

		System.out.println("Before Book id 1 Remove : " + manager.searchBook(1));
		manager.removeBook(1);
		assertNull(manager.searchBook(1));
		System.out.println("After Book id 1 Removed : " + manager.searchBook(1));

		Assertions.assertThrows(BookManager.BookNOTFindException.class, () -> {
			manager.removeBook(1);
		});
		System.out.println("Cannot find Book 1");

		Assertions.assertThrows(BookManager.BookNOTFindException.class, () -> {
			manager.removeBook(3);
		});
		System.out.println("Cannot find Book 3");

		System.out.println("*** Pass the Remove Book Test ***");
	}

	long runSearch(int id) {
		long start = System.nanoTime();
		manager.searchBook(id);
		long end = System.nanoTime();

		return end - start;
	}

	long runBinarySearch(int id) {
		long start = System.nanoTime();
		manager.binarySearchBook(id);
		long end = System.nanoTime();

		return end - start;
	}

	double calImprovement(long time1, long time2) {
		double improvement = (time1 - time2) / (time1 + 1e-10) * 100;
		return improvement;
	}

	/**
	 * Performance test Measure running time in ns, make 1000000 dummy data For full
	 * scan and binary search, measure the time taken to search for the front,mid
	 * and end index, respectively
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