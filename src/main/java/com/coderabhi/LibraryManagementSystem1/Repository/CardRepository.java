package com.coderabhi.LibraryManagementSystem1.Repository;

import com.coderabhi.LibraryManagementSystem1.Entity.LibraryCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<LibraryCard, Integer> {
}
