package ir.maktab.onlineexam.controller;

import ir.maktab.onlineexam.model.dto.ResponseModel;
import ir.maktab.onlineexam.model.entity.Category;
import ir.maktab.onlineexam.service.CategoryService;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping(value = "${category.base.url}")
@PropertySource("classpath:message.properties")
@PreAuthorize("hasAuthority('ADMIN')")
public class CategoryController {
    private final CategoryService categoryService;
    private final Environment env;

    public CategoryController(CategoryService categoryService, Environment env) {
        this.categoryService = categoryService;
        this.env = env;
    }

    @GetMapping(value = "/add")
    public ModelAndView showAddForm() {
        ModelAndView addCategory = new ModelAndView("addCategory");
        addCategory.addObject("category", new Category())
                .addObject("message", "");
        return addCategory;
    }

    @PostMapping(value = "/add")
    public ResponseModel<String> addCategory(@ModelAttribute("category") Category category) {
        String responseMessage = categoryService.addCategory(category) != null ?
                env.getProperty("category.add.successful") : env.getProperty("category.duplicate");
         return new ResponseModel<>(responseMessage);
    }

    @Secured("1")
    @GetMapping(value = "/delete")
    public ModelAndView showDeleteForm(Model model) {
        List<String> nameList = categoryService.findAllCategoryNames();
        if (nameList.isEmpty())
            return new ModelAndView("message", "message", env.getProperty("No.Category.Found"));
        ModelAndView delete = new ModelAndView("deleteCategory");
        delete.addObject("nameList", nameList)
                .addObject("message", model.getAttribute("message"));
        return delete;
    }

    @PostMapping(value = "/delete")
    public ResponseModel<String> deleteCategory(@RequestParam(name = "name") String name) {
        categoryService.deleteCategory(name);
        return new ResponseModel<>(env.getProperty("category.delete.successful"));
        //TODO : DELETE ALL COURSES OF THIS CATEGORY
    }
}