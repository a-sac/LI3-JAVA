package li3;

import java.util.Comparator;

public class ArticleWordComparator implements Comparator<Article> {

    public int compare(Article a1, Article a2){
        return Integer.valueOf(a2.getBiggestRevision().getNWords()).compareTo(Integer.valueOf(a1.getBiggestRevision().getNWords()));
    }
}
