package com.smie.project;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.R.id.message;

public class PersonActivity extends AppCompatActivity {
    // Keting Xiang------------------------start

    /*****************************主界面控件********************************/
    private ImageView       personBackground;           // 背景墙
    private ImageView       personHeadImage;            // 头像
    private TextView        personNickname;             // 昵称
    private ImageView       personGender;               // 性别
    private TextView        personWords;                // 个性签名
    private LinearLayout    personParticipateAll;       // 我参与的
    private LinearLayout    personOrganizeAll;          // 我发起的
    private LinearLayout    personCollectAll;           // 我的收藏
    private Button          personLogOut;               // 退出登录

    /****************************编辑个签弹窗控件********************************/
    private EditText        personEditPersonWords;      // 编辑
    private TextView        personEditGiveUp;           // 放弃修改
    private TextView        personEditSave;             // 保存修改

    /****************************编辑背景弹窗控件********************************/
    private Button          personBackgroundChangeFromPhoto;
    private Button          personBackgroundChangeFromAlbum;
    private Button          personBackgroundChangeGiveUp;

    /****************************编辑头像弹窗控件********************************/
    private Button          personHeadChangeFromPhoto;
    private Button          personHeadChangeFromAlbum;
    private Button          personHeadChangeGiveUp;

    // 传递来的用户名主键
    private String personId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        bindAllViews();
        listenAllViews();
        setPersonInformation();
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    /*****************************绑定主界面控件********************************/
    public void bindAllViews() {
        personBackground     = (ImageView)findViewById(R.id.personBackground);
        personHeadImage      = (ImageView)findViewById(R.id.personHeadImage);
        personNickname       = (TextView)findViewById(R.id.personNickname);
        personGender         = (ImageView)findViewById(R.id.personGender);
        personWords          = (TextView)findViewById(R.id.personWords);
        personParticipateAll = (LinearLayout)findViewById(R.id.personParticipateAll);
        personOrganizeAll    = (LinearLayout)findViewById(R.id.personOrganizeAll);
        personCollectAll     = (LinearLayout)findViewById(R.id.personCollectAll);
        personLogOut         = (Button)findViewById(R.id.perLogOut);
    }

    /*****************************监听主界面控件********************************/

    private static final int BACKGROUND_PHOTO_WITH_CAMERA = 0;
    private static final int BACKGROUND_CHOOSE_PICTURE    = 1;
    private static final int HEAD_PHOTO_WITH_CAMERA       = 2;
    private static final int HEAD_CHOOSE_PICTURE          = 3;

    /*****************************从数据库中更新主界面********************************/
    private static final int DECODE_XML                   = 5;
    private static final int HEAD_FROM_DB                 = 0;
    private static final int BACKGROUND_FROM_DB           = 1;
    private static final int NICKNAME_FROM_DB             = 2;
    private static final int GENDER_FROM_DB               = 3;
    private static final int WORDS_FROM_DB                = 4;


    private Handler mHandler2 = new Handler() {
        public void handleMessage(Message message) {
            Bitmap bitmap = (Bitmap) message.obj;
            switch (message.what) {
                case BACKGROUND_PHOTO_WITH_CAMERA:
                    personBackground.setImageBitmap(bitmap);
                    break;
                case BACKGROUND_CHOOSE_PICTURE:
                    personBackground.setImageBitmap(bitmap);
                    break;
                case HEAD_PHOTO_WITH_CAMERA:
                    personHeadImage.setImageBitmap(bitmap);
                    break;
                case HEAD_CHOOSE_PICTURE:
                    personHeadImage.setImageBitmap(bitmap);
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int dwidth = displayMetrics.widthPixels;
        int dheight = displayMetrics.heightPixels;

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case BACKGROUND_PHOTO_WITH_CAMERA:
                    Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/image.jpg");
                    //Bitmap newBitmap = zoomBitmap(bitmap, bitmap.getWidth() / SCA)
                    Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, dwidth, dheight / 2, true);
                    Message message1 = new Message();
                    message1.what = BACKGROUND_PHOTO_WITH_CAMERA;
                    message1.obj = newBitmap;
                    mHandler2.sendMessage(message1);
                    break;
                case BACKGROUND_CHOOSE_PICTURE:
                    ContentResolver resolver = getContentResolver();
                    Uri originalUri = data.getData();
                    try {
                        Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                        if (photo != null) {
                            Bitmap newBitmap2 = Bitmap.createScaledBitmap(photo, dwidth, dheight / 2, true);
                            Message message2 = new Message();
                            message2.what = BACKGROUND_CHOOSE_PICTURE;
                            message2.obj = newBitmap2;
                            mHandler2.sendMessage(message2);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case HEAD_PHOTO_WITH_CAMERA:
                    Bitmap bitmap2 = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/image.jpg");
                    //Bitmap newBitmap = zoomBitmap(bitmap, bitmap.getWidth() / SCA)
                    Bitmap newBitmap2 = Bitmap.createScaledBitmap(bitmap2, 100, 100, true);
                    Message message3 = new Message();
                    message3.what = HEAD_PHOTO_WITH_CAMERA;
                    message3.obj = newBitmap2;
                    mHandler2.sendMessage(message3);
                    break;
                case HEAD_CHOOSE_PICTURE:
                    ContentResolver resolver2 = getContentResolver();
                    Uri originalUri2 = data.getData();
                    try {
                        Bitmap photo = MediaStore.Images.Media.getBitmap(resolver2, originalUri2);
                        if (photo != null) {
                            Bitmap newBitmap3 = Bitmap.createScaledBitmap(photo, 100, 100, true);
                            Message message4 = new Message();
                            message4.what = HEAD_CHOOSE_PICTURE;
                            message4.obj = newBitmap3;
                            mHandler2.sendMessage(message4);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
        else {
            if (requestCode == BACKGROUND_PHOTO_WITH_CAMERA) {
                Toast.makeText(PersonActivity.this, "拍照失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void start(final Intent intent, final int code) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                startActivityForResult(intent, code);
            }
        }).start();
    }

    public void listenAllViews() {
        // 背景墙
        personBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater factory = LayoutInflater.from(PersonActivity.this);
                View view = factory.inflate(R.layout.activity_person_change_background_image_dialog, null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(PersonActivity.this);
                builder.setView(view);
                final AlertDialog alertDialog = builder.show();
                Window window = alertDialog.getWindow();
                window.setGravity(Gravity.BOTTOM);

                personBackgroundChangeFromPhoto = (Button) view.findViewById(R.id.personBackgroundFromPhoto);
                personBackgroundChangeFromAlbum = (Button) view.findViewById(R.id.personBackgroundFromAlbum);
                personBackgroundChangeGiveUp    = (Button) view.findViewById(R.id.personBackgroundChooseGiveUp);

                personBackgroundChangeFromPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);    //调用相机
                        Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        start(intent, BACKGROUND_PHOTO_WITH_CAMERA);

                        alertDialog.dismiss();
                    }
                });

                personBackgroundChangeFromAlbum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        start(intent, BACKGROUND_CHOOSE_PICTURE);

                        alertDialog.dismiss();
                    }
                });

                personBackgroundChangeGiveUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
            }
        });

        // 头像
        personHeadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater factory = LayoutInflater.from(PersonActivity.this);
                View view = factory.inflate(R.layout.activity_person_change_head_image_dialog, null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(PersonActivity.this);
                builder.setView(view);
                final AlertDialog alertDialog = builder.show();
                Window window = alertDialog.getWindow();
                window.setGravity(Gravity.BOTTOM);

                personHeadChangeFromPhoto = (Button) view.findViewById(R.id.personHeadFromPhoto);
                personHeadChangeFromAlbum = (Button) view.findViewById(R.id.personHeadFromAlbum);
                personHeadChangeGiveUp    = (Button) view.findViewById(R.id.personHeadChooseGiveUp);

                personHeadChangeFromPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);    //调用相机
                        Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        start(intent, HEAD_PHOTO_WITH_CAMERA);

                        alertDialog.dismiss();
                    }
                });

                personHeadChangeFromAlbum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        start(intent, HEAD_CHOOSE_PICTURE);

                        alertDialog.dismiss();
                    }
                });

                personHeadChangeGiveUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
            }
        });

        // 个性签名
        personWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(PersonActivity.this);
                View editView = layoutInflater.inflate(R.layout.activity_person_set_person_words, null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(PersonActivity.this);
                builder.setView(editView);
                final AlertDialog alertDialog = builder.show();
                //alertDialog.getWindow().setLayout(600, 400); // 设置窗口大小

                personEditPersonWords = (EditText) editView.findViewById(R.id.personEditPersonWords);
                personEditGiveUp      = (TextView) editView.findViewById(R.id.personEditGiveUp);
                personEditSave        = (TextView) editView.findViewById(R.id.personEditSave);

                String oldWords = personWords.getText().toString();
                personEditPersonWords.setText(oldWords);

                personEditGiveUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                personEditSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newWords = personEditPersonWords.getText().toString();
                        personWords.setText(" " + newWords + " ");
                        alertDialog.dismiss();
                    }
                });
            }
        });

        // 我参与的
        personParticipateAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonActivity.this, MyParticipatedActivity.class);
                startActivity(intent);
            }
        });

        // 我发起的
        personOrganizeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonActivity.this, MyOrganizedActivity.class);
                startActivity(intent);
            }
        });

        // 我的收藏
        personCollectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonActivity.this, MyCollectedActivity.class);
                startActivity(intent);
            }
        });

        // 退出登录
        personLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到登录界面
                Intent intent = new Intent(PersonActivity.this, PersonActivity.class);
                startActivity(intent);
            }
        });
    }


    /***********************************从数据库中更新UI*************************************/
    public void setPersonInformation() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        personId = bundle.getString("id");
        String requestUrl = getString(R.string.host_ip)+"findusers/" + personId;
        getAllInformation(requestUrl);
    }

    public void getAllInformation(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL myurl = new URL(url.toString());
                    connection = (HttpURLConnection) myurl.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(8000);
                    connection.setConnectTimeout(8000);
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    XmlPullParser parser = factory.newPullParser();
                    parser.setInput(new StringReader(response.toString()));
                    int type = parser.getEventType();
                    ArrayList<String> list = new ArrayList<String>();
                    while (type != XmlPullParser.END_DOCUMENT) {
                        switch (type) {
                            case XmlPullParser.START_TAG:
                                if ("string".equals(parser.getName())) {
                                    String str = parser.nextText();
                                    list.add(str);
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                break;
                            default:
                                break;
                        }
                        type = parser.next();
                    }
                    ArrayList<Bitmap> bitmapArrayList = new ArrayList<Bitmap>();
                    Bitmap bitmap1 = getBitmap(list.get(3));
                    bitmapArrayList.add(bitmap1);
                    Bitmap bitmap2 = getBitmap(list.get(4));
                    bitmapArrayList.add(bitmap2);

                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("string", list);
                    bundle.putParcelableArrayList("bitmap", bitmapArrayList);

                    Message message = new Message();
                    message.what = DECODE_XML;
                    message.setData(bundle);
                    mHandlerDb.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private Handler mHandlerDb = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case DECODE_XML:
                    Bundle bundle = message.getData();
                    ArrayList<String> list = bundle.getStringArrayList("string");

                    // 设置昵称
                    personNickname.setText(personId);

                    // 设置性别
                    if (list.get(2).equals("male")) {
                        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.man);
                        personGender.setImageBitmap(bitmap);
                    }
                    else {
                        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.woman);
                        personGender.setImageBitmap(bitmap);
                    }

                    // 设置个性签名
                    personWords.setText(list.get(5));

                    ArrayList<Bitmap> bitmapArrayList = bundle.getParcelableArrayList("bitmap");
                    // 设置头像
                    Bitmap headBitmap = bitmapArrayList.get(0);
                    personHeadImage.setImageBitmap(headBitmap);

                    // 设置背景
                    Bitmap backBitmap = bitmapArrayList.get(1);
                    personBackground.setImageBitmap(backBitmap);
                    break;
                default:
                    break;
            }
        }
    };

    public Bitmap getBitmap(String url) {
        Bitmap bitmap = null;
        HttpURLConnection connection = null;
        try {
            URL myUrl = new URL(url);
            connection = (HttpURLConnection) myUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(8000);
            connection.setConnectTimeout(8000);
            if (connection.getResponseCode() == 200) {
                System.out.println("hao");
                InputStream inputStream = connection.getInputStream();

                bitmap = BitmapFactory.decodeStream(inputStream);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return bitmap;
    }

    // 12.12之前的版本
//    /***************************从数据库中设置背景图片********************************/
//    private Handler mHandler = new Handler() {
//        public void handleMessage(Message message) {
//            Bitmap bitmap = (Bitmap) message.obj;
////            int bWidth = bitmap.getWidth();
////            int bHeigth = bitmap.getHeight();
//
//            DisplayMetrics displayMetrics = new DisplayMetrics();
//            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//            int dwidth = displayMetrics.widthPixels;
//            int dheight = displayMetrics.heightPixels;
//
//            Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, dwidth, dheight, true);
//
//
//            personBackground.setImageBitmap(newBitmap);
//        }
//    };
//
//    public void sendBitmap(final String url) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Bitmap bitmap = getBitmap(url);
//                Message message = new Message();
//                message.obj = bitmap;
//                mHandler.sendMessage(message);
//            }
//        }).start();
//    }
//
//
//
//
//
//    public void setPersonBackground() {
//
//
//        String url = "http://p1.bpimg.com/567571/5e5889214a4712a1.jpg";
//        sendBitmap(url);
//
//    }
//
//
//    /*****************************从数据库中设置头像********************************/
//    public void setPersonHeadImage() {
//
//    }
//
//    /*****************************从数据库中设置昵称********************************/
//    public void setPersonNickName() {
//
//    }
//
//    /***************************从数据库中设置性别图片********************************/
//    public void setPersonGender() {
//
//    }
//
//    /***************************从数据库中设置个性签名********************************/
//    public void setPersonWords() {
//
//    }


    //                InputStream is = connection.getInputStream();
//                if (is == null){
//                    throw new RuntimeException("stream is null");
//                }else{
//                    try {
//                        byte[] data=readStream(is);
//                        if(data!=null){
//                            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    is.close();
//                }
    /*
     * 得到图片字节流 数组大小
     * */
//    public static byte[] readStream(InputStream inStream) throws Exception{
//        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//        byte[] buffer = new byte[1024];
//        int len = 0;
//        while( (len=inStream.read(buffer)) != -1){
//            outStream.write(buffer, 0, len);
//        }
//        outStream.close();
//        inStream.close();
//        return outStream.toByteArray();
//    }

    // Keting Xiang------------------------end
}
