package in.praveen.moneymanager.repository;

import in.praveen.moneymanager.entity.ExpenseEntity;
import org.apache.commons.math3.analysis.function.Exp;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
    List<ExpenseEntity> findByProfileIdOrderByDateDesc(Long profileId);
    List<ExpenseEntity> findTop5ByProfileIdOrderByDateDesc(Long profileId);
    @Query("SELECT SUM(i.amount) FROM ExpenseEntity i WHERE i.profile.id = :profileId")
    BigDecimal findTotalExpenseByProfileId(@Param("profileId") Long profileId);
    List<ExpenseEntity>findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(
            Long profileId,
            LocalDate startDate,
            LocalDate endDate,
            String keyWord,
            Sort sort
    );
}
