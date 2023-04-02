package com.unzatech.inventory.controller;

import com.unzatech.inventory.model.Category;
import com.unzatech.inventory.response.CategoryResponseRest;
import com.unzatech.inventory.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1")
public class CategoryRestController {
    @Autowired
    private ICategoryService service;

    /**
     * get all categories
     * @return
     */
    @GetMapping("/categories")
    public ResponseEntity<CategoryResponseRest> searchCategories() {
        ResponseEntity<CategoryResponseRest> response = service.search();
        return response;
    }

    /**
     * get categories by id
     * @param id
     * @return
     */
    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryResponseRest> searchCategoriesById(@PathVariable Long id) {
        ResponseEntity<CategoryResponseRest> response = service.searchById(id);
        return response;
    }

    /**
     * save categories
     * @param category
     * @return
     */
    @PostMapping("/categories")
    public ResponseEntity<CategoryResponseRest> save(@RequestBody Category category) {
        ResponseEntity<CategoryResponseRest> response = service.save(category);
        return response;
    }

    /**
     * update categories
     * @param category
     * @param id
     * @return
     */
    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryResponseRest> update(@RequestBody Category category, @PathVariable Long id) {
        ResponseEntity<CategoryResponseRest> response = service.update(category, id);
        return response;
    }

    /**
     * delete categories
     * @param id
     * @return
     */
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<CategoryResponseRest> delete(@PathVariable Long id) {
        ResponseEntity<CategoryResponseRest> response = service.deleteById(id);
        return response;
    }
}
