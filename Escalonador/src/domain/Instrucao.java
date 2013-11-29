/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import data.Dado;

/**
 *
 * @author joao
 */
public class Instrucao {
    private String transacao;
    private String instruction;
    private String dado;

    public Instrucao(String transacao,String instruction, String dado) {
        this.instruction = instruction;
        this.dado = dado;
        this.transacao = transacao;
    }

    public String getInstruction() {
        return instruction;
    }

    public String getDado() {
        return dado;
    }

    public String getTransacao() {
        return transacao;
    }
    
}
