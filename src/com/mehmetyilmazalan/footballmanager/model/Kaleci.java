package com.mehmetyilmazalan.footballmanager.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Kaleci extends Futbolcu {

    public Kaleci(
                  int futbolcuId,
                  String adSoyad,
                  LocalDate dogumTarihi,
                  int formaNumarasi,
                  BigDecimal piyasaDegeri) {
        super(
                futbolcuId,
                adSoyad,
                dogumTarihi,
                formaNumarasi,
                piyasaDegeri
        );
    }

    @Override
    public double performansPuaniHesapla(FutbolcuMacIstatistigi macIstatistigi){
        double kurtarisPuani=macIstatistigi.getKurtarisSayisi()*1;
        double yenilenGolPuani=macIstatistigi.getYenilenGolSayisi()*-3;
        return kurtarisPuani+yenilenGolPuani;
    }
}
