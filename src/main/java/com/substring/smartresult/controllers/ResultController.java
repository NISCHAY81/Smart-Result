package com.substring.smartresult.controllers;

import com.substring.smartresult.entity.Marks;
import com.substring.smartresult.entity.Student;
import com.substring.smartresult.payload.StudentForm;
import com.substring.smartresult.services.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;

@Controller
@RequestMapping("/results")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @RequestMapping("/input")
    public String viewResult() {
        System.out.println("Result page");
        return "view_result";
    }

    @RequestMapping("/view")
    public String getResult() {
        return "redirect:/results/input";
    }

    @RequestMapping("/add")
    public String addResult(Model model) {
        StudentForm studentForm = new StudentForm();

        model.addAttribute("studentForm", studentForm);

        return "add_result";
    }


    @RequestMapping(value = "/process-form", method = RequestMethod.POST)
    public String saveResult(@ModelAttribute StudentForm studentForm, Model model) {

        System.out.println(studentForm.getName());
        studentForm.getMarks().forEach(marks->{
            System.out.println("Subject"+marks.getSubject());
            System.out.println("Marks:" + marks.getMarks());
            System.out.println("MaxMarks:" + marks.getMaxMarks());
            System.out.println("Remarks:"+marks.getRemark());
            System.out.println("Grade:"+marks.getGrade());
            System.out.println("------------------------------------");
        });

        String studentId =resultService.save(studentForm);
        model.addAttribute("studentId", studentId);

        return "result_sucess";
    }

//    @RequestMapping(value = "/view-result",method = RequestMethod.POST)
@PostMapping("/view-result")
public String viewResultByRollNumber(@RequestParam("rollNumber") String rollNumber, Model model) {
    System.out.println("Roll number: " + rollNumber);

    Student student = resultService.getResultByRollNumber(rollNumber);

    if (student == null) {
        return "result_not_found";
    }

    // Now student is guaranteed NOT to be null
//    System.out.println(student.getName());
//    System.out.println(student.getMarksList().size());
  else {
        double totalmarks = 0;
        double totalMaxMarks = 0;
        for (Marks mark : student.getMarksList()) {
            totalmarks += mark.getMarks();
            totalMaxMarks += mark.getMaxMarks();

        }
        double percentage = (totalmarks / totalMaxMarks) * 100;
        model.addAttribute("percentage", String.format("%.2f", percentage));
        model.addAttribute("totalMarks", ((int) totalmarks));
        model.addAttribute("totalMaxMarks", (int) totalMaxMarks);

        String totalgarde = "F";
        if (percentage > 90) {
            totalgarde = "A+";
        } else if (percentage > 80 && percentage <= 90) {

            totalgarde = "A";
        } else if (percentage > 70 && percentage <= 80) {
            totalgarde = "B+";
        } else if (percentage > 60 && percentage <= 70) {
            totalgarde = "B";

        } else {
            totalgarde = "F";
        }
        model.addAttribute("totalGrade", totalgarde);
        model.addAttribute("student", student);
        model.addAttribute("marks", student.getMarksList());

        return "result_data";
    }
}

 }

