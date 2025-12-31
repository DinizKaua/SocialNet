package Sistema;

import Grafo.Grafo;
import Grafo.ListaAdjacencia;
import Modelo.Conexao;
import Modelo.Usuario;

import java.time.LocalDate;
import java.util.*;
public class AnalisadorRedeSocial {
    private Grafo<Usuario> grafo;

    public AnalisadorRedeSocial(Grafo<Usuario> grafo) {
        this.grafo = grafo;
    }

    // Busca em Largura (BFS) - Amigos em comum
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

    private List<Usuario> reconstruirCaminho(Map<Usuario, Usuario> predecessores, Usuario destino) {
        List<Usuario> caminho = new LinkedList<>();
        Usuario atual = destino;

        while(atual != null) {
            caminho.add(0, atual);
            atual = predecessores.get(atual);
        }
        return caminho;
    }

    // Busca em Profundidade (DFS) - Exploração completa
    public Set<Usuario> explorarRedeCompleta(Usuario origem) {
        Set<Usuario> visitados = new HashSet<>();
        if(origem == null) {
            return visitados;
        }
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

    // Recomendação por amigos em comum (2º grau)
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

                contagem.put(amigoDoAmigo, contagem.getOrDefault(amigoDoAmigo, 0) + 1);
            }
        }

        // ordena por numero de amigos em comum
        List<Usuario> recomendados = new ArrayList<>(contagem.keySet());
        recomendados.sort((u1, u2) -> contagem.get(u2).compareTo(contagem.get(u1)));

        return recomendados;
    }

    // Componentes conexas da rede
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

    // Usuários mais influentes (maior grau)
    public List<Usuario> encontrarInfluenciadores(int topN) {
        if(topN <= 0) {
            return Collections.emptyList();
        }

        List<Usuario> usuarios = new ArrayList<>(grafo.getVertices());
        usuarios.sort((u1, u2) -> {
            int grauU1 = grafo.getGrau(u1);
            int grauU2 = grafo.getGrau(u2);
            return Integer.compare(grauU2, grauU1);
        });

        if(topN > usuarios.size()) {
            topN = usuarios.size();
        }
        return usuarios.subList(0, topN);
    }
    
    // Verificar se há ciclos na rede
    public boolean possuiCiclos(){
        Set<Usuario> visitados = new HashSet<>();

        for(Usuario usuario : grafo.getVertices()) {
            if(!visitados.contains(usuario)) {
                if(dfsDetectarCiclo(usuario, null, visitados)) {
                    return true; // Ciclo encontrado
                }
            }
        }
        return false; // Nenhum ciclo encontrado
    }

    private boolean dfsDetectarCiclo(Usuario atual, Usuario pai, Set<Usuario> visitados) {
        visitados.add(atual);

        for(Usuario vizinho : grafo.getAdjacentes(atual)) {
            if(!visitados.contains(vizinho)) {
                if(dfsDetectarCiclo(vizinho, atual, visitados)) {
                    return true;
                }
            } else if(!vizinho.equals(pai)) {
                return true; // Ciclo detectado
            }
        }
        return false;
    }

    // Dijkstra - Força da conexão
    public Map<Usuario, Double> calcularDistanciaSocial(Usuario origem) {
        if(origem == null || !grafo.getVertices().contains(origem)) {
            return Collections.emptyMap();
        }
        Map<Usuario, Double> distancias = new HashMap<>();
        Set<Usuario> visitados = new HashSet<>();

        for(Usuario usuario : grafo.getVertices()) {
            distancias.put(usuario, Double.POSITIVE_INFINITY);
        }
        distancias.put(origem, 0.0);

        PriorityQueue<Usuario> fila = new PriorityQueue<>(Comparator.comparingDouble(distancias::get));
        fila.add(origem);

        while(!fila.isEmpty()) {
            Usuario atual = fila.poll();

            if(visitados.contains(atual)) {
                continue;
            }
            visitados.add(atual);

            for(Usuario vizinho : grafo.getAdjacentes(atual)) {
                double peso = grafo.getPeso(atual, vizinho);
                double custo = 1.0 - peso;
                double novaDistancia = distancias.get(atual) + custo;

                if(novaDistancia < distancias.get(vizinho)) {
                    distancias.put(vizinho, novaDistancia);
                    fila.add(vizinho);
                }
            }
        }
        return distancias;
    }

    // Detecção de pontes (arestas críticas)
    public List<Conexao> encontrarPontes() {
        List<Conexao> pontes = new ArrayList<>();
        Map<Usuario, Integer> disc = new HashMap<>();
        Map<Usuario, Integer> low = new HashMap<>();
        Map<Usuario, Usuario> pai = new HashMap<>();
        Set<Usuario> visitados = new HashSet<>();

        int[] tempo = {0}; // contador de tempo mutavel

        for(Usuario usuario : grafo.getVertices()) {
            if(!visitados.contains(usuario)) {
                dfsPontes(usuario, visitados, disc, low, pai, tempo, pontes);
            }
        }
        
        return pontes;
    }

    private void dfsPontes(Usuario u, Set<Usuario> visitados, Map<Usuario, Integer> disc, Map<Usuario, Integer> low, Map<Usuario, Usuario> pai, int[] tempo, List<Conexao> pontes) {
        visitados.add(u);
        disc.put(u, ++tempo[0]);
        low.put(u, disc.get(u));

        for(Usuario v : grafo.getAdjacentes(u)) {
            if(!visitados.contains(v)) {
                pai.put(v, u);
                dfsPontes(v, visitados, disc, low, pai, tempo, pontes);

                low.put(u, Math.min(low.get(u), low.get(v)));

                if(low.get(v) > disc.get(u)) {
                    double peso = grafo.getPeso(u, v);
                    pontes.add(new Conexao(u, v, peso, LocalDate.now()));
                }
            } else if(!v.equals(pai.get(u))) {
                low.put(u, Math.min(low.get(u), disc.get(v)));
            }
        }
    }

    // Árvore geradora mínima (Prim/Kruskal)
    public Grafo<Usuario> encontrarRedeEssencial() {
        Grafo<Usuario> arvore = new ListaAdjacencia<>();

        List<Usuario> vertice = grafo.getVertices();
        if(vertice.isEmpty()) {
            return arvore;
        }

        Set<Usuario> naArvore = new HashSet<>();
        Map<Usuario, Double> custoMinimo = new HashMap<>();
        Map<Usuario, Usuario> pai = new HashMap<>();

        for(Usuario usuario : vertice) {
            custoMinimo.put(usuario, Double.POSITIVE_INFINITY);
        }

        Usuario inicial = vertice.get(0);
        custoMinimo.put(inicial, 0.0);

        PriorityQueue<Usuario> fila = new PriorityQueue<>(Comparator.comparingDouble(custoMinimo::get));
        fila.add(inicial);

        while(!fila.isEmpty()) {
            Usuario atual = fila.poll();

            if(naArvore.contains(atual)) {
                continue;
            }

            naArvore.add(atual);
            arvore.adicionarVertice(atual);

            if(pai.containsKey(atual)) {
                Usuario origem = pai.get(atual);
                double peso = grafo.getPeso(origem, atual);
                arvore.adicionarAresta(origem, atual, peso);
            }

            for(Usuario vizinho : grafo.getAdjacentes(atual)) {
                if(!naArvore.contains(vizinho)) {
                    double peso = grafo.getPeso(atual, vizinho);
                    double custo = 1.0 - peso;
                    if(custo < custoMinimo.get(vizinho)) {
                        custoMinimo.put(vizinho, custo);
                        pai.put(vizinho, atual);
                        fila.add(vizinho);
                    }
                }
            }
        }
        
        return arvore;
    }

    // Grafo de Cointeresse (grafos bipartidos)
    public Map<String, List<Usuario>> agruparPorInteresse(){
        Map<String, List<Usuario>> grupos = new HashMap<>();

        for(Usuario usuario : grafo.getVertices()) {
            if(usuario.getInteresses() == null) {
                continue;
            }
            for(String interesse : usuario.getInteresses()) {
                grupos.computeIfAbsent(interesse, k -> new ArrayList<>()).add(usuario);
            }
        }
        return grupos;
    }
}
