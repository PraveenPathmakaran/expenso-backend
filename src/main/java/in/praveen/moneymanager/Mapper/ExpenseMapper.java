package in.praveen.moneymanager.Mapper;

import in.praveen.moneymanager.dto.ExpenseDTO;
import in.praveen.moneymanager.entity.CategoryEntity;
import in.praveen.moneymanager.entity.ExpenseEntity;
import in.praveen.moneymanager.entity.ProfileEntity;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {

    public ExpenseEntity toEntity(ExpenseDTO dto, ProfileEntity profileEntity, CategoryEntity categoryEntity) {

        return ExpenseEntity.builder()
                .name(dto.getName())
                .icon(dto.getIcon())
                .amount(dto.getAmount())
                .date(dto.getDate())
                .profile(profileEntity)
                .category(categoryEntity)
                .build();
    }
    public ExpenseDTO toDto(ExpenseEntity entity) {
        return ExpenseDTO.builder().
                id(entity.getId())
                .name(entity.getName())
                .icon(entity.getIcon())
                .categoryId(entity.getCategory()!=null?entity.getCategory().getId():null)
                .categoryName(entity.getCategory()!=null?entity.getCategory().getName():"N/A")
                .amount(entity.getAmount())
                .date(entity.getDate())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();

    }
}
