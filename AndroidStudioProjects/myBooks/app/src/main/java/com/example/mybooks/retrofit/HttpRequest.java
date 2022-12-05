package com.example.mybooks.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface HttpRequest {

    @FormUrlEncoded //POST방식 사용시 입력해야함
    @POST("myBook/register.php") // URL주소의 BaseDomain이하의 주소를 입력
    Call<Response> getRegister( // Call<반환타입> , 파라미터:php로 보내는 데이터. php는 키값인 ""으로 구분함
                            @Field("email") String email, // php의 $_POST['JoinEmail']로 가는 부분
                            @Field("pw") String pw,
                            @Field("methodName") String methodName // 선별용으로 같이 함수명을 보냄
    );




}
