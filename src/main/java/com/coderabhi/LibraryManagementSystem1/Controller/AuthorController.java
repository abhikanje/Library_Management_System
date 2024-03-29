package com.coderabhi.LibraryManagementSystem1.Controller;

import com.coderabhi.LibraryManagementSystem1.Entity.Author;
import com.coderabhi.LibraryManagementSystem1.Service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("author")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    // change to DTO
    @PostMapping("/add")
    public String addAuthor(@RequestBody Author author){

        authorService.addAuthor(author);
        return "Author added successfully";
    }

    @GetMapping("/get_authors")
    public List<Author> getAuthors(){
        return authorService.getAuthors();
    }
}
