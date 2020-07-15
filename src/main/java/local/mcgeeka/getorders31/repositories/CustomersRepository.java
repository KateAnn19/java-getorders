package local.mcgeeka.getorders31.repositories;

import local.mcgeeka.getorders31.models.Customer;
import local.mcgeeka.getorders31.views.CustomerOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomersRepository extends CrudRepository<Customer, Long>
{
    List<Customer> findByCustnameContainingIgnoringCase(String likename);

    //custom query

    @Query(value = "SELECT c.CUSTNAME as name, o.ORDAMOUNT as amount " +
                   "FROM ORDERS o INNER JOIN CUSTOMERS c ON o.CUSTCODE = c.CUSTCODE", nativeQuery = true)
    List<CustomerOrder> getOrderCount();
}
