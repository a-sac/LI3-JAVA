package li3;

import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.*;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.*;

public class Parser {

    public Parser(){}

    /**
    * Inicia o parse dos snapshots para a estrutura
    * @param struct Estrutura a ser carregada
    * @param nsnaps Número de snapshots a ser processados
    * @param args ArrayList de snapshots a ser processados
    */
    public void run(Structure struct, int nsnaps, ArrayList<String> args){
        for(String s : args)
        parse(s, struct);
    }

    /**
    * Processa um snapshot e carrega os seus dados para a estrutura
    * @param filexml Snapshot a ser processado
    * @param struct Estrutura a ser carregada
    */
    public void parse(String filexml, Structure struct) {
        boolean id = false, isTitle = false, isTime = false, isUsername = false, isText = false;
        String articleid=null, revisionid=null, usernameid="-1", title=null, time=null, username=null, text=null, test=null;
        int c=0;
        int nChars = 0;
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            factory.setProperty(XMLInputFactory.IS_COALESCING, true);
            XMLEventReader eventReader =
            factory.createXMLEventReader(new FileReader(filexml));
            while(eventReader.hasNext()){
                XMLEvent event = eventReader.nextEvent();
                switch(event.getEventType()){
                    case XMLStreamConstants.START_ELEMENT:
                    StartElement startElement = event.asStartElement();
                    String qName = startElement.getName().getLocalPart();
                    if (qName.equalsIgnoreCase("id")) {
                        id = true;
                        c++;
                    }
                    if (qName.equalsIgnoreCase("title")) {
                        isTitle = true;
                    }
                    if (qName.equalsIgnoreCase("timestamp")) {
                        isTime = true;
                    }
                    if (qName.equalsIgnoreCase("username")) {
                        isUsername = true;
                    }
                    if (qName.equalsIgnoreCase("text")){
                        isText = true;
                    }
                    break;
                    case XMLStreamConstants.CHARACTERS:
                    Characters characters = event.asCharacters();
                    if(id && c==1){
                        id = false;
                        articleid=characters.getData();
                    }
                    if(id && c==2){
                        id = false;
                        revisionid=characters.getData();
                    }
                    if(id && c==3){
                        id = false;
                        usernameid=characters.getData();
                    }
                    if(isTitle){
                        isTitle = false;
                        title=characters.getData();
                    }
                    if(isTime){
                        isTime = false;
                        time=characters.getData();
                    }
                    if(isUsername){
                        isUsername = false;
                        username=characters.getData();
                    }
                    if(isText){
                        isText=false;
                        text=characters.getData();
                    }
                    break;
                    case XMLStreamConstants.END_ELEMENT:
                    EndElement endElement = event.asEndElement();
                    String lName = endElement.getName().getLocalPart();
                    if(lName.equalsIgnoreCase("page")){
                        c=0;
                        struct.insert(Long.parseLong(articleid), Long.parseLong(revisionid), Long.parseLong(usernameid), title, username, time, text);
                        usernameid="-1";
                    }
                    break;
                }
            }
        } catch (XMLStreamException i){
            i.printStackTrace();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
