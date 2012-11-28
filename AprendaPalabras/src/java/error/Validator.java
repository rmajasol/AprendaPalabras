/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package error;

import java.util.HashMap;

/**
 * Clase que contiene todos los errores:
 * <ul><li> {@code allFieldErrors} para todos los errores posibles al completar 
 * los campos del formulario.
 * <li> {@code logicErrors} para el resto de errores (email no enviado, código de 
 * verificación erróneo, etc), es decir, todos aquellos generados por la lógica de 
 * negocio de la aplicación
 * @author yomac
 */
public class Validator {

    private Errors errors = new Errors();
    private HashMap<String, String> fieldsOk = new HashMap<String, String>();

    /**
     * Pares clave-valor para cada campo escrito en el formulario 
     * (nombre_del_campo, valor) xej. (username, miguel89) para luego en caso de error 
     * dejar rellenos aquellos que están bien y que el usuario no tenga que volver a completarlos.
     */
    public HashMap getFieldsOk() {
        return fieldsOk;
    }

    public String getFieldOk(String fieldName) {
        return (String) fieldsOk.get(fieldName);
    }

    public void putFieldOk(String name, String value) {
        fieldsOk.put(name, value);
    }

    public Errors getErrors() {
        return errors;
    }
    
    public String getLogicError(){
        return errors.getLogicError();
    }
    
    public HashMap<String, String> getFormErrors(){
        return errors.getFormErrors();
    }

    public void putWrongField(String fieldName, String errorDesc) {
        errors.putWrongField(fieldName, errorDesc);
    }

    public boolean hasFormErrors() {
        return errors.hasFormErrors();
    }

    public void setLogicError(String logicErrorDesc) {
        errors.setLogicError(logicErrorDesc);
    }
}
