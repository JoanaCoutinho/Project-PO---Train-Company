package mmt.core;

import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.MINUTES;

/**
* Segmentos de Serviços.	 
*/
public class Segment implements java.io.Serializable{

	/** Custo do Segmento*/
	private double _cost;

	/** Estação de Partida*/
	private TrainStop _departingStation;
	
	/** Estação de Chegada*/
	private TrainStop _arrivingStation;
	
	/** Serviço do Segmento*/
	private Service _service;
	
	/** Tempo de viagem em minutos*/
	private long _travelTime;	

	/**
	 * Cria um novo Segmento.
	 *
	 * @param serv Serviço do Segmento.
	 * @param departure Estação de Partida.
	 * @param arrival Estação de Chegada.
	 */
	public Segment(Service serv, TrainStop departure, TrainStop arrival){
		_service = serv;
		_departingStation = departure;
		_arrivingStation = arrival;

		_travelTime = MINUTES.between(departure.getTime(), arrival.getTime());
		
		_cost = serv.getCost()*_travelTime/serv.getTotalTime();
	}

	/**
	 * Retorna o Serviço do Segmento.
	 *
	 * @return Serviço do Segmento.
	 */	
	protected Service getService(){
		return _service;
	}

	/**
	 * Retorna o custo do Segmento.
	 *
	 * @return Custo do segmento.
	 */
	protected double getCost(){
		return _cost;
	}

	/**
	 * Retorna a estação de Partida.
	 *
	 * @return Estação de Partida.
	 */
	protected TrainStop getDepartingStation(){
		return _departingStation;
	}

	/**
	 * Retorna a estação de Chegada.
	 *
	 * @return Estação de chegada.
	 */
	protected TrainStop getArrivingStation(){
		return _arrivingStation;
	}

	/**
	 * Altera a estação de chegada do Segmento.
	 *
	 * @param stop Nova paragem de chegada.
	 */
	protected void setArrivingStation(TrainStop stop){
		_arrivingStation = stop;
		_travelTime = MINUTES.between(_departingStation.getTime(), _arrivingStation.getTime());
		_cost = _service.getCost()*_travelTime/_service.getTotalTime();
	}

	/**
	 * Retorna o tempo de viagem do Segmento.
	 *
	 * @return Tempo de viagem.
	 */
	protected long getTravelTime(){
		return _travelTime;
	}
}