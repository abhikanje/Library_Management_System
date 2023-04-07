package com.coderabhi.LibraryManagementSystem1.Service;

import com.coderabhi.LibraryManagementSystem1.Dto.BookRequestDto;
import com.coderabhi.LibraryManagementSystem1.Dto.BookResponseDto;
import com.coderabhi.LibraryManagementSystem1.Entity.Author;
import com.coderabhi.LibraryManagementSystem1.Entity.Book;
import com.coderabhi.LibraryManagementSystem1.Repository.AuthorRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    AuthorRepository authorRepository;

    public BookResponseDto addBook(BookRequestDto bookRequestDto) throws Exception {

        // get the author object
        Author author = authorRepository.findById(bookRequestDto.getAuthorId()).get();

        Book book = new Book();
        book.setName(bookRequestDto.getName());
        book.setGenre(bookRequestDto.getGenre());
        book.setPrice(bookRequestDto.getPrice());
        book.setIssued(false);
        book.setAuthor(author);

        author.getBooks().add(book);
        authorRepository.save(author);  // will save both book and author

        // create a response also
        BookResponseDto bookResponseDto = new BookResponseDto();
        bookResponseDto.setName(book.getName());
        bookResponseDto.setPrice(book.getPrice());

        return bookResponseDto;
    }
}
