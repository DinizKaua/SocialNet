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

    // Busca em Largura (BFS) - Caminho mais curto
    public List<Usuario> encontrarCaminhoConexao(Usuario origem, Usuario destino) {
        if (origem == null || destino == null) {
            return Collections.emptyList(); // se não existem, retorna lista vazia
        }

        Queue<Usuario> fila = new LinkedList<>(); // fila para BFS
        Map<Usuario, Usuario> predecessores = new HashMap<>(); // para reconstruir o caminho
        Set<Usuario> visitados = new HashSet<>(); // para evitar ciclos

        fila.add(origem); 
        visitados.add(origem);
        predecessores.put(origem, null);

        while(!fila.isEmpty()) { // enquanto houverem vertices para explorar
            Usuario atual = fila.poll(); // pega o proximo da fila

            if(atual.equals(destino)) { // se chegou ao destino, reconstrói o caminho
                return reconstruirCaminho(predecessores, destino);
            }
            // para cada vizinho nao visitado, marca como visitado e adiciona na fila
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

    // reconstrói o caminho a partir do mapa de predecessores
    private List<Usuario> reconstruirCaminho(Map<Usuario, Usuario> predecessores, Usuario destino) {
        List<Usuario> caminho = new LinkedList<>(); //
        Usuario atual = destino;

        // volta do destino até a origem
        while(atual != null) {
            caminho.add(0, atual); // adiciona no inicio da lista, mantendo origem->destino
            atual = predecessores.get(atual);
        }
        return caminho;
    }

    // Busca em Profundidade (DFS) - Exploração completa
    public Set<Usuario> explorarRedeCompleta(Usuario origem) {
        Set<Usuario> visitados = new HashSet<>();
        if(origem == null) { // se o usuario nao existe retorna vazio
            return visitados;
        }
        dfs(origem, visitados); // chama o metodo recursivo de dfs
        return visitados;
    }

    private void dfs(Usuario atual, Set<Usuario> visitados) {
        if(atual == null || visitados.contains(atual)) { // caso base: se nulo ou ja visitado
            return;
        }

        visitados.add(atual); // marca como visitado

        for(Usuario vizinho : grafo.getAdjacentes(atual)) { // explora cada vizinho
            dfs(vizinho, visitados);
        }
    }

    // Recomendação por amigos em comum (2º grau)
    public List<Usuario> recomendarAmizades(Usuario usuario) {
        if(usuario == null) {
            return Collections.emptyList(); // Usuario nao existe
        }

        Map<Usuario, Integer> contagem = new HashMap<>(); // mapa para contar amigos em comum
        Set<Usuario> amigosDiretos = new HashSet<>(grafo.getAdjacentes(usuario)); // amigos de 1º grau

        for(Usuario amigo : amigosDiretos) { // percorre cada amigo direto
            for(Usuario amigoDoAmigo : grafo.getAdjacentes(amigo)) { // para cada amigo do amigo
                // ignora o proprio usuario e amigos ja existentes
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

        for(Usuario usuario : grafo.getVertices()) { // para cada usuario no grafo
            if(!visitados.contains(usuario)) { // se nao foi visitado
                Set<Usuario> comunidade = new HashSet<>(); // cria nova comunidade
                dfsComunidade(usuario, visitados, comunidade); // preenche a comunidade via DFS
                comunidades.add(comunidade);
            }
        }
        return comunidades;
    }

    // dfs para identificar comunidades
    private void dfsComunidade(Usuario atual, Set<Usuario> visitados, Set<Usuario> comunidade) {
        visitados.add(atual); // marca como visitado
        comunidade.add(atual); // adiciona na comunidade

        for(Usuario vizinho : grafo.getAdjacentes(atual)) { // explora cada vizinho
            if(!visitados.contains(vizinho)) {
                dfsComunidade(vizinho, visitados, comunidade); 
            }
        }
    }

    // Usuários mais influentes (maior grau)
    public List<Usuario> encontrarInfluenciadores(int topN) {
        if(topN <= 0) { // se topN invalido, retorna vazio
            return Collections.emptyList();
        }

        List<Usuario> usuarios = new ArrayList<>(grafo.getVertices()); // pega todos os usuarios
        usuarios.sort((u1, u2) -> { // ordena por grau decrescente
            int grauU1 = grafo.getGrau(u1);
            int grauU2 = grafo.getGrau(u2);
            return Integer.compare(grauU2, grauU1);
        });

        if(topN > usuarios.size()) {
            topN = usuarios.size();
        }
        return usuarios.subList(0, topN); // retorna os top N influenciadores
    }
    
    // Verificar se há ciclos na rede
    public boolean possuiCiclos(){
        Set<Usuario> visitados = new HashSet<>();

        for(Usuario usuario : grafo.getVertices()) { // para cada usuario no grafo
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

        for(Usuario vizinho : grafo.getAdjacentes(atual)) { // para cada vizinho
            if(!visitados.contains(vizinho)) { // se nao visitado, continua DFS
                if(dfsDetectarCiclo(vizinho, atual, visitados)) {
                    return true;
                }
            } else if(!vizinho.equals(pai)) { // se ja visitado e nao é o pai, ciclo detectado
                return true;
            }
        }
        return false;
    }

    // Dijkstra - Força da conexão
    public Map<Usuario, Double> calcularDistanciaSocial(Usuario origem) {
        if(origem == null || !grafo.getVertices().contains(origem)) {
            return Collections.emptyMap();
        }
        Map<Usuario, Double> distancias = new HashMap<>(); // mapa de distancias
        Set<Usuario> visitados = new HashSet<>();

        for(Usuario usuario : grafo.getVertices()) {
            distancias.put(usuario, Double.POSITIVE_INFINITY); // inicializa todas distancias como infinito
        }
        distancias.put(origem, 0.0); // distancia da origem para ela mesma é 0

        // Fila de prioridade com base nas distancias de cada usuario
        PriorityQueue<Usuario> fila = new PriorityQueue<>(Comparator.comparingDouble(distancias::get)); 
        fila.add(origem);

        while(!fila.isEmpty()) { // enquanto houverem vertices para explorar
            Usuario atual = fila.poll(); // Pega o vertice com menor distancia

            if(visitados.contains(atual)) {
                continue;
            }
            visitados.add(atual); // marca como visitado

            for(Usuario vizinho : grafo.getAdjacentes(atual)) { // para cada vizinho
                double peso = grafo.getPeso(atual, vizinho);
                double custo = 1.0 - peso;
                double novaDistancia = distancias.get(atual) + custo; // calcula nova distancia

                if(novaDistancia < distancias.get(vizinho)) { // se nova distancia é menor, atualiza
                    distancias.put(vizinho, novaDistancia);
                    fila.add(vizinho);
                }
            }
        }
        return distancias;
    }

    // Detecção de pontes (arestas críticas)
    public List<Conexao> encontrarPontes() {
        // dfs + tarjan
        List<Conexao> pontes = new ArrayList<>();
        Map<Usuario, Integer> disc = new HashMap<>(); // tempos de descoberta
        Map<Usuario, Integer> low = new HashMap<>(); // O vertice mais baixo alcançável sem passar pela aresta pai
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
        visitados.add(u); // marca como visitado
        disc.put(u, ++tempo[0]); // tempo de descoberta
        low.put(u, disc.get(u)); // valor low inicial

        for(Usuario v : grafo.getAdjacentes(u)) { // para cada vizinho nao visitado
            if(!visitados.contains(v)) {
                pai.put(v, u);
                dfsPontes(v, visitados, disc, low, pai, tempo, pontes); // chamada recursiva

                low.put(u, Math.min(low.get(u), low.get(v)));

                if(low.get(v) > disc.get(u)) { // condição de ponte 
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

        List<Usuario> vertice = grafo.getVertices(); // lista de vertices do grafo original
        if(vertice.isEmpty()) {
            return arvore;
        }

        Set<Usuario> naArvore = new HashSet<>();
        Map<Usuario, Double> custoMinimo = new HashMap<>();
        Map<Usuario, Usuario> pai = new HashMap<>();

        for(Usuario usuario : vertice) { // inicializa custos como infinito
            custoMinimo.put(usuario, Double.POSITIVE_INFINITY);
        }

        Usuario inicial = vertice.get(0);
        custoMinimo.put(inicial, 0.0); // custo inicial é 0

        PriorityQueue<Usuario> fila = new PriorityQueue<>(Comparator.comparingDouble(custoMinimo::get));
        fila.add(inicial);

        while(!fila.isEmpty()) {
            Usuario atual = fila.poll();

            if(naArvore.contains(atual)) { // ja esta na arvore
                continue;
            }

            naArvore.add(atual);
            arvore.adicionarVertice(atual);

            if(pai.containsKey(atual)) { // adiciona a aresta que conecta ao pai
                Usuario origem = pai.get(atual);
                double peso = grafo.getPeso(origem, atual);
                arvore.adicionarAresta(origem, atual, peso);
            }

            for(Usuario vizinho : grafo.getAdjacentes(atual)) { // para cada vizinho, atualiza custos
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
            for(String interesse : usuario.getInteresses()) { // para cada interesse do usuario
                // se nao existe o interesse, cria nova lista e adiciona o usuario
                grupos.computeIfAbsent(interesse, k -> new ArrayList<>()).add(usuario);
            }
        }
        return grupos;
    }
}
