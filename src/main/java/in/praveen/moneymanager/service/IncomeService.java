package in.praveen.moneymanager.service;

import in.praveen.moneymanager.Mapper.IncomesMapper;
import in.praveen.moneymanager.dto.ExpenseDTO;
import in.praveen.moneymanager.dto.IncomeDTO;
import in.praveen.moneymanager.entity.CategoryEntity;
import in.praveen.moneymanager.entity.ExpenseEntity;
import in.praveen.moneymanager.entity.IncomeEntity;
import in.praveen.moneymanager.entity.ProfileEntity;
import in.praveen.moneymanager.repository.CategoryRepository;
import in.praveen.moneymanager.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final CategoryRepository categoryRepository;
    private final ProfileService profileService;
    private final IncomesMapper incomesMapper;


    public IncomeDTO addIncome(IncomeDTO incomeDTO) {
        ProfileEntity profile = profileService.getCurrentProfile();
        CategoryEntity category = categoryRepository.findById(incomeDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        IncomeEntity newIncome = incomesMapper.toEntity(incomeDTO,profile,category);
        newIncome = incomeRepository.save(newIncome);
        return incomesMapper.toDto(newIncome);
    }

    public List<IncomeDTO> getCurrentMonthIncomesForCurrentUser() {
        ProfileEntity profile = profileService.getCurrentProfile();
        LocalDate now = LocalDate.now();
        LocalDate startDate =now.withDayOfMonth(1);
        LocalDate endDate =now.withDayOfMonth(now.lengthOfMonth());

        List<IncomeEntity> list =  incomeRepository.findByProfileIdAndDateBetween(profile.getId(),startDate,endDate);

        return list.stream().map(incomesMapper::toDto).toList();
    }

    public void deleteIncome(Long incomeId) {
        ProfileEntity profile = profileService.getCurrentProfile();
        IncomeEntity entity = incomeRepository.findById(incomeId).orElseThrow(() -> new RuntimeException("Income not found"));

        if(!entity.getProfile().getId().equals(profile.getId())) {
            throw new RuntimeException("Unauthorized to delete this income");
        }
        incomeRepository.delete(entity);
    }

    public List<IncomeDTO> getLatest5IncomesForCurrentUser() {
        ProfileEntity profile = profileService.getCurrentProfile();
        List<IncomeEntity> list =  incomeRepository.findTop5ByProfileIdOrderByDateDesc(profile.getId());
        return list.stream().map(incomesMapper::toDto).toList();
    }

    public BigDecimal getTotalIncomesForCurrentUser() {
        ProfileEntity profile = profileService.getCurrentProfile();
        BigDecimal totalExpenses = incomeRepository.findTotalExpenseByProfileId(profile.getId());
        return totalExpenses!=null?totalExpenses:BigDecimal.ZERO;
    }

    public List<IncomeDTO> filterIncomes(LocalDate startDate, LocalDate endDate, String keyWord, Sort sort) {
        ProfileEntity profile = profileService.getCurrentProfile();
        List<IncomeEntity> expenses =    incomeRepository.findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(profile.getId(),startDate,endDate,keyWord,sort);
        return expenses.stream().map(incomesMapper::toDto).toList();
    }
}
