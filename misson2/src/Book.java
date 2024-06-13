
public class Book {
	private int id;
	private String title;
	private String author;
	private int year;

	public Book(int _id, String _title, String _author, int _year) {
		id = _id;
		title = _title;
		author = _author;
		year = _year;
	}

	@Override
	public String toString() {
		return String.format("Book{id: '%d, 제목: '%s', 저자: '%s', 출판년도: %d}", id, title, author, year);
	}

	public int getId() {
		return id;
	}
}
