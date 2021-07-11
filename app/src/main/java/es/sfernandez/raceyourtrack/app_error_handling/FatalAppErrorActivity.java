package es.sfernandez.raceyourtrack.app_error_handling;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import es.sfernandez.raceyourtrack.R;

public class FatalAppErrorActivity extends AppCompatActivity {

    //---- Activity Methods ----
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_fatal_app_error);

        String error_msg = getIntent().getStringExtra("ERROR_MSG");

        ((TextView)findViewById(R.id.txt_error_msg)).setText(error_msg);*/
    }
}
