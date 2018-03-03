package mmt.core;

/**
* Classe abstracta das categorias de passageiros.
*
* @author Grupo 34
* @version Intermedio
*/
public abstract class Category implements java.io.Serializable{
	
	/**
	* Retorna o tipo de categoria.
	*/
	protected abstract String getType();

	/**
    * Retorna o desconto associado a categoria.
    */
	protected abstract double getDesconto();
}