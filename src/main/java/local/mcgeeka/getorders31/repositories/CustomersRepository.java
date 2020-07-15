package local.mcgeeka.getorders31.repositories;

import local.mcgeeka.getorders31.models.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomersRepository extends CrudRepository<Customer, Long>
{
}
