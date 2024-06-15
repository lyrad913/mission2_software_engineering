import java.util.Collections;
import java.util.Vector;
/*
 * TODO: 
 * 		1. search_revision branch create
 * 		2. implement search_bs(); binary_search by id
 * 		3. do junit test, git merge --> main
 * 		4. search VS search_bs performance test on performance_test branch --> git merge
 * 		5. Stdout --> save to folder by Jenkins stage
 * 		6. README
 * 			- pdf
 * 			- role
 * 			- git/jenkins error msg (at least 1, max 5 for each)
 *  		- git project address
 */

/*
 * FIXME:
 * 		1. Coding Style?
 * 		2. Documentation?
 * 		3. Comment
 * */

public class BookManager {

	private Vector<Book> bookshelf;

	public BookManager() {
		bookshelf = new Vector<>();
	}

	/**
	 * Add book and check if there is a book with the same id
	 * 
	 * @param toAdd book you want to add
	 * @throws BookExistException if there is a book with the same id
	 */
	public void addBook(Book toAdd) throws BookManager.BookExistException {
		int id = toAdd.getId();
		if (this.searchBook(id) == null) {
			this.bookshelf.add(toAdd);
			Collections.sort(this.bookshelf, new BookComparator());
		} else {
			String msg = "해당 ID(" + toAdd.getId() + ")는 이미 존재합니다.";
			throw new BookExistException(msg);
		}
	}

	/**
	 * Find a book based on id in full scan
	 *
	 * @param id target id you want to find
	 * @return book if there is a book with same id else return null
	 */
	public Book searchBook(int id) {
		for (Book book : bookshelf) {
			if (book.getId() == id) {
				return book;
			}
		}
		return null;
	}

	/**
	 * Find a book based on id in binary search
	 * 
	 * @param id target id you want to find
	 * @return book if there is a book with same id else return null
	 */
	public Book binarySearchBook(int id) {
		int left = 0, right = this.bookshelf.size() - 1;
		while (left <= right) {
			int mid = (left + right) >> 1;
			Book book = this.bookshelf.get(mid);

			if (book.getId() == id)
				return book;
			else if (book.getId() < id)
				left = mid + 1;
			else
				right = mid - 1;
		}
		return null;

	}

	/**
	 * Remove a book based on id
	 * 
	 * @param id target id you want to remove
	 * @thorws BookNOTFindExcetipn if there is no book with same id
	 */
	public void removeBook(int id) throws BookManager.BookNOTFindException {
		Book toRemove = this.searchBook(id);
		if (toRemove != null) {
			this.bookshelf.remove(toRemove);
		} else {
			String msg = "해당 ID(" + id + ")의 도서를 찾을 수 없습니다.";
			throw new BookNOTFindException(msg);
		}
	}

	/**
	 * Return list of books
	 *
	 * @return bookshelf Vector of books
	 */
	public Vector<Book> getBookshelf() {
		return this.bookshelf;
	}

	public class BookNOTFindException extends RuntimeException {
		public BookNOTFindException() { // Do Nothing
		}

		public BookNOTFindException(String msg) {
			super(msg);
		}
	}

	public class BookExistException extends RuntimeException {
		public BookExistException() { // Do Nothing
		}

		public BookExistException(String msg) {
			super(msg);
		}
	}
}