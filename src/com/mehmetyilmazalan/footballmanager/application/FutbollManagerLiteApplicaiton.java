package com.mehmetyilmazalan.footballmanager.application;

import com.mehmetyilmazalan.footballmanager.model.Mac;
import com.mehmetyilmazalan.footballmanager.model.Takim;
import com.mehmetyilmazalan.footballmanager.repository.MacRepository;
import com.mehmetyilmazalan.footballmanager.repository.Repository;
import com.mehmetyilmazalan.footballmanager.repository.TakimRepository;
import com.mehmetyilmazalan.footballmanager.service.MacServisi;
import com.mehmetyilmazalan.footballmanager.service.PuanDurumuServisi;
import com.mehmetyilmazalan.footballmanager.service.TakimServisi;
import com.mehmetyilmazalan.footballmanager.service.TransferServisi;

public class FutbollManagerLiteApplicaiton {

    public static void main(String[] args) {
        Repository<Takim, Integer> takimRepository = new TakimRepository();
        Repository<Mac, Integer> macRepository = new MacRepository();

        TakimServisi takimServisi = new TakimServisi(takimRepository);
        TransferServisi transferServisi = new TransferServisi(takimRepository);
        MacServisi macServisi = new MacServisi(macRepository, takimRepository);
        PuanDurumuServisi puanDurumuServisi =
                new PuanDurumuServisi(macRepository, takimRepository);

        KonsolMenusu konsolMenusu = new KonsolMenusu(
                takimRepository,
                macRepository,
                takimServisi,
                transferServisi,
                macServisi,
                puanDurumuServisi
        );

        konsolMenusu.baslat();
    }
}
