/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.gatway.api.dialog;

import iroshan.Company_Profile;

/**
 *
 * @author Iroshan
 */
public class Api {

    private static String apiKey;

//    private static String apiURL = "https://cpsolutions.dialog.lk/index.php/cbs/sms/send";
    private static String apiURL = "";

    public static String getApiKey() {
        if (Company_Profile.getCompanyCode().equalsIgnoreCase(Company_Profile.CompanyCodeEnum.REGAL.toString())) {
            apiKey = "14564750147633";
        }
        if (Company_Profile.getCompanyCode().equalsIgnoreCase(Company_Profile.CompanyCodeEnum.SIDDAMULLA.toString())) {
            System.out.println("in siddamulla........");
            apiKey = "14985547909844";
        }
        if (Company_Profile.getCompanyCode().equalsIgnoreCase(Company_Profile.CompanyCodeEnum.SMWM.toString())) {
            System.out.println("in SMWM........");
            apiKey = "15039994506562";
        }
        if (Company_Profile.getCompanyCode().equalsIgnoreCase(Company_Profile.CompanyCodeEnum.SEWA_CREDIT.toString())) {
            System.out.println("in SMWM........");
            apiKey = "15637964108681";
        }
        if (Company_Profile.getCompanyCode().equalsIgnoreCase(Company_Profile.CompanyCodeEnum.ATHUGALA_INVESTMENTS.toString())) {
            System.out.println("in SMWM........");
            apiKey = "15845131986251";
        }
        if (Company_Profile.getCompanyCode().equalsIgnoreCase(Company_Profile.CompanyCodeEnum.VURSJ_POLONNARUWA.toString())) {
            System.out.println("in VURSJ_POLONNARUWA........");
            apiKey = "0242dd46de80744";
        }
        if (Company_Profile.getCompanyCode().equalsIgnoreCase(Company_Profile.CompanyCodeEnum.HORAHENA.toString())) {
            System.out.println("in HORAHENA........");
            apiKey = "490dbc0d3bc08ce";
        }
        return apiKey.trim();
    }

    public static void setApiKey(String apiKey) {
        Api.apiKey = apiKey;
    }

    public static String getApiURL() {

//        if (Company_Profile.getCompanyCode().equalsIgnoreCase(Company_Profile.CompanyCodeEnum.SEWA_CREDIT.toString())) {
//            apiURL = "https://richcommunication.dialog.lk/api/sms/send";
//        } else {
//            apiURL = "cpsolutions.dialog.lk/index.php/cbs/sms/send";
//        }
        if (Company_Profile.getCompanyCode().equalsIgnoreCase(Company_Profile.CompanyCodeEnum.VURSJ_POLONNARUWA.toString())) {
            apiURL = "richcommunication.dialog.lk/api/sms/inline/send";
            apiURL = apiURL.trim();
        } else if (Company_Profile.getCompanyCode().equalsIgnoreCase(Company_Profile.CompanyCodeEnum.HORAHENA.toString())) {
            apiURL = "richcommunication.dialog.lk/api/sms/inline/send";
            apiURL = apiURL.trim();
        } else {
            apiURL = "cpsolutions.dialog.lk/index.php/cbs/sms/send";
            apiURL = apiURL.trim();
        }

        return apiURL;
    }

    public static void setApiURL(String apiURL) {
        Api.apiURL = apiURL;
    }

}
