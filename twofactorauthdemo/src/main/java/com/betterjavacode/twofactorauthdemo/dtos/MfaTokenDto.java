package com.betterjavacode.twofactorauthdemo.dtos;

import java.io.Serializable;

public class MfaTokenDto implements Serializable
{
    private String qrCode;
    private String mfaCode;

    public MfaTokenDto() {
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getMfaCode() {
        return mfaCode;
    }

    public void setMfaCode(String mfaCode) {
        this.mfaCode = mfaCode;
    }

    public MfaTokenDto(String qrCode, String mfaCode) {
        this.qrCode = qrCode;
        this.mfaCode = mfaCode;
    }
}
