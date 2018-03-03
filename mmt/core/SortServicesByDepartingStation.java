package mmt.core;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.function.Predicate;
import java.util.List;


/**
 * Compara servicos relativamente a estacao de partida.
 */
class ComparatorServiceByDepartureTime implements Comparator<Service>, java.io.Serializable{
  /**
  * Compara dois servicos pela estacao de partida.
  *
  * @param f1 Servico a comparar.
  * @param f2 Servico a comparar.
  */
  public int compare(Service f1, Service f2) {
    return f1.getStationTime(0).compareTo(f2.getStationTime(0));
  }
}

/**
 * Classe de ordenar serviços por estação de partida.
 *
 * @author Grupo 34.
 * @version Final.
 */
public class SortServicesByDepartingStation extends SortServicesBy{

/** Nome da estação a procurar*/
  private String _name;

 /**
   * Cria uma nova instancia de ordenação.
   *
   * @param name Nome da estação a procurar.
   */
  public SortServicesByDepartingStation(String name){
    _name = name;
  }

  /**
   * Filtra as estações que não contêm a estação de procura.
   *
   * @param list Arvore de Serviços.
   *
   * @return Lista filtrada.
   */
	protected List<Service> filter(TreeSet<Service> list){
		return list.stream().filter(p -> p.getStationName(0).equals(_name)).collect(Collectors.<Service>toList());
	}

  /**
   * Ordena a lista por hora da estação de chegada.
   *
   * @param array Lista de serviços
   *
   * @return Lista de serviços ordenada.
   */
	protected List<Service> sort(List<Service> array){
		array.sort(new ComparatorServiceByDepartureTime());
    return array;
	}

}