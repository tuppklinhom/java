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

                Report r = null;
                if (data.length == 4) { // normal report
                    r = new Report(data[0].trim(), data[1].trim(), data[2].replace(";", "\n"), data[3].trim());
                }
                if (data.length == 5) { // report with vote count
                    r = new Report(data[0].trim(), data[1].trim(), data[2].replace(";", "\n"),data[3].trim(), Integer.parseInt(data[4].trim()));
                }
                if (data.length == 6){
                    r = new Report(data[0].trim(), data[1].trim(), data[2].replace(";", "\n"),data[3].trim(), Integer.parseInt(data[4].trim()), data[5].trim());
                }

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
                String line = r.getReporterName() + "," + r.getHeadline() + "," + content + "," + r.getCategory();
                if (r.getVoteCount() != 0){
                    line = line + "," + r.getVoteCount();
                }
                if (r.getStatus() != "waiting"){
                    line = line + "," + r.getStatus();
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
