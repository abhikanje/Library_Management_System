package com.coderabhi.LibraryManagementSystem1.Dto;

import com.coderabhi.LibraryManagementSystem1.Enum.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookRequestDto {
    private String name;
    private  int price;

    private Genre genre;

    private int  authorId;
}
