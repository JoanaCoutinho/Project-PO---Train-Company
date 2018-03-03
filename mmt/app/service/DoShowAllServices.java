package mmt.app.service;

import mmt.core.TicketOffice;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Display;

import java.util.Iterator;

/**
 * Classe que mostra todos os servicos.
 *
 * @author Grupo 34
 * @version Intermedio
 */

public class DoShowAllServices extends Command<TicketOffice> {

  /**
  * Construtor da classe, mostra o seu titulo.
  *
  * @param receiver
  */
  public DoShowAllServices(TicketOffice receiver) {
    super(Label.SHOW_ALL_SERVICES, receiver);
  }

  /**
  * Mostra todos os servicos de um TicketOffice. 
  *
  */

  @Override
  public final void execute() {
    _display.addLine(_receiver.showAllServices());
    _display.display();
  }

}
