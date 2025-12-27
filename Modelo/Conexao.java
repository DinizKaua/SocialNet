package Modelo;

import java.time.LocalDate;

public class Conexao {
    private Usuario origem;
    private Usuario destino;
    private double forcaConexao; // Valor entre 0.0 e 1.0
    private LocalDate dataConexao;

    public Conexao(Usuario origem, Usuario destino, double forcaConexao, LocalDate dataConexao) {
        this.origem = origem;
        this.destino = destino;
        this.forcaConexao = forcaConexao;
        this.dataConexao = dataConexao;
    }

    // Getters e Setters
    public Usuario getOrigem() { return origem; }
    public void setOrigem(Usuario origem) { this.origem = origem; }
    public Usuario getDestino() { return destino; }
    public void setDestino(Usuario destino) { this.destino = destino; }
    public double getForcaConexao() { return forcaConexao; }
    public void setForcaConexao(double forcaConexao) { this.forcaConexao = forcaConexao; }
    public LocalDate getDataConexao() { return dataConexao; }
    public void setDataConexao(LocalDate dataConexao) { this.dataConexao = dataConexao; }

    // toString para representação textual do objeto
    @Override
    public String toString() {
        return origem.getNome() + " <-> " + destino.getNome() +
               " | Força da Conexão: " + forcaConexao +
               " | Data da Conexão: " + dataConexao;
    }
}
