package com.mehmetyilmazalan.footballmanager.application;

import com.mehmetyilmazalan.footballmanager.exception.*;
import com.mehmetyilmazalan.footballmanager.model.*;
import com.mehmetyilmazalan.footballmanager.repository.MacRepository;
import com.mehmetyilmazalan.footballmanager.repository.Repository;
import com.mehmetyilmazalan.footballmanager.repository.TakimRepository;
import com.mehmetyilmazalan.footballmanager.service.MacServisi;
import com.mehmetyilmazalan.footballmanager.service.PuanDurumuServisi;
import com.mehmetyilmazalan.footballmanager.service.TakimServisi;
import com.mehmetyilmazalan.footballmanager.service.TransferServisi;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FutbollManagerTestApplicaiton {
    public static void main(String[] args) {

        Repository<Takim, Integer> takimRepository = new TakimRepository();


        List<FutbolcuMacIstatistigi> macIstatistikleri = new ArrayList<>();

        Takim istatistikEvSahibi = new Takim(300, "Milan", BigDecimal.valueOf(50_000_000));

        Takim istatistikDeplasman = new Takim(400, "Napoli", BigDecimal.valueOf(50_000_000));

        takimRepository.ekle(istatistikEvSahibi);
        takimRepository.ekle(istatistikDeplasman);

        Mac istatistikMaci = new Mac(100, istatistikEvSahibi, istatistikDeplasman, LocalDate.of(2026, 10, 8));

        istatistikMaci.macKaydet(5, 0);

        Futbolcu forvet = new Forvet(9, "Dusan Vlahovic", LocalDate.of(2000, 3, 9), 28, new BigDecimal(80000000));
        FutbolcuMacIstatistigi forvetMacIstatistigi = new FutbolcuMacIstatistigi(
                forvet, istatistikMaci, 90, 3, 7, 15, 5, 10, 8, 3, 2, 8, 0, 0
        );
        macIstatistikleri.add(forvetMacIstatistigi);

        Futbolcu ortaSaha = new OrtaSaha(10, "Orkun Kökçü", LocalDate.of(2001, 7, 9), 10, new BigDecimal(10000000));
        FutbolcuMacIstatistigi ortaSahaMacIstatistigi = new FutbolcuMacIstatistigi(
                ortaSaha, istatistikMaci, 90, 2, 10, 30, 12, 10, 8, 8, 1, 8, 0, 0
        );
        macIstatistikleri.add(ortaSahaMacIstatistigi);

        Futbolcu savunmaci = new Savunmaci(
                3, "Emirhan Topçu", LocalDate.of(2000, 10, 11), 53, BigDecimal.valueOf(7_000_000)
        );
        FutbolcuMacIstatistigi savunmaciIstatistigi = new FutbolcuMacIstatistigi(
                savunmaci, istatistikMaci, 90, 0, 0, 45, 5, 0, 0, 4, 7, 6, 0, 0
        );
        macIstatistikleri.add(savunmaciIstatistigi);

        Futbolcu kaleci = new Kaleci(
                2, "Alexander Nübel", LocalDate.of(1996, 9, 30), 1, BigDecimal.valueOf(15_000_000)
        );
        FutbolcuMacIstatistigi kaleciIstatistigi = new FutbolcuMacIstatistigi(
                kaleci, istatistikMaci, 90, 0, 0, 35, 5, 0, 0, 0, 0, 0, 6, 1
        );
        macIstatistikleri.add(kaleciIstatistigi);

        for (FutbolcuMacIstatistigi MacIstatistigi : macIstatistikleri) {
            double futbolcuPuani = MacIstatistigi.getFutbolcu().performansPuaniHesapla(MacIstatistigi);
            System.out.println(
                    MacIstatistigi.getFutbolcu().getAdSoyad()
                            + " : "
                            + "Maç Id : "
                            + MacIstatistigi.getMac().getMacId()
                            + " : "
                            + "Maç Puanı : "
                            + futbolcuPuani
            );
        }


        Takim takim1 = new Takim(1, "Beşiktaş JK", new BigDecimal(100000000));
        try {
            takim1.futbolcuEkle(forvet);
            takim1.futbolcuEkle(forvet);
        } catch (FutbolcuZatenKadrodaException exception) {
            System.out.println(exception.getMessage());
        }

        try {
            Futbolcu cikarilanFutbolcu =
                    takim1.futbolcuCikar(forvet.getFutbolcuId());

            System.out.println(
                    "Kadrodan çıkarılan futbolcu: "
                            + cikarilanFutbolcu.getAdSoyad()
            );

        } catch (FutbolcuKadrodaBulunamadiException exception) {
            System.out.println(exception.getMessage());
        }

        try {
            takim1.futbolcuCikar(forvet.getFutbolcuId());

        } catch (FutbolcuKadrodaBulunamadiException exception) {
            System.out.println(exception.getMessage());
        }


        Takim evSahibiiTakim1 = new Takim(40, "Chelsa", new BigDecimal(50000000));
        Takim deplasmanTakimi1 = new Takim(50, "Inter", new BigDecimal(100000000));

        Mac mac1 = new Mac(1, evSahibiiTakim1, deplasmanTakimi1, LocalDate.of(2026, 10, 07));

        mac1.macKaydet(2, 1);
        try {
            mac1.macKaydet(3, 0);
        } catch (MacZatenTamamlandiException exception) {
            System.out.println("Maç skoru kaydetme hatası : " + exception.getMessage());
        }

        System.out.println("Ev Sahibi Gol Sayısı:" + mac1.getEvSahibiGolSayisi());
        System.out.println("Deplasman Gol Sayısı:" + mac1.getDeplasmanGolSayisi());
        System.out.println("Maç tamamlandı mı ? :" + mac1.isTamamlandi());

        Takim evSahibiiTakim2 = new Takim(60, "SiverekSpor", new BigDecimal(50000000));
        Takim deplasmanTakimi2 = new Takim(70, "SiverekGücü", new BigDecimal(100000000));

        Mac mac2 = new Mac(2, evSahibiiTakim2, deplasmanTakimi2, LocalDate.of(2026, 10, 11));
        try {
            mac2.macKaydet(-1, 5);
        } catch (GecersizMacSonucuException exception) {
            System.out.println("Mac sonucu kaydetme işlemi başarısız : " + exception.getMessage());
        }
        System.out.println("Ev Sahibi Gol Sayısı:" + mac2.getEvSahibiGolSayisi());
        System.out.println("Deplasman Gol Sayısı:" + mac2.getDeplasmanGolSayisi());
        System.out.println("Maç tamamlandı mı ? :" + mac2.isTamamlandi());

        Takim ayniIdliTakim1 = new Takim(80, "Test Takımı", BigDecimal.valueOf(50_000_000));

        Takim ayniIdliTakim2 = new Takim(80, "Test Takımı", BigDecimal.valueOf(50_000_000));

        try {
            Mac gecersizMac = new Mac(
                    3,
                    ayniIdliTakim1,
                    ayniIdliTakim2,
                    LocalDate.of(2026, 10, 12)
            );

            System.out.println("HATA: Aynı ID'li takımlarla maç oluşturuldu.");

        } catch (GecersizMacException exception) {
            System.out.println("Maç oluşturulamadı: " + exception.getMessage());
        }

        Mac beraberlikMaci = new Mac(4, evSahibiiTakim2, deplasmanTakimi2, LocalDate.of(2026, 10, 13));

        beraberlikMaci.macKaydet(0, 0);

        System.out.println(
                "Skor: "
                        + beraberlikMaci.getEvSahibiGolSayisi()
                        + "-"
                        + beraberlikMaci.getDeplasmanGolSayisi()
        );

        System.out.println("Maç tamamlandı mı?: " + beraberlikMaci.isTamamlandi());

        istatistikMaci.istatistikEkle(forvetMacIstatistigi);
        System.out.println("Forvetin istatistiği başarıyla eklendi.");

        istatistikMaci.istatistikEkle(ortaSahaMacIstatistigi);
        System.out.println("Orta sahanın istatistiği başarıyla eklendi.");

        try {
            istatistikMaci.istatistikEkle(forvetMacIstatistigi);
            System.out.println("HATA: Aynı futbolcunun istatistiği tekrar eklendi.");
        } catch (FutbolcuIstatistigiZatenKayitliException exception) {
            System.out.println("Tekrar ekleme başarısız: " + exception.getMessage());
        }

        Mac baskaMac = new Mac(
                101,
                istatistikEvSahibi,
                istatistikDeplasman,
                LocalDate.of(2026, 10, 20)
        );

        try {
            baskaMac.istatistikEkle(savunmaciIstatistigi);
            System.out.println("HATA: İstatistik yanlış maça eklendi.");
        } catch (GecersizMacException exception) {
            System.out.println("Yanlış maça ekleme başarısız: " + exception.getMessage());
        }
        for (FutbolcuMacIstatistigi istatistik : istatistikMaci.istatistikleriGetir()) {
            System.out.println(istatistik.getFutbolcu().getAdSoyad());
        }


        Takim satanTakim = new Takim(20, "Manchester City", new BigDecimal(50000000));
        Takim alanTakim = new Takim(30, "Real Madrid", new BigDecimal(100000000));

        takimRepository.ekle(satanTakim);
        takimRepository.ekle(alanTakim);

        Takim takim2 = new Takim(2, "Barcelona", new BigDecimal(1000000000));
        Takim takim3 = new Takim(3, "Inter", new BigDecimal(1000000000));

        takimRepository.ekle(takim1);
        takimRepository.ekle(takim2);
        takimRepository.ekle(takim3);

        Takim repodanGetirilenTakim = takimRepository.idIleGetir(1);
        System.out.println(repodanGetirilenTakim.getTakimAdi());

        for (Takim takim : takimRepository.tumunuGetir()) {
            System.out.println(takim.getTakimId() + " - " + takim.getTakimAdi());
        }

        Takim takim4 = new Takim(2, "Villareal", new BigDecimal(1000000000));

        try {
            takimRepository.ekle(takim4);
        } catch (TakimZatenKayitliException exception) {
            System.out.println("Yeni Takım Ekkleme İşlemi Başarısız : " + exception.getMessage());
        }
        Takim repodanGetirilenTakimIdKontrol = takimRepository.idIleGetir(2);
        System.out.println(repodanGetirilenTakimIdKontrol.getTakimAdi());

        try {
            takimRepository.guncelle(takim4);
        } catch (TakimBulunamadiException exception) {
            System.out.println("Yeni Takım Ekleme İşlemi Başarısız : " + exception.getMessage());
        }
        Takim repodanGetirilenTakimIdKontrol2 = takimRepository.idIleGetir(2);
        System.out.println(repodanGetirilenTakimIdKontrol2.getTakimAdi());

        takimRepository.sil(2);
        try {
            takimRepository.idIleGetir(2);
        } catch (Exception exception) {
            System.out.println("Takım Silme İşlemi Başarısız : " + exception.getMessage());
        }

        try {
            takimRepository.sil(2);
            System.out.println("HATA: Olmayan takım silindi.");

        } catch (TakimBulunamadiException exception) {
            System.out.println("Silme başarısız: " + exception.getMessage());
        }

        try {
            takimRepository.guncelle(takim4);
            System.out.println("HATA: Olmayan takım güncellendi.");

        } catch (TakimBulunamadiException exception) {
            System.out.println("Güncelleme başarısız: " + exception.getMessage());
        }


        Repository<Mac, Integer> macRepository = new MacRepository();

        macRepository.ekle(mac1);
        macRepository.ekle(mac2);

        System.out.println(
                "Doğru maç getirildi mi? "
                        + (macRepository.idIleGetir(mac1.getMacId()) == mac1)
        );

        System.out.println("Kayıt sayısı 2 mi? "
                + (macRepository.tumunuGetir().size() == 2));

        for (Mac mac : macRepository.tumunuGetir()) {
            System.out.println("Maç ID: " + mac.getMacId());
        }

        Mac ayniIdliMac = new Mac(
                mac1.getMacId(),
                evSahibiiTakim2,
                deplasmanTakimi2,
                LocalDate.of(2026, 10, 20)
        );

        try {
            macRepository.ekle(ayniIdliMac);
            System.out.println("HATA: Tekrarlanan ID kabul edildi.");
        } catch (MacZatenKayitliException exception) {
            System.out.println("Beklenen ekleme hatası: " + exception.getMessage());
        }

        System.out.println(
                "İlk kayıt korundu mu? "
                        + (macRepository.idIleGetir(mac1.getMacId()) == mac1)
        );

        try {
            macRepository.idIleGetir(999);
            System.out.println("HATA: Olmayan maç getirildi.");
        } catch (MacBulunamadiException exception) {
            System.out.println("Beklenen getirme hatası: " + exception.getMessage());
        }

        Mac yeniKayit = new Mac(
                mac2.getMacId(),
                evSahibiiTakim2,
                deplasmanTakimi2,
                LocalDate.of(2026, 10, 21)
        );
        yeniKayit.macKaydet(4, 2);

        macRepository.guncelle(yeniKayit);

        Mac guncellenenMac = macRepository.idIleGetir(mac2.getMacId());

        System.out.println("Yeni nesne kaydedildi mi? "
                + (guncellenenMac == yeniKayit));

        System.out.println(
                "Güncel skor 4-2 mi? "
                        + (guncellenenMac.getEvSahibiGolSayisi() == 4
                        && guncellenenMac.getDeplasmanGolSayisi() == 2)
        );

        System.out.println("Güncellemede kayıt sayısı korundu mu? "
                + (macRepository.tumunuGetir().size() == 2));

        Mac kayitsizMac = new Mac(
                999,
                evSahibiiTakim2,
                deplasmanTakimi2,
                LocalDate.of(2026, 10, 22)
        );

        try {
            macRepository.guncelle(kayitsizMac);
            System.out.println("HATA: Olmayan maç güncellendi.");
        } catch (MacBulunamadiException exception) {
            System.out.println("Beklenen güncelleme hatası: " + exception.getMessage());
        }

        List<Mac> listeKopyasi = macRepository.tumunuGetir();
        listeKopyasi.clear();

        System.out.println("Liste temizlense de kayıtlar korundu mu? "
                + (macRepository.tumunuGetir().size() == 2));

        macRepository.sil(mac1.getMacId());

        System.out.println("Silme sonrası kayıt sayısı 1 mi? "
                + (macRepository.tumunuGetir().size() == 1));

        try {
            macRepository.idIleGetir(mac1.getMacId());
            System.out.println("HATA: Silinen maç bulundu.");
        } catch (MacBulunamadiException exception) {
            System.out.println("Silinen maç artık bulunamıyor.");
        }

        try {
            macRepository.sil(mac1.getMacId());
            System.out.println("HATA: Olmayan maç silindi.");
        } catch (MacBulunamadiException exception) {
            System.out.println("Beklenen silme hatası: " + exception.getMessage());
        }

        macRepository.sil(mac2.getMacId());

        System.out.println("Repository boş mu? "
                + macRepository.tumunuGetir().isEmpty());


        Futbolcu futbolcu33 = new Forvet(
                33, "Mario Gomez", LocalDate.of(1995, 05, 12), 33, BigDecimal.valueOf(60000000)
        );
        TakimServisi takimServisi = new TakimServisi(takimRepository);
        takimServisi.takimaFutbolcuEkle(1, futbolcu33);
        System.out.println(takimRepository.idIleGetir(1).idIleFutbolcuGetir(33).getAdSoyad());

        takimServisi.takimdanFutbolcuCikar(1, 33);
        try {
            takimRepository.idIleGetir(1).idIleFutbolcuGetir(33);
        } catch (FutbolcuKadrodaBulunamadiException exception) {
            System.out.println("Futbolcu Getirme İşlemi Başarısız : " + exception.getMessage());
        }


        satanTakim.futbolcuEkle(ortaSaha);

        TransferServisi transferServisi = new TransferServisi(takimRepository);

        transferServisi.transferEt(satanTakim.getTakimId(),
                alanTakim.getTakimId(),
                10,
                new BigDecimal(90000000));

        System.out.println(alanTakim.idIleFutbolcuGetir(10).getAdSoyad());
        System.out.println("Satan takımın transferden sonra yeni bütçesi : " + satanTakim.getTransferButcesi());
        System.out.println("Alan takımın transferden sonra yeni bütçesi : " + alanTakim.getTransferButcesi());

        Futbolcu rollbackTestFutbolcusu = new Forvet(
                99,
                "Test Futbolcusu",
                LocalDate.of(2000, 1, 1),
                10,
                BigDecimal.valueOf(5_000_000)
        );

        satanTakim.futbolcuEkle(rollbackTestFutbolcusu);

        BigDecimal satanTakimOncekiButce =
                satanTakim.getTransferButcesi();

        BigDecimal alanTakimOncekiButce =
                alanTakim.getTransferButcesi();

        try {
            transferServisi.transferEt(
                    satanTakim.getTakimId(),
                    alanTakim.getTakimId(),
                    rollbackTestFutbolcusu.getFutbolcuId(),
                    BigDecimal.valueOf(5_000_000)
            );

            System.out.println("Transfer beklenmedik şekilde gerçekleşti.");

        } catch (FormaNumarasiKullanimdaException exception) {
            System.out.println("Transfer başarısız: " + exception.getMessage());
        }

        System.out.println(
                "Satan takımın bütçesi korundu mu? "
                        + (satanTakimOncekiButce.compareTo(
                        satanTakim.getTransferButcesi()) == 0)
        );

        System.out.println(
                "Alan takımın bütçesi geri yüklendi mi? "
                        + (alanTakimOncekiButce.compareTo(
                        alanTakim.getTransferButcesi()) == 0)
        );

        Futbolcu satanTakimdaKalanFutbolcu =
                satanTakim.idIleFutbolcuGetir(
                        rollbackTestFutbolcusu.getFutbolcuId()
                );

        System.out.println(
                "Futbolcu hâlâ satan takımda: "
                        + satanTakimdaKalanFutbolcu.getAdSoyad()
        );

        try {
            alanTakim.idIleFutbolcuGetir(
                    rollbackTestFutbolcusu.getFutbolcuId()
            );

            System.out.println("HATA: Futbolcu alan takımda bulundu.");

        } catch (FutbolcuKadrodaBulunamadiException exception) {
            System.out.println("Futbolcu alan takıma eklenmedi.");
        }

        BigDecimal transferOncesiButce = satanTakim.getTransferButcesi();

        try {
            transferServisi.transferEt(
                    20,
                    999,
                    99,
                    BigDecimal.valueOf(5_000_000)
            );

            System.out.println("HATA: Transfer engellenmedi.");

        } catch (TakimBulunamadiException exception) {
            System.out.println("Transfer başarısız: " + exception.getMessage());
        }

        System.out.println(
                "Satan takımın bütçesi korundu mu? "
                        + (transferOncesiButce.compareTo(
                        satanTakim.getTransferButcesi()) == 0)
        );

        System.out.println(
                "Futbolcu hâlâ satan takımda: "
                        + satanTakim.idIleFutbolcuGetir(99).getAdSoyad()
        );


        Takim macServisiTestTakim1 = new Takim(90, "Arsenal", BigDecimal.valueOf(50_000_000));

        Takim macServisiTestTakim2 = new Takim(100, "Manchester United", BigDecimal.valueOf(50_000_000));

        takimRepository.ekle(macServisiTestTakim1);
        takimRepository.ekle(macServisiTestTakim2);

        MacServisi macServisi = new MacServisi(macRepository, takimRepository);

        macServisi.macPlanla(10, 90, 100, LocalDate.now());
        macServisi.macSonucuKaydet(10, 2, 1);
        System.out.println(
                "MAÇIN SKORU: "
                        + macRepository.idIleGetir(10).getEvSahibiGolSayisi()
                        + " - "
                        + macRepository.idIleGetir(10).getDeplasmanGolSayisi()
        );
        System.out.println("Maç tamamlandı mı? " + macRepository.idIleGetir(10).isTamamlandi());

        try {
            macServisi.macSonucuKaydet(10, 3, 0);
            System.out.println("HATA: Tamamlanan maçın sonucu değiştirildi.");
        } catch (MacZatenTamamlandiException exception) {
            System.out.println("Sonuç değiştirme başarısız: " + exception.getMessage());
        }

        Mac kontrolEdilenMac = macRepository.idIleGetir(10);

        System.out.println(
                "Maçın skoru: "
                        + kontrolEdilenMac.getEvSahibiGolSayisi()
                        + " - "
                        + kontrolEdilenMac.getDeplasmanGolSayisi()
        );

        System.out.println("Maç tamamlandı mı? " + kontrolEdilenMac.isTamamlandi());

        try {
            macServisi.macPlanla(11, 90, 999, LocalDate.now());
            System.out.println("HATA: Olmayan takımla maç planlandı.");
        } catch (TakimBulunamadiException exception) {
            System.out.println("Maç planlama başarısız: " + exception.getMessage());
        }

        try {
            macRepository.idIleGetir(11);
            System.out.println("HATA: Maç repository'ye eklenmiş.");
        } catch (MacBulunamadiException exception) {
            System.out.println("Beklendiği gibi maç repository'ye eklenmedi.");
        }

        try {
            macServisi.macSonucuKaydet(11, 2, 0);
            System.out.println("HATA: Olmayan maçın sonucu kaydedildi.");
        } catch (MacBulunamadiException exception) {
            System.out.println("Maç sonucu kaydetme başarısız: " + exception.getMessage());
        }

        macRepository.ekle(istatistikMaci);

        macServisi.macIstatistigiEkle(
                istatistikMaci.getMacId(),
                savunmaciIstatistigi
        );

        for (FutbolcuMacIstatistigi istatistik
                : macRepository.idIleGetir(100).istatistikleriGetir()) {
            System.out.println(istatistik.getFutbolcu().getAdSoyad());
        }

        for (FutbolcuMacIstatistigi istatistik
                : macServisi.macIstatistikleriniGetir(100)) {
            System.out.println(istatistik.getFutbolcu().getAdSoyad());
        }

        List<FutbolcuMacIstatistigi> istatistikKopyasi =
                macServisi.macIstatistikleriniGetir(100);

        istatistikKopyasi.clear();

        System.out.println("Üç kayıt korundu mu? "
                + (macServisi.macIstatistikleriniGetir(100).size() == 3));

        try {
            macServisi.macIstatistigiEkle(100, savunmaciIstatistigi);
            System.out.println("HATA: Tekrar kayıt kabul edildi.");
        } catch (FutbolcuIstatistigiZatenKayitliException exception) {
            System.out.println("Beklenen tekrar kayıt hatası: " + exception.getMessage());
        }

        try {
            macServisi.macIstatistigiEkle(10, kaleciIstatistigi);
            System.out.println("HATA: Yanlış maça istatistik eklendi.");
        } catch (GecersizMacException exception) {
            System.out.println("Beklenen maç hatası: " + exception.getMessage());
        }

        System.out.println("10 ID'li maçın istatistikleri boş mu? "
                + macServisi.macIstatistikleriniGetir(10).isEmpty());

        try {
            macServisi.macIstatistigiEkle(999, kaleciIstatistigi);
            System.out.println("HATA: Olmayan maça istatistik eklendi.");
        } catch (MacBulunamadiException exception) {
            System.out.println("Beklenen ekleme hatası: " + exception.getMessage());
        }

        try {
            macServisi.macIstatistikleriniGetir(999);
            System.out.println("HATA: Olmayan maçın istatistikleri getirildi.");
        } catch (MacBulunamadiException exception) {
            System.out.println("Beklenen listeleme hatası: " + exception.getMessage());
        }


        Takim galatasaray = new Takim(
                500, "Galatasaray", BigDecimal.valueOf(50_000_000)
        );

        Takim fenerbahce = new Takim(
                600, "Fenerbahçe", BigDecimal.valueOf(50_000_000)
        );

        takimRepository.ekle(galatasaray);
        takimRepository.ekle(fenerbahce);

        Mac derbiMaci = new Mac(200, galatasaray, fenerbahce, LocalDate.of(2026, 10, 10));

        macRepository.ekle(derbiMaci);

        macRepository.idIleGetir(200).macKaydet(5, 5);

        PuanDurumu puanDurumu = new PuanDurumu(fenerbahce);
        puanDurumu.macSonucuIsle(2, 1);
        puanDurumu.macSonucuIsle(0, 0);
        puanDurumu.macSonucuIsle(1, 3);
        System.out.println("Puan: " + puanDurumu.getPuan());
        System.out.println("Averaj: " + puanDurumu.getAveraj());
        System.out.println("Atılan gol: " + puanDurumu.getAtilanGolSayisi());
        System.out.println("Yenilen gol: " + puanDurumu.getYenilenGolSayisi());


        PuanDurumuServisi puanDurumuServisi =
                new PuanDurumuServisi(macRepository, takimRepository);

        for (PuanDurumu satir : puanDurumuServisi.puanTablosunuHesapla()) {
            System.out.println(
                    satir.getTakim().getTakimAdi()
                            + " | Puan: " + satir.getPuan()
                            + " | Averaj: " + satir.getAveraj()
            );
        }

        Mac derbiMaci2 = new Mac(300, galatasaray, fenerbahce, LocalDate.now());
        macRepository.ekle(derbiMaci2);
        for (PuanDurumu satir : puanDurumuServisi.puanTablosunuHesapla()) {
            if (satir.getTakim().getTakimId() == 500
                    || satir.getTakim().getTakimId() == 600) {
                System.out.println(
                        satir.getTakim().getTakimAdi()
                                + " | Maç: " + satir.getTakiminOynanadigiMacSayisi()
                                + " | Puan: " + satir.getPuan()
                );
            }
        }


        Repository<Takim, Integer> kadroTestRepository = new TakimRepository();
        TakimServisi kadroTestServisi = new TakimServisi(kadroTestRepository);

        Takim kadroTestTakimi = new Takim(
                700, "Beşiktaş", BigDecimal.valueOf(50_000_000)
        );

        Futbolcu kadroTestForvet = new Forvet(
                701,
                "Mario Gomez",
                LocalDate.of(1985, 7, 10),
                9,
                BigDecimal.valueOf(5_000_000)
        );

        Futbolcu kadroTestOrtaSaha = new OrtaSaha(
                702,
                "Orkun Kökçü",
                LocalDate.of(2000, 12, 29),
                10,
                BigDecimal.valueOf(10_000_000)
        );

        kadroTestRepository.ekle(kadroTestTakimi);
        kadroTestServisi.takimaFutbolcuEkle(700, kadroTestForvet);
        kadroTestServisi.takimaFutbolcuEkle(700, kadroTestOrtaSaha);

        for (Futbolcu futbolcu : kadroTestServisi.kadroyuGetir(700)) {
            System.out.println(
                    futbolcu.getAdSoyad()
                            + " | Forma: " + futbolcu.getFormaNumarasi()
            );
        }

        List<Futbolcu> kadroKopyasi = kadroTestServisi.kadroyuGetir(700);
        kadroKopyasi.clear();

        System.out.println(
                "Asıl kadro korundu mu? "
                        + (kadroTestServisi.kadroyuGetir(700).size() == 2)
        );

        kadroTestServisi.formaNumarasiDegistir(700, 701, 11);

        System.out.println(
                "Yeni forma numarası: " + kadroTestForvet.getFormaNumarasi()
        );

        kadroTestServisi.formaNumarasiDegistir(700, 701, 11);
        System.out.println("Kendi numarasını tekrar seçmek başarılı.");

        try {
            kadroTestServisi.formaNumarasiDegistir(700, 701, 10);
            System.out.println("HATA: Kullanılan forma numarası kabul edildi.");

        } catch (FormaNumarasiKullanimdaException exception) {
            System.out.println(
                    "Forma değiştirme başarısız: " + exception.getMessage()
            );
        }

        for (int gecersizNumara : new int[]{0, 100}) {
            try {
                kadroTestServisi.formaNumarasiDegistir(
                        700, 701, gecersizNumara
                );

                System.out.println("HATA: Geçersiz forma numarası kabul edildi.");

            } catch (IllegalArgumentException exception) {
                System.out.println(
                        "Geçersiz forma reddedildi: " + gecersizNumara
                );
            }
        }

        System.out.println(
                "Forma numarası korundu mu? "
                        + (kadroTestForvet.getFormaNumarasi() == 11)
        );

        kadroTestServisi.piyasaDegeriDegistir(
                700, 701, BigDecimal.valueOf(8_000_000)
        );

        System.out.println(
                "Yeni piyasa değeri: " + kadroTestForvet.getPiyasaDegeri()
        );

        for (BigDecimal gecersizDeger
                : new BigDecimal[]{BigDecimal.valueOf(-1), null}) {
            try {
                kadroTestServisi.piyasaDegeriDegistir(
                        700, 701, gecersizDeger
                );

                System.out.println("HATA: Geçersiz piyasa değeri kabul edildi.");

            } catch (GecersizParaMiktariException exception) {
                System.out.println(
                        "Piyasa değeri değiştirme başarısız: "
                                + exception.getMessage()
                );
            }
        }

        System.out.println(
                "Piyasa değeri korundu mu? "
                        + (kadroTestForvet.getPiyasaDegeri().compareTo(
                        BigDecimal.valueOf(8_000_000)
                ) == 0)
        );

        kadroTestServisi.piyasaDegeriDegistir(700, 701, BigDecimal.ZERO);

        System.out.println(
                "Sıfır piyasa değeri kabul edildi: "
                        + kadroTestForvet.getPiyasaDegeri()
        );

        try {
            kadroTestServisi.kadroyuGetir(999);
            System.out.println("HATA: Olmayan takımın kadrosu getirildi.");

        } catch (TakimBulunamadiException exception) {
            System.out.println(
                    "Kadro getirme başarısız: " + exception.getMessage()
            );
        }

        try {
            kadroTestServisi.formaNumarasiDegistir(700, 999, 12);
            System.out.println("HATA: Olmayan futbolcu güncellendi.");

        } catch (FutbolcuKadrodaBulunamadiException exception) {
            System.out.println(
                    "Futbolcu güncelleme başarısız: " + exception.getMessage()
            );
        }
    }
}
