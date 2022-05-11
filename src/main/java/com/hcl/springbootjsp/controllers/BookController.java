package com.hcl.springbootjsp.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.hcl.springbootjsp.model.Book;

@Controller
@RequestMapping("/book")
public class BookController {
//	private List<BookData> initData = new ArrayList<>();

    /*private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }*/
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public BookController() {
		//old way
//	    initData.add( new BookData("ISBN-1", "Book 1", "Book 1 Author"));
//	    initData.add(new BookData("ISBN-2", "Book 2", "Book 2 Author"));
//	    initData.add(new BookData("ISBN-3", "Book 3", "Book 3 Author"));
	}

	//To render the form for the book
    @GetMapping("/addBook")
    public String addBookView(Model model) {
        model.addAttribute("book", new Book());
        return "add-book";
    }

    //This one was called when you POST from a form tag
    @PostMapping("/addBook")
    @Transactional
    public RedirectView addBook(@ModelAttribute("book") Book book, RedirectAttributes redirectAttributes) {
        final RedirectView redirectView = new RedirectView("/book/addBook", true);
        /*Book savedBook = bookService.addBook(book);*/
//        initData.add(book);
        
		entityManager.persist(book);
        redirectAttributes.addFlashAttribute("savedBook", book);
        redirectAttributes.addFlashAttribute("addBookSuccess", true);
        return redirectView;
    }
	
	@GetMapping("/viewBooks")
    public String viewBooks(Model model) {
		Query readAll = entityManager.createQuery("select b from Book b");
		List<Book> resultListAll = readAll.getResultList();
		resultListAll.forEach(System.out::println);
        model.addAttribute("books", resultListAll);
        return "view-books";
    }
	
	@DeleteMapping("/deleteBook")
	@Transactional
	public void deleteBook(@PathVariable int id) {
		Query delete = entityManager.createQuery("delete from Book b where b.id=?0");
		delete.setParameter(0, id);
		delete.executeUpdate();
	}
}