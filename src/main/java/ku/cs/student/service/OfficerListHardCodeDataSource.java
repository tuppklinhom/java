package ku.cs.student.service;

import ku.cs.student.models.OfficerList;
import ku.cs.student.models.Officer;

public class OfficerListHardCodeDataSource {
    private OfficerList officerList;

    public OfficerListHardCodeDataSource(){
        officerList = new OfficerList();
        readData();
    }

    private void readData() {
        officerList.addOfficer(new Officer("Pooh","poohza666","123"));
        officerList.addOfficer(new Officer("Xin","Xaxa","333"));
    }

    public OfficerList getOfficerList(){return officerList;}
}
