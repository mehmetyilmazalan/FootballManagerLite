package com.mehmetyilmazalan.footballmanager.model;

import com.mehmetyilmazalan.footballmanager.exception.FutbolcuIstatistigiZatenKayitliException;
import com.mehmetyilmazalan.footballmanager.exception.GecersizMacException;
import com.mehmetyilmazalan.footballmanager.exception.GecersizMacSonucuException;
import com.mehmetyilmazalan.footballmanager.exception.MacZatenTamamlandiException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Formatter;

public class Mac {
    private int macId;
    private Takim evSahibiTakim;
    private Takim deplasmanTakimi;
    private LocalDate macTarihi;
    private int evSahibiGolSayisi;
    private int deplasmanGolSayisi;
    private boolean tamamlandi;



    private final List<FutbolcuMacIstatistigi> macIstatistikleri = new ArrayList<>();

    public Mac(int macId, Takim evSahibiTakim, Takim deplasmanTakimi, LocalDate macTarihi) {

        if(evSahibiTakim.getTakimId()==deplasmanTakimi.getTakimId()){
            throw new GecersizMacException("Bir takım kendisiyle maç yapamaz.");
        }
        this.macId = macId;
        this.evSahibiTakim = evSahibiTakim;
        this.deplasmanTakimi = deplasmanTakimi;
        this.macTarihi = macTarihi;
    }

    public Takim getEvSahibiTakim() {
        return evSahibiTakim;
    }

    public Takim getDeplasmanTakimi() {
        return deplasmanTakimi;
    }

    public void macKaydet(int evSahibiGolSayisi, int deplasmanGolSayisi){
        if(tamamlandi){
            throw new MacZatenTamamlandiException("Önceden tamamlanan Maç !!!");
        }if(evSahibiGolSayisi<0 || deplasmanGolSayisi<0){
            throw new GecersizMacSonucuException("Gol sayıları negatif olamaz. !!");
        }
        this.evSahibiGolSayisi=evSahibiGolSayisi;
        this.deplasmanGolSayisi=deplasmanGolSayisi;
        this.tamamlandi=true;

    }

    public int getMacId() {
        return macId;
    }

    public int getEvSahibiGolSayisi() {
        return evSahibiGolSayisi;
    }

    public int getDeplasmanGolSayisi() {
        return deplasmanGolSayisi;
    }

    public boolean isTamamlandi() {
        return tamamlandi;
    }

    public LocalDate getMacTarihi() {
        return macTarihi;
    }

    public void istatistikEkle(FutbolcuMacIstatistigi futbolcuMacIstatistigi){

        if(futbolcuMacIstatistigi.getMac().getMacId()!=this.macId){
            throw new GecersizMacException("Yanlış Maçın İstatistiğine Ekleme Ypmaya Çalıştınız !");
        }

        for(FutbolcuMacIstatistigi mevcutIstatistik  : macIstatistikleri){
          if (mevcutIstatistik.getFutbolcu().getFutbolcuId()
                    == futbolcuMacIstatistigi.getFutbolcu().getFutbolcuId()) {
                throw new FutbolcuIstatistigiZatenKayitliException(
                        "Bu futbolcunun maç istatistiği zaten kayıtlı!"
                );
            }
        }
        macIstatistikleri.add(futbolcuMacIstatistigi);

    }

    public List<FutbolcuMacIstatistigi> istatistikleriGetir() {

        List<FutbolcuMacIstatistigi> istatistikler=new ArrayList<>();
        for(FutbolcuMacIstatistigi istatistik : macIstatistikleri){
            istatistikler.add(istatistik);
        }
        return  istatistikler;

    }


}
