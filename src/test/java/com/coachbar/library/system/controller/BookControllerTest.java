package com.coachbar.library.system.controller;

import com.coachbar.library.system.config.TestSecurityConfig;
import com.coachbar.library.system.model.Book;
import com.coachbar.library.system.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@Import(TestSecurityConfig.class)  // <-- Add this line
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper; // from jackson-databind

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Harry Potter");
        book.setAuthor("J K Rowling");
        book.setPublicationYear("2000");
    }

    @Test
    void testCreateBook() throws Exception {
        when(bookService.createBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Harry Potter"));
    }

    @Test
    @WithMockUser
    void testGetBookById() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(book);

        mockMvc.perform(get("/book/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Harry Potter"));
    }

    @Test
    void testGetAllBooks() throws Exception {
        List<Book> books = Arrays.asList(book, new Book());

        when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testUpdateBook() throws Exception {
        book.setTitle("New Title");

        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(book);

        mockMvc.perform(put("/book/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Title"));
    }

    @Test
    void testDeleteBook() throws Exception {
        doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/book/{id}", 1L))
                .andExpect(status().isNoContent());
    }

}
