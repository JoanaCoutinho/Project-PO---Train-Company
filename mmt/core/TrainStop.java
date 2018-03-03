package mmt.core;

import java.time.LocalTime;
import mmt.core.TrainStation;

/**
 * Classe que representa uma paragem de uma estacao.
 *
 * @author Grupo 34
 * @version Intermedio
 */
public class TrainStop implements java.io.Serializable{

	/** Horas/minutos da paragem do comboio */
	private LocalTime _time;
	
	/** Estacao associada a paragem*/
	private TrainStation _station;

	/** Serviço associado a esta TrainStop*/
	private Service _service;

	/**
    * Cria uma paragem de comboio.
    *
    * @param serv Serviço associado.
    * @param time Hora/minuto associado a paragem.
    * @param station Estacao associada a paragem.
    */
	protected TrainStop(Service serv, LocalTime time, TrainStation station){
		_service=serv;
		_time=time;
		_station=station;
	}

	/**
    * Retorna o nome da estacao associada a paragem.
    *
    * @return Nome da estacao.
    */
	protected String getStationName(){
		return _station.getName();
	}

   /** 
    * Retorna a Estação desta TrainStop
    *
    * @return Estação da Trainstop.
    */
	protected TrainStation getStation(){
		return _station;
	}

	/**
    * Retorna a hora local associada a paragem.
    *
    * @return Hora da paragem.
    */
	protected LocalTime getTime(){
		return _time;
	}

	/**
	 * Verifica se a estação de uma TrainStop é a mesma que de outra TrainStop.
	 *
	 * @param trainStop Paragem a comparar
	 *
	 * @return true caso tenha a mesma estação, false caso contrario.
	 */
	protected boolean equals(TrainStop trainStop){
		return this.getStationName().equals(trainStop.getStationName());
	}

	/**
	 * Retorna o serviço desta TrainStop.
	 *
	 * @return Serviço da TrainStop.
	 */
	protected Service getService(){
		return _service;
	}
}