package com.unzatech.inventory.response;

import com.unzatech.inventory.model.Category;
import lombok.Data;

import java.util.List;
@Data
public class CategoryResponse {
    private List<Category> category;
}
