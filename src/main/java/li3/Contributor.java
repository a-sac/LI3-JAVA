package li3;


import java.util.*;

public class Contributor{

  private Long id;                  /* id do contribuidor */
  private Integer ncontributions;   /* Número de contribuições */
  private String name;              /* Nome do contribuidor */

  public Contributor(Contributor c){
    this.id = c.id;
    this.ncontributions = c.ncontributions;
    this.name = c.name;
  }

  public Contributor(Long id, String name){
    this.id = id;
    this.name = name;
    this.ncontributions = 1;
  }

  /**
  * Devolve o ID de um contribuidor
  * @return ID do contribuidor
  */
  public Long getId(){
    return this.id;
  }

  /**
  * Devolve o número de contribuições de um contribuidor
  * @return Número de contribuições do contribuidor
  */
  public Integer getNContributions(){
    return this.ncontributions;
  }

  /**
  * Aumenta o número de contribuições
  */
  public void contributionsUp(){
    this.ncontributions++;
  }

  /**
  * Devolve o nome de um contribuidor
  * @return Nome do contribuidor
  */
  public String getName(){
    return this.name;
  }

  /**
  * Clona um contribuidor
  * @return Clone do contribuidor
  */
  public Contributor clone(){
    return new Contributor(this);
  }

  /**
  * Retorna um inteiro menor que zero, igual a zero ou maior que zero respetivamente, se o número de contribuições do contribuidor for menor que, igual a ou maior às do contribuidor dado
  * @param c Contribuidor como comparação
  * @return Inteiro resultado da comparação do número de contribuições
  */
  public int compareNContributions(Contributor c){
    if(this.getNContributions() > c.getNContributions())
        return -1;
    if(this.getNContributions() < c.getNContributions())
        return 1;
    if(this.getId() < c.getId())
        return -1;
    if(this.getId() > c.getId())
        return 1;
    return 0;
  }
}
