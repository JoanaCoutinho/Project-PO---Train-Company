package mmt.core;

import java.time.LocalTime;
import java.util.TreeSet;
import java.util.Iterator; 
import java.util.Comparator;


class ComparatorTrainStopsByTime implements Comparator<TrainStop>, java.io.Serializable{
  /**
  * Compara dois servicos pela estacao de partida.
  *
  * @param f1 Servico a comparar.
  * @param f2 Servico a comparar.
  */
  public int compare(TrainStop f1, TrainStop f2) {
    return f1.getTime().compareTo(f2.getTime());
  }
}

/** 
 * Classe que representa uma estacao de comboios.
 *
 * @author Grupo 34
 * @version Intermedia
 */
public class TrainStation implements java.io.Serializable{

	/** Nome de uma estacao*/
	private String _name;

	/** Lista de Paragens nesta Estação*/
	private TreeSet<TrainStop> _listStops = new TreeSet<>(new ComparatorTrainStopsByTime());

	/**
	 *	Cria uma estacao.
	 *
	 * @param name Nome da TrainStation.
	 */
	protected TrainStation(String name){
		_name = name;
	}

	
	/**
    * Retorna o nome da TrainStation.
    *
    * @return Nome da TrainStation.
    */
	protected String getName(){
		return _name;
	}

   /** 
    * Adiciona uma TrainStop a lista de TrainStops da Estação.
    *
    * @param stop TrainStop a adicionar.
    */
	protected void addStop(TrainStop stop){
		_listStops.add(stop);
	}

	/**
	 * Retorna um iterador da lista de TrainStops da Estação.
	 *
	 * @return Iterador da lista de TrainStops.
	 */
	protected Iterator<TrainStop> getListTrainStops(){
		Iterator<TrainStop> iterator = _listStops.iterator();
		return iterator;
	}
}