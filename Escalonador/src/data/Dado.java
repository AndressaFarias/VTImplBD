/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import domain.Instrucao;
import java.util.ArrayList;

/**
 *
 * @author joao
 */
public class Dado {

    private boolean lockX;
    private String tipoLock;
    private ArrayList<String> waitList;
    private ArrayList<String> lockS;
    private String name;
    private String transacao;

    public Dado(String name) {
        this.lockX = false;
        this.name = name;
        this.tipoLock = "U";
        waitList = new ArrayList<String>();
        lockS = new ArrayList<String>();
        transacao = "";
    }

    public boolean isLocked() {
        return lockX;
    }

    public void setLocked(boolean locked) {
        this.lockX = locked;
    }

    public String getTipoLock() {
        return tipoLock;
    }

    public String getTransacao() {
        return transacao;
    }

    public String getName() {
        return name;
    }

    public void setTransacao(String transacao) {
        this.transacao = transacao;
    }

    public void setTipoLock(String tipoLock) {
        this.tipoLock = tipoLock;
    }

    public void insereLockS(String transacao) {
        lockS.add(transacao);
    }

    public void insereWaitList(String transacao) {
        waitList.add(transacao);
    }

    public String tranLockSUsando() {
        return lockS.get(0);
    }

    public void passaTudoPraWait() {
        lockS.remove(0);
        if (waitList.isEmpty()) {
            ArrayList<String> aux = (ArrayList<String>) lockS.clone();
            waitList = aux;
        } else {
            for (int i = 0; i < lockS.size(); i++) {
                waitList.add(lockS.get(i));
            }
        }
    }

    public boolean maisDeUmEmS() {
        boolean res = false;
        if (lockS.size() > 1) {
            res = true;
        }
        return res;
    }

    public boolean estaEmS(String transacao) {
        boolean res = false;
        for (String s : lockS) {
            if (s.equals(transacao)) {
                res = true;
            }
        }
        return res;
    }

    public boolean estaEmWait(String transacao) {
        boolean res = false;
        for (String s:waitList) {
            if (s.equals(transacao)) {
                res = true;
            }
        }
        return res;
    }
}
