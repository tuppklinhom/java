package ku.cs.student.models;

import java.util.ArrayList;

public class OfficerList {

    private ArrayList<Officer> officers;

    public OfficerList(){ officers = new ArrayList<>();}

    public Officer indexOf(int num){return officers.get(num);}

    public int size() {return officers.size();}

    public void addOfficer(Officer newOfficer) { officers.add(newOfficer);}

    public ArrayList<Officer> getOfficers() {
        return officers;
    }
}
