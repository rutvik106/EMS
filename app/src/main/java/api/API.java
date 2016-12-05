package api;

import java.util.List;

import apimodels.CustomFollowUpReport;
import apimodels.CustomLeadReports;
import apimodels.EfficiencyReport;
import apimodels.FiltersForCustomLeadReports;
import apimodels.UserSnapshotReport;
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


    public void getUserSnapshotReport(String sessionId, String fromDate, String toDate,
                                      Callback<List<UserSnapshotReport>> callback)
    {
        Call<List<UserSnapshotReport>> call = apiService.getUserSnapshotReport("user_snapshot_report", sessionId,
                fromDate, toDate);

        call.enqueue(callback);
    }

    public void getCustomFollowUpReports(String sessionId, String fromDate, String toDate,
                                         Callback<CustomFollowUpReport> callback)
    {
        Call<CustomFollowUpReport> call = apiService.getCustomFollowUpReports("custom_follow_up_reports", sessionId,
                fromDate, toDate);

        call.enqueue(callback);
    }

    public void getEfficiencyReport(String sessionId, String fromDate, String toDate, String userId,
                                    String product,
                                    Callback<EfficiencyReport> callback)
    {
        Call<EfficiencyReport> call = apiService.getEfficiencyReport("effeciency_report", sessionId,
                fromDate, toDate, userId, product);

        call.enqueue(callback);
    }


}
