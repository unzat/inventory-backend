package com.unzatech.inventory.services;

import com.unzatech.inventory.dao.ICategoryDao;
import com.unzatech.inventory.model.Category;
import com.unzatech.inventory.response.CategoryResponse;
import com.unzatech.inventory.response.CategoryResponseRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService{
    @Autowired
    private ICategoryDao categoryDao;
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryResponseRest> search() {
        CategoryResponseRest response = new CategoryResponseRest();
        try {
            List<Category> category = (List<Category>) categoryDao.findAll();
            response.getCategoryResponse().setCategory(category);
            response.setMetadata("Respuesta ok","00","Respuesta exitosa");
        }catch (Exception e){
            response.setMetadata("Respuesta no ok","-1","Error al consultar");
            e.getStackTrace();
            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryResponseRest> searchById(Long id) {
        CategoryResponseRest response = new CategoryResponseRest();
        List<Category> list = new ArrayList<>();
        try {
            Optional<Category> category = categoryDao.findById(id);
            if (category.isPresent()){
                list.add(category.get());
                response.getCategoryResponse().setCategory(list);
                response.setMetadata("Respuesta ok","00","Categoria no encontrada");
            }else{
                response.setMetadata("Respuesta no ok","-1","Categoria no encontrada");
                return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            response.setMetadata("Respuesta no ok","-1","Error al consultar por id");
            e.getStackTrace();
            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CategoryResponseRest> save(Category category) {
        CategoryResponseRest response = new CategoryResponseRest();
        List<Category> list = new ArrayList<>();
        try {
            Category categorySaved = categoryDao.save(category);
            if (categorySaved != null) {
                list.add(categorySaved);
                response.getCategoryResponse().setCategory(list);
                response.setMetadata("Respuesta ok","00","Categoria guardada");
            } else {
                response.setMetadata("Respuesta no ok","-1","Categoria no guardada");
                return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            response.setMetadata("Respuesta no ok","-1","Error al grabar categoria");
            e.getStackTrace();
            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CategoryResponseRest> update(Category category, Long id) {
        CategoryResponseRest response = new CategoryResponseRest();
        List<Category> list = new ArrayList<>();
        try {
            Optional<Category> categorySearch = categoryDao.findById(id); // se pregunta si existe el registro a actualizar
            if (categorySearch.isPresent()) {
                // se actualiza
                categorySearch.get().setName(category.getName());
                categorySearch.get().setDescription(category.getDescription());
                Category categoryToUpdate = categoryDao.save(categorySearch.get());
                if (categorySearch != null) {
                    response.getCategoryResponse().setCategory(list);
                    response.setMetadata("Respuesta ok","00","Categoria actualizada");
                } else {
                    response.setMetadata("Respuesta no ok","-1","Categoria no actualizada");
                    return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.BAD_REQUEST);
                }
            } else {
                response.setMetadata("Respuesta no ok","-1","Categoria no encontrada para actualizar");
                return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            response.setMetadata("Respuesta no ok","-1","Error al actualizar categoria");
            e.getStackTrace();
            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CategoryResponseRest> deleteById(Long id) {
        CategoryResponseRest response = new CategoryResponseRest();
        try {
            categoryDao.deleteById(id);
            response.setMetadata("Respuesta ok","00","Categoria eliminada");
        }catch (Exception e){
            response.setMetadata("Respuesta no ok","-1","Error al eliminar categoria");
            e.getStackTrace();
            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }
}
