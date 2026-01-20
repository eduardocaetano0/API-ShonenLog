package br.com.shonenlog.controller;

import br.com.shonenlog.entity.Category;
import br.com.shonenlog.mapper.CategoryMapper;
import br.com.shonenlog.request.CategoryRequest;
import br.com.shonenlog.response.CategoryResponse;
import br.com.shonenlog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/shonenLog/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponse> findAll() {
        List<Category> categories = categoryService.findAll();
        return categories.stream()
                //.map(category -> CategoryMapper.toCategoryResponse(category)) - sem lambda
                .map(CategoryMapper::toCategoryResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public CategoryResponse findById (@PathVariable Long id) {
        Optional<Category> optCategory = categoryService.findById(id);
        if(optCategory.isPresent()) {
            return CategoryMapper.toCategoryResponse(optCategory.get());
        }
        return null;
    }

    @PutMapping("/{id}")
    public CategoryResponse updateCategory(@PathVariable Long id,@RequestBody CategoryRequest request) {
        Category updateCategory = categoryService.update(id , CategoryMapper.toCategory(request));

        return CategoryMapper.toCategoryResponse(updateCategory);
    }

    @PostMapping
    public CategoryResponse save(@RequestBody CategoryRequest request) {
        Category newCategory = CategoryMapper.toCategory(request);
        Category savedCategory = categoryService.save(newCategory);

        return CategoryMapper.toCategoryResponse(savedCategory);
    }

    @DeleteMapping("/{id}")
    public void deleteById (@PathVariable Long id) {
        categoryService.deleteById(id);
    }

}
