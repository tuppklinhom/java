package ku.cs.ku_help.models;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class OfficerList {

    private Map<String, Officer> officers;

    public OfficerList(){ officers = new TreeMap<String, Officer>();}

    public int size() {return officers.size();}

    public void addOfficer(Officer newOfficer) { officers.put(newOfficer.getUsername(), newOfficer);}

    public Set<String> getAllOfficers() {
        return officers.keySet();
    }

    public Officer findOfficer(String username) {
        Officer found = officers.get(username);
        return found;
    }

    public OfficerList filterBy(Filterer<Officer> filterer) {
        OfficerList filtered = new OfficerList();
        for (String key : getAllOfficers() ) {
            Officer found = findOfficer(key);
            if (filterer.filter(found)) {
                filtered.addOfficer(found);
            }
        }
        return filtered;
    }

    public void changePassword(String username, String password) {
        findOfficer(username).changePassword(password);
    }
}
