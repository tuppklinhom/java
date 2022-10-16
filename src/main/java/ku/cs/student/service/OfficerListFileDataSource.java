package ku.cs.student.service;

import ku.cs.student.models.Officer;
import ku.cs.student.models.OfficerList;
import ku.cs.student.models.StudentList;

import java.io.*;

public class OfficerListFileDataSource implements DataSource<OfficerList>{
    private String directoryName;
    private String fileName;

    public OfficerListFileDataSource(String directoryName, String fileName){
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

    public OfficerList readData(){
        OfficerList officerList = new OfficerList();
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
                Officer f = new Officer(
                        data[0].trim(),
                        data[1].trim(),
                        data[2].trim(),
                        data[3].trim(),
                        data[4].trim()
                );
                // รับหมวดหมู่เข้ามาจนกว่าจะหมดหมวดหมู่ที่เจ้าหน้าที่รับผิดชอบ ( หมวดหมู่สามารถเพิ่มได้เรื่อย ๆ )
                for (int i = 5;i < data.length;i++) {
                    f.addCategory(data[i].trim());
                }

                officerList.addOfficer(f);
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

        return officerList;
    }
    public void writeData(OfficerList officerList){
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file);
            buffer = new BufferedWriter(writer);

            for(String username : officerList.getAllOfficers()){
                Officer officer = officerList.findOfficer(username);
                String line = officer.getName() + "," + officer.getUsername() + "," + officer.getPassword() + "," + officer.getImagePath() + "," + officer.getLatestLoginDate();
                for (String category : officer.getCategory()) { // loop เพื่อที่จะเข้าถึงข้อมูล category ของ officer เพราะ officer คนนึงอาจมีหลาย categories
                    line = line + "," + category;
                }
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
