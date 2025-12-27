package Modelo;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Usuario {
    private String id;
    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private byte[] fotoPerfil;
    private List<String> interesses;

    public Usuario(String id, String nome, String email, LocalDate dataNascimento, byte[] fotoPerfil, List<String> interesses) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.fotoPerfil = fotoPerfil;
        this.interesses = interesses;
    }

    // Getters e Setters
    public String getId(){return id;}
    public void setId(String id){this.id = id;}
    public String getNome(){return nome;}
    public void setNome(String nome){this.nome = nome;}
    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}
    public LocalDate getDataNascimento(){return dataNascimento;}
    public void setDataNascimento(LocalDate dataNascimento){this.dataNascimento = dataNascimento;}
    public byte[] getFotoPerfil(){return fotoPerfil;}
    public void setFotoPerfil(byte[] fotoPerfil){this.fotoPerfil = fotoPerfil;}
    public List<String> getInteresses(){return interesses;}
    public void setInteresses(List<String> interesses){this.interesses = interesses;}

    // equals e hashCode baseados no id

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // toString para representação textual do objeto
    @Override
    public String toString() {
        return "\nID: " + id + "\nNome: " + nome + "\nEmail: " + email + "\nData de Nascimento: " + dataNascimento;
    }
}
