package li3;

import java.util.*;
import java.io.UnsupportedEncodingException;

public class Structure{

    private long allArticles;                /* Número total de artigos */
    private Map<Long,Article> articles;         /* Lista de artigos */
    private Map<Long,Contributor> contributors; /* Lista de contribuidores */

    public Structure(){
        this.allArticles = 0;
        this.articles = new TreeMap<Long,Article>();
        this.contributors = new TreeMap<Long,Contributor>();
    }

    /**
    * Devolve o número de artigos totais nos snapshots processados
    * @return Número de artigos totais nos snapshots processados
    */
    public long getAllArticles(){
        return this.allArticles;
    }

    /**
    * Devolve o TreeMap dos Artigos na estrutura
    * @return TreeMap dos Artigos na estrutura
    */
    public Map<Long,Article> getArticles() throws NoArticlesException{
        if(this.articles.isEmpty()) throw new NoArticlesException("Sem Artigos");
        else{
          /*-------- Se for necessário clone -----------------
          Map<Long,Article> neo = new TreeMap<Long,Article>();
          this.articles.values().stream()
                                .forEach(a -> neo.put(a.getId(), a.clone()));
          return neo;
          */
          return this.articles;
      }
    }

    /**
    * Devolve o TreeMap dos Contributors na estrutura
    * @return TreeMap dos Contributors na estrutura
    */
    public Map<Long,Contributor> getContributors() throws NoContributorsException{
        if(this.contributors.isEmpty()) throw new NoContributorsException("Sem Contribuidores");
        else{
            /*-------- Se for necessário clone -----------------
            Map<Long,Contributor> neo = new TreeMap<Long,Contributor>();
            this.contributors.values().stream()
                                  .forEach(c -> neo.put(c.getId(), c.clone()));
            return neo;
            */
          return this.contributors;
        }
    }

    /**
    * Insere um artigo se não existe já na estrutura e no caso de existir atualiza a sua lista de revisões, além de inserir o contribuidor do artigo na estrutura
    * @param articleID ID do artigo a ser inserido na estrutura
    * @param revisionID ID da revisão a ser inserida na estrutura
    * @param contributorID ID do contribuidor a ser inserido na estrutura
    * @param title Título do artigo a ser inserido
    * @param username Username do contribuidor a ser inserido na estrutura
    * @param time Data da contribuição do artigo a ser inserido
    * @param text Corpo do artigo a ser inserido
    */
    public void insert(long articleID, long revisionID, long contributorID, String title, String username, String time, String text){
        try{
            int nrev;
            Revision newRevision;
            Article art;
            this.allArticles++;
            if(this.articles.containsKey(articleID)){
                art = this.articles.get(articleID);
                if(!art.containsRevision(revisionID)){
                    newRevision = new Revision(revisionID, title, time, contributorID);
                    newRevision.setNChars(text.getBytes("UTF-8").length);
                    newRevision.setNwords(count(text));
                    art.addToList(newRevision);
                    art.nRevisionsUp();
                    this.articles.replace(articleID, art);
                    if(contributorID != -1) {
                        insertContributor(contributorID, username);
                    }
                }
            }
            else{
                art = new Article(1, articleID);
                newRevision = new Revision(revisionID, title, time, contributorID);
                newRevision.setNChars(text.getBytes("UTF-8").length);
                newRevision.setNwords(count(text));
                art.addToList(newRevision);
                this.articles.put(articleID, art);
                if(contributorID != -1) {
                    insertContributor(contributorID, username);
                }
            }
        }
        catch(UnsupportedEncodingException e){
          e.printStackTrace();
        }
    }

    /**
    * Insere um contribuidor se não existe já na estrutura e no caso de existir atualiza o seu número de contribuições
    * @param contributorID ID do contribuidor a ser inserido na estrutura
    * @param name Username do contribuidor a ser inserido na estrutura
    */
    public void insertContributor(long contributorID, String name){
        int n;
        Contributor con;
        if(this.contributors.containsKey(contributorID)){
            this.contributors.get(contributorID).contributionsUp();
        }
        else {
            con = new Contributor(contributorID, name);
            this.contributors.put(contributorID, con);
        }
    }

    /**
    * Retorna o número de palavras no texto content
    * @param content Texto a ser processado
    * @return Número de palavras no texto content
    */
    int count(String content){
      int i, c=0, prev=0, length = content.length();
      for(i=0; i < length; i++){
        if(content.charAt(i)==' ' || content.charAt(i)=='\n' || content.charAt(i)=='\t') {
          if(prev==1){
            c++;
            prev=0;
          }
        }
        else prev=1;
      }
      if(prev==1) c++;
      return c;
    }



}
