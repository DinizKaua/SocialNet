package Sistema;

import Grafo.Grafo;
import Grafo.ListaAdjacencia;
import Modelo.Usuario;
import java.util.*;
public class RedeSocial {
    private Grafo<Usuario> grafo;
    private Map<String, Usuario> usuarios;


    public RedeSocial() {
        this.grafo = new ListaAdjacencia<>();
        this.usuarios = new HashMap<>();
    }

    public RedeSocial(Grafo<Usuario> grafo) {
        this.grafo = grafo;
        this.usuarios = new HashMap<>();
    }

    // usuarios
    public boolean cadastrarUsuario(Usuario usuario) {
        if(usuarios.containsKey(usuario.getId())) {
            return false; // Usuario ja existe
        }
        usuarios.put(usuario.getId(), usuario);
        grafo.adicionarVertice(usuario);
        return true;
    }

    public Usuario buscarUsuario(String id) {
        return usuarios.get(id);
    }

    public Collection<Usuario> listarUsuarios() {
        return usuarios.values();
    }

    // amizades
    public boolean adicionarAmizade(String idOrigem, String idDestino, double forca) {
        Usuario origem = usuarios.get(idOrigem);
        Usuario destino = usuarios.get(idDestino);

        if(origem == null || destino == null) {
            return false; // Usuario nao encontrado
        }
        grafo.adicionarAresta(origem, destino, forca);
        return true;
    }

    public boolean removerAmizade(String idOrigem, String idDestino) {
        Usuario origem = usuarios.get(idOrigem);
        Usuario destino = usuarios.get(idDestino);

        if(origem == null || destino == null) {
            return false; // Usuario nao encontrado
        }
        grafo.removerAresta(origem, destino);
        return true;
    }

    // visualizacao
    public void exibirRede(){
        for(Usuario u : grafo.getVertices()){
            System.out.print(u.getId() + "|" + u.getNome() + " -> ");
            List<Usuario> amigos = grafo.getAdjacentes(u);
            

            for(Usuario amigo : amigos){
                System.out.print(amigo.getNome() + ", ");
            }
            System.out.println();
        }
    }
    
    // acesso ao grafo
    public Grafo<Usuario> getGrafo() {
        return grafo;
    }
}
