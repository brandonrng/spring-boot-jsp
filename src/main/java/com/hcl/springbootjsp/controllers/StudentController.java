package com.hcl.springbootjsp.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.http.HttpSession;
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

import com.hcl.springbootjsp.model.NewUser;
import com.hcl.springbootjsp.model.Student;

@Controller
@RequestMapping("/student")
public class StudentController {
// private List<BookData> initData = new ArrayList<>();

	/*
	 * private final BookService bookService;
	 *
	 * public BookController(BookService bookService) { this.bookService =
	 * bookService; }
	 */

	@PersistenceContext
	private EntityManager entityManager;

	public StudentController() {
// old way
// initData.add( new StudentData("Brandon", "Quinones", "99999999"));
// initData.add(new StudentData("Nathan", "Smith", "888888888"));
// initData.add(new StudentData("Ricky", "Nguyen", "777777777"));
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////// REGISTER A USER //////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////

//Render the form for the user
	@GetMapping("/register")
	public String registerNewUserView(Model model) {
		model.addAttribute("newUser", new NewUser());
		return "register-new-user";
	}

//called when you post from a tag
	@Transactional
	@PostMapping("/register")
	public RedirectView registerNewUser(@ModelAttribute("user") NewUser newUser,
			RedirectAttributes redirectAttributes) {
		final RedirectView redirectView = new RedirectView("/student/login", true);
		final RedirectView redirectView1 = new RedirectView("/student/register", true);

		Query readAll = entityManager.createQuery("select u from NewUser u where u.username=?0 and u.password=?1");
		readAll.setParameter(0, newUser.getUsername());
		readAll.setParameter(1, newUser.getPassword());
		List<NewUser> resultListAll = readAll.getResultList();

		// if it exists, add the user name to the session
		if (resultListAll.size() == 0) {
			entityManager.persist(newUser);
			redirectAttributes.addFlashAttribute("savedNewUser", newUser);
			redirectAttributes.addFlashAttribute("registerNewUserSuccess", true);
			return redirectView;
		} else {
//redirectAttributes.addFlashAttribute("registerNewUserSuccess", false);
			return redirectView1;
		}
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////// END REGISTER /////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////// LOGIN ///////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("newUser", new NewUser());
		return "login-new-user";
	}
	
	@GetMapping("/logoff")
	public String login(HttpSession session) {
		session.invalidate();
		return "logout-new-user";
	}

//called when you post from a tag
	@Transactional
	@PostMapping("/login")
	public RedirectView loginSubmit(@ModelAttribute("user") NewUser user, HttpSession session,
			RedirectAttributes redirectAttributes) {
		final RedirectView redirectView = new RedirectView("/student/viewStudents", true);
		final RedirectView redirectView1 = new RedirectView("/student/login", true);
		System.out.println("line 106: user == " + user);
//check if the user name and password exist in the database,
		Query readAll = entityManager.createQuery("select u from NewUser u where u.username=?0 and u.password=?1");
		readAll.setParameter(0, user.getUsername());
		readAll.setParameter(1, user.getPassword());
		List<NewUser> resultListAll = readAll.getResultList();
		System.out.println("printing reserved list... " + resultListAll.size());
		// if it exists, add the user name to the session
		if (resultListAll.size() == 1) {
			redirectAttributes.addFlashAttribute("savedNewUser", user);
			redirectAttributes.addFlashAttribute("registerNewUserSuccess", true);
			session.setAttribute("loggedInUser", resultListAll.get(0));
			return redirectView;
		} else {
//redirect to error page
			redirectAttributes.addFlashAttribute("savedNewUser", user);
			redirectAttributes.addFlashAttribute("loginFailed", true);
			return redirectView1;
		}

	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////END LOGIN ///////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////// ADD STUDENT /////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////
// To render the form for the book
	@GetMapping("/addStudent")
	public String addStudentView(Model model) {
		model.addAttribute("student", new Student());
		return "add-student";
	}

	// This one was called when you POST from a form tag
	@PostMapping("/addStudent")
	@Transactional
	public RedirectView addStudent(@ModelAttribute("student") Student student, RedirectAttributes redirectAttributes) {
		final RedirectView redirectView = new RedirectView("/student/addStudent", true);

		entityManager.persist(student);

		redirectAttributes.addFlashAttribute("savedStudent", student);
		redirectAttributes.addFlashAttribute("addStudentSuccess", true);
		return redirectView;
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////// END ADD STUDENT /////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////// UPDATE STUDENT /////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////

	@GetMapping("/updateStudent/{id}")
	public String updateStudent(Model m, @PathVariable int id) {
		System.out.println("inside update student b4 anything happens");
		Student updateS = entityManager.find(Student.class, id);
		m.addAttribute("student", updateS);
		System.out.println(updateS.getId() + " == " + id);
		return "update-student";
	}

	@PostMapping("/updateStudent/{id}")
	@Transactional
	@ResponseBody
	public RedirectView updateStudent(@ModelAttribute("student") Student student,
			RedirectAttributes redirectAttributes) {
		final RedirectView redirectView = new RedirectView("/student/viewStudents", true);
		entityManager.merge(student);
		return redirectView;
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////// END UPDATE STUDENT ///////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////// VIEW STUDENT /////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////

	@GetMapping("/viewStudents")
	public String viewStudent(Model model, HttpSession session) {
		NewUser user = (NewUser) session.getAttribute("loggedInUser");
		final RedirectView redirectView = new RedirectView("/student/viewStudents", true);
		final RedirectView redirectView1 = new RedirectView("/student/login", true);
		System.out.println("user****=" + user);
//already logged in
		if (user != null) {
			Query readAll = entityManager.createQuery("select s from Student s");
			List<Student> resultListAll = readAll.getResultList();
			resultListAll.forEach(System.out::println);
			model.addAttribute("student", resultListAll);
			return "view-student";
		} else {
			return "redirect:/student/login";
		}
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////// END VIEW STUDENT /////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////// DELETE STUDENT /////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////

	@GetMapping("/deleteStudent/{id}")
	@Transactional
	@ResponseBody
	public RedirectView deleteStudent(@PathVariable int id) {
		final RedirectView redirectView = new RedirectView("/student/viewStudents", true);
		Query delete = entityManager.createQuery("delete from Student s where s.id=?0");
		delete.setParameter(0, id);
		delete.executeUpdate();
		return redirectView;
/////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////// END DELETE STUDENT ////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////

	}
}