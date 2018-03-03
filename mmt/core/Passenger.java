package mmt.core;

import java.time.LocalTime;
import mmt.core.Itinerary;
import java.util.Locale;

import java.util.TreeSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ArrayList;
/**
* Classe Passenger
*
* @author Grupo 34
* @version Intermedia
*/

class ComparatorByDate implements Comparator<Itinerary>, java.io.Serializable{
  /**
  * Compara dois servicos pela estacao de partida.
  *
  * @param f1 Servico a comparar.
  * @param f2 Servico a comparar.
  */
  public int compare(Itinerary f1,Itinerary f2) {
  	
    if(f1.getDate().compareTo(f2.getDate()) == 0){
    	if(f1.getDepartingTime().compareTo(f2.getDepartingTime()) == 0)
    		return 1;
    	return f1.getDepartingTime().compareTo(f2.getDepartingTime());
    }
    return f1.getDate().compareTo(f2.getDate());
  }
}

public class Passenger extends TicketOffice implements java.io.Serializable{
	/** ID do passageiro*/
	private int _id;

	/** Nome do passageiro*/
	private String _name;
	
	/** Custo total dos itinerarios*/
	private double _totalCost; 

	private double _costOfLast10;

	/** Tempo total de travesia de todos os itinerarios em minutos*/
	private long _totalTravelTime;

	/** Categoria do passagerio*/
	private Category _category;

	/** Itinerarios do passageiro*/
  	private TreeSet<Itinerary> _listIti;

  	/** Lista com os custos dos 10 ultimos Itinerarios dos Passageiros*/
  	private ArrayList<Double> _last10Iti;

	/** 
	 * Cria um passageiro.
	 *
	 * @param namePassenger Nome do passageiro.
	 * @param id Identificador unico do passageiro.
	 */
	protected Passenger(String namePassenger, int id){
		_id = id;
		_name = namePassenger;
		_totalCost=0;
		_totalTravelTime = 0;
		_costOfLast10 = 0;

		_category = new Normal();

		_listIti = new TreeSet<>(new ComparatorByDate()); 
		_last10Iti = new ArrayList<>();
	}

	/** 
	 * Altera o nome de um passageiro.
	 *
	 *@param name Nome novo do passageiro.
	 */
	protected void changeName(String name){
		_name = name;
	}
	
	/** 
	 * Retorna uma string com o conteudo de um passageiro.
	 *
	 * @return Uma string com o conteudo do passageiro.
	 */
	public String toString(){
		String str="";
		str = _id + "|" + _name + "|" + _category.getType() + "|" + _listIti.size() + "|" + String.format(Locale.US,"%.2f", _totalCost)  + "|" + toLocalTime(_totalTravelTime) + "\n";
		return str;
	}

	/**
	 * Transforma um numero de minutos em numero de horas e minutos.
	 *
	 * @param min Minutos a formatar.
	 *
	 * @return String no formato correto.
	 */
	protected String toLocalTime(long min){
		String str = "";
		if(min/60 < 10)
		 	str += "0" + min/60;
		else
			str += "" + min/60;
		if(min%60 < 10)
			str +=  ":0" + min%60;
		else
			str +=  ":" + min%60;
		return str;
	}

	/** 
	 * Obtem o identificador de um passageiro.
	 *
	 * @return ID do passageiro.
	 */
	protected int getId(){
		return _id;
	}

	/** 
	 * Obtem o nome de um passageiro.
	 *
	 * @return Nome do passageiro.
	 */
	protected String getName(){
		return _name;
	}

	/**
	 * Associa um Itinerario ao Passageiro.
	 *
	 * @param itinerary Itinerario a associar.
	 */
	protected void addItinerary(Itinerary itinerary){
		_listIti.add(itinerary);

		_totalCost += (itinerary.getCost()*(1-(_category.getDesconto()/100)));

		_totalTravelTime += itinerary.getTravelTime();
		_costOfLast10 += itinerary.getCost();

		if(_last10Iti.size() == 10){
			_costOfLast10 -= _last10Iti.get(0);
			_last10Iti.remove(0);
		}
		_last10Iti.add(itinerary.getCost());
		update();
	}	

	/**
	 * Atualiza a categoria de um Passageiro.
	 */
	protected void update(){
		if(_costOfLast10 > 2500){
			if (! _category.getType().equals("ESPECIAL")){
				_category = new Special();
			}
		}

		else if(_costOfLast10 > 250){
				if(! _category.getType().equals("FREQUENTE")){
					_category = new Frequent();
				}
			}
			else if(! _category.getType().equals("NORMAL"))
					_category = new Normal();
	}

	/**
	 * Retorna os Itinerarios do Passageiro.
	 *
	 * @return Itinerarios do Passageiro.
	 */
	protected TreeSet<Itinerary> getItineraries(){
		return _listIti;
	}

	/** 
	 * Retorna os Itinerarios do Passageiro em forma de String.
	 *
	 * @return String com os Itinerarios do Passageiro.
	 */
	public String toStringItineraries(){
		if(_listIti.isEmpty())
			return "";
		int i = 1;
		Iterator<Itinerary> iterator;
	    iterator = _listIti.iterator();
	    StringBuffer str = new StringBuffer();
	    str.append("== Passageiro " + getId() + ": " + getName() + " ==\n");
	      
	    while(iterator.hasNext()){
	    	Itinerary aux = iterator.next();
	        str.append(aux.toString(i++));
		}

		String s = str.toString();
    	return s;
	}

}