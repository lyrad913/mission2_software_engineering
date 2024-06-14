import java.util.Comparator;

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
class BookComparator implements Comparator<Book>{
	public int compare(Book arg1,Book arg2) {
		return ((Book)arg1).getId() >((Book)arg2).getId() ?1:0;
	}
}