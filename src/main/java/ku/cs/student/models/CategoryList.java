package ku.cs.student.models;

import java.util.Set;
import java.util.TreeSet;

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
}
