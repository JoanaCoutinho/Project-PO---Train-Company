package mmt.app.main;

import mmt.core.TicketOffice;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;

import java.io.FileNotFoundException;
import java.io.IOException;

import mmt.app.main.Label;
import mmt.app.main.Message;

/**
 * Carrega o conteudo de um ficheiro para o TicketOffice
 *
 * @author Grupo 34
 * @version Intermedio
 */
public class DoOpen extends Command<TicketOffice> {

  /** Nome do ficheiro a ler */
  private Input<String> _filename;

  /**
   * @param receiver
   */
  public DoOpen(TicketOffice receiver) {
    super(Label.OPEN, receiver);
    _filename = _form.addStringInput(Message.openFile());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override

  public final void execute() {
    try{
      _form.parse();
      _receiver.load(_filename.value());
      _receiver.setFileName(_filename.value());
      
      }catch(FileNotFoundException fnfe){
        _display.addLine(Message.fileNotFound());
        _display.display();
      }catch(ClassNotFoundException cnfe){

      }catch(IOException ioe){
      }    
  }

}