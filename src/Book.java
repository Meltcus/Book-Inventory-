import java.io.Serializable;

public class Book implements Serializable {
    
	private String title;
    private String authors;
    private double price;
    private String isbn;
    private String genre;
    private int year;

    /**	Parametrized Constructor
     * 
     * @param title
     * @param authors
     * @param price
     * @param isbn
     * @param genre
     * @param year
     */
    public Book(String title, String authors, double price, String isbn, String genre, int year) {
        this.title = title;
        this.authors = authors;
        this.price = price;
        this.isbn = isbn;
        this.genre = genre;
        this.year = year;
    }
    
    
    
    
    public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthors() {
		return authors;
	}
	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}


	/**
	 * This equals method compares two Books objects to see if they hold the same value
	 */
	@Override
	public  boolean equals(Object a) {
		
		
		if (a == null || a.getClass() != this.getClass())
			return false;
		
		Book Comparison = (Book) a;
		
		return (this.getAuthors().equals(Comparison.getAuthors()) && this.getTitle().equals(Comparison.getTitle()) &&
				this.getGenre().equals( Comparison.getGenre() ) && this.getIsbn().equals( Comparison.getIsbn()) &&
				this.getPrice() == Comparison.getPrice() && this.getYear() == Comparison.getYear() );
		
	}

    /**
     * Thia toString method prints out all the information regarding said book in a CSV file format
     */
    @Override
    public String toString() {
    	return title + ", " + authors + ", " + price + ", " + isbn + ", " + genre + ", " + year + "\n";
    }

    

}
