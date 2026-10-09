package com.mehmetyilmazalan.footballmanager.model;

import com.mehmetyilmazalan.footballmanager.exception.GecersizMacSonucuException;

public class PuanDurumu {
    private Takim takim;
    private int galibiyetSayisi;
    private int beraberlikSayisi;
    private int maglubiyetSayisi;
    private int atilanGolSayisi;
    private int yenilenGolSayisi;

    public PuanDurumu(Takim takim){
        this.takim=takim;
    }

    public Takim getTakim() {
        return takim;
    }

    public int getGalibiyetSayisi() {
        return galibiyetSayisi;
    }

    public int getBeraberlikSayisi() {
        return beraberlikSayisi;
    }

    public int getMaglubiyetSayisi() {
        return maglubiyetSayisi;
    }

    public int getAtilanGolSayisi() {
        return atilanGolSayisi;
    }

    public int getYenilenGolSayisi() {
        return yenilenGolSayisi;
    }

    public int getPuan(){
        int alinanPuan=galibiyetSayisi*3 + beraberlikSayisi*1;
        return alinanPuan;
    }

    public int getAveraj(){
        int eldeEdilenAveraj=atilanGolSayisi-yenilenGolSayisi;
        return eldeEdilenAveraj;
    }

    public int getTakiminOynanadigiMacSayisi(){
        int oynananMacSayisi=galibiyetSayisi+beraberlikSayisi+maglubiyetSayisi;
        return  oynananMacSayisi;
    }

    public void macSonucuIsle(int atilanGol, int yenilenGol){
        if(atilanGol<0 || yenilenGol < 0){
            throw new GecersizMacSonucuException("Gol sayısı negatif olamaz ! ");
        }

        if(atilanGol>yenilenGol){
            galibiyetSayisi++;
        } else if (atilanGol==yenilenGol) {
            beraberlikSayisi++;
        }else{
            maglubiyetSayisi++;
        }

        atilanGolSayisi+=atilanGol;
        yenilenGolSayisi+=yenilenGol;
    }

}


