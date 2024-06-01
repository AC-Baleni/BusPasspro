package com.example.buspasspro.HomePage.Checkout;

import static android.app.PendingIntent.getActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.buspasspro.Auth.Login;
import com.example.buspasspro.Auth.SignUp;
import com.example.buspasspro.HomePage.Home;
import com.example.buspasspro.HomePage.WelcomePage;
import com.example.buspasspro.R;
import com.example.buspasspro.databinding.ActivityCheckoutBinding;
import com.example.buspasspro.util.PaymentsUtil;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.button.ButtonOptions;
import com.google.android.gms.wallet.button.PayButton;
import com.google.android.gms.wallet.contract.TaskResultContracts.GetPaymentDataResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {

    private CheckoutViewModel model;

    private PayButton googlePayButton;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private TextView ticketPrice,destination,dateSelected,companyName,depatureTime,selectedSeat;
    private ImageView logo;
    private ImageButton homeButton;
    private ProgressBar progressBar;


    private final ActivityResultLauncher<Task<PaymentData>> paymentDataLauncher =
            registerForActivityResult(new GetPaymentDataResult(), result -> {
                int statusCode = result.getStatus().getStatusCode();
                switch (statusCode) {
                    case CommonStatusCodes.SUCCESS:
                        handlePaymentSuccess(result.getResult());
                        break;
                    //case CommonStatusCodes.CANCELED: The user canceled
                    case AutoResolveHelper.RESULT_ERROR:
                        handleError(statusCode, result.getStatus().getStatusMessage());
                        break;
                    case CommonStatusCodes.INTERNAL_ERROR:
                        handleError(statusCode, "Unexpected non API" +
                                " exception when trying to deliver the task result to an activity!");
                        break;
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout); // assuming your layout file is named activity_checkout.xml




        // Check Google Pay availability
        model = new ViewModelProvider(this).get(CheckoutViewModel.class);
        model.canUseGooglePay.observe(this, this::setGooglePayAvailable);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        initializeUi();

    }

    private void initializeUi() {

        // Use view binding to access the UI elements
        ActivityCheckoutBinding layoutBinding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());


        // The Google Pay button is a layout file – take the root view
        googlePayButton = layoutBinding.googlePayButton;
        dateSelected = layoutBinding.dateCheckOut;
        companyName = layoutBinding.companyNameCheckOut;
        ticketPrice = layoutBinding.tripPriceCheckOut;
        destination = layoutBinding.destinationCheckout;
        depatureTime = layoutBinding.depatureTimeCheckout;
        selectedSeat = layoutBinding.seatNumberTxtCheck;
        homeButton = layoutBinding.homeBtnCheckout;
        progressBar = layoutBinding.progressBarCheckOut;

        logo = layoutBinding.checkOutlogo;

        try {
            googlePayButton.initialize(
                    ButtonOptions.newBuilder()
                            .setAllowedPaymentMethods(PaymentsUtil.getAllowedPaymentMethods().toString()).build()
            );
            googlePayButton.setOnClickListener(this::requestPayment);
        } catch (JSONException e) {
            // Keep Google Pay button hidden (consider logging this to your app analytics service)
        }
    }

    @SuppressLint("SetTextI18n")
    private void setGooglePayAvailable(boolean available) {
        if (available) {
            homeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(CheckoutActivity.this, Home.class));
                }
            });
            googlePayButton.setVisibility(View.VISIBLE);
            Intent intent = getIntent();
            if (intent != null) {
                String selectedDate = intent.getStringExtra("selectedDate");
                String ticketId = intent.getStringExtra("ticketId");
                String selectedSeatStr = intent.getStringExtra("selectedSeat");

                selectedSeat.setText("Seat: "+selectedSeatStr);
                dateSelected.setText("Ticket Date: "+selectedDate);
                // Now you have busId and ticketId, you can use them as needed
                  populateTicketInfoBus(ticketId);
            }
        } else {
            Toast.makeText(this, R.string.google_pay_status_unavailable, Toast.LENGTH_LONG).show();
        }
    }

    public void requestPayment(View view) {
        // The price provided to the API should include taxes and shipping.
        final Task<PaymentData> task = model.getLoadPaymentDataTask(1000L);
        task.addOnCompleteListener(paymentDataLauncher::launch);
    }

    private void handlePaymentSuccess(PaymentData paymentData) {
        final String paymentInfo = paymentData.toJson();

        try {
            JSONObject paymentMethodData = new JSONObject(paymentInfo).getJSONObject("paymentMethodData");
            // If the gateway is set to "example", no payment information is returned - instead, the
            // token will only consist of "examplePaymentMethodToken".

            final JSONObject info = paymentMethodData.getJSONObject("info");
            final String billingName = info.getJSONObject("billingAddress").getString("name");
            Toast.makeText(
                    this, getString(R.string.payments_show_name, billingName),
                    Toast.LENGTH_LONG).show();

            // Logging token string.
            Log.d("Google Pay token", paymentMethodData
                    .getJSONObject("tokenizationData")
                    .getString("token"));


            Intent intent = getIntent();
            if (intent != null)
            {
                String busId = intent.getStringExtra("busId");
                String selectedSeat = intent.getStringExtra("selectedSeat");
                String selectedDateStr = intent.getStringExtra("selectedDate");
                String ticketId = intent.getStringExtra("ticketId");
                 // Now you have busId and ticketId, you can use them as needed
                // Retrieve busId and ticketId from intent extras
                DocumentReference ticketRef = db.collection("BusTickets").document(ticketId);

                // Retrieve ticket information
                ticketRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Document found, extract required fields
                                String depatureTimeStr = document.getString("departureTime");

                                updateDatabase(selectedSeat,busId,selectedDateStr,depatureTimeStr);

                            } else {
                                Log.e("TicketManager", "No such document with ID: " + ticketId);
                            }
                        } else {
                            Log.e("TicketManager", "Error getting document with ID: " + ticketId, task.getException());
                        }
                    }
                });

              createBookedTicket(ticketId,selectedSeat,selectedDateStr,busId);

                Intent successIntent = new Intent(this, CheckoutSuccessActivity.class);
                successIntent.putExtra("busId", busId);
                successIntent.putExtra("ticketId", ticketId);
                startActivity(successIntent);
            }

        } catch (JSONException e) {
            Log.e("handlePaymentSuccess", "Error: " + e);
        }
    }

    public void createBookedTicket(final String ticketId,String selectedSeat,String selectedDate,String busId) {
        // Get the currently logged in user
        FirebaseUser user = mAuth.getCurrentUser();


        if (user != null) {
            // Get the user ID
            final String userId = user.getUid();
            // Reference to the document in BusTickets collection
            DocumentReference ticketRef = db.collection("BusTickets").document(ticketId);

            // Retrieve ticket information
            ticketRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Document found, extract required fields
                            String busLicensePlate = document.getString("busLicensePlate");
                            String companyName = document.getString("companyName");
                            String departureTime = document.getString("departureTime");
                            String startingLocation = document.getString("startingLocation");
                            String ticketPrice = document.getString("ticketPrice");
                            String destination = document.getString("destination");
                            String logoUrl = document.getString("logoUrl");

                            // Generate QR Code data
                            String qrCodeData = "Ticket Info: " +
                                    "\nBus License Plate: " + busLicensePlate +
                                    "\nDeparture Time: " + departureTime +
                                    "\nStarting Location: " + startingLocation +
                                    "\nDestination: " + destination +
                                    "\nTicket Price: " + ticketPrice +
                                    "\nCompany Name: " + companyName;

                            // Generate QR code bitmap
                            Bitmap qrCodeBitmap = generateQRCode(qrCodeData);
                            // Upload QR code image to Firebase Storage
                            uploadQRCodeToStorage(qrCodeBitmap, busLicensePlate, departureTime, String.valueOf(ticketPrice), companyName,selectedSeat, new OnSuccessListener<String>() {
                                @Override
                                public void onSuccess(String qrCodeUrl) {
                                    // Create a Map to represent booked ticket information
                                    Map<String, Object> bookedTicket = new HashMap<>();
                                    bookedTicket.put("busLicensePlate", busLicensePlate);
                                    bookedTicket.put("companyName", companyName);
                                    bookedTicket.put("departureTime", departureTime);
                                    bookedTicket.put("startingLocation", startingLocation);
                                    bookedTicket.put("ticketStatus", "Not Used");
                                    bookedTicket.put("ticketPrice", ticketPrice);
                                    bookedTicket.put("destination", destination);
                                    bookedTicket.put("qrCodeData", qrCodeData);
                                    bookedTicket.put("qrCodeUrl", qrCodeUrl); // Add qrCodeUrl to the map
                                    bookedTicket.put("selectedSeat",selectedSeat);
                                    bookedTicket.put("selectedDate",selectedDate);
                                    bookedTicket.put("userId",userId);
                                    bookedTicket.put("busId",busId);
                                    bookedTicket.put("logoUrl",logoUrl);
                                    bookedTicket.put("ticketId", task.getResult().getId());

                                    // Add booked ticket information to BookedTickets collection
                                    db.collection("BookedTickets")
                                            .document(userId)
                                            .collection("Booked_Tickets")
                                            .add(bookedTicket)
                                            .addOnSuccessListener(documentReference -> {
                                        // Hide progress bar
                                             progressBar.setVisibility(View.GONE);
                                             Log.d("BookedTickets", "Booked ticket created successfully for user: " + userId);
                                             })
                                            .addOnFailureListener(e -> {
                                                // Hide progress bar
                                                progressBar.setVisibility(View.GONE);
                                                Log.e("BookedTickets", "Error creating booked ticket for user: " + userId, task.getException());
                                            });
                                }
                            });
                        } else {
                            Log.e("TicketManager", "No such document with ID: " + ticketId);
                        }
                    } else {
                        Log.e("TicketManager", "Error getting document with ID: " + ticketId, task.getException());
                    }
                }
            });
        } else {
            Log.e("TicketManager", "User not logged in");
        }

    }

    private void uploadQRCodeToStorage(Bitmap qrCodeBitmap, String busLicensePlate, String departureTime,
                                       String ticketPrice, String companyName,String selectedSeat, OnSuccessListener<String> successListener) {
        // Create a reference to the location where the QR code image will be stored in Firebase Storage
        StorageReference qrCodeRef = FirebaseStorage.getInstance().getReference().child("qr_codes")
                .child(companyName+"_"+selectedSeat+"_"+busLicensePlate + "_" + departureTime + ".png");

        // Convert Bitmap to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        qrCodeBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        // Upload byte array to Firebase Storage
        UploadTask uploadTask = qrCodeRef.putBytes(data);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            // Upon successful upload, get the download URL of the QR code image
            qrCodeRef.getDownloadUrl().addOnSuccessListener(uri -> {
                // URL of the uploaded QR code image
                String qrCodeUrl = uri.toString();
                // Once URL is obtained, invoke success listener with the URL
                successListener.onSuccess(qrCodeUrl);
            });
        }).addOnFailureListener(e -> {
            // Handle upload failure
        });
    }

    @SuppressLint({"ResourceType", "SetTextI18n"})
    private void populateTicketInfoBus(String ticketId) {
        progressBar.setVisibility(View.VISIBLE);
        // Get the ticket details from Firestore based on ticketId
        db.collection("BusTickets")
                .document(ticketId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Ticket document exists, retrieve ticket information
                        String companyNameStr = documentSnapshot.getString("companyName");
                        String destinationStr = documentSnapshot.getString("destination");
                        String depatureTimeStr = documentSnapshot.getString("departureTime");
                        String ticketPriceStr = documentSnapshot.getString("ticketPrice");
                        String logoUrlStr = documentSnapshot.getString("logoUrl");
                        String startingLoctionStr = documentSnapshot.getString("startingLocation");

                        // Set the retrieved strings to your TextViews
                        companyName.setText(companyNameStr);
                        destination.setText("From "+startingLoctionStr+" to "+destinationStr);
                        ticketPrice.setText(ticketPriceStr);
                        depatureTime.setText("Departure Time: "+ depatureTimeStr);

                        Glide.with(this)
                                .load(logoUrlStr)
                                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                                .into(logo);
                        progressBar.setVisibility(View.GONE);
                    } else {
                        // Ticket document doesn't exist
                        Toast.makeText(this, "Ticket details not found", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                })
                .addOnFailureListener(e -> {
                    // Error fetching ticket details
                    progressBar.setVisibility(View.GONE);
                    Log.e("populateTicketInfo", "Error fetching ticket details: " + e.getMessage());
                    Toast.makeText(this, "Failed to fetch ticket details", Toast.LENGTH_SHORT).show();
                });
    }

    private Bitmap generateQRCode(String data) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void handleError(int statusCode, @Nullable String message) {
        Log.e("loadPaymentData failed",
                String.format(Locale.getDefault(), "Error code: %d, Message: %s", statusCode, message));
    }
    private void updateDatabase(String selectedSeat, String busId, String selectedDate,String depatureTime) {
        // Create a reference to the document in Firestore using busId and selectedDate
        DocumentReference docRef = db.collection("Buses").document(busId)
                .collection(selectedDate).document("selected_seat_"+depatureTime);

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Document exists, update the existing field or add a new field
                    docRef.update(selectedSeat, selectedSeat)
                            .addOnSuccessListener(aVoid -> {
                                // Database update successful
                            })
                            .addOnFailureListener(e -> {
                                // Database update failed
                            });
                } else {
                    // Document doesn't exist, create a new document with the field
                    Map<String, Object> data = new HashMap<>();
                    data.put(selectedSeat, selectedSeat);
                    data.put("seatId","selected_seat_"+depatureTime);

                    docRef.set(data)
                            .addOnSuccessListener(aVoid -> {
                                // Document creation successful
                            })
                            .addOnFailureListener(e -> {
                                // Document creation failed
                            });
                }
            } else {
                // Error getting document
            }
        });
    }



}