package ku.cs.ku_help.service;

import ku.cs.ku_help.models.CategoryList;

import java.io.*;

public class CategoryListFileDataSource implements DataSource<CategoryList>{ // just read category data from csv file
    private String directoryName;
    private String fileName;

    public CategoryListFileDataSource(String directoryName, String filename){
        this.directoryName = directoryName;
        this.fileName = filename;
        checkFileExisted();
    }

    private void checkFileExisted() {
        File file = new File(directoryName);
        if (!file.exists()) {
            file.mkdirs();
        }

        String filePath = directoryName + File.separator + fileName;
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public CategoryList readData(){
        CategoryList categoryList = new CategoryList();
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        FileReader reader = null;
        BufferedReader buffer = null;

        try {
            reader = new FileReader(file);
            buffer = new BufferedReader(reader);

            String line = "";
//            Officer officer = null;
            while ((line = buffer.readLine()) != null) {
                categoryList.addCategory(line);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                buffer.close();
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        return categoryList;
    }


    public void writeData(CategoryList categoryList){
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file);
            buffer = new BufferedWriter(writer);

            for(String line : categoryList.getAllCategories()){
                buffer.append(line);
                buffer.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                buffer.close();
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
