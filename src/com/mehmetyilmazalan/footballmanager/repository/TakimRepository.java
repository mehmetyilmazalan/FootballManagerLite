package com.mehmetyilmazalan.footballmanager.repository;

import com.mehmetyilmazalan.footballmanager.exception.TakimBulunamadiException;
import com.mehmetyilmazalan.footballmanager.exception.TakimZatenKayitliException;
import com.mehmetyilmazalan.footballmanager.model.Takim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TakimRepository implements Repository<Takim,Integer>{

    private final Map<Integer, Takim> takimKayitlari = new HashMap<>();

    @Override
    public void ekle(Takim takim){
        if(takimKayitlari.containsKey(takim.getTakimId())){
            throw new TakimZatenKayitliException("Bu ID ile kayıtlı bir takım zaten var.");

        }
        takimKayitlari.put(takim.getTakimId(), takim);
    }

    public Takim idIleGetir(Integer takimId){
        if(!takimKayitlari.containsKey(takimId)){
            throw new TakimBulunamadiException("Bu ID ile kayıtlı bir takım bulunamadı.");
        }
        return takimKayitlari.get(takimId);
    }

    public void sil(Integer takimId){
        if(!takimKayitlari.containsKey(takimId)){
            throw new TakimBulunamadiException("Silinecek takım bulunamadı");
        }
        takimKayitlari.remove(takimId);
    }

    @Override
    public void guncelle(Takim takim){
        if(!takimKayitlari.containsKey(takim.getTakimId())){
            throw new TakimBulunamadiException("Güncellenecek takım bulunamadı");
        }
        takimKayitlari.replace(takim.getTakimId(), takim);
    }

    @Override
    public List<Takim> tumunuGetir(){
        return  new ArrayList<>(takimKayitlari.values());
    }

}
