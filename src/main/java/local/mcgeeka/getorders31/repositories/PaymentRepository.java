package local.mcgeeka.getorders31.repositories;

import local.mcgeeka.getorders31.models.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, Long>
{
}
