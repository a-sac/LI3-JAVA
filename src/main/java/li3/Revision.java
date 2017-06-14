  package li3;

  import java.util.*;

  public class Revision{

    private long id;              /* id of the revision */
    private long idcontributor;   /* id of the contributor that wrote the */
    private String title;         /* title of the revision */
    private String date;          /* date of the creation of the revisions */
    private int nwords;       /* number of words of the revision's article */
    private int nchars;       /* number of characters of the revison's article */

    public Revision(long id, String title, String date, long idcontributor, int nwords, int nchars){
      this.id = id;
      this.title = title;
      this.date = date;
      this.idcontributor = idcontributor;
      this.nwords = nwords;
      this.nchars = nchars;
    }

    public Revision(Revision rev){
      this.id = rev.id;
      this.title = rev.title;
      this.date = rev.date;
      this.idcontributor = rev.idcontributor;
      this.nwords = rev.nwords;
      this.nchars = rev.nchars;
    }

    public Revision(long id, String title, String date, long contributorID){
      this.id = id;
      this.title = title;
      this.date = date;
      this.idcontributor = contributorID;
    }

    /**
    * Devolve o ID de uma revisão
    * @return ID da revisão
    */
    public long getId(){
      return this.id;
    }

    /**
    * Devolve o título de uma revisão
    * @return Título da revisão
    */
    public String getTitle(){
      return this.title;
    }

    /**
    * Devolve a data de uma revisão
    * @return Data da revisão
    */
    public String getDate(){
      return this.date;
    }

    /**
    * Devolve o número de palavras de uma revisão
    * @return Número de palavras da revisão
    */
    public int getNWords(){
      return this.nwords;
    }

    /**
    * Devolve o número de caractéres de uma revisão
    * @return Número de caractéres da revisão
    */
    public int getNChars(){
      return this.nchars;
    }

    /**
    * Define o número de palavras de uma revisão
    * @param words Número de palavras
    */
    public void setNwords(int words){
      this.nwords = words;
    }

    /**
    * Define o número de caractéres de uma revisão
    * @param chars Número de caractéres
    */
    public void setNChars(int chars){
      this.nchars = chars;
    }

    /**
    * Define o ID de um contribuidor de uma revisão
    * @param contributorID ID do contribuidor
    */
    public void setContributorID(long contributorID){
      this.idcontributor = contributorID;
    }

    /**
    * Clona uma revisão
    * @return Clone da revisão
    */
    public Revision clone(){
      return new Revision(this);
    }
  }
