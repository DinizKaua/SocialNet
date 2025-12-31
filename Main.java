import Sistema.AnalisadorRedeSocial;
import Sistema.DatasetSocialNet;
import Sistema.RedeSocial;
import Sistema.SocialGraphMenu;

public class Main {
public static void main(String[] args) {
        RedeSocial rede = new RedeSocial();
        DatasetSocialNet.carregar(rede);

        AnalisadorRedeSocial analisador =
            new AnalisadorRedeSocial(rede.getGrafo());

        new SocialGraphMenu(rede, analisador).executar();
    }
}
