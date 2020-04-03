package backend.dao;

import backend.model.Category;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Repository("categoryDAO")
@Transactional
public class CategoryDAOImpl implements CategoryDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List categories() {
        String selectActiveCategory = "FROM Category WHERE active = :active";

        Query query = sessionFactory.getCurrentSession().createQuery(selectActiveCategory);

        query.setParameter("active", true);

        return query.getResultList();
    }

    /*
     * Getting single category based on id
     */
    @Override
    public Category get(int id) {

        return sessionFactory.getCurrentSession().get(Category.class, id);

    }

    @Override

    public boolean add(Category category) {

        try {
            // add the category to the database table
            sessionFactory.getCurrentSession().persist(category);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    /*
     * Updating a single category
     */
    @Override
    public boolean update(Category category) {

        try {
            // add the category to the database table
            sessionFactory.getCurrentSession().update(category);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Category category) {

        category.setActive(false);

        try {
            // add the category to the database table
            sessionFactory.getCurrentSession().update(category);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
