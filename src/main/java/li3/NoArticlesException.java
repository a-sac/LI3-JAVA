package li3;

public class NoArticlesException extends Exception
{
  public NoArticlesException(){
    super();
  }

  public NoArticlesException(String message){
    super(message);
  }
}
