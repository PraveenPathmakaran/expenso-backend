//package in.praveen.moneymanager.service;
//
//import in.praveen.moneymanager.dto.ExpenseDTO;
//import in.praveen.moneymanager.dto.IncomeDTO;
//import in.praveen.moneymanager.dto.RecentTransactionDTO;
//import in.praveen.moneymanager.entity.ProfileEntity;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.*;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
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
//     List<RecentTransactionDTO> recentTransactions =   Stream.concat(latestIncomes.stream().map(incomeDTO -> RecentTransactionDTO.builder()
//                .id(incomeDTO.getId())
//                .profileId(profile.getId())
//                .icon(incomeDTO.getIcon())
//                .name(incomeDTO.getName())
//                .amount(incomeDTO.getAmount())
//                .date(incomeDTO.getDate())
//                .createdAt(incomeDTO.getCreatedAt())
//                .updatedAt(incomeDTO.getUpdatedAt())
//                .type("income")
//                .build()
//
//
//        ),latestExpenses.stream().map(expenseDTO ->
//                RecentTransactionDTO.builder()
//                        .id(expenseDTO.getId())
//                        .profileId(profile.getId())
//                        .icon(expenseDTO.getIcon())
//                        .name(expenseDTO.getName())
//                        .amount(expenseDTO.getAmount())
//                        .date(expenseDTO.getDate())
//                        .createdAt(expenseDTO.getCreatedAt())
//                        .updatedAt(expenseDTO.getUpdatedAt())
//                        .type("income")
//                        .build()))
//             .sorted((a,b)->{
//                 int cmp = b.getDate().compareTo(a.getDate());
//
//                 if(cmp==0&&a.getCreatedAt()!=null&&b.getCreatedAt()!=null){
//                     return b.getCreatedAt().compareTo(a.getCreatedAt());
//                 }
//                 return cmp;
//             }).toList();
//    }
//}
