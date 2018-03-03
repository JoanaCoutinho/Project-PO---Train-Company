package mmt.core;

/**
* Classe da categoria Normal.
*
* @author Grupo 34
* @version Intermedio
*/

public class Normal extends Category{

	/** Tipo de categoria */
	private String _typeOfUser = "NORMAL";

	/** Valor do desconto */
	private double _desconto = 0;
	
	/**
	* Retorna o tipo de categoria.
	*
	* @return Tipo da categoria.
	*/
	protected String getType(){
		return _typeOfUser;
	}
	
	/**
    * Retorna o valor de desconto associado a categoria Normal.
    *
    * @return Valor de desconto.
    */
	protected double getDesconto(){
		return _desconto;
	}

}
