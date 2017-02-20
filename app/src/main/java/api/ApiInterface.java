package api;

import java.util.List;

import apimodels.AssignUser;
import apimodels.CustomFollowUpReport;
import apimodels.CustomLeadReports;
import apimodels.EfficiencyReport;
import apimodels.FiltersForCustomLeadReports;
import apimodels.UserSnapshotReport;
import extras.AppUtils;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by rutvik on 12/3/2016 at 12:03 PM.
 */

public interface ApiInterface
{

    @FormUrlEncoded
    @POST(AppUtils.URL_WEBSERVICE_RETRO)
    Call<FiltersForCustomLeadReports> getFiltersForCustomLeadReport(@Field("method") String method,
                                                                    @Field("session_id") String sessionId);

    @FormUrlEncoded
    @POST(AppUtils.URL_WEBSERVICE_RETRO)
    Call<CustomLeadReports> getCustomLeadReport(@Field("method") String method,
                                                @Field("session_id") String sessionId,
                                                @Field("from_date") String fromDate,
                                                @Field("to_date") String toDate,
                                                @Field("user_id") String userId,
                                                //@Field("customer_type_id") String customerTypeId,
                                                @Field("leadStatus") String leadStatus,
                                                @Field("product") String product);

    @FormUrlEncoded
    @POST(AppUtils.URL_WEBSERVICE_RETRO)
    Call<List<UserSnapshotReport>> getUserSnapshotReport(@Field("method") String method,
                                                         @Field("session_id") String sessionId,
                                                         @Field("from_date") String fromDate,
                                                         @Field("to_date") String toDate);

    @FormUrlEncoded
    @POST(AppUtils.URL_WEBSERVICE_RETRO)
    Call<CustomFollowUpReport> getCustomFollowUpReports(@Field("method") String method,
                                                        @Field("session_id") String sessionId,
                                                        @Field("from_date") String fromDate,
                                                        @Field("to_date") String toDate);

    @FormUrlEncoded
    @POST(AppUtils.URL_WEBSERVICE_RETRO)
    Call<EfficiencyReport> getEfficiencyReport(@Field("method") String method,
                                               @Field("session_id") String sessionId,
                                               @Field("from_date") String fromDate,
                                               @Field("to_date") String toDate,
                                               @Field("user_id") String userId,
                                               @Field("product") String product);

    @FormUrlEncoded
    @POST(AppUtils.URL_WEBSERVICE_RETRO)
    Call<List<AssignUser>> getListUsersForAssignLead(@Field("method") String method);

}
