package Grafo;

import java.util.*;

// Implementação de um grafo usando matriz de adjacência
public class MatrizAdjacencia<T> implements Grafo<T> {
    private List<T> vertices;
    private double[][] matriz; // se > 0, há uma aresta com peso igual ao valor
    private int numeroArestas;

    public MatrizAdjacencia() {
        this.vertices = new ArrayList<>();
        this.matriz = new double[0][0];
        this.numeroArestas = 0;
    }

    // vertices
    @Override
    public void adicionarVertice(T vertice) {
        if(vertices.contains(vertice)) {
            return; // Vertice ja existe
        }

        vertices.add(vertice);
        int n = vertices.size();
        double[][] novaMatriz = new double[n][n];
        // copia os valores antigos para a nova matriz
        for(int i = 0; i < n - 1; i++) {
            for(int j = 0; j < n - 1; j++) {
                novaMatriz[i][j] = matriz[i][j];
            }
        }
        matriz = novaMatriz;
    }

    @Override
    public void removerVertice(T vertice) { 
        int idx = vertices.indexOf(vertice);   
        if(idx == -1) {
            return; // Vertice nao existe
        }

        int n = vertices.size();
        double[][] novaMatriz = new double[n - 1][n - 1];

        // copia os valores antigos para a nova matriz, pulando a linha e coluna do vertice removido
        for(int i = 0, ni = 0; i < n; i++) {
            if(i == idx) continue;
            for(int j = 0, nj = 0; j < n; j++) {
                if(j == idx) continue;
                novaMatriz[ni][nj++] = matriz[i][j];
            }
            ni++;
        }

        //ajusta o numero de arestas
        for(int i = 0; i < n; i++) {
            if(matriz[idx][i] != 0.0) {
                numeroArestas--;    
            }
        }
        vertices.remove(idx);
        matriz = novaMatriz;
    }

    @Override
    public List<T> getVertices() {
        return new ArrayList<>(vertices);
    }

    @Override
    public int getNumeroVertices() {
        return vertices.size();
    }

    // arestas
    @Override
    public void adicionarAresta(T origem, T destino) {
        adicionarAresta(origem, destino, 1.0);
    }

    @Override
    public void adicionarAresta(T origem, T destino, double peso) {
        adicionarVertice(origem);
        adicionarVertice(destino);

        int i = vertices.indexOf(origem);
        int j = vertices.indexOf(destino);

        // evita duplicatas
        if(matriz[i][j] == 0.0) {
            matriz[i][j] = peso;
            matriz[j][i] = peso;
            numeroArestas++;
        }
    }

    @Override
    public void removerAresta(T origem, T destino) {
        int i = vertices.indexOf(origem);
        int j = vertices.indexOf(destino);

        if(i == -1 || j == -1 ) {
            return; // Aresta nao existe
        }

        if(matriz[i][j] != 0.0) {
            matriz[i][j] = 0.0;
            matriz[j][i] = 0.0;
            numeroArestas--;
        }
    }

    @Override
    public boolean existeAresta(T origem, T destino) {
        int i = vertices.indexOf(origem);
        int j = vertices.indexOf(destino);

        if(i == -1 || j == -1 ) {
            return false; // Aresta nao existe
        }

        return matriz[i][j] != 0.0;
    }

    @Override
    public double getPeso(T origem, T destino) {
        int i = vertices.indexOf(origem);
        int j = vertices.indexOf(destino);  

        if(i == -1 || j == -1 || matriz[i][j] == 0.0) {
            throw new IllegalArgumentException("Aresta nao existe");
        }
        return matriz[i][j];
    }

    @Override
    public int getNumeroArestas() {
        return numeroArestas;
    }

    // propriedades
    @Override
    public List<T> getAdjacentes(T vertice) {
        int idx = vertices.indexOf(vertice);
        if(idx == -1) {
            return Collections.emptyList(); // Vertice nao existe
        }

        List<T> adjacentes = new ArrayList<>();
        for(int j = 0; j < vertices.size(); j++) {
            if(matriz[idx][j] != 0.0) {
                adjacentes.add(vertices.get(j));
            }
        }
        return adjacentes;
    }

    @Override
    public int getGrau(T vertice) {
        return getAdjacentes(vertice).size();
    }

}
