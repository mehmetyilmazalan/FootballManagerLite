package com.mehmetyilmazalan.footballmanager.repository;


import java.util.List;
//Saklanacak şey: __T___
//Onu bulmak için kullanılacak kimliğin türü: __ID___
public interface Repository <T,ID>{
    void ekle(T nesne);

    T idIleGetir(ID id);

    void sil(ID id);

    void guncelle(T nesne);

    List<T> tumunuGetir();


}
