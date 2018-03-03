package mmt.core;

/**
* Classe da categoria Frequente.
*
* @author Grupo 34
* @version Intermedio
*/

public class Frequent extends Category{

	/** Tipo de categoria */
	private String _typeOfUser = "FREQUENTE";
	
	/** Valor do desconto */
	private double _desconto = 15;
	
	/**
	* Retorna o tipo de categoria.
	*
	* @return Tipo da categoria.
	*/
	protected String getType(){
		return _typeOfUser;
	}

	/**
    * Retorna o valor de desconto associado a categoria Frequente.
    *
    * @return Valor de desconto.
    */
	protected double getDesconto(){
		return _desconto;
	}
}
