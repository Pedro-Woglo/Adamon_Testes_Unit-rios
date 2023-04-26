package tech.ada.adamon.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import tech.ada.adamon.model.Adamon;
import tech.ada.adamon.model.Jogador;

import java.util.Arrays;
import java.util.List;

public class TestUtils {

    private TestUtils() {}
    public static List<Adamon> obterAdamons() {
        Adamon adamon = obterAdamon();
        return Arrays.asList(adamon, adamon, adamon, adamon, adamon, adamon);
    }

    public static Adamon obterAdamon() {
        Adamon adamon = new Adamon();
        adamon.setDefesa(30);
        adamon.setAtaque(50);
        adamon.setVelocidade(30);
        adamon.setInteligencia(40);
        adamon.setPoder(20);
        adamon.setVida(80);
        adamon.setNome("Adachu");
        adamon.setUrlFoto("www.foto.com.br/adachu");
        return adamon;
    }

    public static Jogador obterJogador() {
        Jogador jogador = new Jogador();
        jogador.setId(1L);
        jogador.setNickname("Rodolfo");
        jogador.setPassword("12345678");
        return jogador;
    }

    public static String jsonAsString(Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível converter o objeto");
        }
    }
}
