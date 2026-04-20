package in.praveen.moneymanager.service;

import in.praveen.moneymanager.Mapper.ExpenseMapper;
import in.praveen.moneymanager.dto.ExpenseDTO;
import in.praveen.moneymanager.entity.CategoryEntity;
import in.praveen.moneymanager.entity.ExpenseEntity;
import in.praveen.moneymanager.entity.ProfileEntity;
import in.praveen.moneymanager.repository.CategoryRepository;
import in.praveen.moneymanager.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final ProfileService profileService;
    private final ExpenseMapper expenseMapper;

    public ExpenseDTO addExpense(ExpenseDTO expenseDTO) {
        ProfileEntity profile = profileService.getCurrentProfile();
        CategoryEntity category = categoryRepository.findById(expenseDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        ExpenseEntity newExpense = expenseMapper.toEntity(expenseDTO,profile,category);
        newExpense = expenseRepository.save(newExpense);
        return expenseMapper.toDto(newExpense);
    }

    public List<ExpenseDTO> getCurrentMonthExpensesForCurrentUser() {
        ProfileEntity profile = profileService.getCurrentProfile();
        LocalDate now = LocalDate.now();
        LocalDate startDate =now.withDayOfMonth(1);
        LocalDate endDate =now.withDayOfMonth(now.lengthOfMonth());

      List<ExpenseEntity> list =  expenseRepository.findByProfileIdAndDateBetween(profile.getId(),startDate,endDate);

      return list.stream().map(expenseMapper::toDto).toList();
    }

    public void deleteExpense(Long expenseId) {
        ProfileEntity profile = profileService.getCurrentProfile();
        ExpenseEntity entity = expenseRepository.findById(expenseId).orElseThrow(() -> new RuntimeException("Expense not found"));

        if(!entity.getProfile().getId().equals(profile.getId())) {
            throw new RuntimeException("Unauthorized to delete this expense");
        }
        expenseRepository.delete(entity);
    }

    public List<ExpenseDTO> getLatest5ExpensesForCurrentUser() {
        ProfileEntity profile = profileService.getCurrentProfile();
        List<ExpenseEntity> list =  expenseRepository.findTop5ByProfileIdOrderByDateDesc(profile.getId());
        return list.stream().map(expenseMapper::toDto).toList();
    }

    public BigDecimal getTotalExpensesForCurrentUser() {
        ProfileEntity profile = profileService.getCurrentProfile();
        BigDecimal totalExpenses = expenseRepository.findTotalExpenseByProfileId(profile.getId());
        return totalExpenses!=null?totalExpenses:BigDecimal.ZERO;
    }
}
