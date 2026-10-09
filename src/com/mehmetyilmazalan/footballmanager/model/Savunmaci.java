package com.mehmetyilmazalan.footballmanager.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Savunmaci extends Futbolcu{
    public Savunmaci(int futbolcuId, String adSoyad, LocalDate dogumTarihi, int formaNumarasi, BigDecimal piyasaDegeri) {
        super(futbolcuId, adSoyad, dogumTarihi, formaNumarasi, piyasaDegeri);
    }

    @Override
    public double performansPuaniHesapla(FutbolcuMacIstatistigi macIstatistigi){
        double golPuani=macIstatistigi.getGolSayisi()*5;
        double asistPuani=macIstatistigi.getAsistSayisi()*3;
        double topKapmaPuani=macIstatistigi.getTopKapmaSayisi()*1;
        double topUzaklastirmaPuani=macIstatistigi.getTopUzaklastirmaSayisi()*0.5;
        double ikiliMucadelePuani=macIstatistigi.getKazanilanIkiliMucadeleSayisi()*0.5;

        return golPuani+asistPuani+topKapmaPuani+topUzaklastirmaPuani+ikiliMucadelePuani;
    }
}
