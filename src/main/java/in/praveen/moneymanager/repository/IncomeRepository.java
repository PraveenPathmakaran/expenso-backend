package in.praveen.moneymanager.repository;

import in.praveen.moneymanager.entity.IncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<IncomeEntity, Long> {
}
