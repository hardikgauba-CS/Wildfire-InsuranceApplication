package com.wildfireinventory.dao;

import com.wildfireinventory.model.Category;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class CategoryDAOTest {
    private final CategoryDAO categoryDAO = new CategoryDAO();
    private Category testCategory;

    @Before
    public void setUp() throws SQLException {
        testCategory = new Category();
        testCategory.setCategoryName("Test Category");
        testCategory = categoryDAO.createCategory(testCategory);
    }

    @After
    public void tearDown() throws SQLException {
        if (testCategory != null && testCategory.getCategoryId() > 0) {
            categoryDAO.deleteCategory(testCategory.getCategoryId());
        }
    }

    @Test
    public void testCreateAndRetrieveCategory() throws SQLException {
        Category retrieved = categoryDAO.getCategoryById(testCategory.getCategoryId());
        assertNotNull(retrieved);
        assertEquals(testCategory.getCategoryName(), retrieved.getCategoryName());
    }

    @Test
    public void testGetAllCategories() throws SQLException {
        List<Category> categories = categoryDAO.getAllCategories();
        assertNotNull(categories);
        assertFalse(categories.isEmpty());
        assertTrue(categories.stream()
                .anyMatch(cat -> cat.getCategoryId() == testCategory.getCategoryId()));
    }

    @Test
    public void testGetCategoryByName() throws SQLException {
        Category retrieved = categoryDAO.getCategoryByName("Test Category");
        assertNotNull(retrieved);
        assertEquals(testCategory.getCategoryId(), retrieved.getCategoryId());
    }

    @Test
    public void testDeleteCategory() throws SQLException {
        boolean deleted = categoryDAO.deleteCategory(testCategory.getCategoryId());
        assertTrue(deleted);
        Category deletedCheck = categoryDAO.getCategoryById(testCategory.getCategoryId());
        assertNull(deletedCheck);
        testCategory = null; // so tearDown doesn't run delete again
    }
}
