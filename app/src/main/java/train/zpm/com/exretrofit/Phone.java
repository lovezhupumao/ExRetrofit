package train.zpm.com.exretrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/5/22 0022.
 */
public interface Phone {
    @GET("mobile/get")
    Call<PhoneNumInfo> getHaoMa(@Query("phone") String phone,@Query("key") String key);
}
