package in.praveen.moneymanager.service;


import in.praveen.moneymanager.Mapper.CategoryMapper;
import in.praveen.moneymanager.dto.CategoryDTO;
import in.praveen.moneymanager.entity.CategoryEntity;
import in.praveen.moneymanager.entity.ProfileEntity;
import in.praveen.moneymanager.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final ProfileService profileService;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryDTO saveCategory(CategoryDTO categoryDTO){
        ProfileEntity profile = profileService.getCurrentProfile();
        if(categoryRepository.existsByNameAndProfileId(categoryDTO.getName(), profile.getId())){
            throw new RuntimeException("Category with this name already exists");
        }

      return categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(categoryDTO,profile)))  ;

    }

    public List<CategoryDTO>getCategoriesForCurrentUser(){
        ProfileEntity profile = profileService.getCurrentProfile();
        List<CategoryEntity> categories = categoryRepository.findByProfileId(profile.getId());
        return categories.stream().map(categoryMapper::toDto).toList();
    }

    public List<CategoryDTO> getCategoriesByTypeForCurrentUser(String type){
        ProfileEntity profile = profileService.getCurrentProfile();
        List<CategoryEntity> entities = categoryRepository.findByTypeAndProfileId(type,profile.getId());
        return entities.stream().map(categoryMapper::toDto).toList();
    }
}
