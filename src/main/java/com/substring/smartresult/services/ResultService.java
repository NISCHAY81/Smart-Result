package com.substring.smartresult.services;


import com.substring.smartresult.Repository.StudentRepp;
import com.substring.smartresult.entity.Marks;
import com.substring.smartresult.entity.Student;
import com.substring.smartresult.payload.StudentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ResultService {


    @Autowired
    private StudentRepp studentRepp;

    public String save(StudentForm studentForm)
    {

        Student student = new Student();
        student.setId(UUID.randomUUID().toString());
        student.setName(studentForm.getName());
        student.setRollNumber(studentForm.getRollNumber());
        student.setEmail(studentForm.getEmail());
        student.setSchoolName(studentForm.getSchoolName());
        student.setDateOfBirth(studentForm.getDob());
        student.setGender(studentForm.getGender());

        List<Marks> marksList = studentForm.getMarks().stream().map(markForm -> {
            Marks mark = new Marks();
            mark.setSubject(markForm.getSubject());
            mark.setMarks(markForm.getMarks());
            mark.setMaxMarks(markForm.getMaxMarks());
            mark.setRemarks(markForm.getRemark());
            mark.setGrade(markForm.getGrade());
            mark.setStudent(student);
            return mark;
        }).toList();
        student.setMarksList(marksList);

        Student savedStudent = studentRepp.save(student);
        System.out.println("student saved with ID: " + savedStudent.getId());
        return savedStudent.getId();

    }

    public Student getResultByRollNumber(String rollnumber){

        return studentRepp.findByRollNumber(rollnumber);
    }

}
