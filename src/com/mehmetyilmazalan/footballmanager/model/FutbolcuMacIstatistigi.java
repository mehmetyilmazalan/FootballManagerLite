package com.mehmetyilmazalan.footballmanager.model;

public class FutbolcuMacIstatistigi {

    private final Futbolcu futbolcu;
    private int oynananDakika;

    private int golSayisi;
    private int asistSayisi;

    private int basariliPasSayisi;
    private int basarisizPasSayisi;

    private int sutSayisi;
    private int isabetliSutSayisi;

    private int topKapmaSayisi;
    private int topUzaklastirmaSayisi;
    private int kazanilanIkiliMucadeleSayisi;

    private int kurtarisSayisi;
    private int yenilenGolSayisi;

    private Mac mac;

    public FutbolcuMacIstatistigi(Futbolcu futbolcu,Mac mac, int oynananDakika, int golSayisi, int asistSayisi, int basariliPasSayisi, int basarisizPasSayisi, int sutSayisi, int isabetliSutSayisi, int topKapmaSayisi, int topUzaklastirmaSayisi, int kazanilanIkiliMucadeleSayisi, int kurtarisSayisi, int yenilenGolSayisi) {
        this.mac=mac;
        this.futbolcu = futbolcu;
        this.oynananDakika = oynananDakika;
        this.golSayisi = golSayisi;
        this.asistSayisi = asistSayisi;
        this.basariliPasSayisi = basariliPasSayisi;
        this.basarisizPasSayisi = basarisizPasSayisi;
        this.sutSayisi = sutSayisi;
        this.isabetliSutSayisi = isabetliSutSayisi;
        this.topKapmaSayisi = topKapmaSayisi;
        this.topUzaklastirmaSayisi = topUzaklastirmaSayisi;
        this.kazanilanIkiliMucadeleSayisi = kazanilanIkiliMucadeleSayisi;
        this.kurtarisSayisi = kurtarisSayisi;
        this.yenilenGolSayisi = yenilenGolSayisi;
    }

    public Futbolcu getFutbolcu() {
        return futbolcu;
    }


    public Mac getMac() {return mac;}

    public int getOynananDakika() {
        return oynananDakika;
    }

    public void setOynananDakika(int oynananDakika) {
        this.oynananDakika = oynananDakika;
    }

    public int getGolSayisi() {
        return golSayisi;
    }

    public void setGolSayisi(int golSayisi) {
        this.golSayisi = golSayisi;
    }

    public int getAsistSayisi() {
        return asistSayisi;
    }

    public void setAsistSayisi(int asistSayisi) {
        this.asistSayisi = asistSayisi;
    }

    public int getBasariliPasSayisi() {
        return basariliPasSayisi;
    }

    public void setBasariliPasSayisi(int basariliPasSayisi) {
        this.basariliPasSayisi = basariliPasSayisi;
    }

    public int getBasarisizPasSayisi() {
        return basarisizPasSayisi;
    }

    public void setBasarisizPasSayisi(int basarisizPasSayisi) {
        this.basarisizPasSayisi = basarisizPasSayisi;
    }

    public int getSutSayisi() {
        return sutSayisi;
    }

    public void setSutSayisi(int sutSayisi) {
        this.sutSayisi = sutSayisi;
    }

    public int getIsabetliSutSayisi() {
        return isabetliSutSayisi;
    }

    public void setIsabetliSutSayisi(int isabetliSutSayisi) {
        this.isabetliSutSayisi = isabetliSutSayisi;
    }

    public int getTopKapmaSayisi() {
        return topKapmaSayisi;
    }

    public void setTopKapmaSayisi(int topKapmaSayisi) {
        this.topKapmaSayisi = topKapmaSayisi;
    }

    public int getTopUzaklastirmaSayisi() {
        return topUzaklastirmaSayisi;
    }

    public void setTopUzaklastirmaSayisi(int topUzaklastirmaSayisi) {
        this.topUzaklastirmaSayisi = topUzaklastirmaSayisi;
    }

    public int getKazanilanIkiliMucadeleSayisi() {
        return kazanilanIkiliMucadeleSayisi;
    }

    public void setKazanilanIkiliMucadeleSayisi(int kazanilanIkiliMucadeleSayisi) {
        this.kazanilanIkiliMucadeleSayisi = kazanilanIkiliMucadeleSayisi;
    }

    public int getKurtarisSayisi() {
        return kurtarisSayisi;
    }

    public void setKurtarisSayisi(int kurtarisSayisi) {
        this.kurtarisSayisi = kurtarisSayisi;
    }

    public int getYenilenGolSayisi() {
        return yenilenGolSayisi;
    }

    public void setYenilenGolSayisi(int yenilenGolSayisi) {
        this.yenilenGolSayisi = yenilenGolSayisi;
    }


}