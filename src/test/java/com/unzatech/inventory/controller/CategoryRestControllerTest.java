package com.unzatech.inventory.controller;

import com.unzatech.inventory.model.Category;
import com.unzatech.inventory.response.CategoryResponseRest;
import com.unzatech.inventory.services.ICategoryService;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CategoryRestControllerTest {
    @InjectMocks
    CategoryRestController categoryRestController;
    @Mock
    private ICategoryService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void searchCategories() {
    }

    @Test
    void searchCategoriesById() {
    }

    @Test
    void save() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Category category = new Category();
        category.setId(Long.valueOf(1));
        category.setName("Escolar");
        category.setDescription("Uniformes escolares");

        when(service.save(any(Category.class))).thenReturn(
                new ResponseEntity<CategoryResponseRest>(HttpStatus.OK)
        );

        ResponseEntity<CategoryResponseRest> response = categoryRestController.save(category);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void exportToExcel() {
    }
}