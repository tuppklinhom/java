package ku.cs.student.service;

import ku.cs.student.models.Report;
import ku.cs.student.models.ReportList;

import java.io.*;

public class ReportListFileDataSource implements DataSource<ReportList> {
    private String directoryName;
    private String fileName;

    public ReportListFileDataSource(String directoryName, String filename) {
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

    @Override
    public ReportList readData() {
        /**
         * Fixing readData for reading some old file in csv file
         */
        ReportList reportList = new ReportList();
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        FileReader reader = null;
        BufferedReader buffer = null;

        try {
            reader = new FileReader(file);
            buffer = new BufferedReader(reader);

            String line = "";
            while ((line = buffer.readLine()) != null) {
                String[] data = line.split(",");

                Report r = new Report(data[0].trim(), data[1].trim(), data[2].trim(), data[3].replace(";", "\n"), data[4].trim(), Integer.parseInt(data[5].trim()));
                /* Date : 29.09.2022
                Report r จะอ่านข้อมูลที่มีทั้งหมด 6 ตัวแน่นอน เพราะเนื่องจาก constructor ของ Class Report จะ assign ค่าทุกค่าของข้อมูลไว้อยู่แล้ว
                แม้จะรับข้อมูลเพื่อสร้าง Object แค่ 4 ตัว ก็ยังมีข้อมูลทั้งหมด 6 ตัว เพราะอีก 2 ตัวได้กำหนดค่าของมันเอาไว้แล้ว
                ปล1. โค้ดข้างล่างที่ comment ไว้ @Tupp มาดูว่าจะทำแบบนี้ใหม่ ( บรรทัดที่ 55 ) หรือจะทำแบบข้างล่างไปเลย
                ปล2. ไปเปลื่ยนลำดับการ assign ค่าของข้อมูลใน constructor มา ถ้าอยากใช้โค้ดข้างล่างอาจจะต้องเปลื่ยนลำดับการ assign ข้อมูลก่อน
                 */

//                Report r = null;
//                if (data.length == 4) { // normal report
//                    r = new Report(data[0].trim(), data[1].replace(";", "\n"), data[2].trim(), data[3].trim());
//                }
//                if (data.length == 5) { // report with vote count
//                    r = new Report(data[0].trim(), data[1].replace(";", "\n"), data[2].trim(), data[3].trim(), data[4].trim());
//                }
//                if (data.length == 6){
//                    r = new Report(data[0].trim(), data[1].trim(), data[2].trim(), data[3].replace(";", "\n"), data[4].trim(), Integer.parseInt(data[5].trim()));
//                }

                reportList.addReport(r);
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


        return reportList;
    }

    public void writeData(ReportList reportList){
        /**
         * Fixing writeData to write old data that may have change voteCount and status
         */
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file);
            buffer = new BufferedWriter(writer);

            for(Report r : reportList.getAllReport()){
                String content = r.getContent().replace("\n", ";");

                /* Date : 29.09.2022
                เปลื่ยนตำแหน่งการ assign ข้อมูล และข้อความข้างบนเลยเปลื่ยนโค้ดเป็นโค้ดนิดหน่อย ( บรรทัดที่ 124 ) @Tupp อยากจะแก้อะไรเพิ่มเติมมาแก้ได้เลย
                 */

//                String line = r.getReporterName() + "," + r.getHeadline() + "," + content + "," + r.getCategory();
//                if (r.getVoteCount() != 0){
//                    line = line + "," + r.getVoteCount();
//                }
//                if (r.getStatus() != "waiting"){
//                    line = line + "," + r.getStatus();
//                }

                String line = r.getReporterName() + "," + r.getStatus() + "," + r.getHeadline() + "," + content + "," + r.getCategory() + "," + r.getVoteCount();
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
