package li3;

import java.util.Comparator;

public class RevisionComparator implements Comparator<Revision> {

    public int compare(Revision r1, Revision r2){
        return r1.getDate().compareTo(r2.getDate());
    }
}
