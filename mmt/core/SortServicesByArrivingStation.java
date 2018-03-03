package mmt.core;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.List;


/**
 * Compara servicos relativamente a estacao de partida.
 */
class ComparatorServiceByArrivalTime implements Comparator<Service>, java.io.Serializable{
  /**
  * Compara dois servicos pela estacao de partida.
  *
  * @param f1 Servico a comparar.
  * @param f2 Servico a comparar.
  */
  public int compare(Service f1, Service f2) {
    return f1.getStationTime(f1.sizeTrainStop()-1).compareTo(f2.getStationTime(f2.sizeTrainStop()-1));
  }
}

/**
 * Classe de ordenar serviços por estação de chegada.
 *
 * @author Grupo 34.
 * @version Final.
 */
public class SortServicesByArrivingStation extends SortServicesBy{

  /** Nome da estação a procurar*/
  private String _name;

  /**
   * Cria uma nova instancia de ordenação.
   *
   * @param name Nome da estação a procurar.
   */
  public SortServicesByArrivingStation(String name){
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
		return list.stream().filter(p -> p.getStationName(p.sizeTrainStop()-1).equals(_name)).collect(Collectors.<Service>toList());
	}

  /**
   * Ordena a lista por hora da estação de chegada.
   *
   * @param array Lista de serviços
   *
   * @return Lista de serviços ordenada.
   */
	protected List<Service> sort(List<Service> array){
		array.sort(new ComparatorServiceByArrivalTime());
    return array;
	}

}