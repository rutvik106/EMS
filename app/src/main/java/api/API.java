package api;

import apimodels.CustomLeadReports;
import apimodels.FiltersForCustomLeadReports;
import extras.AppUtils;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by rutvik on 12/3/2016 at 12:02 PM.
 */

public class API
{

    private ApiInterface apiService;

    //create an object of SingleObject
    private static API instance = new API();

    private static final String TAG = AppUtils.APP_TAG + API.class.getSimpleName();

    private API()
    {
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    //Get the only object available
    public static API getInstance()
    {
        return instance;
    }


    public void getFilterForCustomEnquiryReport(String sessionId, Callback<FiltersForCustomLeadReports> callback)
    {
        Call<FiltersForCustomLeadReports> call = apiService.getFiltersForCustomLeadReport("get_filters_for_custom_lead_reports", sessionId);

        call.enqueue(callback);
    }


    public void getCustomLeadReport(String sessionId, String fromDate, String toDate, String userId,
                                    String leadStatus, String product,
                                    Callback<CustomLeadReports> callback)
    {
        System.out.println(fromDate);
        System.out.println(toDate);
        Call<CustomLeadReports> call = apiService.getCustomLeadReport("custom_lead_reports", sessionId,
                fromDate, toDate, userId, leadStatus, product);

        call.enqueue(callback);
    }

}
