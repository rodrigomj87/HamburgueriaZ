package com.example.hamburgueriaz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.net.Uri;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private CheckBox cbCheese, cbBacon, cbOnion;
    private TextView tvQuantity, tvTotal, tvOrderSummary;

    private int quantity = 1;

    private int basePriceCents;
    private int cheesePriceCents;
    private int baconPriceCents;
    private int onionPriceCents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    final TextInputEditText etName = findViewById(R.id.etName);
    cbCheese = findViewById(R.id.cbCheese);
    cbBacon = findViewById(R.id.cbBacon);
    cbOnion = findViewById(R.id.cbOnion);
    final MaterialButton btnPlus = findViewById(R.id.btnPlus);
    final MaterialButton btnMinus = findViewById(R.id.btnMinus);
    final MaterialButton btnSubmit = findViewById(R.id.btnSubmit);
    tvQuantity = findViewById(R.id.tvQuantity);
    tvTotal = findViewById(R.id.tvTotal);
    tvOrderSummary = findViewById(R.id.tvOrderSummary);

    basePriceCents = getResources().getInteger(R.integer.base_price_cents);
    cheesePriceCents = getResources().getInteger(R.integer.cheese_price_cents);
    baconPriceCents = getResources().getInteger(R.integer.bacon_price_cents);
    onionPriceCents = getResources().getInteger(R.integer.onion_price_cents);

    NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    String cheesePriceStr = nf.format(cheesePriceCents / 100.0);
    String baconPriceStr = nf.format(baconPriceCents / 100.0);
    String onionPriceStr = nf.format(onionPriceCents / 100.0);

    cbCheese.setText(getString(R.string.addon_with_price_fmt, getString(R.string.cheese_name), cheesePriceStr));
    cbBacon.setText(getString(R.string.addon_with_price_fmt, getString(R.string.bacon_name), baconPriceStr));
    cbOnion.setText(getString(R.string.addon_with_price_fmt, getString(R.string.onion_name), onionPriceStr));

        btnPlus.setOnClickListener(v -> {
            quantity++;
            updateQuantityDisplay();
            updateTotal();
        });

        btnMinus.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                updateQuantityDisplay();
                updateTotal();
            }
        });

        CompoundButton.OnCheckedChangeListener extrasListener = (buttonView, isChecked) -> updateTotal();

        cbCheese.setOnCheckedChangeListener(extrasListener);
        cbBacon.setOnCheckedChangeListener(extrasListener);
        cbOnion.setOnCheckedChangeListener(extrasListener);

        btnSubmit.setOnClickListener(v -> {

            String name = etName.getText() != null ? etName.getText().toString().trim() : "";
            boolean hasCheese = cbCheese.isChecked();
            boolean hasBacon = cbBacon.isChecked();
            boolean hasOnion = cbOnion.isChecked();

            int totalCents = calculateTotalCents(hasCheese, hasBacon, hasOnion, quantity);
            NumberFormat nfInner = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            String totalStr = nfInner.format(totalCents / 100.0);

            String resumo = name + "\n"
                    + "Tem Bacon? " + (hasBacon ? "Sim" : "Não") + "\n"
                    + "Tem Queijo? " + (hasCheese ? "Sim" : "Não") + "\n"
                    + "Tem Onion Rings? " + (hasOnion ? "Sim" : "Não") + "\n"
                    + "Quantidade: " + quantity + "\n"
                    + "Preço final: " + totalStr;

            tvOrderSummary.setText(resumo);
            String toastNamePart = name.isEmpty() ? "" : " para " + name;
            Toast.makeText(this, getString(R.string.toast_sent_fmt, toastNamePart), Toast.LENGTH_SHORT).show();

            // Open email client with the order summary
            enviarPedido(name, resumo);
        });

        updateQuantityDisplay();
        updateTotal();
    }

    private void updateQuantityDisplay() {
        tvQuantity.setText(String.valueOf(quantity));
    }

    private int calculateTotalCents(boolean cheese, boolean bacon, boolean onion, int qty) {
        int extras = 0;
        if (cheese) extras += cheesePriceCents;
        if (bacon) extras += baconPriceCents;
        if (onion) extras += onionPriceCents;

        int unit = basePriceCents + extras;
        return unit * Math.max(1, qty);
    }

    private void enviarPedido(String name, String resumo) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        String subject = "Pedido de " + (name.isEmpty() ? "" : name);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, resumo);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "Nenhum aplicativo de e-mail encontrado.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTotal() {
        int extrasCents = 0;
        if (cbCheese.isChecked()) extrasCents += cheesePriceCents;
        if (cbBacon.isChecked()) extrasCents += baconPriceCents;
        if (cbOnion.isChecked()) extrasCents += onionPriceCents;

        int unitCents = basePriceCents + extrasCents;
        int totalCents = unitCents * quantity;

        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        String formatted = nf.format(totalCents / 100.0);

        tvTotal.setText(getString(R.string.total_with_value_fmt, getString(R.string.total_label_prefix), formatted));
    }
}