package br.com.shonenlog.controller;

import br.com.shonenlog.entity.Category;
import br.com.shonenlog.mapper.CategoryMapper;
import br.com.shonenlog.request.CategoryRequest;
import br.com.shonenlog.response.CategoryResponse;
import br.com.shonenlog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/shonenLog/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAll() {
        List<Category> categories = categoryService.findAll();
        return ResponseEntity.ok(categories.stream()
                //.map(category -> CategoryMapper.toCategoryResponse(category)) - sem lambda
                .map(CategoryMapper::toCategoryResponse)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById (@PathVariable Long id) {
        Optional<Category> optCategory = categoryService.findById(id);
        if(optCategory.isPresent()) {
            CategoryResponse categoryResponse = CategoryMapper.toCategoryResponse(optCategory.get());
            return ResponseEntity.ok(categoryResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id,@RequestBody CategoryRequest request) {
        Category updateCategory = categoryService.update(id , CategoryMapper.toCategory(request));

        return ResponseEntity.ok(CategoryMapper.toCategoryResponse(updateCategory));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> save(@RequestBody CategoryRequest request) {
        Category newCategory = CategoryMapper.toCategory(request);
        Category savedCategory = categoryService.save(newCategory);

        return ResponseEntity.status(HttpStatus.CREATED).body(CategoryMapper.toCategoryResponse(savedCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById (@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
