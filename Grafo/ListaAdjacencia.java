package Grafo;

import java.util.*;

public class ListaAdjacencia<T> implements Grafo<T> {
    private Map<T, Map<T, Double>> adjacencias;
    private int numeroArestas;

    public ListaAdjacencia() {
        this.adjacencias = new HashMap<>();
        this.numeroArestas = 0;
    }

    // vertices
    @Override
    public void adicionarVertice(T vertice) {
        adjacencias.putIfAbsent(vertice, new HashMap<>());
    }

    @Override
    public void removerVertice(T vertice) {
        if(!adjacencias.containsKey(vertice)) return; // vertice nao existe

        // remove todas as arestas ligadas a ele
        for(T adj : adjacencias.get(vertice).keySet()) {
            adjacencias.get(adj).remove(vertice);
            numeroArestas--;
        }
        adjacencias.remove(vertice);
    }

    @Override
    public List<T> getVertices() {
        return new ArrayList<>(adjacencias.keySet());
    }

    @Override
    public int getNumeroVertices() {
        return adjacencias.size();
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

        // evita duplicatas
        if(!adjacencias.get(origem).containsKey(destino)) {
            adjacencias.get(origem).put(destino, peso);
            adjacencias.get(destino).put(origem, peso);
            numeroArestas++;
        }
    }

    @Override
    public void removerAresta(T origem, T destino) {
        if(existeAresta(origem, destino)) {
            adjacencias.get(origem).remove(destino);
            adjacencias.get(destino).remove(origem);
            numeroArestas--;
        }
    }

    @Override
    public boolean existeAresta(T origem, T destino) {
        return adjacencias.containsKey(origem) && adjacencias.get(origem).containsKey(destino);
    }

    @Override
    public double getPeso(T origem, T destino) {
        if(!existeAresta(origem, destino)) {
            throw new IllegalArgumentException("Aresta não existe");
        }
        return adjacencias.get(origem).get(destino);
    }

    @Override
    public int getNumeroArestas() {
        return numeroArestas;  
    }

    // propriedades
    @Override
    public List<T> getAdjacentes(T vertice) {
        if(!adjacencias.containsKey(vertice)) {
            return Collections.emptyList();
        }
        return new ArrayList<>(adjacencias.get(vertice).keySet());
    }

    @Override
    public int getGrau(T vertice) {
        if(!adjacencias.containsKey(vertice)) {
            return 0;
        }
        return adjacencias.get(vertice).size();
    }
}