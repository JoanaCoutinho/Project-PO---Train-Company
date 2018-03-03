package mmt.core;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import static java.time.temporal.ChronoUnit.MINUTES;
import java.time.LocalTime;

/**
 *  Classe Itinerary.
 *
 *  @author Grupo 34
 *  @version Intermedio
 */
public class Itinerary extends TicketOffice implements java.io.Serializable{

	/** Data do itinerario */
	private LocalDate _date;

	/** Custo do itinerario */
	private double _cost; 

	/** Tempo total de percorrer o Itinerario*/
	private long _travelTime;

	/** Lista de segmentos do Itinerario*/
	private ArrayList<Segment> _listSegmServ;
 
	/**
	* Cria um Itinerario com uma certa data.
	*
	* @param date Data do itinerario.
	*	
	*/
	public Itinerary(LocalDate date){
		_date = date;
		_travelTime = 0;
		_cost = 0;
		_listSegmServ = new ArrayList<>();
	}

	/**
	 * Adiciona um Segmento ao Itinerario.
	 *
	 * @param segm Segmento a adicionar
	 */
	protected void addSegm(Segment segm){
		_listSegmServ.add(segm);
		_cost += segm.getCost();
		_travelTime = MINUTES.between(getDepartingStation().getTime(),getArrivingStation().getTime());
	}

	/**
	 * Retorna a data do Itinerario.
	 *
	 * @return Data do Itinerario.
	 */
	protected LocalDate getDate(){
		return _date;
	}

	/**
	 * Retorna o custo do Itinerario.
	 *
	 * @return Custo do Itinerario.
	 */
	protected double getCost(){
		return _cost;
	}

	/**
	 * Retorna a lista de Segmentos do Itinerario.
	 *
	 * @return Lista de Segmentos.
	 */
	protected ArrayList<Segment> getSegments(){
		return _listSegmServ;
	}

	/**
	 * Retorna o tempo de viagem do Itinerario.
	 *
	 * @return Tempo do Itinerario.
	 */
	protected long getTravelTime(){
		return _travelTime;
	}

	/**
	 * Retorna a estação de partida do Itinerario.
	 *
	 * @return Estação de partida.
	 */
	protected TrainStop getDepartingStation(){
		return _listSegmServ.get(0).getDepartingStation();
	}

	/**
	 * Retorna a estação de chegada do Itinerario.
	 *
	 * @return Estação de chegada do Itinerario.
	 */
	protected TrainStop getArrivingStation(){
		return _listSegmServ.get(_listSegmServ.size()-1).getArrivingStation();
	}

	/**
	 * Retorna a hora de partida do Itinerario.
	 *
	 * @return Hora de partida do itinerario.
	 */
	protected LocalTime getDepartingTime(){
		return getDepartingStation().getTime();
	}

	/**
	 * Retorna uma string com a informação do Itinerario.
	 *
	 * @param i Numero do Itinerario.
	 *
	 * @return String com o Itinerario.
	 */
	protected String toString(int i){
		StringBuffer str = new StringBuffer();
		str.append("\nItinerário " + i + " para " + _date + " @ " + String.format(Locale.US,"%.2f", _cost) + "\n");
	        
	    for(Segment o : _listSegmServ){
	      	Service serv = o.getService();
	        str.append(serv.toString(o.getDepartingStation(), o.getArrivingStation(), o.getCost()));
	    }
	    String s = str.toString();
	    return s;
	}


}