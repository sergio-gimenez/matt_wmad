/*
 * To change this template, choose Tools | Templates
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
 * @author juanluis
 */
public class ProductModel {

    UserTransaction utx;
    EntityManager em;

    public ProductModel(EntityManager em, UserTransaction utx) {
        this.utx = utx;
        this.em = em;
    }

    public List<Product> retrieveProductsByCategory(Category category) {
        Query query = em.createQuery("SELECT p FROM Product p WHERE p.category = :category").setParameter("category", category);
        return query.getResultList();
    }

    public Product retrieveProductById(Integer id) {
        Query query = em.createQuery("SELECT p FROM Product p WHERE p.id = :productid").setParameter("productid", id);
        return (Product) query.getSingleResult();
    }
}