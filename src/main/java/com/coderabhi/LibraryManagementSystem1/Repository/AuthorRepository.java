package com.coderabhi.LibraryManagementSystem1.Repository;

import com.coderabhi.LibraryManagementSystem1.Entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
