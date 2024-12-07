package com.klef.jfsd.exam;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class ClientDemo {
    public static void main(String[] args) {
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Customer.class);
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        // Inserting records
        Customer customer1 = new Customer();
        customer1.setName("John Doe");
        customer1.setEmail("john.doe@example.com");
        customer1.setAge(30);
        customer1.setLocation("New York");
        
        session.save(customer1);

        // Applying restrictions
        Criteria criteria = session.createCriteria(Customer.class);
        criteria.add(Restrictions.eq("name", "John Doe"));
        criteria.add(Restrictions.ne("email", "john.doe@example.com"));
        criteria.add(Restrictions.lt("age", 35));
        criteria.add(Restrictions.gt("age", 25));
        criteria.add(Restrictions.between("age", 20, 40));
        criteria.add(Restrictions.like("location", "New%"));

        List<Customer> customers = criteria.list();

        for (Customer customer : customers) {
            System.out.println("Customer: " + customer.getName() + ", Email: " + customer.getEmail());
        }

        tx.commit();
        session.close();
        factory.close();
    }
}
