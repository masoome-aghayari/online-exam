package ir.maktab.controller;

import ir.maktab.model.entity.Category;
import ir.maktab.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/admin/category")
@PropertySource("classpath:message.properties")
@PreAuthorize("hasAuthority('ADMIN')")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    Environment env;

    @GetMapping(value = "")
    public String showCategoryMenu() {
        return "categoryMenu";
    }

    @GetMapping(value = "/add")
    public ModelAndView showAddForm() {
        ModelAndView addCategory = new ModelAndView("addCategory");
        addCategory.addObject("category", new Category())
                .addObject("message", "");
        return addCategory;
    }

    @PostMapping(value = "/add")
    public String addCategoryProcess(@ModelAttribute("category") Category category, Model model) {
        model.addAttribute("message", categoryService.addCategory(category) != null ?
                env.getProperty("Category.Add.Successful") : env.getProperty("Category.Duplicate"));
        return "addCategory";
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
    public ModelAndView deleteCourseProcess(@RequestParam(required = false, name = "name") String name, Model model) {
        categoryService.deleteCategory(name);
        model.addAttribute("message", env.getProperty("Category.Delete.Successful"));
        return showDeleteForm(model);
        //TODO : DELETE ALL COURSES OF THIS CATEGORY
    }
}