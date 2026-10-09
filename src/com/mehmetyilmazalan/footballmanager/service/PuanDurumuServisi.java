package com.mehmetyilmazalan.footballmanager.service;

import com.mehmetyilmazalan.footballmanager.model.Mac;
import com.mehmetyilmazalan.footballmanager.model.PuanDurumu;
import com.mehmetyilmazalan.footballmanager.model.Takim;
import com.mehmetyilmazalan.footballmanager.repository.Repository;

import java.util.*;

public class PuanDurumuServisi {

    private final Repository<Mac ,Integer> macRepository;
    private final Repository<Takim, Integer> takimRepository;

    public PuanDurumuServisi(Repository<Mac, Integer> macRepository, Repository<Takim, Integer> takimRepository) {
        this.macRepository = macRepository;
        this.takimRepository = takimRepository;
    }

    public List<PuanDurumu> puanTablosunuHesapla(){
        Map<Integer, PuanDurumu> puanDurumlari = new HashMap<>();
        for(Takim takim : takimRepository.tumunuGetir()){
            puanDurumlari.put(takim.getTakimId(),new PuanDurumu(takim));
        }
        for(Mac mac : macRepository.tumunuGetir()){
            if(mac.isTamamlandi()){
                PuanDurumu evSahibiPuanDurumu = puanDurumlari.get(mac.getEvSahibiTakim().getTakimId());
                PuanDurumu deplasmanPuanDurumu = puanDurumlari.get(mac.getDeplasmanTakimi().getTakimId());

                evSahibiPuanDurumu.macSonucuIsle(mac.getEvSahibiGolSayisi(),mac.getDeplasmanGolSayisi());
                deplasmanPuanDurumu.macSonucuIsle(mac.getDeplasmanGolSayisi(),mac.getEvSahibiGolSayisi());

            }
        }

        List<PuanDurumu> tablo = new ArrayList<>(puanDurumlari.values());

        tablo.sort(
                Comparator.comparingInt(PuanDurumu::getPuan).reversed()
                        .thenComparing(
                                Comparator.comparingInt(PuanDurumu::getAveraj).reversed()
                        )
        );


        return tablo;
    }
}
