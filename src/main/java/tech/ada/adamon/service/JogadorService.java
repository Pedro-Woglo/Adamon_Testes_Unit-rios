package tech.ada.adamon.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.ada.adamon.dto.SalvarJogadorDTO;
import tech.ada.adamon.dto.converter.JogadorDtoConverter;
import tech.ada.adamon.model.Adamon;
import tech.ada.adamon.model.Jogador;
import tech.ada.adamon.repository.JogadorRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class JogadorService {

    @Autowired
    private JogadorRepository jogadorRepository;

    public void batalhar(Jogador jogador1, Jogador jogador2) {

        List<Adamon> equipeJogador1 = jogador1.getAdamons();
        List<Adamon> equipeJogador2 = jogador2.getAdamons();

        int adamonAtual1 = 0;
        int adamonAtual2 = 0;

        while(true){

            if(adamonAtual2 > 5){
                System.out.println(jogador1.getNickname() + " é o vencedor da batalha!");
                break;
            }

            if(adamonAtual1 > 5){
                System.out.println(jogador2.getNickname() + " é o vencedor da batalha!");
                break;
            }
            
            Adamon adamonEquipe1 = equipeJogador1.get(adamonAtual1);
            Adamon adamonEquipe2 = equipeJogador2.get(adamonAtual2);

            adamonEquipe1.atacar((adamonEquipe2));

            if(adamonEquipe2.getVida() <= 0) {
                adamonAtual2++;
                continue;
            }

            adamonEquipe2.atacar((adamonEquipe1));

            if(adamonEquipe1.getVida() <= 0){
                adamonAtual1++;
            }

        }
    }
    
    public void comprarAdamon(Jogador jogador, Adamon adamon) {
        List<Adamon> equipeAdamonJogador = jogador.getAdamons();
        BigDecimal saldoAtual = jogador.getSaldo();
        BigDecimal precoAdamon = adamon.obterPreco();

        boolean possuiSaldoSuficiente = saldoAtual.compareTo(precoAdamon) > 0;
        boolean possuiEspacoNaEquipe = equipeAdamonJogador.size() < 6;

        if (possuiEspacoNaEquipe && possuiSaldoSuficiente) {
            equipeAdamonJogador.add(adamon);
            jogador.setSaldo(saldoAtual.subtract(precoAdamon));
            atualizarJogador(jogador, jogador.getId());
        } else if (!possuiSaldoSuficiente) {
            throw new RuntimeException("Não possui saldo suficiente");
        } else if (!possuiEspacoNaEquipe) {
            throw new RuntimeException("Não possui espaço na equipe");
        }
    }

    public void venderAdamon(Jogador comprador, Adamon adamon) {

    }

    public void atualizarJogador(Jogador jogador, Long idJogador) {
        encontrarJogadorPorId(idJogador);
        jogador.setId(idJogador);
        jogadorRepository.save(jogador);
    }

    public Jogador encontrarJogadorPorId(Long idJogador) {
        Optional<Jogador> optionalJogador = jogadorRepository.findById(idJogador);
        return optionalJogador
                .orElseThrow(() -> new RuntimeException("Não encontrado jogador com ID: " + idJogador));
    }

    public Jogador salvarJogador(SalvarJogadorDTO dto) {
        return jogadorRepository.save(JogadorDtoConverter.converterDto(dto));
    }



}
