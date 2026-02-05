package com.sistema;

import java.util.*;

public class SistemaNotas {
    private List<Aluno> alunos = new ArrayList<>();
    
    public void adicionarAluno(String nome, double[] notas, double frequencia) {
        alunos.add(new Aluno(nome, notas, frequencia));
    }
    
    public double[] calcularMediaTurma() {
        double[] mediaDisciplinas = new double[5];
        for (int i = 0; i < 5; i++) {
            double soma = 0;
            for (Aluno aluno : alunos) {
                soma += aluno.getNotas()[i];
            }
            mediaDisciplinas[i] = soma / alunos.size();
        }
        return mediaDisciplinas;
    }
    
    public double calcularMediaGeralTurma() {
        double soma = 0;
        for (Aluno aluno : alunos) {
            soma += aluno.getMedia();
        }
        return soma / alunos.size();
    }
    
    public List<String> getAlunosAcimaMedia() {
        double mediaGeral = calcularMediaGeralTurma();
        List<String> resultado = new ArrayList<>();
        for (Aluno aluno : alunos) {
            if (aluno.getMedia() > mediaGeral) {
                resultado.add(aluno.getNome());
            }
        }
        return resultado;
    }
    
    public List<String> getAlunosFrequenciaBaixa() {
        List<String> resultado = new ArrayList<>();
        for (Aluno aluno : alunos) {
            if (aluno.getFrequencia() < 75) {
                resultado.add(aluno.getNome());
            }
        }
        return resultado;
    }
    
    public void exibirResultados() {
        for (Aluno aluno : alunos) {
            System.out.printf("%s %.0f %.0f%%\n", 
                aluno.getNome(), aluno.getMedia(), aluno.getFrequencia());
        }
        
        double[] mediaTurma = calcularMediaTurma();
        for (int i = 0; i < mediaTurma.length; i++) {
            System.out.printf("%.0f", mediaTurma[i]);
            if (i < mediaTurma.length - 1) System.out.print(" ");
        }
        System.out.println();
        
        List<String> acimaMedia = getAlunosAcimaMedia();
        if (acimaMedia.isEmpty()) {
            System.out.println();
        } else {
            for (String nome : acimaMedia) {
                System.out.println(nome);
            }
        }
        
        List<String> frequenciaBaixa = getAlunosFrequenciaBaixa();
        if (frequenciaBaixa.isEmpty()) {
            System.out.println();
        } else {
            for (String nome : frequenciaBaixa) {
                System.out.println(nome);
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SistemaNotas sistema = new SistemaNotas();
        
        String linha;
        while (scanner.hasNextLine() && !(linha = scanner.nextLine()).isEmpty()) {
            String[] dados = linha.split(" ");
            String nome = dados[0];
            double[] notas = new double[5];
            for (int i = 0; i < 5; i++) {
                notas[i] = Double.parseDouble(dados[i + 1]);
            }
            double frequencia = Double.parseDouble(dados[6].replace("%", ""));
            sistema.adicionarAluno(nome, notas, frequencia);
        }
        
        sistema.exibirResultados();
        scanner.close();
    }
}