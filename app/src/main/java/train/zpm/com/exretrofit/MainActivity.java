package train.zpm.com.exretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {


    private TextView mtext;
    private EditText meditview;
    private Button   mBtn;
    private String key="2f121eb8bf260e938df638ec3cc2e5d4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtext=(TextView)findViewById(R.id.mytext);
        meditview=(EditText)findViewById(R.id.myedit);
        mBtn=(Button)findViewById(R.id.btn);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl("http://apis.juhe.cn/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Phone phone=retrofit.create(Phone.class);
                final Call<PhoneNumInfo> call=phone.getHaoMa(meditview.getText().toString(), key);


                   new Thread(){
                       @Override
                       public void run() {
                           try {
                               Response<PhoneNumInfo> bodyResponse = call.execute();
                               final String body = bodyResponse.body().getResult().getCity();//获取返回体的字符串
                               MainActivity.this.runOnUiThread(new Runnable() {
                                   @Override
                                   public void run() {
                                       mtext.setText(body);
                                   }
                               });
                           }
                           catch (IOException e){
                               e.printStackTrace();
                           }
                       }
                   }.start();
/*
                call.enqueue(new Callback<PhoneNumInfo>() {
                    @Override
                    public void onResponse(Call<PhoneNumInfo> call, Response<PhoneNumInfo> response) {
                        mtext.setText(response.body().getResult().getCity());
                    }

                    @Override
                    public void onFailure(Call<PhoneNumInfo> call, Throwable t) {
                        mtext.setText(t.getMessage());
                    }
                });*/

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
