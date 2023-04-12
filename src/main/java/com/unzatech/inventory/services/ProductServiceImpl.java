package com.unzatech.inventory.services;

import com.unzatech.inventory.dao.ICategoryDao;
import com.unzatech.inventory.dao.IProductDao;
import com.unzatech.inventory.model.Category;
import com.unzatech.inventory.model.Product;
import com.unzatech.inventory.response.CategoryResponseRest;
import com.unzatech.inventory.response.ProductResponseRest;
import com.unzatech.inventory.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private IProductDao productDao;
    @Autowired
    private ICategoryDao categoryDao;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductResponseRest> search() {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> list = new ArrayList<>();
        List<Product> listAux = new ArrayList<>();
        try {
            listAux = (List<Product>) productDao.findAll();

            if (listAux.size() > 0){
                listAux.stream().forEach((product) -> {
                    byte[] imageDescompressed = Util.decompressZLib(product.getPicture());
                    product.setPicture(imageDescompressed);
                    list.add(product);
                });
                response.getProductResponse().setProduct(list);
                response.setMetadata("Respuesta ok","00","Productos encontrados");
            }else{
                response.setMetadata("Respuesta no ok","-1","Productos no encontrados");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            response.setMetadata("Respuesta no ok","-1","Error al consultar producto por nombre");
            e.getStackTrace();
            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId) {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> list = new ArrayList<>();
        try {
            // search category to set in the product object
            Optional<Category> category = categoryDao.findById(categoryId);
            if (category.isPresent()) {
                product.setCategory(category.get());
            } else {
                response.setMetadata("Respuesta no ok","-1","Categoria no encontrada para asociar al producto");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
            }

            // save product
            Product productSaved = productDao.save(product);

            if (productSaved != null) {
                list.add(productSaved);
                response.getProductResponse().setProduct(list);
                response.setMetadata("Respuesta ok","00","Producto guardado");
            } else {
                response.setMetadata("Respuesta no ok","-1","Producto no guardado");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            response.setMetadata("Respuesta no ok","-1","Error al grabar producto");
            e.getStackTrace();
            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ProductResponseRest> update(Product product, Long id, Long categoryId) {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> list = new ArrayList<>();
        try {
            // search category to set in the product object
            Optional<Category> category = categoryDao.findById(categoryId);
            if (category.isPresent()) {
                product.setCategory(category.get());
            } else {
                response.setMetadata("Respuesta no ok","-1","Categoria no encontrada para asociar al producto");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
            }

            // buscar product a actualizar
            Optional<Product> productSearch = productDao.findById(id);

            if (productSearch.isPresent()) {
                productSearch.get().setQuantity(product.getQuantity());
                productSearch.get().setCategory(product.getCategory());
                productSearch.get().setName(product.getName());
                productSearch.get().setPicture(product.getPicture());
                productSearch.get().setPrice(product.getPrice());

                Product productUpdated = productDao.save(productSearch.get());
                if (productUpdated != null) {
                    list.add(productUpdated);
                    response.getProductResponse().setProduct(list);
                    response.setMetadata("Respuesta ok","00","Producto actualizado");
                } else {
                    response.setMetadata("Respuesta no ok","-1","Producto no actualizado");
                    return new ResponseEntity<ProductResponseRest>(response, HttpStatus.BAD_REQUEST);
                }
            } else {
                response.setMetadata("Respuesta no ok","-1","Producto no actualizado");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            response.setMetadata("Respuesta no ok","-1","Error al actualizar producto");
            e.getStackTrace();
            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductResponseRest> searchById(Long id) {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> list = new ArrayList<>();
        try {
            Optional<Product> product = productDao.findById(id);
            if (product.isPresent()){
                byte[] imageDescompressed = Util.decompressZLib(product.get().getPicture());
                product.get().setPicture(imageDescompressed);
                list.add(product.get());
                response.getProductResponse().setProduct(list);
                response.setMetadata("Respuesta ok","00","Producto encontrado");
            }else{
                response.setMetadata("Respuesta no ok","-1","Producto no encontrado");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            response.setMetadata("Respuesta no ok","-1","Error al consultar producto por id");
            e.getStackTrace();
            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductResponseRest> searchNyName(String name) {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> list = new ArrayList<>();
        List<Product> listAux = new ArrayList<>();
        try {
            listAux = productDao.findByNameContainingIgnoreCase(name);

            if (listAux.size() > 0){
                listAux.stream().forEach((product) -> {
                    byte[] imageDescompressed = Util.decompressZLib(product.getPicture());
                    product.setPicture(imageDescompressed);
                    list.add(product);
                });
                response.getProductResponse().setProduct(list);
                response.setMetadata("Respuesta ok","00","Productos encontrados");
            }else{
                response.setMetadata("Respuesta no ok","-1","Productos no encontrados");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            response.setMetadata("Respuesta no ok","-1","Error al consultar producto por nombre");
            e.getStackTrace();
            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
    }


    @Override
    @Transactional
    public ResponseEntity<ProductResponseRest> deleteById(Long id) {
        ProductResponseRest response = new ProductResponseRest();
        try {
            productDao.deleteById(id);
            response.setMetadata("Respuesta ok","00","Producto eliminado");
        }catch (Exception e){
            response.setMetadata("Respuesta no ok","-1","Error al eliminar producto");
            e.getStackTrace();
            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
    }
}
