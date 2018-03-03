package mmt.app.service;

import mmt.core.TicketOffice;
import mmt.core.exceptions.NoSuchServiceIdException;
import mmt.app.exceptions.NoSuchServiceException;
import mmt.app.service.Message;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Display;
import java.util.Iterator;

/**
 * Classe que mostra um servico a partir do seu Identificador.
 *
 * @author Grupo 34
 * @version Intermedio
 */
public class DoShowServiceByNumber extends Command<TicketOffice> {

  /** Identificador unico de um servico*/
  private Input<Integer> _id;

  /**
  * Construtor da classe, mostra o seu titulo e obtem do utilizador o Identificador do servico.
  *
  * @param receiver
  */
  public DoShowServiceByNumber(TicketOffice receiver) {
    super(Label.SHOW_SERVICE_BY_NUMBER, receiver);
    _id = _form.addIntegerInput(Message.requestServiceId());
  }

  /**
  * Mostra um servico a partir do idenficador dado pelo utilizador
  *
  */
  @Override
  public final void execute() throws DialogException {
    try{
      _form.parse();        
      _display.addLine(_receiver.showServiceByNumber(_id.value()));
      _display.display();

    } catch (NoSuchServiceIdException e){
        throw new NoSuchServiceException(e.getId());
      }
  }

}
