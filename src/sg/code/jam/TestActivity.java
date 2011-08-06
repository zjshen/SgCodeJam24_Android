package sg.code.jam;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.googlecode.tesseract.android.TessBaseAPI;

public class TestActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            InputStream is = getAssets().open("test.jpg");
            FileOutputStream os = getApplicationContext().openFileOutput("test.jpg", MODE_PRIVATE);
            int offset;
            byte[] arrayOfByte = new byte[1024];
            while ((offset = is.read(arrayOfByte)) > 0) {
                os.write(arrayOfByte, 0, offset);
            }
            os.close();
            is.close();
            new File(getApplicationContext().getFilesDir().getPath() + "/tessdata").mkdirs();
            is = getAssets().open("tessdata/eng.traineddata");
            os = new FileOutputStream(getApplicationContext().getFilesDir().getPath() + "/tessdata/eng.traineddata");
            while ((offset = is.read(arrayOfByte)) > 0) {
                os.write(arrayOfByte, 0, offset);
            }
            os.close();
            is.close();
        } catch (IOException e) {

        }
        TessBaseAPI api = new TessBaseAPI();
        api.init(getApplicationContext().getFilesDir().getPath() +  "/", "eng");
        api.setImage(new File(getApplicationContext().getFilesDir() + "/test.jpg"));
        TextView tv = new TextView(this);
        tv.setText(api.getUTF8Text());
        setContentView(tv);
    }

}
