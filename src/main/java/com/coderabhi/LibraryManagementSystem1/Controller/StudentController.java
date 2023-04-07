package com.coderabhi.LibraryManagementSystem1.Controller;

import com.coderabhi.LibraryManagementSystem1.Dto.StudentRequestDto;
import com.coderabhi.LibraryManagementSystem1.Dto.StudentResponseDto;
import com.coderabhi.LibraryManagementSystem1.Dto.StudentUpdateEmailRequestDto;
import com.coderabhi.LibraryManagementSystem1.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @PostMapping("/add")
    public String addStudent(@RequestBody StudentRequestDto studentRequestDto){
        studentService.addStudent(studentRequestDto);
        return "Student has been added";
    }

    @GetMapping("/find_by_email")
    public String findStudentByEmail(@RequestParam("email") String email){

        return studentService.findByEmail(email);
    }

    // get students of particular age

    // try for some other attribute also

    @PutMapping("/update_email")
    public StudentResponseDto updateEmail(@RequestBody StudentUpdateEmailRequestDto studentUpdateEmailRequestDto){
        return studentService.updateEmail(studentUpdateEmailRequestDto);
    }
}
