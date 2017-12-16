package com.example.MenuPicker_MJU;

import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import db.DBHelper;

public class ViewListActivity extends AppCompatActivity {
    DBHelper dbHelper;
    ListView listView;
    SimpleCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);

        dbHelper = new DBHelper(getApplicationContext(), "MenuBook.db", null, 1);

        Cursor cursor = dbHelper.getCursor();

        cursorAdapter = new SimpleCursorAdapter(this, R.layout.lists_in_listview, cursor,
                new String[] {"storeName","menu"}, new int[] {R.id.tv_storeName, R.id.tv_menu}, 0);
        listView = (ListView) findViewById(R.id.resultList);
        listView.setAdapter(cursorAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_selected_menu :
                deleteSelectedMenu();
                break;
            case R.id.delete_all_menu :
                deleteAllMenu();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteSelectedMenu() {
        Toast.makeText(getApplicationContext(), "삭제할 메뉴를 선택하세요", Toast.LENGTH_SHORT).show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
                String result = dbHelper.getSelectedItem(cursorAdapter.getItemId(position));
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewListActivity.this);
                builder.setTitle(result);
                builder.setMessage("삭제하시겠습니까?");
                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbHelper.deleteSelectedItem(cursorAdapter.getItemId(position));
                        Toast.makeText(getApplicationContext(), "삭제 완료!", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }
        });
    }

    private void deleteAllMenu() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("전체 삭제");
        builder.setMessage("메뉴가 전부 삭제됩니다. 계속하시겠습니까?");
        builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dbHelper.deleteAll();
                Toast.makeText(getApplicationContext(), "전체 삭제 완료!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }
}
