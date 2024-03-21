package iroshan.com.smsserver.pojo;



import java.util.Date;

/**
 *
 * @author Iroshan
 */
public class AutomatedSetting_pojo
{

    static String upload_date_time;
    static Date upload_date_time_as_date;
    static String upload_execution_period;
    
    static String download_date_time;
    static Date download_date_time_as_date;
    static String download_execution_period;
    
    static String fullSettlementDiscountRate;
    
      /**
       *
       * @return
       */
      public static String getUpload_date_time ()
    {
        return upload_date_time;
    }

      /**
       *
       * @param upload_date_time
       */
      public static void setUpload_date_time (String upload_date_time)
    {
        AutomatedSetting_pojo.upload_date_time = upload_date_time;
    }

      /**
       *
       * @return
       */
      public static String getUpload_execution_period ()
    {
        return upload_execution_period;
    }

      /**
       *
       * @param upload_execution_period
       */
      public static void setUpload_execution_period (String upload_execution_period)
    {
        AutomatedSetting_pojo.upload_execution_period = upload_execution_period;
    }

      /**
       *
       * @return
       */
      public static String getDownload_date_time ()
    {
        return download_date_time;
    }

      /**
       *
       * @param download_date_time
       */
      public static void setDownload_date_time (String download_date_time)
    {
        AutomatedSetting_pojo.download_date_time = download_date_time;
    }

      /**
       *
       * @return
       */
      public static String getDownload_execution_period ()
    {
        return download_execution_period;
    }

      /**
       *
       * @param download_execution_period
       */
      public static void setDownload_execution_period (String download_execution_period)
    {
        AutomatedSetting_pojo.download_execution_period = download_execution_period;
    }

      /**
       *
       * @return
       */
      public static Date getUpload_date_time_as_date ()
    {
        return upload_date_time_as_date;
    }

      /**
       *
       * @param upload_date_time_as_date
       */
      public static void setUpload_date_time_as_date (Date upload_date_time_as_date)
    {
        AutomatedSetting_pojo.upload_date_time_as_date = upload_date_time_as_date;
    }

      /**
       *
       * @return
       */
      public static Date getDownload_date_time_as_date ()
    {
        return download_date_time_as_date;
    }

      /**
       *
       * @param download_date_time_as_date
       */
      public static void setDownload_date_time_as_date (Date download_date_time_as_date)
    {
        AutomatedSetting_pojo.download_date_time_as_date = download_date_time_as_date;
    }

      /**
       *
       * @return
       */
      public static String getFullSettlementDiscountRate() {
        return fullSettlementDiscountRate;
    }

      /**
       *
       * @param fullSettlementDiscountRate
       */
      public static void setFullSettlementDiscountRate(String fullSettlementDiscountRate) {
        AutomatedSetting_pojo.fullSettlementDiscountRate = fullSettlementDiscountRate;
    }
    
    
    
    
}
