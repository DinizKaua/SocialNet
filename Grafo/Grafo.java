package Grafo;

import java.util.List;

public interface Grafo<T> {
    // vertices
    void adicionarVertice(T vertice);
    void removerVertice(T vertice);
    List<T> getVertices();
    int getNumeroVertices();

    // arestas
    void adicionarAresta(T origem, T destino);
    void adicionarAresta(T origem, T destino, double peso);
    void removerAresta(T origem, T destino);
    boolean existeAresta(T origem, T destino);
    double getPeso(T origem, T destino);
    int getNumeroArestas();

    // propriedades
    List<T> getAdjacentes(T vertice);
    int getGrau(T vertice);
}
