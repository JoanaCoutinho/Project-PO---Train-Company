package mmt.core;

/**
* Classe da categoria Especial.
*
* @author Grupo 34
* @version Intermedio
*/

public class Special extends Category{

	/** Tipo de categoria */
	private String _typeOfUser = "ESPECIAL";

	/** Valor do desconto */
	private double _desconto = 50;
	
	/**
	* Retorna o tipo de categoria.
	*
	* @return Tipo da categoria.
	*/
	protected String getType(){
		return _typeOfUser;
	}

	/**
    * Retorna o valor de desconto associado a categoria Especial.
    *
    * @return Valor de desconto.
    */
	protected double getDesconto(){
		return _desconto;
	}
}
