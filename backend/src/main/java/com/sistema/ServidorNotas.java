package com.sistema;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.*;

public class ServidorNotas {
    private static SistemaNotas sistema = new SistemaNotas();
    
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        
        server.createContext("/api/alunos", new AlunosHandler());
        server.createContext("/api/resultados", new ResultadosHandler());
        
        server.setExecutor(null);
        server.start();
        System.out.println("Servidor rodando em: http://localhost:8080");
    }
    
    static class AlunosHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
            
            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(200, 0);
                exchange.close();
                return;
            }
            
            if ("POST".equals(exchange.getRequestMethod())) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String body = reader.readLine();
                String[] dados = body.split(",");
                
                String nome = dados[0];
                double[] notas = new double[5];
                for (int i = 0; i < 5; i++) {
                    notas[i] = Double.parseDouble(dados[i + 1]);
                }
                double frequencia = Double.parseDouble(dados[6]);
                
                sistema.adicionarAluno(nome, notas, frequencia);
                
                String response = "{\"status\":\"ok\"}";
                exchange.sendResponseHeaders(200, response.length());
                exchange.getResponseBody().write(response.getBytes());
            }
            exchange.close();
        }
    }
    
    static class ResultadosHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            
            StringBuilder json = new StringBuilder("{");
            
            // Médias da turma
            double[] mediaTurma = sistema.calcularMediaTurma();
            json.append("\"mediaTurma\":[");
            for (int i = 0; i < mediaTurma.length; i++) {
                json.append(String.format("%.0f", mediaTurma[i]));
                if (i < mediaTurma.length - 1) json.append(",");
            }
            json.append("],");
            
            // Alunos acima da média
            List<String> acimaMedia = sistema.getAlunosAcimaMedia();
            json.append("\"acimaMedia\":[");
            for (int i = 0; i < acimaMedia.size(); i++) {
                json.append("\"").append(acimaMedia.get(i)).append("\"");
                if (i < acimaMedia.size() - 1) json.append(",");
            }
            json.append("],");
            
            // Frequência baixa
            List<String> freqBaixa = sistema.getAlunosFrequenciaBaixa();
            json.append("\"frequenciaBaixa\":[");
            for (int i = 0; i < freqBaixa.size(); i++) {
                json.append("\"").append(freqBaixa.get(i)).append("\"");
                if (i < freqBaixa.size() - 1) json.append(",");
            }
            json.append("]}");
            
            String response = json.toString();
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        }
    }
}