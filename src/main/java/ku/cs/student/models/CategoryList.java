package ku.cs.student.models;

import java.util.*;

public class CategoryList { // list for categories in create report page
    private Set<String> categories;

    public CategoryList(){
        categories = new TreeSet<String>();
    }
    public void addCategory(String category){// for someone who work in officer
        categories.add(category);
    }

    public Set<String> getAllCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return "CategoryList{" +
                "categories=" + categories +
                '}';
    }

    public void clearCategoryList(){
        this.categories.clear();
    }

    //    private Map<String, String> categoriesMap;
//
//    public CategoryList() {
//        categoriesMap = new TreeMap<String, String>();
//    }
//
//    public CategoryList(Officer officer) {
//        categoriesMap = new TreeMap<String, String>();
//    }
//
//    public void addCategoryWithOfficer(String category, String officer) {
//        categoriesMap.put(category, officer);
//    }
//
//    public Set<String> getAllCategory() {
//        return categoriesMap.keySet();
//    }
//
//    public String getOfficerByCategory(String category) {
//        return categoriesMap.get(category);
//    }
//
//    public void addOfficer(String category, String officer) {
//        String officerOfCategory = categoriesMap.get(category);
//        officerOfCategory = officerOfCategory + "\n" + officer;
//        categoriesMap.get(category) = officerOfCategory;
//    }
}
