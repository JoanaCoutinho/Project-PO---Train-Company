package mmt.app.itineraries;

import mmt.core.TicketOffice;
import mmt.app.exceptions.BadDateException;
import mmt.app.exceptions.BadTimeException;
import mmt.app.exceptions.NoSuchItineraryException;
import mmt.app.exceptions.NoSuchPassengerException;
import mmt.app.exceptions.NoSuchStationException;
import mmt.core.exceptions.BadDateSpecificationException;
import mmt.core.exceptions.BadTimeSpecificationException;
import mmt.core.exceptions.NoSuchPassengerIdException;
import mmt.core.exceptions.NoSuchStationNameException;
import mmt.core.exceptions.NoSuchItineraryChoiceException;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Display;
import pt.tecnico.po.ui.Form;


/**
 * §3.4.3. Add new itinerary.
 */
public class DoRegisterItinerary extends Command<TicketOffice> {

  private Input<Integer> _id;
  private Input<String> _departingStationName;
  private Input<String> _arrivingStationName;
  private Input<String> _departureDate;
  private Input<String> _minTime;
  private Input<Integer> _option;

  /**
   * @param receiver
   */
  public DoRegisterItinerary(TicketOffice receiver) {
    super(Label.REGISTER_ITINERARY, receiver);
    
    _id = _form.addIntegerInput(Message.requestPassengerId());
    _departingStationName = _form.addStringInput(Message.requestDepartureStationName());
    _arrivingStationName = _form.addStringInput(Message.requestArrivalStationName());
    _departureDate = _form.addStringInput(Message.requestDepartureDate());
    _minTime = _form.addStringInput(Message.requestDepartureTime());
   
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    
    try {
      _form.parse();
      String str = _receiver.searchItineraries(_id.value(),_departingStationName.value(),_arrivingStationName.value(),_departureDate.value(),_minTime.value());
      if(str.length() != 0){
        _display.addLine(str);
        _display.display();
        Form form = new Form(title());
        
        _option = form.addIntegerInput(Message.requestItineraryChoice());
        form.parse();
        _receiver.commitItinerary(_id.value(),_option.value());

      }
      
    } catch (NoSuchPassengerIdException e) {
      throw new NoSuchPassengerException(e.getId());
    }catch (NoSuchStationNameException e) {
      throw new NoSuchStationException(e.getName());
    } catch (NoSuchItineraryChoiceException e) {
      throw new NoSuchItineraryException(e.getPassengerId(), e.getItineraryId());
    } catch (BadDateSpecificationException e) {
      throw new BadDateException(e.getDate());
    } catch (BadTimeSpecificationException e) {
      throw new BadTimeException(e.getTime());
    }
  }
}
