package li3;

import java.util.*;

public class ContributorComparator implements Comparator<Contributor>{

  public int compare(Contributor c1, Contributor c2){
    return c1.compareNContributions(c2);
  }
}
