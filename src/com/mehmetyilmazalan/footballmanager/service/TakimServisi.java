package com.mehmetyilmazalan.footballmanager.service;

import com.mehmetyilmazalan.footballmanager.model.Futbolcu;
import com.mehmetyilmazalan.footballmanager.model.Takim;
import com.mehmetyilmazalan.footballmanager.repository.Repository;

import java.math.BigDecimal;
import java.util.List;

public class TakimServisi {
    private final Repository<Takim, Integer> takimRepository;

    public TakimServisi(Repository<Takim, Integer> takimRepository) {
        this.takimRepository = takimRepository;
    }

    public void takimaFutbolcuEkle(int takimId, Futbolcu futbolcu){
        takimRepository.idIleGetir(takimId).futbolcuEkle(futbolcu);
    }

    public void takimdanFutbolcuCikar(int takimId,int futbolcuId){
        takimRepository.idIleGetir(takimId).futbolcuCikar(futbolcuId);
    }

    public List<Futbolcu> kadroyuGetir(int takimId) {
        return takimRepository.idIleGetir(takimId).kadroyuGetir();
    }

    public void formaNumarasiDegistir(
            int takimId,
            int futbolcuId,
            int yeniFormaNumarasi
    ) {
        takimRepository.idIleGetir(takimId)
                .formaNumarasiDegistir(futbolcuId, yeniFormaNumarasi);
    }

    public void piyasaDegeriDegistir(
            int takimId,
            int futbolcuId,
            BigDecimal yeniPiyasaDegeri
    ) {
        Futbolcu futbolcu = takimRepository.idIleGetir(takimId)
                .idIleFutbolcuGetir(futbolcuId);

        futbolcu.setPiyasaDegeri(yeniPiyasaDegeri);
    }


}
