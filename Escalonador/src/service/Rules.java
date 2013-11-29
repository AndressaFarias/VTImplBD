/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import data.Dado;
import domain.Instrucao;
import java.util.ArrayList;

/**
 *
 * @author joao
 */
public class Rules {

    private Leitor l;
    private ArrayList<Dado> listaDados;
    private ArrayList<Instrucao> listaInstrucao;

    public Rules() {
        l = new Leitor();
        listaDados = new ArrayList<Dado>();
    }

    public String processaEscala() {
        StringBuilder sb = new StringBuilder();
        int cont = 0;
        Instrucao i;
        while (!listaInstrucao.isEmpty()) {
            i = listaInstrucao.get(cont);
            if (i.getInstruction().equals("LOCK-S")) {
                Dado d = buscaDado(i.getDado());
                if (d.getTipoLock().equals("U")) {
                    d.insereLockS(i.getTransacao());
                    d.setTipoLock("S");
                    sb.append("\n" + i.getTransacao() + "" + i.getInstruction() + "" + i.getDado());
                    listaInstrucao.remove(0);
                } else if (d.getTipoLock().equals("S")) {
                    d.insereLockS(i.getTransacao());
                    sb.append("\n" + i.getTransacao() + "" + i.getInstruction() + "" + i.getDado());
                    listaInstrucao.remove(0);
                } else if (d.getTipoLock().equals("X")) {
                    if (!d.estaEmWait(i.getTransacao())) {
                        d.insereWaitList(i.getTransacao());
                        i = listaInstrucao.get(cont +=1);
                    }
                }

            } else if (i.getInstruction().equals("LOCK-X")) {
                Dado d = buscaDado(i.getDado());
                if (d.getTipoLock().equals("U")) {
                    d.insereLockS(i.getTransacao());
                    d.setTipoLock("X");
                    d.setLocked(true);
                    d.setTransacao(i.getTransacao());
                    sb.append("\n" + i.getTransacao() + "" + i.getInstruction() + "" + i.getDado());
                    listaInstrucao.remove(0);
                } else if (d.getTipoLock().equals("S") && d.tranLockSUsando().equals(i.getTransacao()) && d.maisDeUmEmS()) {
                    d.setTipoLock("X");
                    d.setTransacao(i.getTransacao());
                    d.setLocked(true);
                    d.passaTudoPraWait();
                    sb.append("\n" + i.getTransacao() + "" + i.getInstruction() + "" + i.getDado());
                    listaInstrucao.remove(0);
                } else if (d.getTipoLock().equals("X")) {
                    if (!d.estaEmWait(i.getTransacao())) {
                        d.insereWaitList(i.getTransacao());
                        i = listaInstrucao.get(cont += 1);
                    }
                } else if (d.getTipoLock().equals("S") && !d.tranLockSUsando().equals(i.getTransacao())) {
                    if (!d.estaEmWait(i.getTransacao())) {
                        d.insereWaitList(i.getTransacao());
                        i = listaInstrucao.get(cont +=1);
                    }
                }else if(d.getTipoLock().equals("S") && d.tranLockSUsando().equals(i.getTransacao())){
                    d.setTipoLock("X");
                    d.setTransacao(i.getTransacao());
                    d.setLocked(true);
                    sb.append("\n" + i.getTransacao() + "" + i.getInstruction() + "" + i.getDado());
                    listaInstrucao.remove(0);
                }
            } else if (i.getInstruction().equals("READ")) {
                Dado d = buscaDado(i.getDado());
                if (d.getTipoLock().equals("X")) {
                    if (d.getTransacao().equals(i.getTransacao())) {
                        sb.append("\n" + i.getTransacao() + "" + i.getInstruction() + "" + i.getDado());
                        listaInstrucao.remove(0);
                    } else {
                        if (!d.estaEmWait(i.getTransacao())) {
                            d.insereWaitList(i.getTransacao());
                            i = listaInstrucao.get(cont +=1);
                        }
                    }
                } else {
                    if (d.estaEmS(i.getTransacao())) {
                        sb.append("\n" + i.getTransacao() + "" + i.getInstruction() + "" + i.getDado());
                        listaInstrucao.remove(0);
                    } else {
                        if (!d.estaEmWait(i.getTransacao())) {
                            d.insereWaitList(i.getTransacao());
                            i = listaInstrucao.get(cont +=1);
                        }
                    }
                }
            } else if (i.getInstruction().equals("WRITE")) {
                Dado d = buscaDado(i.getDado());
                if (d.getTipoLock().equals("X")) {
                    if (d.getTransacao().equals(i.getTransacao())) {
                        sb.append("\n" + i.getTransacao() + "" + i.getInstruction() + "" + i.getDado());
                        listaInstrucao.remove(0);
                    } else {
                        if (!d.estaEmWait(i.getTransacao())) {
                            d.insereWaitList(i.getTransacao());
                            i = listaInstrucao.get(cont +=1);
                        }
                        i = listaInstrucao.get(cont +=1);
                    }
                } else {
                    if (d.estaEmS(i.getTransacao())) {
                        sb.append("\n" + i.getTransacao() + "" + i.getInstruction() + "" + i.getDado());
                        listaInstrucao.remove(0);
                    } else {
                        if (!d.estaEmWait(i.getTransacao())) {
                            d.insereWaitList(i.getTransacao());
                            i = listaInstrucao.get(cont +=1);
                        }
                    }
                }
            } else if (i.getInstruction().equals("UNLOCK")) {
            } else {
            }
        }


        return sb.toString();
    }

    public void geraEscala() {
        listaInstrucao = l.readSplited();
    }

    public void geraDados() {
        listaDados.add(new Dado("A"));
        listaDados.add(new Dado("B"));
        listaDados.add(new Dado("C"));
        listaDados.add(new Dado("D"));
        listaDados.add(new Dado("E"));
    }

    public Dado buscaDado(String label) {
        Dado dadoResultado = null;
        for (Dado d : listaDados) {
            if (d.getName().equals(label)) {
                dadoResultado = d;
            }
        }
        return dadoResultado;
    }

    public Leitor getL() {
        return l;
    }

    public void lockx() {
    }

    public String exibeTrasaocao() {
        StringBuilder sb = new StringBuilder();
        sb.append(listaInstrucao.get(0).getTransacao());
        sb.append(listaInstrucao.get(0).getInstruction());
        sb.append(listaInstrucao.get(0).getDado());
        return sb.toString();
    }
}
