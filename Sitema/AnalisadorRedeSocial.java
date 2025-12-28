package Sitema;

import Grafo.Grafo;
import Modelo.Usuario;

import java.util.*;
public class AnalisadorRedeSocial {
    private Grafo<Usuario> grafo;

    public AnalisadorRedeSocial(Grafo<Usuario> grafo) {
        this.grafo = grafo;
    }

    // encontrar amigos em comum entre dois usuarios (bfs)
    public List<Usuario> encontrarCaminhoConexao(Usuario origem, Usuario destino) {
        if (origem == null || destino == null) {
            return Collections.emptyList();
        }

        Queue<Usuario> fila = new LinkedList<>();
        Map<Usuario, Usuario> predecessores = new HashMap<>();
        Set<Usuario> visitados = new HashSet<>();

        fila.add(origem);
        visitados.add(origem);
        predecessores.put(origem, null);

        while(!fila.isEmpty()) {
            Usuario atual = fila.poll();

            if(atual.equals(destino)) {
                return reconstruirCaminho(predecessores, destino);
            }

            for(Usuario vizinho : grafo.getAdjacentes(atual)) {
                if(!visitados.contains(vizinho)) {
                    visitados.add(vizinho);
                    predecessores.put(vizinho, atual);
                    fila.add(vizinho);
                }
            }
        }
        return Collections.emptyList(); // Caminho nao encontrado
    }

    public List<Usuario> reconstruirCaminho(Map<Usuario, Usuario> predecessores, Usuario destino) {
        List<Usuario> caminho = new LinkedList<>();
        Usuario atual = destino;

        while(atual != null) {
            caminho.add(0, atual);
            atual = predecessores.get(atual);
        }
        return caminho;
    }

    // Exploração completa da rede (DFS)
    public Set<Usuario> explorarRedeCompleta(Usuario origem) {
        Set<Usuario> visitados = new HashSet<>();
        dfs(origem, visitados);
        return visitados;
    }

    private void dfs(Usuario atual, Set<Usuario> visitados) {
        if(atual == null || visitados.contains(atual)) {
            return;
        }

        visitados.add(atual);

        for(Usuario vizinho : grafo.getAdjacentes(atual)) {
            dfs(vizinho, visitados);
        }
    }

    // recomendação por amigos em comun segundo grau)
    public List<Usuario> recomendarAmizades(Usuario usuario) {
        if(usuario == null) {
            return Collections.emptyList(); // Usuario nao existe
        }

        Map<Usuario, Integer> contagem = new HashMap<>();
        Set<Usuario> amigosDiretos = new HashSet<>(grafo.getAdjacentes(usuario));

        for(Usuario amigo : amigosDiretos) {
            for(Usuario amigoDoAmigo : grafo.getAdjacentes(amigo)) {
                // ignora o proprio usuario
                if(amigoDoAmigo.equals(usuario) || amigosDiretos.contains(amigoDoAmigo)) {
                    continue;
                }

                // ignora amigos diretos
                if(amigosDiretos.contains(amigoDoAmigo)) {
                    continue;
                }

                contagem.put(amigoDoAmigo, contagem.getOrDefault(amigoDoAmigo, 0) + 1);
            }
        }

        // ordena por numero de amigos em comum
        List<Usuario> recomendados = new ArrayList<>(contagem.keySet());
        recomendados.sort((u1, u2) -> contagem.get(u2).compareTo(contagem.get(u1)));

        return recomendados;
    }

    public List<Set<Usuario>> identificarComunidades() {    
        List<Set<Usuario>> comunidades = new ArrayList<>();
        Set<Usuario> visitados = new HashSet<>();

        for(Usuario usuario : grafo.getVertices()) {
            if(!visitados.contains(usuario)) {
                Set<Usuario> comunidade = new HashSet<>();
                dfsComunidade(usuario, visitados, comunidade);
                comunidades.add(comunidade);
            }
        }
        return comunidades;
    }

    // dfs para identificar comunidades
    private void dfsComunidade(Usuario atual, Set<Usuario> visitados, Set<Usuario> comunidade) {
        visitados.add(atual);
        comunidade.add(atual);

        for(Usuario vizinho : grafo.getAdjacentes(atual)) {
            if(!visitados.contains(vizinho)) {
                dfsComunidade(vizinho, visitados, comunidade);
            }
        }
    }

    
}
