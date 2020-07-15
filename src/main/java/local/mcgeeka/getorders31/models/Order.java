package local.mcgeeka.getorders31.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order
{
    //ordnum, ordamount, advanceamount, custcode, orderdescription
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ordnum;

    private double ordamount;

    private double advanceamount;

    private String orderdescription;

    //main class - w/o no payments
    //in data.sql ---> ORDERSPAYMENTS
    @ManyToMany()
    @JoinTable(name = "orderspayments", joinColumns = @JoinColumn(name = "ordnum"),
        inverseJoinColumns = @JoinColumn(name = "paymentid"))
    @JsonIgnoreProperties("ordersfoo")
    private Set<Payment> paymentsfoo = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "custcode", nullable = false) //this connects agent to customer
    //nullable forces customers to have an agent
    @JsonIgnoreProperties("ordersfoo")
    private Customer customerfoo;

    public Order()
    {
    }

    //ordnum, ordamount, advanceamount, custcode, orderdescription

    public Order(
        double ordamount,
        double advanceamount,
        Customer customer,
        String orderdescription)
    {
        this.ordamount = ordamount;
        this.advanceamount = advanceamount;
        this.customerfoo = customer;
        this.orderdescription = orderdescription;
        this.customerfoo = customerfoo;
    }

    public long getOrdnum()
    {
        return ordnum;
    }

    public void setOrdnum(long ordnum)
    {
        this.ordnum = ordnum;
    }

    public double getOrdamount()
    {
        return ordamount;
    }

    public void setOrdamount(double ordamount)
    {
        this.ordamount = ordamount;
    }

    public double getAdvanceamount()
    {
        return advanceamount;
    }

    public void setAdvanceamount(double advanceamount)
    {
        this.advanceamount = advanceamount;
    }

    public String getOrderdescription()
    {
        return orderdescription;
    }

    public void setOrderdescription(String orderdescription)
    {
        this.orderdescription = orderdescription;
    }

    public Set<Payment> getPaymentsfoo()
    {
        return paymentsfoo;
    }

    public void setPaymentsfoo(Set<Payment> paymentsfoo)
    {
        this.paymentsfoo = paymentsfoo;
    }

    public Customer getCustomerfoo()
    {
        return customerfoo;
    }

    public void setCustomerfoo(Customer customerfoo)
    {
        this.customerfoo = customerfoo;
    }
}
