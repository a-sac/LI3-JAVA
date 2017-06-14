package li3;

public class NoRevisionsException extends Exception
{
  public NoRevisionsException(){
    super();
  }

  public NoRevisionsException(String message){
    super(message);
  }
}
