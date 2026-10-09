package com.mehmetyilmazalan.footballmanager.repository;

import com.mehmetyilmazalan.footballmanager.exception.MacBulunamadiException;
import com.mehmetyilmazalan.footballmanager.exception.MacZatenKayitliException;
import com.mehmetyilmazalan.footballmanager.model.Mac;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class MacRepository implements Repository<Mac,Integer> {
    private final Map<Integer, Mac> macKayitlari = new HashMap<>();

    @Override
    public void ekle(Mac mac){
        if(macKayitlari.containsKey(mac.getMacId())){
            throw new MacZatenKayitliException("Bu maç daha önceden kaydedilmiş.!");
        }
        macKayitlari.put(mac.getMacId(), mac);
    }

    @Override
    public Mac idIleGetir(Integer macId){
        if(!macKayitlari.containsKey(macId)){
            throw new MacBulunamadiException("Böyle bir maç kaydedilmemiş.!");
        }
        return macKayitlari.get(macId);
    }

    @Override
    public void sil(Integer macId){
        if(!macKayitlari.containsKey(macId)){
            throw new MacBulunamadiException("Silinecek maç bulunamadı");
        }
        macKayitlari.remove(macId);
    }

    @Override
    public void guncelle(Mac mac) {
        if (!macKayitlari.containsKey(mac.getMacId())) {
            throw new MacBulunamadiException("Güncellenecek maç bulunamadı.");
        }

        macKayitlari.replace(mac.getMacId(), mac);
    }

    @Override
    public List<Mac> tumunuGetir() {
        return new ArrayList<>(macKayitlari.values());
    }


    //MacRepository Testleri



}
