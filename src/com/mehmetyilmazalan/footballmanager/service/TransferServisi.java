package com.mehmetyilmazalan.footballmanager.service;

import com.mehmetyilmazalan.footballmanager.exception.FormaNumarasiKullanimdaException;
import com.mehmetyilmazalan.footballmanager.exception.FutbolcuZatenKadrodaException;
import com.mehmetyilmazalan.footballmanager.exception.GecersizTransferException;
import com.mehmetyilmazalan.footballmanager.exception.KadroDoluException;
import com.mehmetyilmazalan.footballmanager.model.Futbolcu;
import com.mehmetyilmazalan.footballmanager.model.Takim;
import com.mehmetyilmazalan.footballmanager.repository.Repository;

import java.math.BigDecimal;

public class TransferServisi {

    private final Repository<Takim, Integer> takimRepository;

    public TransferServisi(Repository<Takim, Integer> takimRepository) {
        this.takimRepository = takimRepository;
    }


    public void transferEt(
             int satanTakimId,
             int alanTakimId,
             int transferEdilecekFutbolcuId,
             BigDecimal transferBedeli
             )
     {
         if(satanTakimId==alanTakimId){
             throw new GecersizTransferException("Satan ve alan takım aynı olamaz.");
         }

         Takim satanTakim = takimRepository.idIleGetir(satanTakimId);
         Takim alanTakim = takimRepository.idIleGetir(alanTakimId);

         Futbolcu satilacakFutbolcu= satanTakim.idIleFutbolcuGetir(transferEdilecekFutbolcuId);
         alanTakim.butceDusur(transferBedeli);
        try{
            alanTakim.futbolcuEkle(satilacakFutbolcu);
        } catch (KadroDoluException
                 | FutbolcuZatenKadrodaException
                 | FormaNumarasiKullanimdaException exception) {
            alanTakim.butceArttir(transferBedeli);
            throw exception;
        }

        satanTakim.futbolcuCikar(transferEdilecekFutbolcuId);
        satanTakim.butceArttir(transferBedeli);

     }

}
