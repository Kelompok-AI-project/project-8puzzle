package com.project.Project_8_Puzzle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    String TAG="myApp";

    Button btnNext,btnPrev ;

    ArrayList<ButtonPuzzle> listPuzzle;//list Button Puzzle
    Random r =new Random();//declare random
    int stateNow=9;//state sekarang yang kosong = 9 karena angka random 9 yang mewakili kosong
    int jalan=0;
    //winstate
    String WinState="123456789";
    ArrayList<String> stateJawaban=new ArrayList<>(); // tampung jawaban
    int stateJawabanNow=-1; // tampung index jawaban yang sekarang supaya bisa di next / prev
    //

    // DFS
    Stack<String> stackDFSOpen= new Stack<>();
    Stack<String> stackDFSClose= new Stack<>();
    //

    // BFS
    Queue<String> queueBFSOpen = new LinkedList<>();
    Stack<String> stackBFSClose= new Stack<>();
    ArrayList<bfs> lBfs = new ArrayList<>();

    Button restart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPrev=findViewById(R.id.btnPref);
        btnNext=findViewById(R.id.btnNext);

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stateJawabanNow>0){
                    stateJawabanNow--;
                    gantiTextButton(stateJawaban.get(stateJawabanNow));
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stateJawabanNow<stateJawaban.size()-1){
                    stateJawabanNow++;
                    gantiTextButton(stateJawaban.get(stateJawabanNow));
                }
            }
        });

        init();
        random();
    }

    public void gantiTextButton(String text){
        for (int i = 0; i < listPuzzle.size(); i++) {
            String potong =text.substring(i,i+1);
            listPuzzle.get(i).getBtn().setText(potong);
            if(potong.equals("9")){
                listPuzzle.get(i).getBtn().setText("");
            }
        }
    }

    public void init(){
        listPuzzle= new ArrayList<>();
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
    boolean win=false;
    public void dfs_onclick(View view) {
        stackDFSOpen.push(getState());
        String x="";
        while(!stackDFSOpen.empty()&&!win){
            x=stackDFSOpen.iterator().next();
            stackDFSOpen.remove(x);

//            jalan++;
//            if(jalan%5000==0){
//                Log.i(TAG, "dfs_onclick: state "+jalan);
//            }


            int pos = x.indexOf("9");
//            Log.i(TAG, "dfs_onclick: "+pos);
//            Log.i(TAG, "dfs_onclick: State 1 = "+x.substring(0,3));
//            Log.i(TAG, "dfs_onclick: State 2 = "+x.substring(3,6));
//            Log.i(TAG, "dfs_onclick: State 3 = "+x.substring(6,9));
            if(x.equals(WinState)){
                stackDFSClose.add(x);
                //berhasil
//                while(stackDFSOpen.iterator().hasNext()){
//                    x =stackDFSOpen.iterator().next();
//                    Log.i(TAG, "dfs_onclick: Open : "+x);
//                    Log.i(TAG, "dfs_onclick: State 1 = "+x.substring(0,3));
//                    Log.i(TAG, "dfs_onclick: State 2 = "+x.substring(3,6));
//                    Log.i(TAG, "dfs_onclick: State 3 = "+x.substring(6,9));
//                    stackDFSOpen.remove(stackDFSOpen.iterator().next());
//                }
//
                while(stackDFSClose.iterator().hasNext()){
                    x =stackDFSClose.iterator().next();
                    Log.i(TAG, "dfs_onclick: Close : "+x);
                    Log.i(TAG, "dfs_onclick: State 1 = "+x.substring(0,3));
                    Log.i(TAG, "dfs_onclick: State 2 = "+x.substring(3,6));
                    Log.i(TAG, "dfs_onclick: State 3 = "+x.substring(6,9));
                    stackDFSClose.remove(stackDFSClose.iterator().next());
                }
                Log.i(TAG, "dfs_onclick: "+stackDFSOpen);
                Log.i(TAG, "dfs_onclick: "+stackDFSClose);
                Log.i(TAG, "dfs_onclick: Berhasil ");
                win=true;
            }else{
                stackDFSClose.add(x);
                String temp="";
                temp = down(x, pos);
                if (!(temp.equals("-1")))
                    stackDFSOpen.add(temp);
                temp = right(x, pos);
                if (!(temp.equals("-1")))
                    stackDFSOpen.add(temp);
                temp = left(x, pos);
                if (!(temp.equals("-1")))
                    stackDFSOpen.add(temp);
                temp = up(x, pos);
                if (!(temp.equals("-1")))
                     stackDFSOpen.add(temp);
            }
        }
    }

    public void bfs_onclick(View view) {
        queueBFSOpen = new LinkedList<>();
        stackBFSClose= new Stack<>();
        lBfs = new ArrayList<>();
        queueBFSOpen.add(getState());
        lBfs.add(new bfs(getState(),""));
        String x="";
        while(!queueBFSOpen.isEmpty()&&!win){
            x=queueBFSOpen.peek();
            stackBFSClose.add(x);

            queueBFSOpen.remove(x);
            int pos = x.indexOf("9");

            jalan++;
            if(jalan%5000==0){
                Log.i(TAG, "bfs_onclick: state "+jalan);
            }

            if(x.equals(WinState)){

                stateJawabanNow=0;
                int idx=-1;
                for (int i = 0; i < lBfs.size()-1; i++) {
                    if(lBfs.get(i).now.equals(x)){
                        idx=i;
                        break;
                    }
                }

                bfs lastBfs=lBfs.get(idx);

                while(!lastBfs.parent.equals("")){
                    for (int i = 0; i < lBfs.size()-1; i++) {
                        if(lastBfs.parent.equals(lBfs.get(i).now) ){
                            stateJawaban.add(lastBfs.now);
                            lastBfs=lBfs.get(i);
                            i=0;
                        }
                    }
                }

                stateJawaban.add(lastBfs.now);
                Log.i(TAG, "bfs_onclick: "+stateJawaban);
                Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "bfs_onclick: Berhasil ");
                win=true;
            }else{
                String temp="";
                temp = down(x, pos);
                if (!(temp.equals("-1"))){
                    queueBFSOpen.add(temp);
                    lBfs.add(new bfs(temp,x));
                }
                temp = up(x, pos);
                if (!(temp.equals("-1"))){
                    queueBFSOpen.add(temp);
                    lBfs.add(new bfs(temp,x));
                }

                temp = left(x, pos);
                if (!(temp.equals("-1"))){
                    queueBFSOpen.add(temp);
                    lBfs.add(new bfs(temp,x));
                }

                temp = right(x, pos);
                if (!(temp.equals("-1"))){
                    queueBFSOpen.add(temp);
                    lBfs.add(new bfs(temp,x));
                }
            }


        }
    }

    public void cetak(String s){
        String x =s;
        Log.i(TAG, "dfs_onclick: Close : "+x);
        Log.i(TAG, "dfs_onclick: State 1 = "+x.substring(0,3));
        Log.i(TAG, "dfs_onclick: State 2 = "+x.substring(3,6));
        Log.i(TAG, "dfs_onclick: State 3 = "+x.substring(6,9));
    }

    /*
     * MOVEMENT UP
     */
    public String up(String s, int p) {
        String str = s;
        if (!(p < 3)) {
            char a = str.charAt(p - 3);
            String newS = str.substring(0, p) + a + str.substring(p + 1);
            str = newS.substring(0, (p - 3)) + '9' + newS.substring(p - 2);
        }
        // Eliminates child of X if its on OPEN or CLOSED
        if (!queueBFSOpen.contains(str)  && !stackBFSClose.contains(str))
            return str;
        else
            return "-1";
    }

    /*
     * MOVEMENT DOWN
     */
    public String down(String s, int p) {
        String str = s;
        if (!(p > 5)) {
            char a = str.charAt(p + 3);
            String newS = str.substring(0, p) + a + str.substring(p + 1);
            str = newS.substring(0, (p + 3)) + '9' + newS.substring(p + 4);
        }

        // Eliminates child of X if its on OPEN or CLOSED
        if (!queueBFSOpen.contains(str) && !stackBFSClose.contains(str))
            return str;
        else
            return "-1";
    }

    /*
     * MOVEMENT LEFT
     */
    public String left(String s, int p) {
        String str = s;
        if (p%3!=0) {
            char a = str.charAt(p - 1);
            String newS = str.substring(0, p) + a + str.substring(p + 1);
            str = newS.substring(0, (p - 1)) + '9' + newS.substring(p);
        }
        // Eliminates child of X if its on OPEN or CLOSED
        if (!queueBFSOpen.contains(str) && !stackBFSClose.contains(str))
            return str;
        else
            return "-1";
    }

    /*
     * MOVEMENT RIGHT
     */
    public String right(String s, int p) {
        String str = s;
        if (p != 2 && p != 5 && p != 8) {
            char a = str.charAt(p + 1);
            String newS = str.substring(0, p) + a + str.substring(p + 1);
            str = newS.substring(0, (p + 1)) + '9' + newS.substring(p + 2);
        }
        // Eliminates child of X if its on OPEN or CLOSED
        if (!queueBFSOpen.contains(str) && !stackBFSClose.contains(str))
            return str;
        else
            return "-1";
    }

    public String getState(){
        String stateNow="";
        for (ButtonPuzzle btn:listPuzzle) {
            stateNow=stateNow+btn.getBtn().getText().toString();
            if(btn.getBtn().getText().toString()==""){
                stateNow=stateNow+9;
            }
        }
        return stateNow;
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
        swap(listPuzzle.get(4).getBtn(),listPuzzle.get(7).getBtn());
        swap(listPuzzle.get(4).getBtn(),listPuzzle.get(8).getBtn());
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
//                Log.i("myApp", "random: "+random);
//                Log.i("myApp", "Boolean : "+tmpRandom.matches("(.*)"+random.toString()+"(.*)"));
//                Log.i("myApp", "Boolean : "+tmpRandom);
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