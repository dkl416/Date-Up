package com.example.date_up;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//http://androidhuman.com/193 ui store
public class MainActivity extends Activity {
    private ArrayList<String> arrayListToDo;
    private ArrayAdapter<String> arrayAdapterToDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // arrayListToDo = new ArrayList<String>();
       // arrayAdapterToDo = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayListToDo);
       // ListView listViewToDo = (ListView) findViewById(R.id.listViewToDo);
       // listViewToDo.setAdapter(arrayAdapterToDo);

      //  registerForContextMenu(listViewToDo);

//        listViewToDo.setOnClickListener(new OnItemClickListener() {
    //        @Override
  //          public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
      //          String itmep=((TextView)view).getText().toString();
        //        Toast.makeText(getBaseContext(),item,Toast,LENGTH_LONG).show();

         //   }
        //});
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() != R.id.listViewToDo) {
            return;
        }

        menu.setHeaderTitle("What would you like to do?");
        String[] options = {"Delete Task", "Return"};

        for (String option : options) {
            menu.add(option);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int selectedIndex = info.position;

        if (item.getTitle().equals("Delete Task")) {
            arrayListToDo.remove(selectedIndex);
            arrayAdapterToDo.notifyDataSetChanged();
        }

        return true;
    }

    public void deleteButton(View view) {
        EditText editText = (EditText) findViewById(R.id.add_to_do);
        editText.setText("");
    }

    public void addButton(View v) {
        EditText editTextToDo = (EditText) findViewById(R.id.add_to_do);
        String toDo = editTextToDo.getText().toString().trim();

        if (toDo.isEmpty()) {
            return;
        }

        arrayAdapterToDo.add(toDo);
        editTextToDo.setText("");

    }
}