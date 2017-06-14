
package li3;

import java.util.*;

public class Article{

    private int nrevisions;           /* Número de revisões do artigo */
    private long id;                      /* O id do artigo */
    private TreeSet<Revision> revSet;    /* Lista de revisões */

    public Article(int nrevisions, long id){
        this.nrevisions = nrevisions;
        this.id = id;
        this.revSet = new TreeSet<Revision>(new RevisionComparator());
    }

    public Article(Article a){
        this.nrevisions = a.nrevisions;
        this.id = a.id;
        this.revSet = a.revSet;
    }

    /**
    * Devolve o ID de um artigo
    * @return ID do artigo
    */
    public long getId(){
        return this.id;
    }

    /**
    * Devolve o número de revisões do artigo
    * @return Número de revisões do artigo
    */
    public int getNRevisions(){
        return this.nrevisions;
    }

    /**
    * Devolve um TreeSet de revisões
    * @return TreeSet de revisões
    */
    public TreeSet<Revision> getRevSet() throws NoRevisionsException{
        if(this.revSet.isEmpty()) throw new NoRevisionsException("Sem Revisões");
        else return this.revSet;
    }

    /**
    * Devolve um TreeSet de revisões com um exception que a Java Stream consegue processar
    * @return TreeSet de revisões
    */
    public TreeSet<Revision> safeGetRevSet() throws RuntimeException{
      try{
        return this.getRevSet();
      }catch (NoRevisionsException e) {throw new RuntimeException(e);}
    }

    /**
    * Clona um artigo
    * @return Clone de um artigo
    */
    public Article clone(){
        return new Article(this);
    }

    /**
    * Aumenta o número de revisões
    */
    public void nRevisionsUp(){
        this.nrevisions++;
    }

    /**
    * Adiciona uma revisão à lista de revisões de um artigo
    * @param r Revisão a adicionar
    */
    public void addToList(Revision r){
        this.revSet.add(r);
    }

    /**
    * Retorna a revisão mais recente
    * @return Revisão mais recente
    */
    public Revision getRecentRevision(){
      return this.revSet.last();
    }

    /**
    * Retorna a revisão com maior número de caractéres
    * @return Revisão com maior número de caractéres
    */
    public Revision getLargestRevision(){
      Iterator<Revision> it = this.revSet.iterator();
      Revision r, rev=this.revSet.first();
      int n = rev.getNChars();
      int tmp;
      while(it.hasNext()){
        r = it.next();
        tmp = r.getNChars();
        if(n < tmp){
          rev = r;
          n = tmp;
        }
      }
      return rev;
    }

  /**
  * Retorna a revisão com maior número de palavras
  * @return Revisão com maior número de palavras
  */
    public Revision getBiggestRevision(){
      Iterator<Revision> it = this.revSet.iterator();
      Revision r, rev=this.revSet.first();
      int n = rev.getNWords();
      int tmp;
      while(it.hasNext()){
        r = it.next();
        tmp = r.getNWords();
        if(n < tmp){
          rev = r;
          n = tmp;
        }
      }
      return rev;
  }

  /**
  * Retorna se contém a revisão dado o seu ID (true) ou não (false)
  * @param revID ID da revisão
  * @return Se contém a revisão dado o seu ID (true) ou não (false)
  */
    public boolean containsRevision(long revID){
        Iterator<Revision> it = this.revSet.iterator();
        Revision r;
        boolean found = false;
        while(it.hasNext() && !found){
          r = it.next();
          if(r.getId()==(revID)) found = true;
        }
        return found;
     }

    /**
    * Retorna uma revisão dado o seu ID
    * @param revID ID da revisão
    * @return Revisão
    */
    public Revision getRevision(long revID){
        for(Revision r : this.revSet){
          if(r.getId()==(revID)) return r;
        }
        return null;
    }

    /**
    * Retorna um inteiro menor que zero, igual a zero ou maior que zero respetivamente, se o número de caractéres do artigo for menor que, igual a ou maior que o artigo dado
    * @param a Artigo como comparação
    * @return Inteiro resultado da comparação do número de chars
    */
    public int compareCharsTo(Article a){
        if(this.getRecentRevision().getNChars() > a.getRecentRevision().getNChars())
            return -1;
        if(this.getRecentRevision().getNChars() < a.getRecentRevision().getNChars())
            return 1;
        if(this.id < a.getId())
            return -1;
        if(this.id > a.getId())
            return 1;
        return 0;
    }

    /**
    * Retorna um inteiro menor que zero, igual a zero ou maior que zero respetivamente, se o número de palavras do artigo for menor que, igual a ou maior que o artigo dado
    * @param a Artigo como comparação
    * @return Inteiro resultado da comparação do número de words
    */
    public int compareWordsTo(Article a){
        if(this.getRecentRevision().getNWords() > a.getRecentRevision().getNWords())
            return -1;
        if(this.getRecentRevision().getNWords() < a.getRecentRevision().getNWords())
            return 1;
        if(this.id < a.getId())
            return -1;
        if(this.id > a.getId())
            return 1;
        return 0;
    }
}
