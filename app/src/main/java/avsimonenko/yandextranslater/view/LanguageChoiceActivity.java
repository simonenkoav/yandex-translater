package avsimonenko.yandextranslater.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import avsimonenko.yandextranslater.R;
import avsimonenko.yandextranslater.dao.LanguagesDao;
import avsimonenko.yandextranslater.models.LanguageModel;
import avsimonenko.yandextranslater.view.adapters.LanguagesListAdapter;

/**
 * Created by avsimonenko on 05.04.17.
 */

public class LanguageChoiceActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_choice);

        String curLangCode = null;
        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            boolean extraValue = intent.getBooleanExtra(Intent.EXTRA_TEXT, false);
            TextView langTypeTextView = (TextView) findViewById(R.id.language_type_text_view);
            if (extraValue)
                langTypeTextView.setText("Source language");
            else
                langTypeTextView.setText("Target language");

        }

        if (intent.hasExtra(TranslateFragment.ARG_CUR_LANG_CODE)) {
            curLangCode = intent.getStringExtra(TranslateFragment.ARG_CUR_LANG_CODE);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.languages_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        LanguagesListAdapter languagesListAdapter = new LanguagesListAdapter(
                LanguagesDao.getLanguagesDao().getAllLanguages(),
                curLangCode,
                new ItemSelectCallback());
        mRecyclerView.setAdapter(languagesListAdapter);

    }

    public class ItemSelectCallback {

        public final View.OnClickListener onItemSelected() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = mRecyclerView.getChildAdapterPosition(v);
                    LanguageModel languageModelSelected =
                            LanguagesDao.getLanguagesDao().getAllLanguages().get(itemPosition);
                    Log.d(ItemSelectCallback.class.getSimpleName(),languageModelSelected.getName());
                    finish();
                }
            };
        }

    }
}
