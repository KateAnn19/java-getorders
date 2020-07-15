package local.mcgeeka.getorders31.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "payments")
public class Payment
{
    //paymentid, type
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long paymentid;

    @Column(nullable = false, unique = true)
    private String type;

    //many to many with orders
    @ManyToMany(mappedBy = "paymentsfoo")
    private Set<Order> ordersfoo = new HashSet<>();

    public Payment()
    {
    }

    public Payment(String type)
    {
        this.type = type;
    }

    public long getPaymentid()
    {
        return paymentid;
    }

    public void setPaymentid(long paymentid)
    {
        this.paymentid = paymentid;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public Set<Order> getOrdersfoo()
    {
        return ordersfoo;
    }

    public void setOrdersfoo(Set<Order> ordersfoo)
    {
        this.ordersfoo = ordersfoo;
    }
}
