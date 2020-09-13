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

    ArrayList<ButtonPuzzle> listPuzzle;//list Button Puzzle
    Random r =new Random();//declare random
    int stateNow=9;//state sekarang yang kosong = 9 karena angka random 9 yang mewakili kosong

    Button restart;
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
        restart = findViewById(R.id.btnRestart);
        addButtons(listPuzzle); //add button ke array button

        for (int i = 0; i< listPuzzle.size();i++) {
            Button btnClick = listPuzzle.get(i).getBtn();
            btnClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button btn=(Button)view;
                    int lokasi = (int)btn.getTag();
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

                    if(checkWin()){
                        for (int j = 0; j < listPuzzle.size(); j++) {
                            Button b = listPuzzle.get(j).getBtn();
                            b.setEnabled(false);
                        }
                        Toast.makeText(MainActivity.this, "You Wins!!!", Toast.LENGTH_LONG).show();
                        restart.setVisibility(View.VISIBLE); //tampilkan button restart
                    }

                }
            });
        }
    }

    public void restart_onclick(View view) {
        listPuzzle.clear();
        init();
        random();

        for (int j = 0; j < listPuzzle.size(); j++) {
            Button b = listPuzzle.get(j).getBtn();
            b.setEnabled(true);
        }

        restart.setVisibility(View.INVISIBLE);
    }


    public void cheat_onclick(View view) {
        //-1 karna tidak cetak angka 9
        for (int i = 0; i < listPuzzle.size()-1; i++) {
            Button btn = listPuzzle.get(i).getBtn();
            btn.setText((i+1)+"");
            listPuzzle.get(i).setLokasi(i);
            int lokasi = listPuzzle.get(i).getLokasi();
            btn.setTag(lokasi);
        }

        Button btn = listPuzzle.get(8).getBtn();
        btn.setText("");
        stateNow = 9;
    }

    public void swap(Button b1,Button b2){// swap antara kosong dan yang di tekan
        String tmp=b2.getText().toString();
        b2.setText(b1.getText());
        b1.setText(tmp);
        stateNow=(int)b1.getTag();// isi state kosong yang baru
    }

    public void random(){
        //Random angka yang ada di button

        String tmpRandom="";//tmpRandom untuk check apakah angka random sudah ada atau belum
        Integer random=-1;
        Boolean isEmpty=false;
        for (int i = 0; i< listPuzzle.size();i++) {
            do{
                random = r.nextInt(9)+1;
                Log.i("myApp", "random: "+random);
                Log.i("myApp", "Boolean : "+tmpRandom.matches("(.*)"+random.toString()+"(.*)"));
                Log.i("myApp", "Boolean : "+tmpRandom);
            }while(tmpRandom.matches("(.*)"+random.toString()+"(.*)"));

            tmpRandom=tmpRandom+random.toString();
            listPuzzle.get(i).getBtn().setText(random.toString());

            if(random==stateNow&&!isEmpty){// untuk yang kosong
                stateNow=i;
                isEmpty=true;
                listPuzzle.get(i).getBtn().setText("");
            }
        }
    }
    
    public void addButtons(ArrayList<ButtonPuzzle> list){
        list.add(new ButtonPuzzle((Button)findViewById(R.id.b1),0,0));
        list.add(new ButtonPuzzle((Button)findViewById(R.id.b2),1,0));
        list.add(new ButtonPuzzle((Button)findViewById(R.id.b3),2,0));
        list.add(new ButtonPuzzle((Button)findViewById(R.id.b4),3,0));
        list.add(new ButtonPuzzle((Button)findViewById(R.id.b5),4,0));
        list.add(new ButtonPuzzle((Button)findViewById(R.id.b6),5,0));
        list.add(new ButtonPuzzle((Button)findViewById(R.id.b7),6,0));
        list.add(new ButtonPuzzle((Button)findViewById(R.id.b8),7,0));
        list.add(new ButtonPuzzle((Button)findViewById(R.id.b9),8,0));
    }
    
    public boolean checkWin(){

        ArrayList<String> goalState = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            goalState.add(i+"");
        }

        // -1 karena tidak cek tombol kosong terakhir
        for (int i = 0; i < listPuzzle.size()-1; i++) {
            Button btn = listPuzzle.get(i).getBtn();
            if(!btn.getText().toString().equals(goalState.get(i))){
                return false;
            }
        }

        return true;
    }


}