package mmt.core;

import java.util.ArrayList;
import java.util.Locale;
import java.time.LocalTime; 
import mmt.core.TrainStop;
import static java.time.temporal.ChronoUnit.MINUTES;
import java.util.Iterator;

/**
*	Classe que representa um servico.
*
* @author Grupo 34
* @version Intermedia
*/

public class Service extends TicketOffice implements java.io.Serializable{

  /** Identificador unico de um servico*/
	private int _id;								

  /** Custo base de um servico*/
	private double _baseCost; 		

  /** Lista das estacoes de um servico*/
	private ArrayList<TrainStop> _stations;

  /** Tempo de viagem do Serviço*/
  private long _totalTime;

  /**
  * Cria um servico.
  *
  * @param id Identificador unico do servico.
  * @param baseCost Custo base de um servico.
  */
	protected Service(int id, double baseCost){
		_id = id;
    _baseCost = baseCost;

		_stations = new ArrayList<>();
	}

  /**
  * Adiciona uma paragem de comboios (TrainStop) a um servico.
  *
  * @param station Estacao a ser adicionada ao servico.
  */
	protected void addTrainStop(TrainStop station){
		_stations.add(station);
	}

  /**
  * Obtem o identificador unico do servico.
  *
  * @return Identificador unico do servico.
  */
	protected int getId(){
		return _id;
	}

  /**
   * Retorna o custo do Serviço.
   *
   * @return Custo do Serviço.
   */
  protected double getCost(){
    return _baseCost;
  }

  /**
   * Calcula o tempo de viagem total do Serviço.
   */
  protected void setTotalTime(){
    LocalTime departure = _stations.get(0).getTime();
    LocalTime arrival = _stations.get(_stations.size()-1).getTime();

    _totalTime = MINUTES.between(departure, arrival);
  }

  /**
   * Retorna o tempo total do Serviço.
   *
   * @return Tempo total do Serviço.
   */
  protected long getTotalTime(){
    return _totalTime;
  }

  /**
  * Retorna uma String o conteudo de um servico.
  *
  * @return Uma String com o conteudo do servico.
  */
	public String toString(){
		StringBuffer str = new StringBuffer();

		str.append("Serviço #" + _id + " @ " + String.format(Locale.US,"%.2f", _baseCost)  + "\n");

		for(TrainStop e : _stations)
			str.append("" + e.getTime().toString() + " " + e.getStationName() + "\n");

    String s = str.toString();
		return s;
	}

  /**
   * Retorna uma String com um segmento de um serviço com inicio numa estação e termino em outra.
   *
   * @param departure Estação de partida.
   * @param arrival Estação de chegada.
   * @param cost Custo do segmento.
   *
   * @param String com o segmento, ou null caso o segmento não exista.
   */
  public String toString(TrainStop departure, TrainStop arrival, double cost){
    StringBuffer str = new StringBuffer();  
    boolean flag = false;

    str.append("Serviço #" + _id + " @ " + String.format(Locale.US,"%.2f", cost)  + "\n");

    for(TrainStop e : _stations){
      if (e.equals(departure))
        flag = true;

      if(flag)  
        str.append("" + e.getTime().toString() + " " + e.getStationName() + "\n");

      if(e.equals(arrival)){
        String s = str.toString();
        return s;
      }
    }
    
    return null;
  }

  /** 
   * Retorna o nome da estacao de um servico pelo seu indice (index).
   *
   * @param index Indice da estacao que se quer obter o nome.
   * @return Nome da primeira estacao de um servico.
   */
  protected String getStationName(int index){
    return _stations.get(index).getStationName();
  }

  /** 
   * Retorna as horas e minutos a que a estacao de um servico sai pelo seu indice (index).
   *
   * @param index Indice da estacao que se quer obter a hora.
   * @return Hora e minutos de saida da primeira estacao de um servico.
   */
  protected LocalTime getStationTime(int index){
    return _stations.get(index).getTime();
  }

  /**
   * Procura no Serviço uma paragem numa certa estação.
   *
   * @param name Nome da estação a ser procurada.
   *
   * @return TrainStop da estação, ou null caso não exista.
   */
  protected TrainStop findStation(String name){
    for(TrainStop e : _stations)
      if(e.getStationName().equals(name))
        return e;
    return null;
  }

  /**
   * Retorna o tamanho da lista de TrainStops do Serviço.
   *
   * @return Tamanho da lista de TrainStops.
   */
  protected int sizeTrainStop(){
    return _stations.size();
  }

  /**
   * Procura se uma paragem existe no Serviço depois de uma certa hora.
   *
   * @param station Estação da paragem.
   * @param time Hora minima da paragem.
   *
   * @return TrainStop da paragem, ou null caso não exista.
   */
  protected TrainStop sameService(TrainStation station, LocalTime time){
    for(TrainStop e : _stations)
      if(e.getStation().equals(station) && e.getTime().isAfter(time))
        return e;
    
    return null;
  }

  /**
  * Procura se existe um caminho entre duas estações.
  *
  * @param departure Paragem de said, arrival - paragem de destino, serv - serviço a percorrer, itinerary - itinerario a ser encontrado
  * @param arrival Estação de chegada.
  * @param forbiddenServices Lista de serviços que não podem ser revisitados.
  * @param forbiddenStations Lista de estações que não podem ser revisitadas.
  * @param current Lista de segmentos do Itinerario atual.
  * @param mostEfficient Itinerario mais eficiente encontrado.
  *
  * @return Itinerario mais eficiente com inicio na TrainStop.
  */
  protected Itinerary findItineraryBetween( TrainStop departure, TrainStation arrival, ArrayList<Integer> forbiddenServices, ArrayList<TrainStation> forbiddenStations, ArrayList<Segment> current, Itinerary mostEficient){
    
    TrainStop auxStop = sameService(arrival, departure.getTime());

    if (auxStop != null ){
      current.add(new Segment(this,departure, auxStop));
      
      if(mostEficient.getSegments().isEmpty()){        
        Itinerary iter = new Itinerary(mostEficient.getDate());
        for(Segment e : current)
          iter.addSegm(e);
        mostEficient = iter;
      }

      else{
        if(current.get(current.size()-1).getArrivingStation().getTime().isBefore(mostEficient.getArrivingStation().getTime())){

          Itinerary iter = new Itinerary(mostEficient.getDate());
          for(Segment e : current)
            iter.addSegm(e);
          mostEficient = iter;
        }
      }
      return mostEficient;
    }

    else{
     
      Iterator<TrainStop> iteratorStations = _stations.iterator();

      while(iteratorStations.hasNext()){
        if(iteratorStations.next().equals(departure))
          break;
      }
      Segment segm = new Segment(this,departure,departure);
      current.add(segm);
      
      while(iteratorStations.hasNext()){//estamos na trainStop seguinte
        int contador = 0;
          TrainStop nextStop = iteratorStations.next();
          segm.setArrivingStation(nextStop);


          if(forbiddenStations.contains(nextStop.getStation()) || timeDiferenceBetween(current, mostEficient)){
            current.remove(current.size()-1);
            break;
          }
          contador++;       
          forbiddenStations.add(nextStop.getStation());
          Iterator<TrainStop> trainStops = nextStop.getStation().getListTrainStops();

          while(trainStops.hasNext()){
            TrainStop e = trainStops.next();
            
            Service serv  = e.getService();
            if(departure.getTime().isBefore(e.getTime()) && ! forbiddenServices.contains(serv.getId())){
              forbiddenServices.add(serv.getId());
              Itinerary aux = serv.findItineraryBetween(e, arrival, forbiddenServices, forbiddenStations, current, mostEficient);
              if(aux!= null){
                mostEficient = aux;
                current.remove(current.size()-1);
                
                break;
              }
              //remover Segmento
              while(contador-- != 0)
                forbiddenStations.remove(forbiddenStations.size()-1);
              
              current.remove(current.size()-1);
            }
          }
      }
    
    if(!mostEficient.getSegments().isEmpty() && departure.equals(mostEficient.getSegments().get(0).getDepartingStation())){
      
      ArrayList<Segment> segs= mostEficient.getSegments();
      int i = segs.size()-1;
      
      while(i-- != 0){
        Segment seg = segs.get(i);
        seg.setArrivingStation(seg.getService().findStation(segs.get(i+1).getDepartingStation().getStationName()));
      }

      return mostEficient;
    }
    
    return null;
    }
    
  }

  /**
   * Verifica se o tempo corrente da lista de segmentos é menor que o tempo de viagem do Itinerario mais eficiente.
   *
   * @param segs Lista de segmentos.
   * @param iti Itinerario.
   *
   * @return True caso o tempo do percurso de segs for superior ao do Itinerario (logo nunca sera mais eficiente), false caso contrario ou caso o Itinerario nao contenha segmentos.
   */
  protected boolean timeDiferenceBetween(ArrayList<Segment> segs, Itinerary iti){
    if(!iti.getSegments().isEmpty())
      return MINUTES.between(segs.get(0).getDepartingStation().getTime(), segs.get(segs.size()-1).getArrivingStation().getTime()) >= iti.getTravelTime();
    return false;
  }

}


