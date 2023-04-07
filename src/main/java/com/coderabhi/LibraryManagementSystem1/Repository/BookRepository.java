package com.coderabhi.LibraryManagementSystem1.Repository;

import com.coderabhi.LibraryManagementSystem1.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
