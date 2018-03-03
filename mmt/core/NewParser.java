package mmt.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.time.LocalTime;
import java.time.LocalDate;

import mmt.core.exceptions.ImportFileException;
import mmt.core.Service;
import mmt.core.Passenger;
import mmt.core.Itinerary;
import mmt.core.TrainCompany;
import mmt.core.TrainStation;
import mmt.core.TrainStop;

/** 
 *  Classe que fara Parse do ficheiro.
 *  Cria a TrainCompany com o conteudo do ficheiro.
 *
 *  @author Grupo 34
 *  @version Intermedio
 */

public class NewParser {

  /** TrainCompany a criar e a inserir os dados.*/
  private TrainCompany _trainCompany = new TrainCompany();

  /**
   * Interpreta o ficheiro e cria uma TrainCompany com os seus dados.
   *
   * @param fileName Nome do ficheiro a ler.
   * @return Uma TrainCompany completa com os dados de filename.
   */
  protected TrainCompany parseFile(String fileName) throws ImportFileException {

    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
      String line;
      while ((line = reader.readLine()) != null) {
        parseLine(line);
      }
    } catch (IOException ioe) {

      throw new ImportFileException(ioe);
    }

    return _trainCompany;
  }

  /** 
   * Le e interpreta uma linha do ficheiro, transformando-a numa classe Passenger, Service ou Itinerary.
   *
   * @param line Linha a interpretar.
   */
  private void parseLine(String line) throws ImportFileException {
    String[] components = line.split("\\|");

    switch (components[0]) {
      case "PASSENGER":
        parsePassenger(components);
        break;

      case "SERVICE":
        parseService(components);
        break;

      case "ITINERARY":
        parseItinerary(components);
        break;

     default:
       throw new ImportFileException("invalid type of line: " + components[0]);
    }
  }

  /** 
   * Torna uma linha do ficheiro numa classe do tipo Passenger.
   *
   * @param components Componentes de uma linha a tornar numa classe Passenger. 
   */
  private void parsePassenger(String[] components) throws ImportFileException {
    if (components.length != 2)
      throw new ImportFileException("invalid number of arguments in passenger line: " + components.length);

    String passengerName = components[1];
    _trainCompany.registerPassenger(passengerName);
  }

  /** 
   * Torna uma linha do ficheiro numa classe do tipo Service.
   *
   * @param components Componentes de uma linha a tornar numa classe Service.
   */
  private void parseService(String[] components) {
    double cost = Double.parseDouble(components[2]);
    int serviceId = Integer.parseInt(components[1]);
    Service serv = new Service(serviceId,cost);

    for (int i = 3; i < components.length; i += 2) {
      String time = components[i];
      String stationName = components[i + 1];
      LocalTime ltime = LocalTime.parse(time);

      TrainStation station = new TrainStation(stationName);
      TrainStation stationAux = _trainCompany.checkStation(station);
      if(stationAux == null)
          _trainCompany.addStation(station); 
      else
        station = stationAux;
      TrainStop trainStop = new TrainStop(serv,ltime,station);
      serv.addTrainStop(trainStop);
      station.addStop(trainStop);
    }
    serv.setTotalTime();
    _trainCompany.addService(serv);
  }

  /** 
   * Torna uma linha do ficheiro numa classe do tipo Itinerary.
   *
   * @param components Componentes de uma linha a tornar numa classe Itinerary.
   */
  private void parseItinerary(String[] components) throws ImportFileException {
    if (components.length < 4)
      throw new ImportFileException("Invalid number of elements in itinerary line: " + components.length);
    int passengerId = Integer.parseInt(components[1]);
    LocalDate date = LocalDate.parse(components[2]);
    Itinerary itinerary = new Itinerary(date);

    for (int i = 3; i < components.length; i++) {
      String segmentDescription[] = components[i].split("/");

      int serviceId = Integer.parseInt(segmentDescription[0]);
      String departureTrainStop = segmentDescription[1];
      String arrivalTrainStop = segmentDescription[2];

      Service serv = _trainCompany.findServ(serviceId);

      TrainStop departTrainStop = serv.findStation(departureTrainStop);
      TrainStop arrivTrainStop = serv.findStation(arrivalTrainStop);

      Segment segm = new Segment(serv,departTrainStop,arrivTrainStop);
      itinerary.addSegm(segm);
    }
    _trainCompany.setItineraryToPassenger(passengerId, itinerary);
  }
}