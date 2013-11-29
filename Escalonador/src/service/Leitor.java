/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import data.Dado;
import domain.Instrucao;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author joao
 */
public class Leitor {

    public void read() {
        Scanner ler = new Scanner(System.in);
        System.out.printf("Informe o nome de arquivo texto:\n");
        String nome = ler.nextLine();
        System.out.printf("\nConteúdo do arquivo texto:\n");
        try {
            FileReader arq = new FileReader(nome);
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = lerArq.readLine();
            // lê a primeira linha 
            // a variável "linha" recebe o valor "null" quando o processo 
            // de repetição atingir o final do arquivo texto 
            while (linha != null) {

                System.out.printf("%s\n", linha);
                linha = lerArq.readLine();
                // lê da segunda até a última linha } 
                arq.close();
            }
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }
        System.out.println();
    }

    public ArrayList<Instrucao> readSplited() {
         ArrayList<Instrucao> insAux = new ArrayList<>();
        Scanner ler = new Scanner(System.in);
        System.out.printf("Informe o nome de arquivo texto:\n");
        String nome = ler.nextLine();
        try {
            FileReader arq = new FileReader(nome);
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = lerArq.readLine();
            // lê a primeira linha 
            // a variável "linha" recebe o valor "null" quando o processo 
            // de repetição atingir o final do arquivo texto 
            while (linha != null) {
                String[] aux = linha.split(":");
                String t = aux[0];
                String i = aux[1];
                String d = aux[2];
                Instrucao ins = new Instrucao(t,i,d);
                insAux.add(ins);
                linha = lerArq.readLine();
                // lê da segunda até a última linha } 
            }
            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }
        return insAux;
    }
}
