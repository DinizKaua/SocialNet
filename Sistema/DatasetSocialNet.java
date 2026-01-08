package Sistema;

import java.time.LocalDate;
import java.util.List;

import Modelo.Usuario;

public class DatasetSocialNet {
    public static void carregar(RedeSocial rede){

        rede.cadastrarUsuario(new Usuario("u1","Ana","ana@mail.com",
                LocalDate.of(2000,5,10),null,List.of("Cinema","Música","Viagem")));

        rede.cadastrarUsuario(new Usuario("u2","Bruno","bruno@mail.com",
                LocalDate.of(1999,3,22),null,List.of("Esporte","Tecnologia")));

        rede.cadastrarUsuario(new Usuario("u3","Carla","carla@mail.com",
                LocalDate.of(2001,8,14),null,List.of("Leitura","Cinema")));

        rede.cadastrarUsuario(new Usuario("u4","Daniel","daniel@mail.com",
                LocalDate.of(1998,11,2),null,List.of("Games","Tecnologia")));

        rede.cadastrarUsuario(new Usuario("u5","Eduarda","edu@mail.com",
                LocalDate.of(2002,1,30),null,List.of("Arte","Fotografia")));

        rede.cadastrarUsuario(new Usuario("u6","Felipe","felipe@mail.com",
                LocalDate.of(2000,7,18),null,List.of("Games","Esporte")));

        rede.cadastrarUsuario(new Usuario("u7","Gabriela","gabi@mail.com",
                LocalDate.of(1999,9,5),null,List.of("Música","Dança")));

        rede.cadastrarUsuario(new Usuario("u8","Henrique","henrique@mail.com",
                LocalDate.of(2001,12,1),null,List.of("Tecnologia","Robótica")));

        rede.cadastrarUsuario(new Usuario("u9","Isabela","isa@mail.com",
                LocalDate.of(2000,4,9),null,List.of("Cinema","Séries")));

        rede.cadastrarUsuario(new Usuario("u10","João","joao@mail.com",
                LocalDate.of(1998,6,27),null,List.of("Esporte","Saúde")));

        rede.cadastrarUsuario(new Usuario("u11","Karla","karla@mail.com",
                LocalDate.of(2001,10,10),null,List.of("Arte","Design")));

        rede.cadastrarUsuario(new Usuario("u12","Lucas","lucas@mail.com",
                LocalDate.of(1999,2,14),null,List.of("Tecnologia","Startups")));

        rede.cadastrarUsuario(new Usuario("u13","Marina","marina@mail.com",
                LocalDate.of(2002,3,8),null,List.of("Fotografia","Viagem")));

        rede.cadastrarUsuario(new Usuario("u14","Nicolas","nico@mail.com",
                LocalDate.of(2000,1,19),null,List.of("Games","Cinema")));

        rede.cadastrarUsuario(new Usuario("u15","Olivia","olivia@mail.com",
                LocalDate.of(1998,12,12),null,List.of("Leitura","Escrita")));

        rede.cadastrarUsuario(new Usuario("u16","Paulo","paulo@mail.com",
                LocalDate.of(1997,5,6),null,List.of("Negócios","Economia")));

        rede.cadastrarUsuario(new Usuario("u17","Quezia","quezia@mail.com",
                LocalDate.of(2001,9,21),null,List.of("Moda","Arte")));

        rede.cadastrarUsuario(new Usuario("u18","Rafael","rafa@mail.com",
                LocalDate.of(1999,11,11),null,List.of("Tecnologia","IA")));

        rede.cadastrarUsuario(new Usuario("u19","Sofia","sofia@mail.com",
                LocalDate.of(2002,2,2),null,List.of("Música","Canto")));

        rede.cadastrarUsuario(new Usuario("u20","Tiago","tiago@mail.com",
                LocalDate.of(1998,7,7),null,List.of("Esporte","Corrida")));

        rede.cadastrarUsuario(new Usuario("u21","Ursula","ursula@mail.com",
                LocalDate.of(2000,10,30),null,List.of("Cinema","Direção")));

        rede.cadastrarUsuario(new Usuario("u22","Victor","victor@mail.com",
                LocalDate.of(1999,4,18),null,List.of("Tecnologia","Hardware")));

        rede.cadastrarUsuario(new Usuario("u23","Wesley","wes@mail.com",
                LocalDate.of(2001,6,3),null,List.of("Games","Streaming")));

        rede.cadastrarUsuario(new Usuario("u24","Xavier","xavier@mail.com",
                LocalDate.of(1997,8,25),null,List.of("Fotografia","Natureza")));

        rede.cadastrarUsuario(new Usuario("u25","Yasmin","yas@mail.com",
                LocalDate.of(2002,9,9),null,List.of("Dança","Música")));

        rede.cadastrarUsuario(new Usuario("u26","Zeca","zeca@mail.com",
                LocalDate.of(1998,3,3),null,List.of("Humor","Podcast")));

        rede.cadastrarUsuario(new Usuario("u27","Alice","alice@mail.com",
                LocalDate.of(2000,11,11),null,List.of("UX","Design")));

        rede.cadastrarUsuario(new Usuario("u28","Breno","breno@mail.com",
                LocalDate.of(1999,1,1),null,List.of("Segurança","Redes")));

        rede.cadastrarUsuario(new Usuario("u29","Clara","clara@mail.com",
                LocalDate.of(2001,4,4),null,List.of("Leitura","Educação")));

        rede.cadastrarUsuario(new Usuario("u30","Diego","diego@mail.com",
                LocalDate.of(1997,6,6),null,List.of("Cinema","Roteiro")));

        // ================= AMIZADES =================
        rede.adicionarAmizade("u1","u2",0.9);
        rede.adicionarAmizade("u2","u3",0.8);
        rede.adicionarAmizade("u3","u1",0.7);
        rede.adicionarAmizade("u1","u4",0.6);
        rede.adicionarAmizade("u2","u5",0.5);
        rede.adicionarAmizade("u1","u6",0.8);
        rede.adicionarAmizade("u1","u7",0.9); 
        rede.adicionarAmizade("u4","u11",0.4);
        rede.adicionarAmizade("u11","u12",0.9);
        rede.adicionarAmizade("u12","u13",0.7);
        rede.adicionarAmizade("u13","u14",0.8);
        rede.adicionarAmizade("u14","u15",0.6);
        rede.adicionarAmizade("u11","u15",0.5);
        rede.adicionarAmizade("u12","u16",0.4);
        rede.adicionarAmizade("u16","u17",0.9);
        rede.adicionarAmizade("u17","u18",0.8);
        rede.adicionarAmizade("u18","u19",0.7);
        rede.adicionarAmizade("u19","u20",0.6);
        rede.adicionarAmizade("u16","u18",0.9);
        rede.adicionarAmizade("u18","u21",0.4);
        rede.adicionarAmizade("u21","u22",0.8);
        rede.adicionarAmizade("u22","u23",0.7);
        rede.adicionarAmizade("u23","u24",0.6);
        rede.adicionarAmizade("u24","u25",0.5);
        rede.adicionarAmizade("u25","u26",0.7);
        rede.adicionarAmizade("u26","u27",0.6);
        rede.adicionarAmizade("u27","u28",0.8);
        rede.adicionarAmizade("u28","u29",0.9);
        rede.adicionarAmizade("u29","u30",0.7);
    }
}
