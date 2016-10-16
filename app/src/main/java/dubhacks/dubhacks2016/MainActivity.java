package dubhacks.dubhacks2016;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    System.out.println("WIN!");
                    String jsonString = MicrosoftOCRTest.runOCR("https://c1.staticflickr.com/3/2775/4074957339_abea7ce5af_b.jpg");
                    //String jsonString = MicrosoftOCRTest.runOCR(new File("receipt.jpg"));
                    System.out.println(MicrosoftOCRTest.toPrettyFormat(jsonString));
                    System.out.println(":)");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        setContentView(R.layout.activity_main);
    }
}
