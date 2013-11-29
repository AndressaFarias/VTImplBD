/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exe;

import service.Rules;



/**
 *
 * @author joao
 */
public class Executable {
    public static void main(String args[]){
        Rules r = new Rules();
        r.geraEscala();
        r.geraDados();
        System.out.println("Resultado da execução: " + r.processaEscala()); 
    }
}
