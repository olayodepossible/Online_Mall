package frontend.controller;

import backend.dao.CategoryDAO;
import backend.dao.ProductDAO;
import backend.model.Category;
import backend.model.Product;
import frontend.util.FileUtil;
import frontend.validator.ProductValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/manage")
public class ManagementController {

    private static final Logger logger = LoggerFactory.getLogger(ManagementController.class);

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    @RequestMapping("/product")
    public ModelAndView manageProduct(@RequestParam(name="success",required=false)String success) {

        ModelAndView mv = new ModelAndView("page");
        mv.addObject("title","Product Management");
        mv.addObject("userClickManageProduct",true);

        Product nProduct = new Product();

        // assuming that the user is ADMIN
        // I will later fixed it based on user is SUPPLIER or ADMIN
        nProduct.setSupplierId(1);
        nProduct.setActive(true);

        mv.addObject("product", nProduct);


        if(success != null) {
            if(success.equals("product")){
                mv.addObject("message", "Product submitted successfully!");
            }
            else if (success.equals("category")) {
                mv.addObject("message", "Category submitted successfully!");
            }
        }

        return mv;

    }


    @RequestMapping("/{id}/product")
    public ModelAndView manageProductEdit(@PathVariable int id) {

        ModelAndView mv = new ModelAndView("page");
        mv.addObject("title","Product Management");
        mv.addObject("userClickManageProduct",true);

         Product nProduct = new Product();
        mv.addObject("product", productDAO.get(id));

        return mv;
    }


    @RequestMapping(value = "/product", method= RequestMethod.POST)
    public String managePostProduct(@Valid @ModelAttribute("product") Product modifiedProduct,
                                    BindingResult results, Model model, HttpServletRequest request) {

        // mandatory file upload check
        if(modifiedProduct.getId() == 0) {
            new ProductValidator().validate(modifiedProduct, results);
        }
        else {
            // edit check only when the file has been selected
            if(!modifiedProduct.getFile().getOriginalFilename().equals("")) {
                new ProductValidator().validate(modifiedProduct, results);
            }
        }

        if(results.hasErrors()) {
            model.addAttribute("message", "Validation fails for adding the product!");
            model.addAttribute("userClickManageProduct",true);
            return "page";
        }


        if(modifiedProduct.getId() == 0 ) {
            productDAO.add(modifiedProduct);
        }
        else {
            productDAO.update(modifiedProduct);
        }

        //upload the file
        if(!modifiedProduct.getFile().getOriginalFilename().equals("") ){
            FileUtil.uploadFile(request, modifiedProduct.getFile(), modifiedProduct.getCode());
        }

        return "redirect:/manage/product?success=product";
    }


    @RequestMapping(value = "/product/{id}/activation", method= RequestMethod.GET)
    @ResponseBody
    public String managePostProductActivation(@PathVariable int id) {
        Product product = productDAO.get(id);
        boolean isActive = product.isActive();
        product.setActive(!isActive);
        productDAO.update(product);
        return (isActive)? "Product Deactivated Successfully!": "Product Activated Successfully";
    }


    @RequestMapping(value = "/category", method= RequestMethod.POST)
    public String managePostCategory(@ModelAttribute("category") Category mCategory, HttpServletRequest request) {
        categoryDAO.add(mCategory);
        return "redirect:" + request.getHeader("Referer") + "?success=category";
    }



    @ModelAttribute("categories")
    public List<Category> modelCategories() {
        return categoryDAO.categories();
    }

    @ModelAttribute("category")
    public Category modelCategory() {
        return new Category();
    }


}
