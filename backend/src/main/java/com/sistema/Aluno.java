package com.sistema;

public class Aluno {
    private String nome;
    private double[] notas;
    private double frequencia;
    private double media;

    public Aluno(String nome, double[] notas, double frequencia) {
        this.nome = nome;
        this.notas = notas;
        this.frequencia = frequencia;
        this.media = calcularMedia();
    }

    private double calcularMedia() {
        double soma = 0;
        for (double nota : notas) {
            soma += nota;
        }
        return soma / notas.length;
    }

    public String getNome() { return nome; }
    public double[] getNotas() { return notas; }
    public double getFrequencia() { return frequencia; }
    public double getMedia() { return media; }
}