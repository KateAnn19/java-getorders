package local.mcgeeka.getorders31.repositories;

import local.mcgeeka.getorders31.models.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomersRepository extends CrudRepository<Customer, Long>
{
    List<Customer> findByCustnameContainingIgnoringCase(String likename);
}
