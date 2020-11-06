/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Category;
import entity.Product;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

/**
 *
 * @author osboxes
 */
public class OrderModel {

    UserTransaction utx;
    EntityManager em;

    public OrderModel(EntityManager em, UserTransaction utx) {
        this.utx = utx;
        this.em = em;
    }

    public ProductModel retrieveProductById(Integer id) {
        Query query = em.createQuery("SELECT p FROM Product p WHERE p.id = :id").setParameter("id", id);
        return (ProductModel) query.getSingleResult();
    }
}
