package mmt.core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import mmt.core.exceptions.BadDateSpecificationException;
import mmt.core.exceptions.BadTimeSpecificationException;
import mmt.core.exceptions.ImportFileException;
import mmt.core.exceptions.MissingFileAssociationException;
import mmt.core.exceptions.NoSuchPassengerIdException;
import mmt.core.exceptions.NoSuchServiceIdException;
import mmt.core.exceptions.NoSuchStationNameException;
import mmt.core.exceptions.NoSuchItineraryChoiceException;
import mmt.core.exceptions.NonUniquePassengerNameException;
import mmt.core.exceptions.InvalidPassengerNameException;

import java.util.Iterator;
import java.util.TreeSet;



/**
 * Classe TicketOffice
 *
 * Faz a ligacao entre a aplicacao (app) e o seu Core.
 *
 * @author Grupo 34
 * @version Intermedia
 */

public class TicketOffice {

  /** Companhia de comboios (trainCompany) que contem todos os dados */
  private TrainCompany _trainCompany = new TrainCompany();

  /** Ficheiro atualmente associado ao TicketOffice*/
  private String _filename = "";

  /** 
   * Altera o ficheiro associado ao TicketOffice.
   *
   * @param filename Nome do ficheiro a associar.
   */
  public void setFileName(String filename){
    _filename = filename;
  }

  /** 
   * Obtem o nome do ficheiro atualmente associado ao TicketOffice.
   *
   * @return Nome do ficheiro associado.
   */
  public String getFileName(){
    return _filename;
  }

  /** 
   * Reinicia a aplicacao, apagando toda a informacao excepto a relativa aos servicos.
   */
  public void reset() {
    _trainCompany.reset();
  }

  /** 
   * Grava o estado atual da TicketOffice num ficheiro, em forma de bits
   * 
   * @param filename Nome do ficheiro a guardar os dados.
   */
  public void save(String filename) throws IOException {
    ObjectOutputStream obOut = null;
    try{
      FileOutputStream fpout = new FileOutputStream(filename);
      obOut = new ObjectOutputStream(fpout);
      obOut.writeObject(_trainCompany);
      
    } finally{
      if(obOut != null)
        obOut.close();
    }
  }

  /** 
   * Carrega os dados de uma TicketOffice anterior, vindo de um ficheiro em forma de bits.
   *
   * @param filename Nome do ficheiro de onde se ira carregar os dados.
   */
  public void load(String filename) throws FileNotFoundException, IOException, ClassNotFoundException {
    ObjectInputStream obIn = null;
    try{
      FileInputStream fpin = new FileInputStream(filename);
      obIn = new ObjectInputStream(fpin);
      _trainCompany = (TrainCompany) obIn.readObject();

    } finally{
      if(obIn != null)
        obIn.close();
    }
  }

  /** 
   * Importa os dados vindo de um ficheiro textual.
   *
   * @param datafile Nome do ficheiro a importar os dados.
   */
  public void importFile(String datafile) throws ImportFileException {
    NewParser parser = new NewParser();
    _trainCompany = parser.parseFile(datafile);
  }
  
  public String searchItineraries(int passengerId, String departureStation, String arrivalStation, String departureDate, String departureTime) throws NoSuchPassengerIdException, NoSuchStationNameException, BadDateSpecificationException, BadTimeSpecificationException{
    return _trainCompany.searchItineraries(passengerId,departureStation,arrivalStation,departureDate,departureTime);
  }

  public void commitItinerary(int passengerId, int itineraryNumber) throws NoSuchItineraryChoiceException{
    _trainCompany.commitItinerary(passengerId,itineraryNumber);
  }

  /** 
   * Manda para a app uma string com todos os servicos presentes na trainCompany.
   *
   * @return  String de todos os servicos.
   */
  public String showAllServices(){
      return _trainCompany.showAllServices();
  }

  /** 
   * Manda para a app uma string com o servico com um certo identificador.
   *
   * @param id Identificador do servico a procurar.
   *
   * @return String do servico com identificador id.
   */
  public String showServiceByNumber(int id) throws NoSuchServiceIdException{
    return _trainCompany.showServiceByNumber(id);
  }

  /** 
   * Manda para a app uma string com todos os servicos com uma certa estacao inicial.
   *
   * @param name Nome da estacao a procurar.
   *
   * @return String de todos os servicos com comeco na estacao name.
   */
  public String showServiceDepartingFromStation(String name) throws NoSuchStationNameException{
    return _trainCompany.showServiceFromDepartingStation(name);
  }

 /** 
   * Manda para a app uma string com todos os servicos com termino numa estação especifica.
   *
   * @param name Nome da estacao a procurar.
   *
   * @return String de todos os servicos com termino na estacao name.
   */
  public String showServiceArrivingFromStation(String name) throws NoSuchStationNameException{
    return _trainCompany.showServiceFromArrivingStation(name);
  }

  /** 
   * Manda para a app uma string com todos os passageiros presentes na trainCompany.
   *
   * @return String de todos os passageiros.
   */
  public String showAllPassengers(){
    return _trainCompany.showAllPassengers();
  }

  /** 
   * Manda para a app uma string com o passageiro contendo um certo identificador.
   *
   * @param id Identificador do passageiro a procurar.
   *
   * @return String do passageiro com identificador id.
   */
  public String showPassengerByNumber(int id) throws NoSuchPassengerIdException{
    return _trainCompany.showPassengerByNumber(id);
  }
  
  /** 
   * Regista um novo passageiro na trainCompany.
   *
   * @param passegnerName Nome do novo passageiro a adicionar.
   */
  public void registerPassenger(String passengerName){
    _trainCompany.registerPassenger(passengerName);
  }

  /** 
   * Altera o nome de uma passageiro presente na trainCompany.
   *
   * @param id Identificador do passageiro a alterar o nome.
   * @param name Novo nome do passageiro.
   */
  public void changePassengerName(int id, String name) throws NoSuchPassengerIdException{
    _trainCompany.changePassengerName(id,name);
  }

 /** 
   * Manda para a app uma string com todos os Itinerarios da TrainCompany.
   *
   * @return String com os Itinerarios.
   */
  public String showAllItineraries(){
    return _trainCompany.showAllItineraries();
  }

 /** 
   * Manda para a app uma string com os Itinerarios de um certo passageiro.
   *
   * @param id Identificador do Passageiro.
   *
   * @return String com os Itinerarios do Passageiro.
   */
  public String showItineraryByPassenger(int id) throws NoSuchPassengerIdException{
    return _trainCompany.showItineraryByPassenger(id);
  }
}
