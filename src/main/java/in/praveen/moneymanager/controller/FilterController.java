package in.praveen.moneymanager.controller;

import in.praveen.moneymanager.dto.ExpenseDTO;
import in.praveen.moneymanager.dto.FilterDTO;
import in.praveen.moneymanager.dto.IncomeDTO;
import in.praveen.moneymanager.entity.IncomeEntity;
import in.praveen.moneymanager.service.ExpenseService;
import in.praveen.moneymanager.service.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/filter")
public class FilterController {
    private final IncomeService incomeService;
    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<?>filterTransactions(@RequestBody FilterDTO filter){
        LocalDate startDate = filter.getStartDate()!=null?filter.getStartDate():LocalDate.now().minusYears(10);
        LocalDate endDate = filter.getEndDate()!=null?filter.getEndDate():LocalDate.now();
        String keyword = filter.getKeyword()!=null?filter.getKeyword():"";
        String sortField = filter.getSortField()!=null?filter.getSortField():"date";
        Sort.Direction direction = "desc".equalsIgnoreCase(filter.getSortOrder())?Sort.Direction.DESC:Sort.Direction.ASC;
        Sort sort = Sort.by(direction,sortField);

        if("income".equals(filter.getType())){
         List<IncomeDTO> incomes =   incomeService.filterIncomes(startDate,endDate,keyword,sort);
         return ResponseEntity.ok(incomes);
        }else if("expense".equals(filter.getType())){
            List<ExpenseDTO> expenses =  expenseService.filterExpenses(startDate,endDate,keyword,sort);
            return ResponseEntity.ok(expenses);
        }else {
            return ResponseEntity.badRequest().body("Invalid type. Must be 'income' or 'expense'");
        }
    }
}