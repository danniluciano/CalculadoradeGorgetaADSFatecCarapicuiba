package br.com.danielaluciano.calculadoradegorgetaadsfateccarapicuiba;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private NumberFormat percentFormat =
            NumberFormat.getPercentInstance();
    private double billAmount = 0.0;
    private double percent = 0.15;
    private TextView amountTextView;
    private TextView percentTextView;
    private TextView tipTextView, totalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        amountTextView = (TextView)
                findViewById(R.id.amountTextView);  //fazendo referencia
        percentTextView = (TextView)
                findViewById(R.id.percentTextView); //fazendo referencia
        tipTextView = (TextView)
                findViewById(R.id.tipTextView); //fazendo referencia
        totalTextView = (TextView)  //separar para ter boa legibilidade
                findViewById(R.id.totalTextView); //fazendo referencia
        tipTextView.setText(currencyFormat.format(0)); //envia 0 para o currency format e ele devolve R$0,00 (graficamente)
        totalTextView.setText(currencyFormat.format(0)); //mesmo que o anterior
        EditText amountEditText =
                (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);
        SeekBar percentSeekBar =  //buscar a seekbar
                (SeekBar) findViewById(R.id.percentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(seekBarListener); //O método que quer saber se houve alterações no seekbar
    }

    private SeekBar.OnSeekBarChangeListener seekBarListener =
        new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                percent = progress / 100.0;
                double tip = billAmount * percent;
                double total = billAmount + tip;
                tipTextView.setText(currencyFormat.format(tip));
                totalTextView.setText(currencyFormat.format(total));
                percentTextView.setText(percentFormat.format(percent));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };

    private TextWatcher amountEditTextWatcher =
        new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    billAmount = Double.parseDouble(charSequence.toString()) /100; //preenche da esquerda para direita
                    double tip = billAmount * percent;
                    double total = billAmount+ tip;
                    tipTextView.setText(currencyFormat.format(tip));
                    totalTextView.setText((currencyFormat.format(total)));
                    amountTextView.setText(currencyFormat.format(billAmount));
                }
                catch (NumberFormatException e){
                    billAmount = 0.0;
                    tipTextView.setText(currencyFormat.format(billAmount));
                    amountTextView.setText(currencyFormat.format(billAmount));
                    totalTextView.setText((currencyFormat.format(billAmount)));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
    };
}
