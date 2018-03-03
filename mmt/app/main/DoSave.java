package mmt.app.main;

import java.io.IOException;

import mmt.core.TicketOffice;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;

/**
 * Salva o estado actual do TicketOffice ao ficheiro associado (caso nao haja associados, pede um nome ao utilizador).
 *
 * @author Grupo 34
 * @version Intermedio
 */
public class DoSave extends Command<TicketOffice> {
  
  private Input<String> _inputFilename;

  /**
   * @param receiver
   */
  public DoSave(TicketOffice receiver) {
    super(Label.SAVE, receiver);
    if(_receiver.getFileName().equals(""))
      _inputFilename = _form.addStringInput(Message.newSaveAs());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    try{
      String filename = _receiver.getFileName();
      if (filename.equals("")){
        _form.parse();
        filename = _inputFilename.value();
        _receiver.setFileName(filename);
      }
      
      _receiver.save(filename);
    } catch(IOException ioe){
    }
  }
}
