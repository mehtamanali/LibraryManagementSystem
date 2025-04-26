package com.coachbar.library.system.repository;

import com.coachbar.library.system.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
