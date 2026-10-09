package com.mehmetyilmazalan.footballmanager.application;

import com.mehmetyilmazalan.footballmanager.exception.*;
import com.mehmetyilmazalan.footballmanager.model.*;
import com.mehmetyilmazalan.footballmanager.repository.Repository;
import com.mehmetyilmazalan.footballmanager.service.MacServisi;
import com.mehmetyilmazalan.footballmanager.service.PuanDurumuServisi;
import com.mehmetyilmazalan.footballmanager.service.TakimServisi;
import com.mehmetyilmazalan.footballmanager.service.TransferServisi;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.CancellationException;

public class KonsolMenusu {

    private static final boolean RENKLER_AKTIF = true;
    private static final String YESIL = "\u001B[92m";
    private static final String MAVI = "\u001B[96m";
    private static final String CERCEVE_MAVISI = "\u001B[36m";
    private static final String SARI = "\u001B[93m";
    private static final String KIRMIZI = "\u001B[91m";
    private static final String BEYAZ = "\u001B[97m";
    private static final String GRI = "\u001B[37m";
    private static final String KALIN = "\u001B[1m";
    private static final String SIFIRLA = "\u001B[0m";
    private static final int EKRAN_GENISLIGI = 96;
    private static final String MENU_CIZGISI = "─".repeat(EKRAN_GENISLIGI);
    private static final Locale TURKCE = Locale.forLanguageTag("tr-TR");
    private static final String[] ACILIS_METNI = {
            "Futbol sahada oynanır. Takımlar kararlarınla şekillenir.",
            "Kadro kur, transfer yap, bütçeni yönet.",
            "Maçlarını planla; sonuçları kaydet, puan yarışını takip et."
    };

    private final Scanner scanner = new Scanner(System.in);
    private final NumberFormat paraFormati = NumberFormat.getNumberInstance(TURKCE);
    private final DateTimeFormatter tarihFormati = DateTimeFormatter
            .ofPattern("dd.MM.uuuu").withResolverStyle(ResolverStyle.STRICT);
    private final Repository<Takim, Integer> takimRepository;
    private final Repository<Mac, Integer> macRepository;
    private final TakimServisi takimServisi;
    private final TransferServisi transferServisi;
    private final MacServisi macServisi;
    private final PuanDurumuServisi puanDurumuServisi;

    public KonsolMenusu(
            Repository<Takim, Integer> takimRepository,
            Repository<Mac, Integer> macRepository,
            TakimServisi takimServisi,
            TransferServisi transferServisi,
            MacServisi macServisi,
            PuanDurumuServisi puanDurumuServisi
    ) {
        this.takimRepository = takimRepository;
        this.macRepository = macRepository;
        this.takimServisi = takimServisi;
        this.transferServisi = transferServisi;
        this.macServisi = macServisi;
        this.puanDurumuServisi = puanDurumuServisi;
        paraFormati.setMinimumFractionDigits(2);
        paraFormati.setMaximumFractionDigits(2);
    }

    public void baslat() {
        try {
            boolean devam = true;
            while (devam) {
                anaMenuyuGoster();
                switch (menuSecimiOku(6)) {
                    case 1 -> takimMenusu();
                    case 2 -> futbolcuMenusu();
                    case 3 -> transferMenusu();
                    case 4 -> macMenusu();
                    case 5 -> puanDurumuMenusu();
                    case 6 -> istatistikMenusu();
                    case 0 -> devam = false;
                }
            }
        } catch (NoSuchElementException exception) {
            bilgi("Konsol girişi kapandı.");
        } catch (CancellationException exception) {
            bilgi("Oturum kapatıldı.");
        }
        menuBasligi("GÖRÜŞMEK ÜZERE");
        ortaliYaz("Football Manager Lite oturumu kapatıldı.", BEYAZ);
        System.out.println();
    }

    private void islemiCalistir(Runnable islem) {
        try {
            islem.run();
        } catch (CancellationException exception) {
            bilgi("İşlem iptal edildi. Bulunduğun menüden devam edebilirsin.");
        } catch (TakimBulunamadiException | TakimZatenKayitliException
                 | FutbolcuKadrodaBulunamadiException | FutbolcuZatenKadrodaException
                 | FormaNumarasiKullanimdaException | KadroDoluException
                 | GecersizTransferException | GecersizParaMiktariException
                 | YetersizButceException | MacBulunamadiException
                 | MacZatenKayitliException | GecersizMacException
                 | MacZatenTamamlandiException | GecersizMacSonucuException
                 | FutbolcuIstatistigiZatenKayitliException
                 | IllegalArgumentException | IllegalStateException exception) {
            islemHatasi(exception.getMessage());
        }
    }

    private void anaMenuyuGoster() {
        int tamamlananMac = 0;
        int futbolcuSayisi = 0;
        List<Mac> maclar = macRepository.tumunuGetir();
        List<Takim> takimlar = takimRepository.tumunuGetir();
        for (Mac mac : maclar) {
            if (mac.isTamamlandi()) tamamlananMac++;
        }
        for (Takim takim : takimlar) {
            futbolcuSayisi += takim.kadroyuGetir().size();
        }

        menuBasligi("FOOTBALL MANAGER LITE");
        for (String satir : ACILIS_METNI) ortaliYaz(satir, GRI);

        islemlerBasligi();
        secenek(1, "TAKIMLAR", "Takım ekle, detayları ve kadroyu gör, bütçeyi yönet, takım sil");
        secenek(2, "FUTBOLCULAR", "Futbolcu ekle, incele, güncelle ve kadrodan çıkar");
        secenek(3, "TRANSFER MERKEZİ", "Kadroları incele ve takımlar arasında transfer yap");
        secenek(4, "MAÇ MERKEZİ", "Maç planla, sonuç kaydet, detayları incele ve maç sil");
        secenek(5, "PUAN DURUMU", "Puan, averaj, galibiyet ve diğer takım istatistiklerini gör");
        secenek(6, "MAÇ İSTATİSTİKLERİ", "Futbolcu istatistiklerini kaydet ve performans puanlarını gör");
        System.out.println();
        secenek(0, "ÇIKIŞ", "Oturumu kapat");
        System.out.println(renklendir("\n  Veriler bu oturumda tutulur; uygulama kapanınca silinir.\n", GRI));
    }


    private void takimMenusu() {
        while (true) {
            menuBasligi("TAKIMLAR");
            takimlariGoster();
            islemlerBasligi();
            secenek(1, "TAKIM EKLE", "Yeni bir takım oluştur");
            secenek(2, "TAKIM DETAYLARI", "Bütçe, kadro sayısı ve toplam piyasa değerini gör");
            secenek(3, "KADROYU LİSTELE", "Takımın futbolcularını görüntüle");
            secenek(4, "TAKIM SİL", "Maç kaydı bulunmayan bir takımı kaldır");
            secenek(5, "BÜTÇE ARTIR", "Takımın transfer bütçesine tutar ekle");
            secenek(6, "BÜTÇE DÜŞÜR", "Takımın transfer bütçesinden tutar çıkar");
            altMenuBilgisi();
            switch (menuSecimiOku(6)) {
                case 1 -> islemiCalistir(this::takimEkle);
                case 2 -> islemiCalistir(this::takimDetaylariniGoster);
                case 3 -> islemiCalistir(this::kadroListele);
                case 4 -> islemiCalistir(this::takimSil);
                case 5 -> islemiCalistir(() -> butceDegistir(true));
                case 6 -> islemiCalistir(() -> butceDegistir(false));
                case 0 -> { return; }
            }
        }
    }

    private void takimEkle() {
        int id = tamSayiOku("Takım ID", 1, Integer.MAX_VALUE);
        String ad = metinOku("Takım adı");
        BigDecimal butce = paraOku("Transfer bütçesi", true);
        takimRepository.ekle(new Takim(id, ad, butce));
        basarili(ad + " kaydedildi.");
    }

    private void takimlariGoster() {
        List<Takim> takimlar = takimRepository.tumunuGetir();
        takimlar.sort(Comparator.comparingInt(Takim::getTakimId));
        if (takimlar.isEmpty()) {
            bilgi("Henüz takım yok. Takım ekleyerek başlayabilirsin.");
            return;
        }
        tabloBasligi("  %-10s %-30s %8s %24s", "ID", "TAKIM", "KADRO", "TRANSFER BÜTÇESİ");
        for (Takim takim : takimlar) {
            System.out.printf("  %-10d %-30.30s %5d/24 %24s%n",
                    takim.getTakimId(), takim.getTakimAdi(), takim.kadroyuGetir().size(),
                    paraFormati.format(takim.getTransferButcesi()));
        }
    }

    private void takimDetaylariniGoster() {
        Takim takim = takimSec("Takım ID");
        baslik("TAKIM DETAYLARI");
        detay("Takım", takim.getTakimAdi());
        detay("Takım ID", takim.getTakimId());
        detay("Transfer bütçesi", paraFormati.format(takim.getTransferButcesi()));
        detay("Kadro", takim.kadroyuGetir().size() + " / 24");
        detay("Toplam piyasa değeri", paraFormati.format(takim.takimDegeriHesapla()));
        kadroyuGoster(takim);
    }

    private void kadroListele() {
        Takim takim = takimSec("Takım ID");
        baslik("KADRO · " + takim.getTakimAdi());
        kadroyuGoster(takim);
    }

    private void kadroyuGoster(Takim takim) {
        List<Futbolcu> kadro = takimServisi.kadroyuGetir(takim.getTakimId());
        kadro.sort(Comparator.comparingInt(Futbolcu::getFormaNumarasi));
        if (kadro.isEmpty()) {
            bilgi(takim.getTakimAdi() + " kadrosunda futbolcu bulunmuyor.");
            return;
        }
        System.out.println();
        tabloBasligi("  %-10s %-25s %-12s %5s %5s %22s",
                "ID", "FUTBOLCU", "MEVKİ", "FORMA", "YAŞ", "PİYASA DEĞERİ");
        for (Futbolcu futbolcu : kadro) {
            System.out.printf("  %-10d %-25.25s %-12s %5d %5d %22s%n",
                    futbolcu.getFutbolcuId(), futbolcu.getAdSoyad(), mevkiAdi(futbolcu),
                    futbolcu.getFormaNumarasi(), futbolcu.getYas(),
                    paraFormati.format(futbolcu.getPiyasaDegeri()));
        }
    }

    private void takimSil() {
        Takim takim = takimSec("Silinecek takım ID");
        for (Mac mac : macRepository.tumunuGetir()) {
            if (mac.getEvSahibiTakim().getTakimId() == takim.getTakimId()
                    || mac.getDeplasmanTakimi().getTakimId() == takim.getTakimId()) {
                throw new IllegalStateException(
                        "Bu takımın maç kayıtları var. Önce ilgili maçları kaldırmalısın."
                );
            }
        }
        if (!onayOku(takim.getTakimAdi() + " ve " + takim.kadroyuGetir().size()
                + " futbolcudan oluşan kadrosu kaldırılsın mı?")) return;
        takimRepository.sil(takim.getTakimId());
        basarili(takim.getTakimAdi() + " silindi.");
    }

    private void butceDegistir(boolean arttir) {
        Takim takim = takimSec("Takım ID");
        detay("Mevcut bütçe", paraFormati.format(takim.getTransferButcesi()));
        BigDecimal miktar = paraOku(arttir ? "Eklenecek tutar" : "Çıkarılacak tutar", false);
        if (arttir) takim.butceArttir(miktar);
        else takim.butceDusur(miktar);
        basarili("Yeni bütçe: " + paraFormati.format(takim.getTransferButcesi()));
    }


    private void futbolcuMenusu() {
        while (true) {
            menuBasligi("FUTBOLCULAR");
            takimlariGoster();
            islemlerBasligi();
            secenek(1, "FUTBOLCU EKLE", "Takım kadrosuna yeni futbolcu ekle");
            secenek(2, "FUTBOLCU DETAYLARI", "Mevki, yaş, forma numarası ve piyasa değerini gör");
            secenek(3, "KADRODAN ÇIKAR", "Futbolcuyu takımın kadrosundan kaldır");
            secenek(4, "FORMA NUMARASI DEĞİŞTİR", "Kullanılmayan bir forma numarası seç");
            secenek(5, "PİYASA DEĞERİ DEĞİŞTİR", "Futbolcunun piyasa değerini güncelle");
            secenek(6, "KADROYU LİSTELE", "Bir takımın tüm futbolcularını görüntüle");
            altMenuBilgisi();
            switch (menuSecimiOku(6)) {
                case 1 -> islemiCalistir(this::futbolcuEkle);
                case 2 -> islemiCalistir(this::futbolcuDetaylariniGoster);
                case 3 -> islemiCalistir(this::futbolcuCikar);
                case 4 -> islemiCalistir(this::formaNumarasiDegistir);
                case 5 -> islemiCalistir(this::piyasaDegeriDegistir);
                case 6 -> islemiCalistir(this::kadroListele);
                case 0 -> { return; }
            }
        }
    }

    private void futbolcuEkle() {
        Takim takim = takimSec("Takım ID");
        int id = tamSayiOku("Futbolcu ID", 1, Integer.MAX_VALUE);
        for (Takim mevcutTakim : takimRepository.tumunuGetir()) {
            for (Futbolcu mevcut : mevcutTakim.kadroyuGetir()) {
                if (mevcut.getFutbolcuId() == id) {
                    throw new FutbolcuZatenKadrodaException(
                            "Bu futbolcu ID'si " + mevcutTakim.getTakimAdi() + " kadrosunda kayıtlı."
                    );
                }
            }
        }
        String ad = metinOku("Ad soyad");
        LocalDate dogum = tarihOku("Doğum tarihi");
        while (dogum.isAfter(LocalDate.now())) {
            uyari("Doğum tarihi gelecekte olamaz.");
            dogum = tarihOku("Doğum tarihi");
        }
        int forma = tamSayiOku("Forma numarası", 1, 99);
        BigDecimal deger = paraOku("Piyasa değeri", true);
        System.out.println("\n  [1] Kaleci   [2] Savunmacı   [3] Orta saha   [4] Forvet\n");
        Futbolcu futbolcu = switch (tamSayiOku("Mevki", 1, 4)) {
            case 1 -> new Kaleci(id, ad, dogum, forma, deger);
            case 2 -> new Savunmaci(id, ad, dogum, forma, deger);
            case 3 -> new OrtaSaha(id, ad, dogum, forma, deger);
            default -> new Forvet(id, ad, dogum, forma, deger);
        };
        takimServisi.takimaFutbolcuEkle(takim.getTakimId(), futbolcu);
        basarili(ad + ", " + takim.getTakimAdi() + " kadrosuna eklendi.");
    }

    private void futbolcuDetaylariniGoster() {
        Takim takim = takimSec("Takım ID");
        kadroyuGoster(takim);
        Futbolcu futbolcu = futbolcuSec(takim);
        baslik("FUTBOLCU DETAYLARI");
        detay("Ad soyad", futbolcu.getAdSoyad());
        detay("Futbolcu ID", futbolcu.getFutbolcuId());
        detay("Takım", takim.getTakimAdi());
        detay("Mevki", mevkiAdi(futbolcu));
        detay("Forma numarası", futbolcu.getFormaNumarasi());
        detay("Doğum tarihi", futbolcu.getDogumTarihi().format(tarihFormati));
        detay("Yaş", futbolcu.getYas());
        detay("Piyasa değeri", paraFormati.format(futbolcu.getPiyasaDegeri()));
    }

    private void futbolcuCikar() {
        Takim takim = takimSec("Takım ID");
        kadroyuGoster(takim);
        Futbolcu futbolcu = futbolcuSec(takim);
        if (!onayOku(futbolcu.getAdSoyad() + " kadrodan çıkarılsın mı?")) return;
        takimServisi.takimdanFutbolcuCikar(takim.getTakimId(), futbolcu.getFutbolcuId());
        basarili(futbolcu.getAdSoyad() + " kadrodan çıkarıldı.");
    }

    private void formaNumarasiDegistir() {
        Takim takim = takimSec("Takım ID");
        kadroyuGoster(takim);
        Futbolcu futbolcu = futbolcuSec(takim);
        detay("Mevcut forma numarası", futbolcu.getFormaNumarasi());
        int numara = tamSayiOku("Yeni forma numarası", 1, 99);
        takimServisi.formaNumarasiDegistir(takim.getTakimId(), futbolcu.getFutbolcuId(), numara);
        basarili(futbolcu.getAdSoyad() + " için yeni forma numarası: " + numara);
    }

    private void piyasaDegeriDegistir() {
        Takim takim = takimSec("Takım ID");
        kadroyuGoster(takim);
        Futbolcu futbolcu = futbolcuSec(takim);
        detay("Mevcut piyasa değeri", paraFormati.format(futbolcu.getPiyasaDegeri()));
        BigDecimal deger = paraOku("Yeni piyasa değeri", true);
        takimServisi.piyasaDegeriDegistir(takim.getTakimId(), futbolcu.getFutbolcuId(), deger);
        basarili("Yeni piyasa değeri: " + paraFormati.format(deger));
    }


    private void transferMenusu() {
        while (true) {
            menuBasligi("TRANSFER MERKEZİ");
            takimlariGoster();
            islemlerBasligi();
            secenek(1, "TRANSFER YAP", "Satan takım, alan takım, futbolcu ve transfer bedelini seç");
            secenek(2, "KADROYU İNCELE", "Transfer öncesinde futbolcuları ve değerlerini gör");
            altMenuBilgisi();
            switch (menuSecimiOku(2)) {
                case 1 -> islemiCalistir(this::transferYap);
                case 2 -> islemiCalistir(this::kadroListele);
                case 0 -> { return; }
            }
        }
    }

    private void transferYap() {
        Takim satan = takimSec("Satan takım ID");
        Takim alan = takimSec("Alan takım ID");
        if (satan.getTakimId() == alan.getTakimId()) {
            throw new GecersizTransferException("Satan ve alan takım aynı olamaz.");
        }
        kadroyuGoster(satan);
        Futbolcu futbolcu = futbolcuSec(satan);
        BigDecimal bedel = paraOku("Transfer bedeli", false);
        transferServisi.transferEt(satan.getTakimId(), alan.getTakimId(), futbolcu.getFutbolcuId(), bedel);
        basarili(futbolcu.getAdSoyad() + ", " + alan.getTakimAdi() + " takımına transfer oldu.");
        detay("Transfer bedeli", paraFormati.format(bedel));
        detay("Satan takım bütçesi", paraFormati.format(satan.getTransferButcesi()));
        detay("Alan takım bütçesi", paraFormati.format(alan.getTransferButcesi()));
    }


    private void macMenusu() {
        while (true) {
            menuBasligi("MAÇ MERKEZİ");
            maclariGoster();
            islemlerBasligi();
            secenek(1, "MAÇ PLANLA", "Takımları ve maç tarihini belirle");
            secenek(2, "SONUÇ KAYDET", "Planlanan maçın skorunu gir");
            secenek(3, "MAÇ DETAYLARI", "Takımları, tarihi, sonucu ve istatistik sayısını gör");
            secenek(4, "MAÇ SİL", "Maçı ve ona bağlı istatistik kayıtlarını kaldır");
            altMenuBilgisi();
            switch (menuSecimiOku(4)) {
                case 1 -> islemiCalistir(this::macPlanla);
                case 2 -> islemiCalistir(this::macSonucuKaydet);
                case 3 -> islemiCalistir(this::macDetaylariniGoster);
                case 4 -> islemiCalistir(this::macSil);
                case 0 -> { return; }
            }
        }
    }

    private void maclariGoster() {
        List<Mac> maclar = macRepository.tumunuGetir();
        maclar.sort(Comparator.comparing(Mac::getMacTarihi).thenComparingInt(Mac::getMacId));
        if (maclar.isEmpty()) {
            bilgi("Henüz maç yok. Takımlarını ekledikten sonra bir maç planlayabilirsin.");
            return;
        }
        tabloBasligi("  %-10s %-12s %-23s %-23s %-12s",
                "ID", "TARİH", "EV SAHİBİ", "DEPLASMAN", "SONUÇ");
        for (Mac mac : maclar) {
            System.out.printf("  %-10d %-12s %-23.23s %-23.23s %-12s%n",
                    mac.getMacId(), mac.getMacTarihi().format(tarihFormati),
                    mac.getEvSahibiTakim().getTakimAdi(), mac.getDeplasmanTakimi().getTakimAdi(),
                    macSonucMetni(mac));
        }
    }

    private void macPlanla() {
        takimlariGoster();
        int id = tamSayiOku("Maç ID", 1, Integer.MAX_VALUE);
        int ev = tamSayiOku("Ev sahibi takım ID", 1, Integer.MAX_VALUE);
        int deplasman = tamSayiOku("Deplasman takım ID", 1, Integer.MAX_VALUE);
        LocalDate tarih = tarihOku("Maç tarihi");
        macServisi.macPlanla(id, ev, deplasman, tarih);
        basarili(id + " numaralı maç " + tarih.format(tarihFormati) + " tarihine planlandı.");
    }

    private void macSonucuKaydet() {
        Mac mac = macSec();
        if (mac.isTamamlandi()) {
            throw new MacZatenTamamlandiException("Bu maç zaten tamamlandı; tekrar sonuç kaydedilemez.");
        }
        macOzetiniGoster(mac);
        int evGol = tamSayiOku("Ev sahibi gol sayısı", 0, Integer.MAX_VALUE);
        int deplasmanGol = tamSayiOku("Deplasman gol sayısı", 0, Integer.MAX_VALUE);
        macServisi.macSonucuKaydet(mac.getMacId(), evGol, deplasmanGol);
        basarili("Maç tamamlandı. Sonuç: " + macSonucMetni(mac));
    }

    private void macDetaylariniGoster() {
        Mac mac = macSec();
        baslik("MAÇ DETAYLARI · " + mac.getMacId());
        macOzetiniGoster(mac);
        detay("İstatistik kaydı", macServisi.macIstatistikleriniGetir(mac.getMacId()).size());
    }

    private void macSil() {
        Mac mac = macSec();
        macOzetiniGoster(mac);
        if (!onayOku("Bu maç ve ona bağlı futbolcu istatistikleri kaldırılsın mı?")) return;
        macRepository.sil(mac.getMacId());
        basarili(mac.getMacId() + " numaralı maç silindi. Puan tablosu kalan maçlardan hesaplanacak.");
    }

    private void macOzetiniGoster(Mac mac) {
        detay("Maç ID", mac.getMacId());
        detay("Ev sahibi", mac.getEvSahibiTakim().getTakimAdi());
        detay("Deplasman", mac.getDeplasmanTakimi().getTakimAdi());
        detay("Tarih", mac.getMacTarihi().format(tarihFormati));
        detay("Durum", mac.isTamamlandi() ? "Tamamlandı" : "Planlandı");
        detay("Sonuç", macSonucMetni(mac));
    }

    private String macSonucMetni(Mac mac) {
        return mac.isTamamlandi()
                ? mac.getEvSahibiGolSayisi() + " - " + mac.getDeplasmanGolSayisi()
                : "Planlandı";
    }


    private void istatistikMenusu() {
        while (true) {
            menuBasligi("MAÇ İSTATİSTİKLERİ");
            maclariGoster();
            islemlerBasligi();
            secenek(1, "İSTATİSTİK EKLE", "Tamamlanan maç için futbolcu istatistiği gir");
            secenek(2, "İSTATİSTİKLERİ LİSTELE", "Dakika, gol, asist ve maç puanlarını gör");
            secenek(3, "İSTATİSTİK DETAYLARI", "Bir futbolcunun maçtaki tüm istatistiklerini incele");
            secenek(4, "PERFORMANS SIRALAMASI", "Futbolcuları hesaplanan maç puanına göre sırala");
            altMenuBilgisi();
            switch (menuSecimiOku(4)) {
                case 1 -> islemiCalistir(this::istatistikEkle);
                case 2 -> islemiCalistir(() -> istatistikleriGoster(false));
                case 3 -> islemiCalistir(this::istatistikDetaylariniGoster);
                case 4 -> islemiCalistir(() -> istatistikleriGoster(true));
                case 0 -> { return; }
            }
        }
    }

    private void istatistikEkle() {
        Mac mac = macSec();
        if (!mac.isTamamlandi()) {
            throw new IllegalStateException("İstatistik girmeden önce maç sonucunu kaydetmelisin.");
        }
        System.out.println("\n  [1] " + mac.getEvSahibiTakim().getTakimAdi()
                + "   [2] " + mac.getDeplasmanTakimi().getTakimAdi());
        Takim takim = tamSayiOku("Futbolcunun takımı", 1, 2) == 1
                ? mac.getEvSahibiTakim() : mac.getDeplasmanTakimi();
        kadroyuGoster(takim);
        Futbolcu futbolcu = futbolcuSec(takim);
        for (FutbolcuMacIstatistigi mevcut : macServisi.macIstatistikleriniGetir(mac.getMacId())) {
            if (mevcut.getFutbolcu().getFutbolcuId() == futbolcu.getFutbolcuId()) {
                throw new FutbolcuIstatistigiZatenKayitliException(
                        "Bu futbolcunun maç istatistiği zaten kayıtlı."
                );
            }
        }
        baslik("İSTATİSTİK GİRİŞİ · " + futbolcu.getAdSoyad());
        int dakika = sayacOku("Oynanan dakika");
        int gol = sayacOku("Gol sayısı");
        int asist = sayacOku("Asist sayısı");
        int basariliPas = sayacOku("Başarılı pas sayısı");
        int basarisizPas = sayacOku("Başarısız pas sayısı");
        int sut = sayacOku("Şut sayısı");
        int isabetliSut = tamSayiOku("İsabetli şut sayısı", 0, sut);
        int topKapma = sayacOku("Top kapma sayısı");
        int topUzaklastirma = sayacOku("Top uzaklaştırma sayısı");
        int ikiliMucadele = sayacOku("Kazanılan ikili mücadele sayısı");
        int kurtaris = sayacOku("Kurtarış sayısı");
        int yenilenGol = sayacOku("Yenilen gol sayısı");
        FutbolcuMacIstatistigi istatistik = new FutbolcuMacIstatistigi(
                futbolcu, mac, dakika, gol, asist, basariliPas, basarisizPas,
                sut, isabetliSut, topKapma, topUzaklastirma, ikiliMucadele, kurtaris, yenilenGol
        );
        macServisi.macIstatistigiEkle(mac.getMacId(), istatistik);
        basarili(futbolcu.getAdSoyad() + " için istatistik kaydedildi.");
        detay("Maç puanı", String.format(TURKCE, "%.1f", futbolcu.performansPuaniHesapla(istatistik)));
    }

    private void istatistikleriGoster(boolean performansaGore) {
        Mac mac = macSec();
        List<FutbolcuMacIstatistigi> istatistikler = macServisi.macIstatistikleriniGetir(mac.getMacId());
        baslik((performansaGore ? "PERFORMANS SIRALAMASI · " : "İSTATİSTİKLER · ") + mac.getMacId());
        if (istatistikler.isEmpty()) {
            bilgi("Bu maça ait futbolcu istatistiği bulunmuyor.");
            return;
        }
        if (performansaGore) {
            istatistikler.sort(Comparator.comparingDouble(
                    (FutbolcuMacIstatistigi i) -> i.getFutbolcu().performansPuaniHesapla(i)
            ).reversed());
        } else {
            istatistikler.sort(Comparator.comparingInt(i -> i.getFutbolcu().getFutbolcuId()));
        }
        tabloBasligi("  %-5s %-10s %-25s %-12s %6s %5s %5s %12s",
                "SIRA", "ID", "FUTBOLCU", "MEVKİ", "DAKİKA", "GOL", "ASİST", "MAÇ PUANI");
        int sira = 1;
        for (FutbolcuMacIstatistigi i : istatistikler) {
            Futbolcu f = i.getFutbolcu();
            System.out.printf(TURKCE, "  %-5d %-10d %-25.25s %-12s %6d %5d %5d %12.1f%n",
                    sira++, f.getFutbolcuId(), f.getAdSoyad(), mevkiAdi(f),
                    i.getOynananDakika(), i.getGolSayisi(), i.getAsistSayisi(), f.performansPuaniHesapla(i));
        }
    }

    private void istatistikDetaylariniGoster() {
        Mac mac = macSec();
        int futbolcuId = tamSayiOku("Futbolcu ID", 1, Integer.MAX_VALUE);
        for (FutbolcuMacIstatistigi i : macServisi.macIstatistikleriniGetir(mac.getMacId())) {
            if (i.getFutbolcu().getFutbolcuId() == futbolcuId) {
                baslik("MAÇ İSTATİSTİĞİ · " + i.getFutbolcu().getAdSoyad());
                detay("Maç ID", mac.getMacId());
                detay("Oynanan dakika", i.getOynananDakika());
                detay("Gol / asist", i.getGolSayisi() + " / " + i.getAsistSayisi());
                detay("Başarılı / başarısız pas", i.getBasariliPasSayisi() + " / " + i.getBasarisizPasSayisi());
                detay("Şut / isabetli şut", i.getSutSayisi() + " / " + i.getIsabetliSutSayisi());
                detay("Top kapma", i.getTopKapmaSayisi());
                detay("Top uzaklaştırma", i.getTopUzaklastirmaSayisi());
                detay("Kazanılan ikili mücadele", i.getKazanilanIkiliMucadeleSayisi());
                detay("Kurtarış", i.getKurtarisSayisi());
                detay("Yenilen gol", i.getYenilenGolSayisi());
                detay("Maç puanı", String.format(TURKCE, "%.1f", i.getFutbolcu().performansPuaniHesapla(i)));
                return;
            }
        }
        throw new IllegalStateException("Bu futbolcunun seçilen maçta istatistik kaydı bulunmuyor.");
    }


    private void puanDurumuMenusu() {
        while (true) {
            islemiCalistir(this::puanTablosunuGoster);
            islemlerBasligi();
            secenek(1, "TABLOYU YENİLE", "Güncel maç kayıtlarından yeniden hesapla");
            altMenuBilgisi();
            if (menuSecimiOku(1) == 0) return;
        }
    }

    private void puanTablosunuGoster() {
        menuBasligi("PUAN DURUMU");
        List<PuanDurumu> tablo = puanDurumuServisi.puanTablosunuHesapla();
        if (tablo.isEmpty()) {
            bilgi("Puan tablosunu görmek için önce takım eklemelisin.");
            return;
        }
        tabloBasligi("  %-4s %-26s %4s %3s %3s %3s %4s %4s %6s %6s",
                "SIRA", "TAKIM", "OM", "G", "B", "M", "AG", "YG", "AV", "PUAN");
        int sira = 1;
        for (PuanDurumu satir : tablo) {
            System.out.printf("  %-4d %-26.26s %4d %3d %3d %3d %4d %4d %+6d %6d%n",
                    sira++, satir.getTakim().getTakimAdi(), satir.getTakiminOynanadigiMacSayisi(),
                    satir.getGalibiyetSayisi(), satir.getBeraberlikSayisi(), satir.getMaglubiyetSayisi(),
                    satir.getAtilanGolSayisi(), satir.getYenilenGolSayisi(), satir.getAveraj(), satir.getPuan());
        }
        System.out.println();
        bilgi("OM: Oynanan maç · G: Galibiyet · B: Beraberlik · M: Mağlubiyet");
        bilgi("AG: Atılan gol · YG: Yenilen gol · AV: Averaj");
        bilgi("Sıralama: Puan, ardından averaj. Yalnızca tamamlanan maçlar işlenir.");
    }


    private Takim takimSec(String mesaj) {
        return takimRepository.idIleGetir(tamSayiOku(mesaj, 1, Integer.MAX_VALUE));
    }

    private Futbolcu futbolcuSec(Takim takim) {
        return takim.idIleFutbolcuGetir(tamSayiOku("Futbolcu ID", 1, Integer.MAX_VALUE));
    }

    private Mac macSec() {
        return macRepository.idIleGetir(tamSayiOku("Maç ID", 1, Integer.MAX_VALUE));
    }

    private String mevkiAdi(Futbolcu futbolcu) {
        if (futbolcu instanceof Kaleci) return "Kaleci";
        if (futbolcu instanceof Savunmaci) return "Savunmacı";
        if (futbolcu instanceof OrtaSaha) return "Orta saha";
        if (futbolcu instanceof Forvet) return "Forvet";
        return futbolcu.getClass().getSimpleName();
    }

    private String metinOku(String mesaj) {
        while (true) {
            System.out.print("  " + renklendir(mesaj, BEYAZ) + renklendir(" > ", MAVI));
            String giris = scanner.nextLine().trim();
            if (giris.equalsIgnoreCase("iptal")) throw new CancellationException();
            if (!giris.isEmpty()) return giris;
            uyari("Bu alan boş bırakılamaz.");
        }
    }

    private int tamSayiOku(String mesaj, int enAz, int enFazla) {
        while (true) {
            try {
                int sayi = Integer.parseInt(metinOku(mesaj));
                if (sayi >= enAz && sayi <= enFazla) return sayi;
                uyari(enAz + " ile " + enFazla + " arasında bir değer girin.");
            } catch (NumberFormatException exception) {
                uyari("Geçerli bir tam sayı girin.");
            }
        }
    }

    private int menuSecimiOku(int enFazla) {
        try {
            return tamSayiOku("Seçiminiz", 0, enFazla);
        } catch (CancellationException exception) {
            return 0;
        }
    }

    private int sayacOku(String mesaj) {
        return tamSayiOku(mesaj, 0, Integer.MAX_VALUE);
    }

    private BigDecimal paraOku(String mesaj, boolean sifirKabulEdilsin) {
        while (true) {
            String giris = metinOku(mesaj + " (örnek: 50000000 veya 50000000,50)");
            if (giris.matches("[0-9]+([,.][0-9]{1,2})?")) {
                BigDecimal miktar = new BigDecimal(giris.replace(',', '.'));
                if (miktar.signum() > 0 || (sifirKabulEdilsin && miktar.signum() == 0)) return miktar;
            }
            uyari((sifirKabulEdilsin ? "Sıfır veya pozitif" : "Sıfırdan büyük")
                    + " bir tutar girin. Binlik ayırıcı kullanmayın.");
        }
    }

    private LocalDate tarihOku(String mesaj) {
        while (true) {
            try {
                return LocalDate.parse(metinOku(mesaj + " (gg.aa.yyyy)"), tarihFormati);
            } catch (DateTimeParseException exception) {
                uyari("Geçerli bir tarih girin. Örnek: 09.10.2026");
            }
        }
    }

    private boolean onayOku(String mesaj) {
        while (true) {
            String cevap = metinOku(mesaj + " [E/H]");
            if (cevap.equalsIgnoreCase("e")) return true;
            if (cevap.equalsIgnoreCase("h")) {
                bilgi("İşlemden vazgeçildi.");
                return false;
            }
            uyari("Onaylamak için E, vazgeçmek için H girin.");
        }
    }

    private void menuBasligi(String baslik) {
        System.out.println();
        System.out.println();
        System.out.println(renklendir("  " + MENU_CIZGISI, CERCEVE_MAVISI));
        ortaliYaz(baslik, KALIN + MAVI);
        System.out.println(renklendir("  " + MENU_CIZGISI, CERCEVE_MAVISI));
        System.out.println();
    }

    private void baslik(String baslik) {
        System.out.println();
        System.out.println("  " + renklendir("── ", GRI) + renklendir(baslik, KALIN + BEYAZ));
        System.out.println();
    }

    private void islemlerBasligi() {
        System.out.println();
        System.out.println(renklendir("  İŞLEMLER", GRI));
        System.out.println();
    }

    private void tabloBasligi(String format, Object... sutunlar) {
        System.out.println(renklendir(String.format(format, sutunlar), KALIN + GRI));
    }

    private void ortaliYaz(String metin, String renk) {
        int uzunluk = metin.codePointCount(0, metin.length());
        int solBosluk = Math.max(0, (EKRAN_GENISLIGI - uzunluk) / 2);
        System.out.println("  " + " ".repeat(solBosluk) + renklendir(metin, renk));
    }

    private void secenek(int numara, String baslik, String aciklama) {
        System.out.printf("  %s %-26s %s%n",
                renklendir("[" + numara + "]", numara == 0 ? GRI : MAVI),
                renklendir(String.format("%-26s", baslik), numara == 0 ? GRI : BEYAZ),
                renklendir(aciklama, GRI));
    }

    private void altMenuBilgisi() {
        System.out.println();
        secenek(0, "ANA MENÜ", "Üst menüye dön");
        System.out.println(renklendir(
                "\n  Bilgi girerken işlemi bırakmak için 'iptal' yazabilirsin.\n", GRI));
    }

    private void detay(String etiket, Object deger) {
        System.out.printf("  %s : %s%n", renklendir(String.format("%-27s", etiket), GRI),
                renklendir(String.valueOf(deger), BEYAZ));
    }

    private void basarili(String mesaj) {
        System.out.println(renklendir("\n  BAŞARILI · " + mesaj, YESIL));
    }

    private void bilgi(String mesaj) {
        System.out.println(renklendir("  " + mesaj, BEYAZ));
    }

    private void uyari(String mesaj) {
        System.out.println("  " + renklendir("UYARI", SARI) + " · " + renklendir(mesaj, BEYAZ));
    }

    private void islemHatasi(String mesaj) {
        System.out.println("\n  " + renklendir("UYARI", SARI)
                + " · " + renklendir("İşlem tamamlanamadı", KIRMIZI));
        bilgi(mesaj);
    }

    private String renklendir(String metin, String renk) {
        return RENKLER_AKTIF ? renk + metin + SIFIRLA : metin;
    }
}
