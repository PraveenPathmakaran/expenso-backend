//package in.praveen.moneymanager.service;
//
//import in.praveen.moneymanager.dto.ExpenseDTO;
//import in.praveen.moneymanager.entity.ProfileEntity;
//import in.praveen.moneymanager.repository.ProfileRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class NotificationService {
//
//    private final ProfileRepository profileRepository;
//    private final EmailService emailService;
//    private final ExpenseService expenseService;
//    @Value("${money.manager.frontend.url}")
//    private String frontEndUrl;
//
//    @Scheduled(cron = "0 0 22 * * *",zone = "IST")
//    public void sendDailyIncomeExpenseReminder(){
//        log.info("Job started : send DailyIncomeExpenseReminder");
//        List<ProfileEntity> profiles = profileRepository.findAll();
//        for (ProfileEntity profile : profiles) {
//            String body = "Hi "+ profile.getFullName() + ",<br><br>"
//                    + "This is a friendly reminder to add your income and expenses for today in Money Manger.<br><br>"
//                    +"<a href="+frontEndUrl+"style ='display:inline-block;padding:10px 20px;background-color:#4CAF50;color:#fff;text-decoration:none;border-radius:5px;font-weight:bold;'>Go to Money Manager</a>"
//                    +"<br><br> Best regards,<br>Money Manager Team";
//
//            emailService.sendEmail(profile.getEmail(),"Daily Reminder: Add your income and expenses",body);
//
//        }
//        log.info("Job completed : send DailyIncomeExpenseReminder()");
//    }
//
//
//    @Scheduled(cron = "0 0 23 * * *", zone = "IST")
//    public void sendDailyExpenseSummary() {
//        log.info("Job started : sendDailyExpenseSummary()");
//
//        List<ProfileEntity> profiles = profileRepository.findAll();
//
//        for (ProfileEntity profile : profiles) {
//
//            List<ExpenseDTO> todayExpenses =
//                    expenseService.getExpensesForUserOnDate(profile.getId(), LocalDate.now());
//
//            if (!todayExpenses.isEmpty()) {
//
//                StringBuilder table = new StringBuilder();
//
//                table.append("<h3>Daily Expense Summary</h3>");
//                table.append("<table style='border-collapse: collapse; width: 100%;'>");
//
//                // Header row
//                table.append("<tr style='background-color: #f2f2f2;'>")
//                        .append("<th style='border:1px solid #ddd; padding:8px;'>Date</th>")
//                        .append("<th style='border:1px solid #ddd; padding:8px;'>Category</th>")
//                        .append("<th style='border:1px solid #ddd; padding:8px;'>Amount</th>")
//                        .append("</tr>");
//
//                BigDecimal total = BigDecimal.ZERO;
//
//                // Data rows
//                for (ExpenseDTO expense : todayExpenses) {
//                    table.append("<tr>")
//                            .append("<td style='border:1px solid #ddd; padding:8px;'>")
//                            .append(expense.getDate())
//                            .append("</td>")
//
//                            .append("<td style='border:1px solid #ddd; padding:8px;'>")
//                            .append(expense.getCategoryName())
//                            .append("</td>")
//
//                            .append("<td style='border:1px solid #ddd; padding:8px;'>")
//                            .append(expense.getAmount())
//                            .append("</td>")
//
//
//                            .append("</tr>");
//
//                    total = total.add(expense.getAmount());
//                }
//
//                // Total row
//                table.append("<tr style='font-weight:bold;'>")
//                        .append("<td colspan='2' style='border:1px solid #ddd; padding:8px;'>Total</td>")
//                        .append("<td style='border:1px solid #ddd; padding:8px;'>")
//                        .append(total)
//                        .append("</td>")
//                        .append("<td style='border:1px solid #ddd; padding:8px;'></td>")
//                        .append("</tr>");
//
//                table.append("</table>");
//
//                // Send email (assuming you have emailService)
//                emailService.sendEmail(
//                        profile.getEmail(),
//                        "Daily Expense Summary",
//                        table.toString()
//                );
//            }
//        }
//
//        log.info("Job completed : sendDailyExpenseSummary()");
//    }
//
//}
