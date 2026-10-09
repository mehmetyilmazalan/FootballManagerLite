# Football Manager Lite

Java ile geliştirilmiş, konsol üzerinden kullanılan bir futbol yönetim uygulaması. Takım oluşturma, kadro yönetimi, transfer, maç planlama, futbolcu performansı ve puan tablosu hesaplama işlemlerini içerir.

Bu proje, nesne yönelimli programlama (OOP) kavramlarını bir uygulama üzerinde çalışmak amacıyla hazırlanmıştır. Model, repository, servis ve konsol arayüzü ayrı paketlerde düzenlenmiştir.

## Özellikler

### Takım ve kadro yönetimi

- Takım oluşturma, listeleme ve detaylarını görüntüleme
- Transfer bütçesini artırma ve azaltma
- Takım kadrosunu ve toplam piyasa değerini görüntüleme
- Futbolcu ekleme, detaylarını görüntüleme ve kadrodan çıkarma
- Forma numarası ve piyasa değeri değiştirme
- Maç kaydı bulunmayan takımları silme

### Transfer işlemleri

- Takımlar arasında futbolcu transferi
- Transfer bedeline göre alıcı ve satıcı bütçelerini güncelleme
- Yetersiz bütçe, dolu kadro ve forma numarası çakışması kontrolleri
- Alıcı kadrosuna ekleme sırasında belirli bir kadro hatası oluşursa düşürülen bütçeyi geri yükleme

### Maç ve istatistik yönetimi

- Maç planlama, listeleme ve detaylarını görüntüleme
- Maç sonucunu kaydetme ve maçı tamamlanmış olarak işaretleme
- Tamamlanan bir maçın sonucunun tekrar kaydedilmesini engelleme
- Maç kaydını silme
- Futbolcuların maç istatistiklerini ekleme ve görüntüleme
- Mevkiye göre performans puanı hesaplama ve sıralama

### Puan tablosu

- Tamamlanan maçlardan puan tablosu hesaplama
- Galibiyet, beraberlik, mağlubiyet ve oynanan maç sayısını gösterme
- Atılan gol, yenilen gol ve averaj hesaplama
- Önce puan, ardından averaja göre sıralama
- Her hesaplamada tabloyu yeniden oluşturma; tekrar hesaplamada puanları ikiye katlamama

## Kullanılan teknolojiler ve kavramlar

- Java 17 veya üzeri
- Java Collections: `List`, `Map`, `ArrayList`, `HashMap`
- Para işlemleri için `BigDecimal`
- Tarih işlemleri için `LocalDate`
- Kalıtım, soyut sınıflar ve polymorphism
- Interface ve generic türler: `Repository<T, ID>`
- Constructor üzerinden bağımlılık aktarımı
- Özel exception sınıfları ile hata yönetimi
- ANSI renkleriyle konsol menüsü

Uygulama harici kütüphane gerektirmez.

## Proje yapısı

Kaynak kodlar `src/com/mehmetyilmazalan/footballmanager` altında bulunur.

| Paket | Görevi |
| --- | --- |
| `model` | Futbolcu, takım, maç, maç istatistiği ve puan durumu modelleri |
| `repository` | Takımları ve maçları bellekte saklama; ekleme, getirme, silme ve güncelleme |
| `service` | Takım, transfer, maç ve puan tablosu işlemleri |
| `exception` | İş kurallarına ilişkin özel hata sınıfları |
| `application` | Uygulama başlangıcı, konsol menüsü ve manuel test senaryoları |

| Sınıf / interface | Görevi |
| --- | --- |
| `Futbolcu` | Futbolcuların ortak özelliklerini ve performans hesaplama sözleşmesini tanımlar |
| `Kaleci`, `Savunmaci`, `OrtaSaha`, `Forvet` | Mevkiye göre farklı performans hesaplamaları uygular |
| `Takim` | Kadroyu, transfer bütçesini ve takım içi kuralları yönetir |
| `Mac` | Maçın takımlarını, tarihini, sonucunu ve futbolcu istatistiklerini tutar |
| `FutbolcuMacIstatistigi` | Bir futbolcunun belirli bir maçtaki istatistiklerini tutar |
| `PuanDurumu` | Bir takımın puan tablosundaki değerlerini hesaplar |
| `Repository<T, ID>` | Kayıt işlemlerinin ortak metotlarını tanımlar |
| `TakimRepository`, `MacRepository` | Ortak repository sözleşmesini kendi model türleri için uygular |
| `TakimServisi` | Takım ID'si üzerinden kadro ve futbolcu güncelleme işlemlerini yürütür |
| `TransferServisi` | Futbolcu transferini ve ilgili bütçe hareketlerini yürütür |
| `MacServisi` | Maç planlama, sonuç kaydetme ve istatistik işlemlerini yürütür |
| `PuanDurumuServisi` | Kayıtlı takımlar ve tamamlanan maçlardan sıralı puan tablosunu oluşturur |
| `KonsolMenusu` | Kullanıcı girişlerini, menüleri ve işlem mesajlarını yönetir |

## Çalıştırma

1. Repository'yi indirin veya Git ile klonlayın.
2. Proje klasörünü IntelliJ IDEA ile açın.
3. Project SDK olarak Java 17 veya üzeri bir JDK seçin. Gerekirse `src` klasörünü **Sources Root** olarak işaretleyin.
4. `application` paketindeki `FutbollManagerLiteApplicaiton` sınıfının `main` metodunu çalıştırın.
5. Ana menüde **Takımlar** bölümünden takım oluşturarak başlayın.

Sınıf adları kaynak koddaki mevcut yazımlarıyla verilmiştir.

## Konsol kullanımı

| Ana menü seçeneği | Bölüm |
| --- | --- |
| `1` | Takımlar |
| `2` | Futbolcular |
| `3` | Transfer Merkezi |
| `4` | Maç Merkezi |
| `5` | Puan Durumu |
| `6` | Maç İstatistikleri |
| `0` | Çıkış |

- Alt menülerde `0`, ana menüye döner.
- Form doldururken `iptal` yazarak işlemi bırakabilirsiniz.
- Tarihler `gg.aa.yyyy` biçiminde girilir: `10.10.2026`.
- Para girişinde binlik ayırıcı kullanılmaz: `50000000` veya `50000000,50`.
- İşlem hataları açıklamalı olarak gösterilir ve kullanıcı bulunduğu alt menüden devam eder.
- Renkleri kapatmak için `KonsolMenusu` içindeki `RENKLER_AKTIF` değerini `false` yapabilirsiniz.

## Temel kurallar

- Takım kadrosu en fazla 24 futbolcudan oluşur.
- Aynı kadroda futbolcu ID'si ve forma numarası tekrarlanamaz.
- Konsoldan yeni futbolcu eklenirken ID'nin diğer kayıtlı takım kadrolarında kullanılıp kullanılmadığı da kontrol edilir.
- Forma numarası değiştirilirken 1–99 aralığı ve takım içindeki numara çakışmaları kontrol edilir.
- Bir takım kendisiyle maç yapamaz veya kendi kendine transfer yapamaz.
- Gol sayıları negatif olamaz.
- Aynı maç için aynı futbolcunun istatistiği tekrar eklenemez.
- Konsol, maç kaydı bulunan takımların silinmesini engeller.

## Manuel testler

`application` paketindeki `FutbollManagerTestApplicaiton` sınıfının `main` metodunu çalıştırarak manuel test senaryolarını inceleyebilirsiniz.

Senaryolar; kadro işlemleri, repository işlemleri, transfer, başarısız transferde bütçe geri yükleme, maç sonuçları, istatistik kayıtları ve puan tablosu hesaplamasını kapsar. Beklenen başarısız işlemler `try-catch` ile yakalanır ve konsola yazdırılır. Bu sınıf JUnit test paketi değildir.

## Veri saklama

Veriler uygulama çalışırken bellekte tutulur. Uygulama kapatıldığında takım, futbolcu, maç ve istatistik kayıtları silinir. Veritabanı veya dosyaya kalıcı kayıt desteği bulunmaz.

Puan tablosu, kayıtlı tüm takımlar ve tamamlanan maçlar üzerinden hesaplanır; ayrı lig veya sezon ayrımı bulunmaz.

## Geliştirici

Mehmet Yılmaz Alan
