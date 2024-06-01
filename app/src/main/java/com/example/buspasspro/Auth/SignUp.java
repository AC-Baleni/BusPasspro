package com.example.buspasspro.Auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buspasspro.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;

    private EditText emailEditText, passwordEditText, usernameEditText;
    private TextView navLogin;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private ProgressBar progressBar;
    private FirebaseFirestore db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize GoogleSignInOptions
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        emailEditText = findViewById(R.id.EmailSignUpET);
        passwordEditText = findViewById(R.id.PasswordSignUpET);
        usernameEditText = findViewById(R.id.userNameSignUp);
        progressBar = findViewById(R.id.SignUpProgressBar);

        navLogin = findViewById(R.id.SignUpToLoginTxt);
        navLogin.setOnClickListener(v -> startActivity(new Intent(SignUp.this, Login.class)));

        Button googleSignUpButton = findViewById(R.id.SignUpGoogleBtn);
        googleSignUpButton.setOnClickListener(view -> signInWithGoogle());

        Button emailSignUpButton = findViewById(R.id.SignUpBtn);
        emailSignUpButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString();
            signUpWithEmail(username);
        });


        // Save FCM token to Firestore
        saveTokenToFirestore();
    }

    private void signInWithGoogle() {
        showProgressBar();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signUpWithEmail(String username) {
        showProgressBar();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email is required");
            hideProgressBar();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is required");
            hideProgressBar();
            return;
        }

        if (TextUtils.isEmpty(username)) {
            usernameEditText.setError("Username is required");
            hideProgressBar();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Save the username to Firestore
                            Map<String, Object> userData = new HashMap<>();
                            userData.put("username", username);

                            db.collection("users")
                                    .document(user.getUid())
                                    .set(userData)
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            Toast.makeText(SignUp.this, "User registration successful.", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(SignUp.this, Login.class));
                                            finish(); // Finish current activity to prevent going back to SignUp page
                                        } else {
                                            Toast.makeText(SignUp.this, "Failed to save username.", Toast.LENGTH_SHORT).show();
                                        }
                                        hideProgressBar();
                                    });
                        }
                    } else {
                        Toast.makeText(SignUp.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        hideProgressBar();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Handle Google Sign-In result
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken(), account.getDisplayName());
            } catch (ApiException e) {
                Toast.makeText(SignUp.this, "Google sign in failed.", Toast.LENGTH_SHORT).show();
                hideProgressBar();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken, String displayName) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Save the username to Firestore
                            Map<String, Object> userData = new HashMap<>();
                            userData.put("username", displayName);

                            db.collection("users")
                                    .document(user.getUid())
                                    .set(userData)
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            Toast.makeText(SignUp.this, "User registration successful.", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(SignUp.this, Login.class));
                                            finish(); // Finish current activity to prevent going back to SignUp page
                                        } else {
                                            Toast.makeText(SignUp.this, "Failed to save username.", Toast.LENGTH_SHORT).show();
                                        }
                                        hideProgressBar();
                                    });
                        }
                    } else {
                        Toast.makeText(SignUp.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        hideProgressBar();
                    }
                });
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }



    private void saveTokenToFirestore() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Get new FCM token
                        String token = task.getResult();
                        // Save FCM token to Firestore
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            Map<String, Object> userData = new HashMap<>();
                            userData.put("token", token);
                            db.collection("users")
                                    .document(user.getUid())
                                    .set(userData, SetOptions.merge())
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(SignUp.this, "Token saved to Firestore", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(SignUp.this, "Failed to save token to Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    } else {
                        Toast.makeText(SignUp.this, "Failed to get FCM token: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
