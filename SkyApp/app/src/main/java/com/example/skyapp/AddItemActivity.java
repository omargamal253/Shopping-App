package com.example.skyapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.skyapp.Adapter.FireBase;
import com.example.skyapp.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddItemActivity extends AppCompatActivity {
     Spinner spinner;
     EditText  title,description, brand ,color , price  , discount ;
     ImageView imageAdded ;
     String Category = "Deals" ;
     Uri imageUri ;
    String imageName , DownloadUrl ;
    Product product ;

    private static final int IMAGE_REQUEST =  2 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
         spinner =  findViewById(R.id.planets_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

         spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                Toast.makeText(AddItemActivity.this, spinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                Category = spinner.getSelectedItem().toString() ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

         title = findViewById(R.id.item_title);
         description = findViewById(R.id.item_description);
         brand = findViewById(R.id.item_brand);
         color = findViewById(R.id.item_color);
         price = findViewById(R.id.item_price);
         discount = findViewById(R.id.item_discount);
        imageAdded = findViewById(R.id.image_added);





    }

    public void SelectPhoto(View view) {
        OpedImage();
    }
    public void OpedImage(){
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction((Intent.ACTION_GET_CONTENT));
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_REQUEST && resultCode == RESULT_OK){
            imageUri = data.getData();
            imageAdded.setImageURI(imageUri);
          //  UploadData();

        }
    }

    public String getFileExtension(Uri uri){
        ContentResolver contentResolver =  getContentResolver();
        MimeTypeMap minMimeTypeMap = MimeTypeMap.getSingleton();
        return minMimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }
    private void UploadData() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();
        if(imageUri!= null){
             imageName = System.currentTimeMillis()+"."+getFileExtension(imageUri);
            final StorageReference fileRef = FirebaseStorage.getInstance().getReference().child("uploads").child(
                    title.getText().toString()+brand.getText().toString()+color.getText().toString());

            fileRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            DownloadUrl = uri.toString() ;
                           Log.d("DownLoadUi ", DownloadUrl);

                           // Picasso.get().load(DownloadUrl).into(imageAdded);
                            LoadData();

                            pd.dismiss();
                            finish();
                            Toast.makeText(AddItemActivity.this, "Product Uploaded Successful ",Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            });
        }

    }

    public void LoadData(){
        product = new Product();
        product.setTitle(title.getText().toString());
        product.setDescription(description.getText().toString());
        product.setBrand(brand.getText().toString());
        product.setColor(color.getText().toString());
        product.setPrice(Double.parseDouble(price.getText().toString()));
        product.setDiscount(Double.parseDouble(discount.getText().toString()));

        product.setImage_url(DownloadUrl);

        FireBase.AddProductTo_FireBase(product, Category);
        //product.getTitle()+product.getBrand()+product.getColor()
       // title.getText().toString()+brand.getText().toString()+color.getText().toString()

    }


    public void AddProduct(View view) {
        UploadData();
    }
}
