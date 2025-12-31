package Sistema;

import Modelo.Usuario;
import Grafo.Grafo;

import java.time.LocalDate;
import java.util.*;

public class SocialGraphMenu {
    private RedeSocial rede;
    private AnalisadorRedeSocial analisador;
    private Scanner scanner;

    public SocialGraphMenu(RedeSocial rede, AnalisadorRedeSocial analisador) {
        this.rede = rede;
        this.analisador = analisador;
        this.scanner = new Scanner(System.in);
    }

    public void executar(){
        while(true){
            exibirMenu();
            int opcao = lerOpcao();

            switch (opcao){
                case 1 -> cadastrarUsuario();
                case 2 -> adicionarAmizade();
                case 3 -> recomendarAmizades();
                case 4 -> visualizarRede();
                case 5 -> encontrarCaminho();
                case 6 -> menuAnalises(); 
                case 0 -> {
                    System.out.println("Saindo...");
                    return;
                }
                default -> System.out.println("Opção inválida");
            }
        }
    }

    private void menuAnalises() {
        while (true) {
            exibirMenuAnalises();
            int opcao = lerOpcao();

            switch (opcao) {
                case 1 -> identificarComunidades();
                case 2 -> mostrarInfluenciadores();
                case 3 -> calcularDistanciaSocial();
                case 4 -> verificarCiclos();
                case 5 -> encontrarPontes();
                case 6 -> gerarRedeEssencial();
                case 0 -> { return; }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void exibirMenu() {
        System.out.println("\n=== SOCIALNET ===");
        System.out.println("1 - Cadastrar usuário");
        System.out.println("2 - Adicionar amizade");
        System.out.println("3 - Recomendar amizades");
        System.out.println("4 - Visualizar rede");
        System.out.println("5 - Encontrar caminho entre usuários");
        System.out.println("6 - Analises da rede");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private void exibirMenuAnalises() {
        System.out.println("\n=== ANÁLISES DA REDE ===");
        System.out.println("1 - Identificar comunidades");
        System.out.println("2 - Mostrar influenciadores");
        System.out.println("3 - Calcular distância social");
        System.out.println("4 - Verificar ciclos");
        System.out.println("5 - Encontrar pontes");
        System.out.println("6 - Gerar rede essencial");
        System.out.println("0 - Voltar");
        System.out.print("Escolha uma opção: ");
    }

    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Métodos para cada funcionalidade do menu
    private void cadastrarUsuario() {
        System.out.println("\n=== Cadastro de Usuário ===");

        System.out.print("ID: ");
        String id = scanner.nextLine();

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Data de nascimento (AAAA-MM-DD): ");
        LocalDate dataNascimento;
        try {
            dataNascimento = LocalDate.parse(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Data inválida.");
            return;
        }

        System.out.print("Interesses (separados por vírgula): ");
        String entrada = scanner.nextLine();
        List<String> interesses = new ArrayList<>();

        if (!entrada.isBlank()) {
            for (String interesse : entrada.split(",")) {
                interesses.add(interesse.trim());
            }
        }

        Usuario usuario = new Usuario(
                id,
                nome,
                email,
                dataNascimento,
                null,
                interesses
        );

        if (rede.cadastrarUsuario(usuario)) {
            System.out.println("Usuário cadastrado com sucesso!");
        } else {
            System.out.println("Já existe um usuário com esse ID.");
        }
    }

    private void adicionarAmizade() {
        System.out.println("\n=== Adicionar Amizade ===");

        System.out.print("ID do usuário origem: ");
        String idOrigem = scanner.nextLine();

        System.out.print("ID do usuário destino: ");
        String idDestino = scanner.nextLine();

        System.out.print("Força da conexão (0.0 a 1.0): ");
        double forca;
        try {
            forca = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido.");
            return;
        }

        if (forca < 0.0 || forca > 1.0) {
            System.out.println("A força da conexão deve estar entre 0.0 e 1.0.");
            return;
        }

        if (rede.adicionarAmizade(idOrigem, idDestino, forca)) {
            System.out.println("Amizade adicionada com sucesso!");
        } else {
            System.out.println("Erro ao adicionar amizade. Verifique os IDs.");
        }
    }

    private void recomendarAmizades() {
        System.out.println("\n=== Recomendar Amizades ===");

        System.out.print("ID do usuário: ");
        String id = scanner.nextLine();

        Usuario usuario = rede.buscarUsuario(id);

        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        List<Usuario> recomendados = analisador.recomendarAmizades(usuario);

        if (recomendados.isEmpty()) {
            System.out.println("Nenhuma recomendação encontrada.");
            return;
        }

        System.out.println("Sugestões de amizade:");
        for (Usuario u : recomendados) {
            System.out.println("- " + u.getNome() + " (ID: " + u.getId() + ")");
        }
    }

    private void visualizarRede() {
        System.out.println("\n=== Visualizar Rede ===");
        rede.exibirRede();
    }

    private void encontrarCaminho(){
        System.out.print("ID do usuário origem: ");
        String idOrigem = scanner.nextLine();

        System.out.print("ID do usuário destino: ");
        String idDestino = scanner.nextLine();

        Usuario origem = rede.buscarUsuario(idOrigem);
        Usuario destino = rede.buscarUsuario(idDestino);

        if(origem == null || destino == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        List<Usuario> caminho = analisador.encontrarCaminhoConexao(origem, destino);

        if(caminho.isEmpty()) {
            System.out.println("Não existe caminho entre os usuários.");
        } else {
            System.out.println("Caminho de conexão:");
            for(int i = 0; i < caminho.size(); i++) {
                System.out.print(caminho.get(i).getNome());
                if(i < caminho.size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println();
        }
    }

    // Métodos para análises da rede

    private void  identificarComunidades(){
        List<Set<Usuario>> comunidades = analisador.identificarComunidades();

        int i = 1;
        for(Set<Usuario> comunidade : comunidades) {
            System.out.println("Comunidade " + i + ":");
            for(Usuario u : comunidade) {
                System.out.println("- " + u.getNome());
            }
            System.out.println();
            i++;
        }
    }

    private void mostrarInfluenciadores(){
        System.out.print("Quantidade de influenciadores (top N): ");
        int n = Integer.parseInt(scanner.nextLine());

        List<Usuario> influenciadores = analisador.encontrarInfluenciadores(n);

        if(influenciadores.isEmpty()) {
            System.out.println("Nenhum influenciador encontrado.");
            return;
        }

        System.out.println("Top " + influenciadores.size() + " influenciadores:");
        for(Usuario u : influenciadores) {
            System.out.println(
                u.getNome() + " (grau: " + rede.getGrafo().getGrau(u) + ")"
            );
        }
    }

    private void calcularDistanciaSocial() {
        System.out.print("ID origem: ");
        Usuario o = rede.buscarUsuario(scanner.nextLine());

        analisador.calcularDistanciaSocial(o).forEach((u, d) ->
            System.out.println(o.getNome() + " -> " + u.getNome() + ": " + d)
        );
    }

    private void verificarCiclos() {
        System.out.println(
            analisador.possuiCiclos()
                ? "A rede possui ciclos."
                : "A rede não possui ciclos."
        );
    }

    private void encontrarPontes() {
        analisador.encontrarPontes().forEach(p ->
            System.out.println(p.getOrigem().getNome() +
                " - " + p.getDestino().getNome())
        );
    }

    private void gerarRedeEssencial() {
        Grafo<Usuario> arvore = analisador.encontrarRedeEssencial();
        System.out.println("Rede essencial gerada com " +
            arvore.getNumeroArestas() + " conexões.");
    }
}
