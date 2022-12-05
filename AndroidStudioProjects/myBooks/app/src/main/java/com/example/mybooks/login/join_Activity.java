package com.example.mybooks.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mybooks.databinding.ActivityJoinBinding;
import com.example.mybooks.home_bottom_1to5.homeActivity;
import com.example.mybooks.retrofit.HttpRequest;
import com.example.mybooks.retrofit.Response;
import com.example.mybooks.retrofit.RetrofitClient;

import java.util.Properties;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import retrofit2.Call;
import retrofit2.Callback;

public class join_Activity extends AppCompatActivity {

    private final String TAG=this.getClass().getSimpleName();
    private ActivityJoinBinding binding;
    private String strCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.mybooks.databinding.ActivityJoinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 초기 설정
        binding.btnCheckCode.setEnabled(false); // 인증확인 버튼 눌러도 반응X


        // 인증번호 보내기 버튼
        binding.btnSendCodeToEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Log.e(TAG, "인증번호 보내기");

                sendEmailForGmail(binding.etJoinEmail.getText().toString(), createEmailCode(5)); // 입력한 이메일 내 Gmail로 보내기

            }
        });


        // 인증 확인 버튼
        binding.btnCheckCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (strCode.equals(binding.etJoinEmailCheck.getText().toString())) { Log.e(TAG, "(인증번호 입력 성공) 시 버튼 색 비활성화");
                    viewChangeAfterCheckingStringCode(binding.etJoinEmail, binding.etJoinEmailCheck, binding.btnSendCodeToEmail, binding.btnCheckCode);
                } else {
                    Toast.makeText(getApplicationContext(), "인증번호를 다시 확인해 주세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });


        // 회원가입 완료 버튼
        binding.btnJoinDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (patternPW(binding.etJoinPw.getText().toString()) // (정규식, 비번재입력 일치 통과) 시 회원가입 완료
                        && binding.etJoinPw.getText().toString().equals(binding.etJoinPwDoubleCheck.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                    //
                    // 디비에 회원 추가하기기
                   //
                    //

                    Intent intent = new Intent(join_Activity.this, homeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // 현재화면빼고 아래액티비티 지움
                    startActivity(intent);
                    Log.e(TAG, "startActivity() \n"+TAG+" -> homeActivity.class ");

                } else {
                    Toast.makeText(getApplicationContext(), "다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });




    } // ~onCreate()


    private void dfsf(String email, String pw, String methodName) {

        RetrofitClient retrofitClient = RetrofitClient.getInstance(); // 요청 인터페이스, 빌드, 로그 한 데 모아둔 클래스
        HttpRequest httpRequest = retrofitClient.getRetrofitInterface(); // 에서 꺼내쓴다.


        // 요청 메소드 : getRegister()
        httpRequest.getRegister(email, pw, methodName).enqueue(new Callback<Response>() { // 파라미터 3개보내면서 요청하면 결과로 응답/실패 // 비동기
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                Response response1 = response.body();
                assert response1 != null;
                Log.e(TAG, "onResponse() / response1 : "+response1.getMessage());

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.e(TAG, "onFailure() "+t.getMessage());
            }
        });
    }


    private boolean patternPW(String PW) { //비밀번호 유효성 검사(정규식)
        Log.e(TAG, "patternPW() parameter (PW : "+PW+")");

        boolean patternPW;
        if(Pattern.matches("^(?=.*[a-zA-z])(?=.*[0-9]).{6,12}$", PW)) { //숫자1개이상, 문자1개이상, 6~12자리(입력자체를 막아두긴 했지만 정규식으로 한 번더 필터)
            patternPW = true;
        } else {
            patternPW = false;
        }

        return patternPW;
    }



    //인증확인 후 일치하면 변경되는 et,btn상태
    public void viewChangeAfterCheckingStringCode(EditText et_email, EditText et_emailCheck, Button btn_sendEmail, Button btn_emailCheck) { // (이메일 입력, 인증번호 입력, 인증번호보내기 버튼, 인증확인 버튼)

        et_email.setText("전송 완료"); //입력한 코드대신 '인증완료'로 변경
        et_email.setTextColor(Color.parseColor("#CCCFCC"));
        et_email.setFocusable(false); //이메일
        et_email.setClickable(false);

        et_emailCheck.setText("인증 완료"); //입력한 코드대신 '인증완료'로 변경
        et_emailCheck.setTextColor(Color.parseColor("#CCCFCC")); //회색으로 변경(비활성화)
        et_emailCheck.setFocusable(false); //껌뻑껌뻑 포커스 비활성화
        et_emailCheck.setClickable(false); //클릭 비활성화
        et_emailCheck.setEnabled(false); //버튼 비활성화(클릭리스너 작동x)

        btn_sendEmail.setEnabled(false); //인증번호보내기 버튼

        btn_emailCheck.setBackgroundColor(Color.parseColor("#FF9422")); // 주황색으로 변경
        btn_emailCheck.setText("인증 완료"); //버튼 인증확인->인증완료
        btn_emailCheck.setEnabled(false);

        Toast.makeText(getApplicationContext(), "인증되었습니다.", Toast.LENGTH_SHORT).show();

    }



    private void viewChangeAfterSendingEmail(Button btn_sendEmail, Button btn_EmailCheck) {

        btn_sendEmail.setText("다시\n보내기");
        btn_sendEmail.setBackgroundColor(Color.parseColor("#FF9422"));

        btn_EmailCheck.setEnabled(true); //이메일로 인증번호보내기 전까지는 인증확인버튼 비활성화 >> 보내고 바로 활성화

        Toast.makeText(getApplicationContext(), "기입하신 이메일로 인증번호를 보냈습니다.", Toast.LENGTH_SHORT).show();

    }



    private void sendEmailForGmail(String receiverEmail, String stringCode) { // 회원가입 시 이메일 인증번호 보내기 (사용자가 입력한 이메일, 랜덤생성코드 5자리)
        Log.e(TAG, "sendEmailForGmail() parameter (receiverEmail : "+receiverEmail+", stringCode : "+stringCode+")");
        Log.e(TAG, "sendEmailForGmail() strCode /전역변수로 뺌. 이 액티비티 안에서 유효하도록(null이면 안 됨) : "+strCode);

        if (!receiverEmail.equals("")) {
            try {

                String senderEmail = "cocohan4919@gmail.com";
                String senderPw = "rzmffmkroxspoolh"; // 임시 키 받음 (+22.05부터 정책이 바껴서 2단계 인증안하면 안된다고 함. 구글 보안 설정이 없어짐)
                String host = "smtp.gmail.com";
//            receiverEmail = "rlagksdl96@naver.com"; // test용

                Properties properties = System.getProperties();
                properties.put("mail.smtp.host", host);
                properties.put("mail.smtp.port", "465");
                properties.put("mail.smtp.ssl.enable", "true");
                properties.put("mail.smtp.auth", "true");

                Session session = Session.getInstance(properties, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() { // 인증
                        return new PasswordAuthentication(senderEmail, senderPw);
                    }
                });

                MimeMessage mimeMessage = new MimeMessage(session);
                mimeMessage.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(receiverEmail))); // 에러나면 여기 확인하기


                // 메일 작성
                mimeMessage.setSubject("[ myBook ]에서 보낸 인증번호입니다."); // 제목
                mimeMessage.setText("인증번호 : "+stringCode+
                        "\n 어플로 돌아가 인증번호를 입력해주세요."); // 내용


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Transport.send(mimeMessage);
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


                // 뷰 변경
                viewChangeAfterSendingEmail(binding.btnSendCodeToEmail, binding.btnCheckCode);


            } catch (MessagingException e) { e.printStackTrace(); }

        } else { // 이메일란이 빈 값일 경우
            Toast.makeText(getApplicationContext(), "이메일을 다시 확인해 주세요.", Toast.LENGTH_SHORT).show();
        }


    }



    private String createEmailCode(int how_figure) { // 이메일 인증코드 생성 (자리수)
        Log.e(TAG, "createEmailCode() parameter (how_figure : "+how_figure+")");


        String[] str = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
                "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String newCode = new String();

        for (int x = 0; x < how_figure; x++) {
            int random = (int) (Math.random() * str.length);
            newCode += str[random];
        }

        return strCode = newCode;
    }




}