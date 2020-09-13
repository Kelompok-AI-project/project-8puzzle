package com.project.Project_8_Puzzle;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    LinearLayout BaseLayout;
    ArrayList<ButtonPuzzle> listPuzzle;
    Random r =new Random();
    int stateNow=8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        random();
    }

    public void init(){
        listPuzzle= new ArrayList<>();
//        BaseLayout = findViewById(R.id.BaseLayout);
//        int location=0;
//        for (int i = 0; i< BaseLayout.getChildCount();i++){
//            View v = BaseLayout.getChildAt(i);
//            if(v instanceof Button){
//                Button vBtn = (Button) v;
//                ButtonPuzzle btnPuzzle=new ButtonPuzzle(vBtn,location,0);
//                listPuzzle.add(btnPuzzle);
//                vBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Button btn = (Button)view;
//                        Toast.makeText(MainActivity.this, btn.getText(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        }
        listPuzzle.add(new ButtonPuzzle((Button)findViewById(R.id.b1),0,0));
        listPuzzle.add(new ButtonPuzzle((Button)findViewById(R.id.b2),1,0));
        listPuzzle.add(new ButtonPuzzle((Button)findViewById(R.id.b3),2,0));
        listPuzzle.add(new ButtonPuzzle((Button)findViewById(R.id.b4),3,0));
        listPuzzle.add(new ButtonPuzzle((Button)findViewById(R.id.b5),4,0));
        listPuzzle.add(new ButtonPuzzle((Button)findViewById(R.id.b6),5,0));
        listPuzzle.add(new ButtonPuzzle((Button)findViewById(R.id.b7),6,0));
        listPuzzle.add(new ButtonPuzzle((Button)findViewById(R.id.b8),7,0));
        listPuzzle.add(new ButtonPuzzle((Button)findViewById(R.id.b9),8,0));

        for (int i = 0; i< listPuzzle.size();i++) {
            Button btnClick = listPuzzle.get(i).getBtn();
            btnClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button btn=(Button)view;
                    int lokasi = (int)btn.getTag();
//                    Log.i("myApp", "onClick: "+stateNow+ " "+lokasi);
                    if(stateNow-3==lokasi){//swap atas
                        swap(btn,listPuzzle.get(stateNow).getBtn());
                    }
                    else if(stateNow+3==lokasi){//swap bawah
                        swap(btn,listPuzzle.get(stateNow).getBtn());
                    }
                    else if(stateNow+1==lokasi&&(stateNow+1)%3!=0){//swap kanan
                        swap(btn,listPuzzle.get(stateNow).getBtn());
                    }
                    else if(stateNow-1==lokasi&&(stateNow)%3!=0){//swap kiri
                        swap(btn,listPuzzle.get(stateNow).getBtn());
                    }
                }
            });
        }
    }
    public void swap(Button b1,Button b2){
        String tmp=b2.getText().toString();
        b2.setText(b1.getText());
        b1.setText(tmp);
        stateNow=(int)b1.getTag();
    }
    public void random(){
        String tmpRandom="";
        Integer random=-1;
        for (int i = 0; i< listPuzzle.size()-1;i++) {
            do{
                random = r.nextInt(9);
                Log.i("myApp", "random: "+random);
                Log.i("myApp", "Boolean : "+tmpRandom.matches("(.*)"+random.toString()+"(.*)"));
                Log.i("myApp", "Boolean : "+tmpRandom);
            }while(tmpRandom.matches("(.*)"+random.toString()+"(.*)"));
            tmpRandom=tmpRandom+random.toString();
            listPuzzle.get(i).getBtn().setText(random.toString());
            if(random==9){
                listPuzzle.get(i).getBtn().setText("");
            }
        }
    }
}