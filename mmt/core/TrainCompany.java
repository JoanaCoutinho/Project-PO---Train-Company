package mmt.core;

import mmt.core.Passenger;
import mmt.core.Service;
import mmt.core.Itinerary;
import mmt.core.TrainStation;

import mmt.core.exceptions.BadDateSpecificationException;
import mmt.core.exceptions.BadEntryException;
import mmt.core.exceptions.BadTimeSpecificationException;
import mmt.core.exceptions.InvalidPassengerNameException;
import mmt.core.exceptions.NoSuchDepartureException;
import mmt.core.exceptions.NoSuchPassengerIdException;
import mmt.core.exceptions.NoSuchServiceIdException;
import mmt.core.exceptions.NoSuchStationNameException;
import mmt.core.exceptions.NoSuchItineraryChoiceException;
import mmt.core.exceptions.NonUniquePassengerNameException;

import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeMap;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.function.Predicate;

/** 
 * Compara servicos relativamente ao seu identificador.
*/
class ComparatorServiceById implements Comparator<Service>, java.io.Serializable{
 /**
 * Compara dois servicos pelo seu identificador.
 *
 * @param f1 Servico a comparar.
 * @param f2 Servico a comparar.
 */
  public int compare(Service f1, Service f2) {
    return f1.getId() - f2.getId();
  }
}



/**
* Compara itinerarios relativamente a estação de partida, chegada e hora.
*/
class ComparatorItinerary implements Comparator<Itinerary>, java.io.Serializable{
  /**
  * Compara dois itinerarios pela estacao de partida, chegada e hora.
  *
  * @param f1 Itinerario a comparar.
  * @param f2 Itinerario a comparar.
  */
  public int compare(Itinerary f1, Itinerary f2) {
    int j = f1.getDepartingStation().getTime().compareTo(f2.getDepartingStation().getTime());
    if( j == 0){
      int i = f1.getArrivingStation().getTime().compareTo(f2.getArrivingStation().getTime());
      if(i == 0){
        if(f1.getTravelTime() < f2.getTravelTime())
          return -1;
        else
          return 1;
      }
      return i;
    }
    return j; 
  }
}

/**
 * Uma companhia de comboios tem servicos para os seus comboios e passageiros que adquirem itinerarios basiados nos seus servicos.
 *
 * @author Grupo 34
 * @version Intermedio
 */
public class TrainCompany implements java.io.Serializable {
 
  /** Serial number for serialization. */
  private static final long serialVersionUID = 201708301010L;
  
  /** Lista de passageiros da TrainCompany */
  private ArrayList<Passenger> _listPass = new ArrayList<>();
  
  /** Arvore de servicos da TrainCompany */
  private TreeSet<Service> _listServ = new TreeSet<>(new ComparatorServiceById());

  /** Lista de Estacoes da TrainCompany */
  private TreeMap<String,TrainStation> _listStations = new TreeMap<>();

  /** Lista de itinerarios temporarios de onde o utilizador escolhera um itinerario */
  private TreeSet<Itinerary> _listItinerary = new TreeSet<>(new ComparatorItinerary());

  /**
   * Reinicia a TrainCompany, apagando a lista de passageiros (e itinerarios), mantendo os servicos intactos.
   */
  protected void reset(){
    //reset itinerarios
    _listPass.clear();
  }

  /** 
   * Verifica se uma certa TrainStation ja existed na TrainCompany.
   * 
   * @param station TrainStation a verificar se existe.
   *
   * @return true se essa estacao existir, false caso contrario.
   */
  protected TrainStation checkStation(TrainStation station){
    return _listStations.get(station.getName());
  }

  /**
   * Adiciona uma estacao a lista de TrainStations.
   *
   * @param station Estacao a adicionar.
   */
  protected void addStation(TrainStation station){
    _listStations.put(station.getName(),station);
  }

  /**
   * Adiciona um servico a TrainCompany.
   * 
   * @param serv Servico a adicionar.
   */
  protected void addService(Service serv){
    _listServ.add(serv);
  }

  /**
   * Retorna uma String com todos os servicos da TrainCompany.
   *
   * @return String com todos os servicos da TrainCompany.
   */
  protected String showAllServices(){
    Iterator iterator;
    iterator = _listServ.iterator();
    StringBuffer str = new StringBuffer();

    while(iterator.hasNext()){
      str.append(iterator.next().toString());
    }
    String s = str.toString();
    return s;
  }

  /**
   * Retorna uma String com um servico com um certo identificador.
   *
   * @param id Identificador do servico a procurar.
   *
   * @return String com um certo servico.
   */
  protected String showServiceByNumber(int id) throws NoSuchServiceIdException{
    
    Iterator<Service> iterator;
    iterator = _listServ.iterator();
    StringBuffer str = new StringBuffer();

    
    while(iterator.hasNext()){
      Service serv = iterator.next();
      if (serv.getId() == id){
        str.append(serv.toString());
        break;
      }
    }
    String s = str.toString();
    if(s.equals(""))
      throw new NoSuchServiceIdException(id);

    return s;
  }

  /**
   * Retorna uma String com todos os servicos que tenham comeco numa certa estacao.
   *
   * @param name Nome da estacao de comeco a procurar.
   *
   * @return String com todos os servicos com comeco nessa estacao.
   */
  protected String showServiceFromDepartingStation(String name) throws NoSuchStationNameException{
    TrainStation station = new TrainStation(name);
      if(checkStation(station) == null)
        throw new NoSuchStationNameException(name);

    SortServicesByDepartingStation sorter = new SortServicesByDepartingStation(name);
    return sorter.template(_listServ);
  }

  /**
   * Retorna uma String com todos os servicos que tenham terminado numa certa estacao.
   *
   * @param name Nome da estacao de comeco a procurar.
   *
   * @return String com todos os servicos com comeco nessa estacao.
   */
  protected String showServiceFromArrivingStation(String name) throws NoSuchStationNameException{
    TrainStation station = new TrainStation(name);
      if(checkStation(station) == null)
        throw new NoSuchStationNameException(name);

    SortServicesByArrivingStation sorter = new SortServicesByArrivingStation(name);
    return sorter.template(_listServ);
  }

  /**
  * Retorna um Serviço com um determinado identificador.
  *
  * @param id Identificador do Servico.
  *
  * @return Servico com identificador id, ou null caso nao exista.
  */
  protected Service findServ(int id){
    Iterator<Service> iterator;
    iterator = _listServ.iterator();

    while(iterator.hasNext()){
      Service serv = iterator.next();
      if(serv.getId() == id)
        return serv;
    }
    return null;
  }

  /**
   * Retorna uma String contento todos os passageiros registados na TrainCompany.
   *
   * @return String com os passageiros.
   */
  protected String showAllPassengers(){
    StringBuffer str = new StringBuffer();

    for(Passenger e : _listPass){
      str.append(e.toString());
    }

    String s = str.toString();
    return s;
  }

  /**
   * Retorna uma String contento toda a informacao de um passageiro com um certo identificador.
   *
   * @param id Identificador unico do passageiro a procurar.
   * 
   * @return String com o passageiro de identificador id.
   */
  protected String showPassengerByNumber(int id) throws NoSuchPassengerIdException{
    StringBuffer str = new StringBuffer();

    for(Passenger e : _listPass){
      if(e.getId() == id){
        str.append(e.toString());
        break;
      }
    }
    String s = str.toString();
    if(s.equals(""))
      throw new NoSuchPassengerIdException(id);

    return s;
  }

  /**
   * Regista um passageiro na TrainCompany.
   *
   * @param passengerName Nome do passageiro a adicionar.
   */
  protected void registerPassenger(String passengerName) {  
      int id = _listPass.size();
      Passenger pass = new Passenger(passengerName,id);
      _listPass.add(pass);
  }

  /**
   * Altera o nome de um passageiro na TrainCompany.
   *
   * @param id Identificador do passageiro a alterar.
   * @param name Novo nome do passageiro a alterar.
   */
  protected void changePassengerName(int id, String name) throws NoSuchPassengerIdException {
    if(_listPass.size() <= id)
      throw new NoSuchPassengerIdException(id);
    
    Passenger pass;
    pass = _listPass.get(id);
    _listPass.remove(id);
    pass.changeName(name);
    _listPass.add(id,pass); 
  }

  /**
  * Retorna uma string contendo todos os Itinerarios.
  *
  * @return String com todos os Itinerarios da TrainCompany.
  */
  protected String showAllItineraries(){
    StringBuffer str = new StringBuffer();
    for(Passenger e : _listPass){
      str.append(e.toStringItineraries());
    }
    String s = str.toString();
    return s;
  }

  /**
  * Mostra os Itinerarios de um Passageiro.
  *
  * @param id Identificador do passageiro.
  *
  * @return String com todos os Itinerarios do Passageiro.
  */
  protected String showItineraryByPassenger(int id) throws NoSuchPassengerIdException{
    for(Passenger e: _listPass)
      if (e.getId() ==id){
        if(e.getItineraries().isEmpty())
          return "";
        return e.toStringItineraries();
      }
    throw new NoSuchPassengerIdException(id);
  }

  /**
  * Associa um itinerario a um passageiro.
  *
  * @param index Identificador do passageiro.
  * @param itinerary Itinerario a associar.
  */
  protected void setItineraryToPassenger(int index, Itinerary itinerary){
    _listPass.get(index).addItinerary(itinerary);
  }

  /**
  * Procura recursivamente os Itinerarios com começo numa estação e termino em outra depois de uma certa hora.
  *
  * @param passengerId Identificador do passageiro que procura os Itinerarios.
  * @param deparStation Estação de partida.
  * @param arrivStation Estação de chegada.
  * @param departureDate Data de partida.
  * @param departureTime Tempo de Partida.
  *
  * @return String com os Itinerarios possiveis com partida em diferentes serviços. 
  */
  protected String searchItineraries(int passengerId, String deparStation, String arrivStation, String departureDate, String departureTime) throws NoSuchPassengerIdException, NoSuchStationNameException, BadDateSpecificationException, BadTimeSpecificationException{
    
    LocalDate date = parseLocalDate(departureDate);
    LocalTime time = parseLocalTime(departureTime);
    TrainStation departureStation = _listStations.get(deparStation);
    TrainStation arrivalStation = _listStations.get(arrivStation);

    if(departureStation == null)
      throw new NoSuchStationNameException(deparStation);
    
    if(arrivalStation == null)
      throw new NoSuchStationNameException(arrivStation);
    
    if(passengerId >= _listPass.size())
      throw new NoSuchPassengerIdException(passengerId);

    StringBuffer str = new StringBuffer();
    int i = 1;

    Iterator<TrainStop> iterator = departureStation.getListTrainStops();
    
    while(iterator.hasNext()){
      TrainStop e = iterator.next();
      if(time.isBefore(e.getTime())) {
        ArrayList<Segment> iti = new ArrayList<>();
        Itinerary mostEficient = new Itinerary(date);

        ArrayList<Integer> forbiddenServices = new ArrayList<>();
        ArrayList<TrainStation> forbiddenStations = new ArrayList<>();
        Service serv = e.getService();
        forbiddenServices.add(serv.getId());

        Itinerary aux = new Itinerary(date);
        aux = serv.findItineraryBetween(e, arrivalStation, forbiddenServices, forbiddenStations, iti, mostEficient);
        if(aux != null)
          mostEficient = aux;
        if(!mostEficient.getSegments().isEmpty()){
          _listItinerary.add(mostEficient);
          str.append(mostEficient.toString(i++));
        }
      }
    }
    
    String s = str.toString();
    return s;
    
  }

  /**
  * Associa o Itinerario escolhido pelo utilizador ao Passageiro.
  *
  * @param passengerId Identificador do Passageiro a associar o itinerario.
  * @param choice Numero do Itinerario escolhido.
  */
  protected void commitItinerary(int passengerId, int choice) throws NoSuchItineraryChoiceException{
    if(choice == 0){
      _listItinerary.clear();
      return;
    }
    Iterator<Itinerary> iterator = _listItinerary.iterator();
    int i = 0;
    while(iterator.hasNext()){
      Itinerary iti = iterator.next();
      if(i++ == choice-1){
        _listPass.get(passengerId).addItinerary(iti);
        _listItinerary.clear();
        return;
      }
    }
    _listItinerary.clear();
    throw new NoSuchItineraryChoiceException(passengerId,choice);
  }

  /**
  * Transforma uma String em um LocalDate.
  *
  * @param departDate String com a data.
  *
  * @return Data LocalDate.
  */
  protected LocalDate parseLocalDate(String departDate) throws BadDateSpecificationException{
    try{
      LocalDate date = LocalDate.parse(departDate);
      return date;
    }catch(Exception e){
      throw new BadDateSpecificationException(departDate);
    }
  }

  /**
  * Transforma uma String em um LocalTime.
  *
  * @param departTime String com o tempo.
  *
  * @return tempo LocalTime.
  */
  protected LocalTime parseLocalTime(String departTime) throws BadTimeSpecificationException{
    try{
      LocalTime time = LocalTime.parse(departTime);
      return time;
    }catch(Exception e){
      throw new BadTimeSpecificationException(departTime);
    }
  }

}
