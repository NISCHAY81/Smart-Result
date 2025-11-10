package com.substring.smartresult.Repository;

import com.substring.smartresult.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepp extends JpaRepository<Student,String>
{
    Student findByRollNumber(String rollNumber);
}
