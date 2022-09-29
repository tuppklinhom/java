package ku.cs.student.service;

import ku.cs.student.models.AdminList;
import ku.cs.student.models.Admin;
import ku.cs.student.models.Officer;
import ku.cs.student.models.OfficerList;

import java.io.*;

public class AdminListFileDataSource implements DataSource<AdminList>{

    private String directoryName;
    private String fileName;

    public AdminListFileDataSource(String directoryName, String fileName) {
        this.directoryName = directoryName;
        this.fileName = fileName;
        checkFileIsExisted();
    }

    private void checkFileIsExisted(){
        File file = new File(directoryName);
        if ( ! file.exists()){
            file.mkdirs();
        }
        String filePath = directoryName + File.separator + fileName;
        file = new File(filePath);
        if ( ! file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public AdminList readData(){
        AdminList adminList = new AdminList();
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        FileReader reader = null;
        BufferedReader buffer = null;

        try {
            reader = new FileReader(file);
            buffer = new BufferedReader(reader);

            String line = "";
            while((line = buffer.readLine()) != null){
                String[] data = line.split(","); // แยกด้วยคอมม่า
                // data ลำดับที่ 1 - 3 ( Index : 0 - 1 ) ควรจะกำหนดไว้อยู่แล้วเพราะ Officer จะถูกเพิ่มด้วย Admin ดังนั้น ข้อมูลอันดับที่ 1 - 3 จึงจะมีอยู่แล้ว
                Admin s = new Admin(
                        data[0].trim(),
                        data[1].trim()
                );
                adminList.addAdmin(s);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                buffer.close();
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        return adminList;
    }
    public void writeData(AdminList adminList){
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file);
            buffer = new BufferedWriter(writer);

            for(Admin admin : adminList.getAllAdmin()){
                String line = admin.getUsername() + "," + admin.getPassword();
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
