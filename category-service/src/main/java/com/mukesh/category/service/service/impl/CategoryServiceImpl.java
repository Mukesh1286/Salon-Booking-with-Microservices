package com.mukesh.category.service.service.impl;

import com.mukesh.category.service.model.Category;
import com.mukesh.category.service.payload.dto.SalonDTO;
import com.mukesh.category.service.repository.CategoryRepository;
import com.mukesh.category.service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category, SalonDTO salonDTO) {

        Category newcategory = new Category();

        newcategory.setName(category.getName());
        newcategory.setSalonId(salonDTO.getId());
        newcategory.setSalonId(salonDTO.getId());
        newcategory.setImage(category.getImage());


        return categoryRepository.save(newcategory);
    }

    @Override
    public Set<Category> getAllCategoriesBySalon(Long id) {
         return categoryRepository.findBySalonId(id);
    }

    @Override
    public Category getCategoryById(Long id) throws Exception {
       Category category = categoryRepository.findById(id).orElse(null);

       if (category==null){
           throw new Exception("category not exist with id" + id);
       }
       return category;
    }

    @Override
    public void deleteCategoryById(Long id, Long salonId) throws Exception {

        Category category = getCategoryById(id);

        if (!category.getSalonId().equals(salonId)){
            throw new Exception("you don't have Permission to delete category");
        }
        categoryRepository.deleteById(id);
    }
}
