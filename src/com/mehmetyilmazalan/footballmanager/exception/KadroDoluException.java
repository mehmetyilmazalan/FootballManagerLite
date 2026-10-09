package com.mehmetyilmazalan.footballmanager.exception;

import com.mehmetyilmazalan.footballmanager.model.Takim;

public class KadroDoluException extends RuntimeException {
    public  KadroDoluException(String mesaj){
        super(mesaj);
    }

}
