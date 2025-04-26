package com.coachbar.library.system.service;

import com.coachbar.library.system.model.Book;
import com.coachbar.library.system.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository; // ✅ Mock it

    @InjectMocks
    private BookServiceImpl bookService; // ✅ Inject the mock into service

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // ✅ Very important
    }

    @Test
    void testCreateBook() {

        Book book = new Book();
        book.setTitle("Harry Potter");
        book.setAuthor("J K Rowling");

        when(bookRepository.save(any(Book.class))).thenReturn(book); // ✅ Mock behavior


        Book createdBook = bookService.createBook(book);


        assertNotNull(createdBook);
        assertEquals("Harry Potter", createdBook.getTitle());
        assertEquals("J K Rowling", createdBook.getAuthor());
        verify(bookRepository, times(1)).save(book); // ✅ verify that save() was called once
    }


    @Test
    void testGetBook() {

        Long id = 1L;
        Book book = new Book();
        book.setId(id);
        book.setTitle("Harry Potter");
        book.setAuthor("J K Rowling");
        book.setPublicationYear("2000");

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));


        Book fetchedBook = bookService.getBookById(id);


        assertNotNull(fetchedBook);
        assertEquals(id, fetchedBook.getId());
        assertEquals("Harry Potter", fetchedBook.getTitle());
        verify(bookRepository, times(1)).findById(id);
    }

    @Test
    void testGetAllBooks() {
        // Arrange
        Book book1 = new Book();
        book1.setTitle("Harry Potter");
        book1.setAuthor("J K Rowling");
        book1.setPublicationYear("2000");

        Book book2 = new Book();
        book2.setTitle("2 States");
        book2.setAuthor("Chetan Bhagat");
        book2.setPublicationYear("2014");

        List<Book> books = List.of(book1, book2);
        when(bookRepository.findAll()).thenReturn(books);

        // Act
        List<Book> allBooks = bookService.getAllBooks();

        // Assert
        assertEquals(2, allBooks.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testUpdateBook() {
        // Arrange
        Long id = 1L;
        Book existingBook = new Book();
        existingBook.setId(id);
        existingBook.setTitle("Harry Potter");
        existingBook.setAuthor("J K Rowling");
        existingBook.setPublicationYear("2000");

        Book updatedBook = new Book();
        updatedBook.setTitle("Avengers");
        updatedBook.setAuthor("Stan Lee");
        updatedBook.setPublicationYear("2004");

        when(bookRepository.findById(id)).thenReturn(Optional.of(existingBook)); // Mock the findById
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook); // Mock the save method

        // Act
        Book result = bookService.updateBook(id, updatedBook);

        // Assert
        assertNotNull(result);
        assertEquals("Avengers", result.getTitle());
        assertEquals("Stan Lee", result.getAuthor());
        assertEquals("2004", result.getPublicationYear());
        verify(bookRepository, times(1)).findById(id); // Verify findById was called once
        verify(bookRepository, times(1)).save(any(Book.class)); // Verify save was called once
    }




}
