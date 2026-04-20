//package in.praveen.moneymanager.service;
//
//import in.praveen.moneymanager.dto.ExpenseDTO;
//import in.praveen.moneymanager.dto.IncomeDTO;
//import in.praveen.moneymanager.entity.ProfileEntity;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.*;
//
//@Service
//@RequiredArgsConstructor
//public class DashboardService {
//    private final IncomeService incomeService;
//    private final ExpenseService expenseService;
//    private final ProfileService profileService;
//
//    public Map<String, Objects> getDashboardData() {
//        ProfileEntity profile = profileService.getCurrentProfile();
//        Map<String, Objects> returnValue = new LinkedHashMap<>();
//        List<IncomeDTO> latestIncomes = incomeService.getLatest5IncomesForCurrentUser();
//        List<ExpenseDTO> latestExpenses = expenseService.getLatest5ExpensesForCurrentUser();
////        concat()
//    }
//}
