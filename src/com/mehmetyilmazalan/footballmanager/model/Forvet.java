package com.mehmetyilmazalan.footballmanager.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Forvet extends Futbolcu {
    public Forvet(int futbolcuId, String adSoyad, LocalDate dogumTarihi, int formaNumarasi, BigDecimal piyasaDegeri) {
        super(futbolcuId,adSoyad,dogumTarihi,formaNumarasi,piyasaDegeri);
    }

    @Override
    public double performansPuaniHesapla(FutbolcuMacIstatistigi macIstatistigi){
        double golPuani=macIstatistigi.getGolSayisi()*5;
        double asistPuani=macIstatistigi.getAsistSayisi()*3;
        double isabetliSutPuani=macIstatistigi.getIsabetliSutSayisi()*1;
        double isabetsizSutPuani=(macIstatistigi.getSutSayisi()-macIstatistigi.getIsabetliSutSayisi())*-0.5;
        return golPuani+asistPuani+isabetliSutPuani+isabetsizSutPuani;
    }


}
