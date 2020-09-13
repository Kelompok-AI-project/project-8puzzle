package com.project.Project_8_Puzzle;

import android.widget.Button;

public class ButtonPuzzle {
    Button btn;
    int lokasi,now;

    public ButtonPuzzle(Button btn, int lokasi, int now) {
        this.btn = btn;
        this.lokasi = lokasi;
        this.now = now;
        btn.setTag(lokasi);
    }


    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    public int getLokasi() {
        return lokasi;
    }

    public void setLokasi(int lokasi) {
        this.lokasi = lokasi;
    }

    public int getNow() {
        return now;
    }

    public void setNow(int now) {
        this.now = now;
    }
}
