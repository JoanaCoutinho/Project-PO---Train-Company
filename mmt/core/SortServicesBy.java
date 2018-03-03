package mmt.core;

import java.util.Iterator;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.List;



/**
 * Ordena Serviços por um Template especifico.
 *
 * @author Grupo 34.
 * @version Final.
 */
public abstract class SortServicesBy implements java.io.Serializable{
	/**
	 * Ordena Serviços por um Template.
	 *
	 * @param listServ Lista de Serviços.
	 * @param predicate Predicado para especificar uma caracteristica.
	 * @param comparator Comparador para ordenar por uma certa ordem.
	 *
	 * @return String com os Serviços que contenham as caracteristicas especificas do predicado ordenados pelo comparador.
	 */
	protected String template(TreeSet<Service> listServ){
		
	    List<Service> array = new ArrayList<>();
	    
	    array = filter(listServ);
	    array = sort(array);

	    StringBuffer str = new StringBuffer();
	    for(Service e : array){
	       	str.append(e.toString());
	    }
	    String s = str.toString();
	    return s;
	}

	protected abstract List<Service> filter(TreeSet<Service> list);

	protected abstract List<Service> sort(List<Service> list);
}