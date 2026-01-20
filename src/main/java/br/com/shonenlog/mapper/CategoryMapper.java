package br.com.shonenlog.mapper;


import br.com.shonenlog.entity.Category;
import br.com.shonenlog.request.CategoryRequest;
import br.com.shonenlog.response.CategoryResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoryMapper {

    // É apenas oq o cliente envia - logo n envia ID
    public static Category toCategory(CategoryRequest categoryRequest) {
        return Category
                .builder()
                .name(categoryRequest.name())
                .build();

    }

    //É oq vc devolve para o cliente, junto do ID
    public static CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse
                .builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
