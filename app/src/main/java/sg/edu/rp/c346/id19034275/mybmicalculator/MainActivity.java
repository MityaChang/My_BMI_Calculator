package sg.edu.rp.c346.id19034275.mybmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight, etHeight;
    Button btnCalculate, btnReset;
    RadioGroup rgWeight, rgHeight;
    TextView tvDate, tvBMI, tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);
        btnCalculate = findViewById(R.id.calButton);
        btnReset = findViewById(R.id.resetButton);
        tvDate = findViewById(R.id.tvDate);
        tvBMI = findViewById(R.id.tvBMI);
        tvDescription = findViewById(R.id.tvDescription);
        rgHeight = findViewById(R.id.radioGroupHeight);
        rgWeight = findViewById(R.id.radioGroupWeight);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double Weight = Double.parseDouble(etWeight.getText().toString().trim());
                double Height = Double.parseDouble(etHeight.getText().toString().trim());
                DecimalFormat oneDForm = new DecimalFormat("#.##");

                if (rgHeight.getCheckedRadioButtonId() == R.id.radioCM) {
                    Height = Height / 100;
                }
                if (rgWeight.getCheckedRadioButtonId() == R.id.radioG) {
                    Height = Weight / 100;
                }

                double BMI = Weight / (Height * Height);
                tvBMI.setText("Last Calculated BMI: " + Double.valueOf(oneDForm.format(BMI)));

                Calendar now = Calendar.getInstance();
                String date = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH) + 1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);
                tvDate.setText("Last Calculated Date: " + date);

                if (BMI < 18.5) {
                    tvDescription.setText("You are underweight");
                } else if (BMI <= 24.9) {
                    tvDescription.setText("Your BMI is normal");
                } else if (BMI <= 29.9) {
                    tvDescription.setText("You are overweight");
                } else {
                    tvDescription.setText("You are obese");
                }

                etHeight.setText("");
                etWeight.setText("");


            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etWeight.setText("");
                etHeight.setText("");
                tvDate.setText("Last Calculated Date:");
                tvBMI.setText("Last Calculated BMI:");
                tvDescription.setText("");
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();

        save();
    }


    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String Date = pref.getString("date", "Last Calculated Date:");
        String BMI = pref.getString("bmi", "Last Calculated BMI:");
        String strDescription = pref.getString("description", "");

        tvDate.setText(Date);
        tvBMI.setText(BMI);
        tvDescription.setText(strDescription);

    }

    protected void save() {
        String BMI = tvBMI.getText().toString().trim();
        String Date = tvDate.getText().toString().trim();
        String description = tvDescription.getText().toString().trim();

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = pref.edit();

        prefEdit.putString("DATE", Date);
        prefEdit.putString("BMI", BMI);
        prefEdit.putString("DESCRIPTION", description);
        prefEdit.commit();
    }

}
