package com.example.apple.week10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    Button BT1, BT2, BT3, BT4,BT5, BT6;
    TextView tv1;
    String outputStr = "";
    ProgressBar progressBar;
    ImageView imageView;
    int i;
    ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BT1 = findViewById(R.id.button1);
        BT2 = findViewById(R.id.button2);
        BT3 = findViewById(R.id.button3);
        BT4 = findViewById(R.id.button4);
        BT5 = findViewById(R.id.button5);
        BT6 = findViewById(R.id.button6);

        tv1 = findViewById(R.id.textView1);
        progressBar = findViewById(R.id.progressBar1);
        imageView = findViewById(R.id.imageView1);

        BT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outputStr += "\n";
                ex1();
                //ex2();
                //ex3();
                //ex4();
                //ex5();
                //ex6();
            }
        });
        BT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ex2();
            }
        });
        BT3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ex3();
            }
        });
        BT4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ex4();
            }
        });
        BT5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ex5();
            }
        });
        BT6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ex6();
            }
        });
    }

    private void ex1() {

        for (i = 0; i < 5; i++) {
            outputStr += "A";
        }

        for (i = 0; i < 5; i++) {
            outputStr += "B";
        }
        tv1.setText(outputStr);
    }

    private void ex2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (i = 0; i < 5; i++) {
                    outputStr += "A";
                }
            }
        }).start();

        //thread
        //Runable
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (i = 0; i < 5; i++) {
                    outputStr += "B";
                }
            }
        }).start();

        tv1.setText(outputStr);
    }

    private void ex3() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (i = 0; i < 100; i++) {
                   // final int value = i; ประกาศตัวแปร final เรียกใช้ใน inner class
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(i);
                            tv1.setText(Integer.toString(i));
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
    private void ex4() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (i = 0; i < 100; i++) {
                    // final int value = i; ประกาศตัวแปร final เรียกใช้ใน inner class
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(i);

                            if(i < 10){
                                tv1.setText("Updating");
                            }
                            else if(i > 20){
                                Toast.makeText(getApplicationContext(),"20",Toast.LENGTH_SHORT).show();
                                tv1.setText("Please wait......");
                                imageView.setImageResource(R.drawable.mut);
                            }
                        }
                    });
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    private void ex5(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL newUrl = new URL("https://img00.deviantart.net/495f/i/2012/261/7/6/star_wallpaper_by_roweig-d5f77yi.jpg");
                    final Bitmap bitmap = BitmapFactory.decodeStream(newUrl.openConnection().getInputStream());
                    //RunOnUIThread
                    imageView.post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bitmap);
                        }
                    });
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void ex6(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{

                    URL newUrl = new URL("https://wallpaperscraft.com/image/mountains_sky_clouds_mountain_range_stones_99500_1920x1080.jpg");

                    HttpURLConnection con = (HttpURLConnection)newUrl.openConnection();
                    InputStream inputStream = con.getInputStream();
                    int imgSize = con.getContentLength();
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    int sum = 0;
                    while ((len = inputStream.read(buffer)) > 0){
                        byteBuffer.write(buffer,0,len);
                        sum += len;
                        final float percent = (sum * 100.0f) / imgSize;
                        progressBar.setProgress((int)percent);
                        tv1.post(new Runnable() {
                            @Override
                            public void run() {
                                tv1.setText(Float.toString(percent));

                            }
                        });

                    }
                    imageView.post(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(byteBuffer.toByteArray(),0,byteBuffer.size());
                            imageView.setImageBitmap(bitmap);
                        }
                    });

                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
