package com.bakery.pj.domain.breadstore;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Breadstore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entrpNm;
    private String loadAddr;
    private String cityDoCd;
    private String cityGnGuCd;
    private double xposLo;
    private double yposLa;
    private String areaNm;
    private String homepageUrl;
    private String telNo;
    private String reprsntMenuNm;
    private String menuPc;
    private String baseYmd;
    private String naverPlaceUrl;
    private String businessHours;

    @Lob
    private byte[] image;

    @Builder
    public Breadstore(String entrpNm, String loadAddr, String cityDoCd, String cityGnGuCd, double xposLo,
                      double yposLa, String areaNm, String homepageUrl, String telNo, String reprsntMenuNm,
                      String menuPc, String baseYmd, String naverPlaceUrl, String businessHours, byte[] image) {

        this.entrpNm = entrpNm;
        this.loadAddr = loadAddr;
        this.cityDoCd = cityDoCd;
        this.cityGnGuCd = cityGnGuCd;
        this.xposLo = xposLo;
        this.yposLa = yposLa;
        this.areaNm = areaNm;
        this.homepageUrl = homepageUrl;
        this.telNo = telNo;
        this.reprsntMenuNm = reprsntMenuNm;
        this.menuPc = menuPc;
        this.baseYmd = baseYmd;
        this.naverPlaceUrl = naverPlaceUrl;
        this.businessHours = businessHours;
        this.image = image;
    }






}
