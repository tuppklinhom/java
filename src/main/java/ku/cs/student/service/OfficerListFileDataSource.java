package ku.cs.student.service;

import ku.cs.student.models.Officer;
import ku.cs.student.models.OfficerList;
import ku.cs.student.models.Student;
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
                Officer f = new Officer(
                        data[0].trim(),
                        data[1].trim(),
                        data[2].trim()
                );
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

            for(Officer officer : officerList.getOfficers()){
                String line = officer.getName() + "," + officer.getUsername() + "," + officer.getPassword();
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
