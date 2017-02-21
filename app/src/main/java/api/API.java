package api;

import android.content.Context;

import java.util.List;

import apimodels.AssignUser;
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

    private static final String TAG = AppUtils.APP_TAG + API.class.getSimpleName();
    private ApiInterface apiService;

    public API(Context context)
    {
        apiService = ApiClient.getClient(context).create(ApiInterface.class);
    }

    //Get the only object available
    public API getInstance(Context context)
    {
        return new API(context);
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

    public void getListUsersForAssignLead(Callback<List<AssignUser>> callback)
    {
        Call<List<AssignUser>> call = apiService.getListUsersForAssignLead("list_users_for_assign_lead");

        call.enqueue(callback);
    }

    public Call<String> assignLeadTo(String sessionId, String enquiryId, String assigneeId,
                                     Callback<String> callback)
    {
        Call<String> call = apiService.assignLeadTo("assign_to", sessionId, enquiryId, assigneeId);

        call.enqueue(callback);

        return call;
    }


}
