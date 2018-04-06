package com.example.pc_seven.contadorauth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.firebase.ui.auth.AuthUI;


import java.util.Arrays;
import java.util.List;

public class inicio extends AppCompatActivity {

    private Button btnIniciar;
    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        btnIniciar= (Button) findViewById(R.id.btn_iniciar);


        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth = FirebaseAuth.getInstance();
                FirebaseUser usuarioActual= mAuth.getCurrentUser();

                if (usuarioActual == null) {
                    List<AuthUI.IdpConfig> providers = Arrays.asList(
                            new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());

                    /* Create and launch sign-in intent */
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(providers)
                                    .build(),
                            RC_SIGN_IN);
                }else{
                    Intent mIntent = new Intent(inicio.this, sessionIniciada.class);
                    startActivity(mIntent);
                    finish();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                Intent mIntent = new Intent(inicio.this, sessionIniciada.class);
                startActivity (mIntent);
                finish();
                // ...
            } else {
                Toast.makeText(inicio.this,
                        "La session no fue inicidad satisfactoriamente ", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
