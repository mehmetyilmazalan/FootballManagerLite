package com.mehmetyilmazalan.footballmanager.model;

import com.mehmetyilmazalan.footballmanager.exception.GecersizParaMiktariException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

public abstract class Futbolcu {
    private final int futbolcuId;
    private final String adSoyad;
    private final LocalDate dogumTarihi;
    private int formaNumarasi;
    private BigDecimal piyasaDegeri;


    //Protected yapınca constructor ın doğrudan alt sınıflar tarafından kullanılacağı belirtmiş oluyorum.
    protected Futbolcu(int futbolcuId, String adSoyad, LocalDate dogumTarihi, int formaNumarasi, BigDecimal piyasaDegeri) {
        this.futbolcuId = futbolcuId;
        this.adSoyad = adSoyad;
        this.dogumTarihi = dogumTarihi;
        this.formaNumarasi = formaNumarasi;
        this.piyasaDegeri = piyasaDegeri;
    }

    public int getFutbolcuId() {return futbolcuId;}

    public String getAdSoyad() {return adSoyad;}

    public LocalDate getDogumTarihi() {return dogumTarihi;}

    public int getFormaNumarasi() {return formaNumarasi;}

    public void setFormaNumarasi(int formaNumarasi) {
        if (formaNumarasi < 1 || formaNumarasi > 99) {
            throw new IllegalArgumentException(
                    "Forma numarası 1 ile 99 arasında olmalıdır."
            );
        }

        this.formaNumarasi = formaNumarasi;
    }
    public BigDecimal getPiyasaDegeri() {return piyasaDegeri;}

    public void setPiyasaDegeri(BigDecimal piyasaDegeri) {
        if (piyasaDegeri == null
                || piyasaDegeri.compareTo(BigDecimal.ZERO) < 0) {
            throw new GecersizParaMiktariException(
                    "Piyasa değeri boş veya negatif olamaz."
            );
        }

        this.piyasaDegeri = piyasaDegeri;
    }
    public int getYas(){return Period.between(dogumTarihi, LocalDate.now()).getYears();}

    public abstract double performansPuaniHesapla(FutbolcuMacIstatistigi macIstatistigi);


}
