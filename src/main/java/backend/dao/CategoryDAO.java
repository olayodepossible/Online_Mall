package backend.dao;

import backend.model.Category;

import java.util.List;

public interface CategoryDAO {

    Category get(int id);
    List<Category> categories();
    boolean add(Category category);
    boolean update(Category category);
    boolean delete(Category category);
}
