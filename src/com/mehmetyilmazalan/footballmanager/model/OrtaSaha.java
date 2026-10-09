package com.mehmetyilmazalan.footballmanager.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OrtaSaha extends Futbolcu{
    public OrtaSaha(int futbolcuId, String adSoyad, LocalDate dogumTarihi, int formaNumarasi, BigDecimal piyasaDegeri) {
        super(futbolcuId, adSoyad, dogumTarihi, formaNumarasi, piyasaDegeri);
    }

    @Override
    public double performansPuaniHesapla(FutbolcuMacIstatistigi macIstatistigi){
        double golPuani=macIstatistigi.getGolSayisi()*5;
        double asistPuani=macIstatistigi.getAsistSayisi()*3;
        double basariliPasPuani=macIstatistigi.getBasariliPasSayisi()*0.05;
        double basarisizPasPuan=macIstatistigi.getBasarisizPasSayisi()*-0.1;
        double topKapmaPuani=macIstatistigi.getTopKapmaSayisi()*0.5;


        return golPuani+asistPuani+basariliPasPuani+basarisizPasPuan+topKapmaPuani;
    }
}
