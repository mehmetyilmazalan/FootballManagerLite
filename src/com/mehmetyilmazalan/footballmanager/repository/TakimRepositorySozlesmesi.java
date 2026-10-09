package com.mehmetyilmazalan.footballmanager.repository;

import com.mehmetyilmazalan.footballmanager.model.Takim;

import java.util.List;

public interface TakimRepositorySozlesmesi {

    void takimEkle(Takim takim);

    Takim idIleTakimGetir(int takimId);

     void takimSil(int takimId);

    void takimGuncelle(Takim takim);

    List<Takim> tumTakimlariGetir();

}