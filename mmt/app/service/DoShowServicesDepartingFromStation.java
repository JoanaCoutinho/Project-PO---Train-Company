package mmt.app.service;

import mmt.core.TicketOffice;
import mmt.core.exceptions.NoSuchStationNameException;
import mmt.app.exceptions.NoSuchStationException;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Display;


//FIXME import other classes if necessary

/**
 * 3.2.3 Show services departing from station.
 */
public class DoShowServicesDepartingFromStation extends Command<TicketOffice> {

  /** Nome de um servico*/
  private Input<String> _name;

  /**
  * Construtor da classe, mostra o seu titulo e obtem do utilizador o nome de uma estacao.
  *
  * @param receiver
  */
  public DoShowServicesDepartingFromStation(TicketOffice receiver) {
    super(Label.SHOW_SERVICES_DEPARTING_FROM_STATION, receiver);
    _name = _form.addStringInput(Message.requestStationName());
  }

  /**
  * Mostra um servico com origem na estacao dada pelo utilizador.
  *
  */
  @Override
  public final void execute() throws DialogException {
    try{    
      _form.parse();
      _display.addLine(_receiver.showServiceDepartingFromStation(_name.value()));
      _display.display();

    } catch (NoSuchStationNameException e){
        throw new NoSuchStationException(e.getName());
    }
  }
}