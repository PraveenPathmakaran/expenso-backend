package in.praveen.moneymanager.Mapper;

import in.praveen.moneymanager.dto.IncomeDTO;
import in.praveen.moneymanager.entity.CategoryEntity;
import in.praveen.moneymanager.entity.IncomeEntity;
import in.praveen.moneymanager.entity.ProfileEntity;
import org.springframework.stereotype.Component;

@Component
public class IncomesMapper {

    public IncomeEntity toEntity(IncomeDTO dto, ProfileEntity profileEntity, CategoryEntity categoryEntity) {

        return IncomeEntity.builder()
                .name(dto.getName())
                .icon(dto.getIcon())
                .amount(dto.getAmount())
                .date(dto.getDate())
                .profile(profileEntity)
                .category(categoryEntity)
                .build();
    }
    public IncomeDTO toDto(IncomeEntity entity) {
        return IncomeDTO.builder().
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
