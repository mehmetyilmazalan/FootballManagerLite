package com.mehmetyilmazalan.footballmanager.service;

import com.mehmetyilmazalan.footballmanager.model.FutbolcuMacIstatistigi;
import com.mehmetyilmazalan.footballmanager.model.Mac;
import com.mehmetyilmazalan.footballmanager.model.Takim;
import com.mehmetyilmazalan.footballmanager.repository.Repository;

import java.time.LocalDate;
import java.util.List;

public class MacServisi {
    private final Repository<Mac ,Integer> macRepository;
    private final Repository<Takim, Integer> takimRepository;


    public MacServisi(Repository<Mac, Integer> macRepository, Repository<Takim, Integer> takimRepository) {
        this.macRepository = macRepository;
        this.takimRepository = takimRepository;
    }

    public void macPlanla(Integer macId, Integer evSahibiTakimId, Integer deplasmanTakimId, LocalDate macTarihi){
        Mac mac=new Mac(macId,takimRepository.idIleGetir(evSahibiTakimId),takimRepository.idIleGetir(deplasmanTakimId),macTarihi);
        macRepository.ekle(mac);

    }

    public void macSonucuKaydet(Integer macId,Integer evSahibiTakimGolSayisi,Integer deplasmanTakimiGolSayisi ){
        macRepository.idIleGetir(macId).macKaydet(evSahibiTakimGolSayisi,deplasmanTakimiGolSayisi);
    }

    public void macIstatistigiEkle(Integer macId, FutbolcuMacIstatistigi futbolcuMacIstatistigi){

        macRepository.idIleGetir(macId).istatistikEkle(futbolcuMacIstatistigi);

    }

    public List<FutbolcuMacIstatistigi> macIstatistikleriniGetir(Integer macId) {
        return macRepository.idIleGetir(macId).istatistikleriGetir();
    }
}
