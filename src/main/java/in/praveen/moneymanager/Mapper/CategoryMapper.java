package in.praveen.moneymanager.Mapper;


import in.praveen.moneymanager.dto.CategoryDTO;
import in.praveen.moneymanager.entity.CategoryEntity;
import in.praveen.moneymanager.entity.ProfileEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryEntity toEntity(CategoryDTO categoryDTO, ProfileEntity profile){
        return CategoryEntity
                .builder()
                .name(categoryDTO.getName())
                .icon(categoryDTO.getIcon())
                .profile(profile)
                .type(categoryDTO.getType())
                .build();
    }


    public CategoryDTO toDto(CategoryEntity entity){
        return CategoryDTO
                .builder()
                .id(entity.getId())
                .profileId(entity.getProfile()!=null?entity.getProfile().getId():null)
                .name(entity.getName())
                .icon(entity.getIcon())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .type(entity.getType())
                .build();
    }
}
