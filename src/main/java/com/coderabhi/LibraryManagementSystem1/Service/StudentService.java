package com.coderabhi.LibraryManagementSystem1.Service;

import com.coderabhi.LibraryManagementSystem1.Dto.StudentRequestDto;
import com.coderabhi.LibraryManagementSystem1.Dto.StudentResponseDto;
import com.coderabhi.LibraryManagementSystem1.Dto.StudentUpdateEmailRequestDto;
import com.coderabhi.LibraryManagementSystem1.Entity.LibraryCard;
import com.coderabhi.LibraryManagementSystem1.Entity.Student;
import com.coderabhi.LibraryManagementSystem1.Enum.CardStatus;
import com.coderabhi.LibraryManagementSystem1.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    public void addStudent(StudentRequestDto studentRequestDto){

        // Create a student object
        Student student = new Student();
        student.setAge(studentRequestDto.getAge());
        student.setName(studentRequestDto.getName());
        student.setDepartment(studentRequestDto.getDepartment());
        student.setEmail(studentRequestDto.getEmail());

        // crreate a card object
        LibraryCard card = new LibraryCard();
        card.setStatus(CardStatus.ACTIVATED);
        card.setStudent(student);

        // set card in student
        student.setCard(card);

        // check

        studentRepository.save(student);  // will save both student and card
    }

    public String findByEmail(String email){

        Student student = studentRepository.findByEmail(email);
        return student.getName();
    }

    public StudentResponseDto updateEmail(StudentUpdateEmailRequestDto studentUpdateEmailRequestDto){

        Student student = studentRepository.findById(studentUpdateEmailRequestDto.getId()).get();
        student.setEmail(studentUpdateEmailRequestDto.getEmail());

        // update step
        Student updatedStudent = studentRepository.save(student);


        // convert updated student to response dto
        StudentResponseDto studentResponseDto = new StudentResponseDto();
        studentResponseDto.setId(updatedStudent.getId());
        studentResponseDto.setName(updatedStudent.getName());
        studentResponseDto.setEmail(updatedStudent.getEmail());

        return studentResponseDto;
    }
}
