package local.mcgeeka.getorders31.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@JsonIgnoreProperties(value = "hasvalueforopeningamt")
public class Customer
{
    //custcode, custname, custcity, workingarea,
    // custcountry, grade, openingamt, receiveamt, paymentamt, outstandingamt, phone, agentcode
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long custcode;

    private String custname;

    private String custcity;

    private String workingarea;

    private String custcountry;

    private String grade;

    @Transient //labeled this because it's never saved to database
    public boolean hasvalueforopeningamt = false; //this is never saved to database (just for our purposes)
    //this is made public
    private double openingamt;

    @Transient //labeled this because it's never saved to database
    public boolean hasvalueforreceiveamt = false; //this is never saved to database (just for our purposes)
    //this is made public
    private double receiveamt;

    @Transient //labeled this because it's never saved to database
    public boolean hasvalueforpaymentamt = false; //this is never saved to database (just for our purposes)
    //this is made public
    private double paymentamt;

    @Transient //labeled this because it's never saved to database
    public boolean hasvalueforoutstandingamt= false; //this is never saved to database (just for our purposes)
    //this is made public
    private double outstandingamt;

    private String phone;

    @ManyToOne
    @JoinColumn(name = "agentcode", nullable = false) //this connects agent to customer//nullable forces customers to have an agent
    @JsonIgnoreProperties("customersfoo")
    private Agent agentfoo;

    @OneToMany(mappedBy = "customerfoo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("customerfoo")
    private List<Order> ordersfoo = new ArrayList<>(); //ties agent to customers

    public Customer()
    {
    }

    public Customer(
        String custname,
        String custcity,
        String workingarea,
        String custcountry,
        String grade,
        double openingamt,
        double receiveamt,
        double paymentamt,
        double outstandingamt,
        String phone, Agent agent)
    {
        this.custname = custname;
        this.custcity = custcity;
        this.workingarea = workingarea;
        this.custcountry = custcountry;
        this.grade = grade;
        this.openingamt = openingamt;
        this.receiveamt = receiveamt;
        this.paymentamt = paymentamt;
        this.outstandingamt = outstandingamt;
        this.phone = phone;
        this.agentfoo= agent;
    }

    public long getCustcode()
    {
        return custcode;
    }

    public void setCustcode(long custcode)
    {
        this.custcode = custcode;
    }

    public String getCustname()
    {
        return custname;
    }

    public void setCustname(String custname)
    {
        this.custname = custname;
    }

    public String getCustcity()
    {
        return custcity;
    }

    public void setCustcity(String custcity)
    {
        this.custcity = custcity;
    }

    public String getWorkingarea()
    {
        return workingarea;
    }

    public void setWorkingarea(String workingarea)
    {
        this.workingarea = workingarea;
    }

    public String getCustcountry()
    {
        return custcountry;
    }

    public void setCustcountry(String custcountry)
    {
        this.custcountry = custcountry;
    }

    public String getGrade()
    {
        return grade;
    }

    public void setGrade(String grade)
    {
        this.grade = grade;
    }

    public double getOpeningamt()
    {
        return openingamt;
    }

    public void setOpeningamt(double openingamt)
    {
        this.hasvalueforopeningamt = true;
        this.openingamt = openingamt;
    }

    public double getReceiveamt()
    {

        return receiveamt;
    }

    public void setReceiveamt(double receiveamt)
    {
        this.hasvalueforreceiveamt = true;
        this.receiveamt = receiveamt;
    }

    public double getPaymentamt()
    {
        return paymentamt;
    }

    public void setPaymentamt(double paymentamt)
    {
        this.hasvalueforpaymentamt = true;
        this.paymentamt = paymentamt;
    }

    public double getOutstandingamt()
    {
        return outstandingamt;
    }

    public void setOutstandingamt(double outstandingamt)
    {
        this.hasvalueforoutstandingamt = true;
        this.outstandingamt = outstandingamt;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public Agent getAgentfoo()
    {
        return agentfoo;
    }

    public void setAgentfoo(Agent agentfoo)
    {
        this.agentfoo = agentfoo;
    }

    public List<Order> getOrdersfoo()
    {
        return ordersfoo;
    }

    public void setOrdersfoo(List<Order> ordersfoo)
    {
        this.ordersfoo = ordersfoo;
    }
}
