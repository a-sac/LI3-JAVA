package li3;

import java.util.Comparator;

public class ArticleCharComparator implements Comparator<Article> {

    public int compare(Article a1, Article a2){
        return Integer.valueOf(a2.getLargestRevision().getNChars()).compareTo(Integer.valueOf(a1.getLargestRevision().getNChars()));
    }
}
