package com.hcl.springbootjsp.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.hcl.springbootjsp.model.Student;

@Controller
@RequestMapping("/student")
public class StudentController {
//	private List<BookData> initData = new ArrayList<>();

    /*private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }*/
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public StudentController() {
		//old way
//	    initData.add( new StudentData("Brandon", "Quinones", "99999999"));
//	    initData.add(new StudentData("Nathan", "Smith", "888888888"));
//	    initData.add(new StudentData("Ricky", "Nguyen", "777777777"));
	}

	//To render the form for the book
    @GetMapping("/addStudent")
    public String addStudentView(Model model) {
        model.addAttribute("student", new Student());
        return "add-student";
    }

    //This one was called when you POST from a form tag
    @PostMapping("/addStudent")
    @Transactional
    public RedirectView addStudent(@ModelAttribute("student") Student student, RedirectAttributes redirectAttributes) {
        final RedirectView redirectView = new RedirectView("/student/addStudent", true);

		entityManager.persist(student);
		
        redirectAttributes.addFlashAttribute("savedStudent", student);
        redirectAttributes.addFlashAttribute("addStudentSuccess", true);
        return redirectView;
    }
    
    
//    @GetMapping("/updateStudent")
//    public String updateStudentView(Model model) {
//        model.addAttribute("student", new Student());
//        return "update-student";
//    }
    
    @GetMapping("/updateStudent/{id}/{firstName}")
	@Transactional
	@ResponseBody
	@Modifying
	public void updateStudent(@PathVariable int id, @PathVariable String firstName) {
    	//using jql
//		Query update = entityManager.createQuery("update from Student s set firstName =?1 where s.id=?0"); 
//		update.setParameter(0, id);
//		update.setParameter(1, firstName);
//		update.executeUpdate();
    	//------------------------
    	System.out.println(id + " " + firstName);
    	//using entityManager
		Student updateS = entityManager.find(Student.class, id);
		updateS.setFirstName(firstName);
    	
	}

//    //This one was called when you POST from a form tag
//    @PostMapping("/updateStudent")
//    @Transactional
//    public RedirectView updateStudent(@ModelAttribute("student") Student student, RedirectAttributes redirectAttributes) {
//        final RedirectView redirectView = new RedirectView("/student/updateStudent/", true);
//
//		entityManager.persist(student);
//		
//        redirectAttributes.addFlashAttribute("savedStudent", student);
//        redirectAttributes.addFlashAttribute("updateStudentSuccess", true);
//        return redirectView;
//    }
    
	
	@GetMapping("/viewStudents")
    public String viewStudent(Model model) {
		Query readAll = entityManager.createQuery("select s from Student s");
		List<Student> resultListAll = readAll.getResultList();
		resultListAll.forEach(System.out::println);
        model.addAttribute("student", resultListAll);
        return "view-student";
    }
	
	@GetMapping("/deleteStudent/{id}")
	@Transactional
	@ResponseBody
	public void deleteStudent(@PathVariable int id) {
		Query delete = entityManager.createQuery("delete from Student s where s.id=?0");
		delete.setParameter(0, id);
		delete.executeUpdate();
	}
}