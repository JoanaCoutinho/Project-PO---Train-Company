package mmt.app.main;

import mmt.core.TicketOffice;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;

/**
 * Reinicia o TicketOffice.
 *
 * @author Grupo 34
 * @version Intermedio
 */
public class DoReset extends Command<TicketOffice> {
  
  /**
   * @param receiver
   */
  public DoReset(TicketOffice receiver) {
    super(Label.RESET, receiver);
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    _receiver.reset();
  }

}
