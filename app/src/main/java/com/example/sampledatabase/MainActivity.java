package com.example.sampledatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText dbText, tableText;
    TextView textView;
    SQLiteDatabase database;
    String tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //초기화 영역
        dbText = findViewById(R.id.dbText);
        tableText = findViewById(R.id.tableText2);
        textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);

        //이벤트 영역
        button.setOnClickListener(v -> {
            createDB(dbText.getText().toString());
        });
        button2.setOnClickListener(v -> {
            tableName = tableText.getText().toString();
            createTable(tableName);
            insertRecord();
        });
    }
    // 메서드 영역
    private void createDB(String name) {
        Log.i(">>>","createDB 호출됨");
        database = openOrCreateDatabase(name,MODE_PRIVATE, null); //데이터베이스를 만들기 위한 메서드 실행하기
        println("데이터베이스 생성 "+name);
    }

    private void createTable(String name) {
        if (database == null) {
            Toast.makeText(this, "데이터베이스를 먼저 만드세요!", Toast.LENGTH_SHORT).show();
            return;
        }

        database.execSQL("create table if not exists " + name +
                " (_id integer PRIMARY KEY autoincrement, " +
                " name text, " +
                " age ingeger, " +
                " mobile text )");

        println("테이블 생성 "+name);
    }

    private void insertRecord() {
        if (database == null) {
            Toast.makeText(this, "데이터베이스를 먼저 만드세요!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (tableName == null) {
            Toast.makeText(this, "테이블을 먼저 생성하세요!", Toast.LENGTH_SHORT).show();
            return;
        }

        for (int i = 0; i < 5; i++) {
            database.execSQL("insert into " + tableName +
                    "(name, age, mobile) " +
                    "values (" +
                    "'John" + i + "'," +
                    i + "," +
                    "'010-1111-111" + i +
                    "')");
        }
    }

    public void println(String msg) {
        textView.append(msg+"\n");
    }
}