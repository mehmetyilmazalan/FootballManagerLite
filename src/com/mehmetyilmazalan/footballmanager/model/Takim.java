package com.mehmetyilmazalan.footballmanager.model;

import com.mehmetyilmazalan.footballmanager.exception.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Takim {
    private final int takimId;
    private final String takimAdi;
    private BigDecimal transferButcesi;
    private final List<Futbolcu> takimdakiFutbolcular=new ArrayList<>();

    public Takim(int takimId, String takimAdi, BigDecimal transferButcesi) {
        this.takimId = takimId;
        this.takimAdi = takimAdi;
        this.transferButcesi = transferButcesi;
    }

    public int getTakimId() {
        return takimId;
    }

    public String getTakimAdi() {
        return takimAdi;
    }

    public BigDecimal getTransferButcesi() {
        return transferButcesi;
    }

    public void futbolcuEkle(Futbolcu futbolcu){
        if(takimdakiFutbolcular.size()>=24){
            throw new KadroDoluException("Takım kadrosu dolu.En fazla 24 futbolcu eklenebilir .");
        }

        for(Futbolcu mevcutFutbolcu : takimdakiFutbolcular){
            if(mevcutFutbolcu.getFutbolcuId()==futbolcu.getFutbolcuId()){
                throw new FutbolcuZatenKadrodaException("Bu futbolcu zaten takım kadrosunda bulunuyor.");

            }
        }

        for(Futbolcu mevcutFutbolcu : takimdakiFutbolcular){
            if(mevcutFutbolcu.getFormaNumarasi()==futbolcu.getFormaNumarasi()){
                throw new FormaNumarasiKullanimdaException("Bu forma numarası mevut bir oyuncuya ait.Başka numara seçiniz..!");
            }
        }
        takimdakiFutbolcular.add(futbolcu);
    }

    public Futbolcu futbolcuCikar(int futbolcuId){
        for(int i=0;i<takimdakiFutbolcular.size();i++){
            Futbolcu mevcutFutbolcu = takimdakiFutbolcular.get(i);
            if (mevcutFutbolcu.getFutbolcuId() == futbolcuId) {
                return takimdakiFutbolcular.remove(i);
            }
        }

        throw  new FutbolcuKadrodaBulunamadiException("Çıkarılacak oyuncu kadroda bulunmuyor");

    }

    public BigDecimal takimDegeriHesapla(){
        BigDecimal takimDegeri=BigDecimal.ZERO;
        for(Futbolcu futbolcuDegeri:takimdakiFutbolcular){
           takimDegeri= takimDegeri.add(futbolcuDegeri.getPiyasaDegeri());
        }
        return takimDegeri;
    }


    public void butceDusur(BigDecimal harcananPara){

        if (harcananPara.compareTo(BigDecimal.ZERO)<=0) {
            throw new GecersizParaMiktariException("Eksik veya hatalı tutar girdiniz kontrol ediniz.");
        } else if(transferButcesi.compareTo(harcananPara)<0) {
            throw new YetersizButceException("Transfere harcanacak para transfer bütçesinden fazladır.");
        }else{
            BigDecimal sonButce = transferButcesi.subtract(harcananPara);
            this.transferButcesi = sonButce;
        }
    }

    public void butceArttir(BigDecimal alinanPara){
        if(alinanPara.compareTo(BigDecimal.ZERO)<=0){
            throw new GecersizParaMiktariException("Eksik veya hatalı tutar girdiniz kontrol ediniz.");
        }else{
            BigDecimal sonButce = transferButcesi.add(alinanPara);
            this.transferButcesi=sonButce;
        }

    }


    public Futbolcu idIleFutbolcuGetir(int futbolcuId){
        for(int i=0;i<takimdakiFutbolcular.size();i++){
            Futbolcu mevcutFutbolcu=takimdakiFutbolcular.get(i);
            if(mevcutFutbolcu.getFutbolcuId()==futbolcuId){
                return mevcutFutbolcu;
            }
        }
        throw new FutbolcuKadrodaBulunamadiException("Transfer edilmek istenen futbolcu kadroda bulunmuyor.");
    }

    public List<Futbolcu> kadroyuGetir() {
        return new ArrayList<>(takimdakiFutbolcular);
    }

    public void formaNumarasiDegistir(int futbolcuId, int yeniFormaNumarasi) {
        Futbolcu futbolcu = idIleFutbolcuGetir(futbolcuId);

        for (Futbolcu mevcutFutbolcu : takimdakiFutbolcular) {
            if (mevcutFutbolcu.getFutbolcuId() != futbolcuId
                    && mevcutFutbolcu.getFormaNumarasi() == yeniFormaNumarasi) {
                throw new FormaNumarasiKullanimdaException(
                        "Bu forma numarası başka bir futbolcu tarafından kullanılıyor."
                );
            }
        }

        futbolcu.setFormaNumarasi(yeniFormaNumarasi);
    }






}
