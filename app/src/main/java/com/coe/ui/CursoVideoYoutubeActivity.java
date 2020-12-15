package com.coe.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.coe.R;
import com.coe.bean.Aula;
import com.coe.bean.Matricula;
import com.coe.util.Constants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import mobi.stos.httplib.HttpAsync;
import mobi.stos.httplib.inter.FutureCallback;

public class CursoVideoYoutubeActivity extends AppCompatActivity {

    public static final String AULAS = "AULAS";
    private YouTubePlayerView youTubePlayerView;
    private CheckBox checkBox;
    private Matricula matricula;
    private Aula aula, aulaSelecionada;
    private String resultado;
    private Toolbar mToolbar;
    private TextView mTitleToolbar;
    private Button mBtnVoltar;
    private List<Aula> aulas;
    private List<Matricula> matriculas;
//    private ListView listView;
    private SharedPreferences.Editor sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curso_video_youtube);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        checkBox = findViewById(R.id.checkbox);
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        mToolbar = findViewById(R.id.toolbar_youtube);
        mTitleToolbar = findViewById(R.id.title_toolbar_youtube);
        mBtnVoltar = findViewById(R.id.btn_voltar_youtube);
//        listView = findViewById(R.id.list_view_youtube);
//        mNomeTitle = findViewById(R.id.nome_title);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);
        sharedPreferences = getSharedPreferences(Constants.PREF, Context.MODE_PRIVATE).edit();

        matricula = (Matricula) getIntent().getSerializableExtra(Matricula.class.getSimpleName());

        aula = (Aula) getIntent().getSerializableExtra(Aula.class.getSimpleName());


        if (aula.getVisualizado()) {
            mBtnVoltar.setBackground(getResources().getDrawable(R.drawable.button_green));
            checkBox.setTextColor(getResources().getColorStateList(R.color.colorGreen));
            checkBox.setButtonTintList(getResources().getColorStateList(R.color.colorGreen));
            checkBox.setChecked(true);
        }

        mBtnVoltar.setOnClickListener(v -> {
//            if(aulas != null){
//                finish();
//                startActivity(new Intent(this, ListaAulaCursoActivity.class).putExtra(AULAS,(Serializable) aulas));
//            }else{
//                finish();
//                startActivity(new Intent(this, ListaAulaCursoActivity.class));
//            }
            onBackPressed();

        });


    }

    @Override
    protected void onResume() {
        super.onResume();

//        mNomeTitle.setText(matricula.getDescricao());

        getLifecycle().addObserver(youTubePlayerView);

        mTitleToolbar.setText(aula.getNome());
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {


                youTubePlayer.loadVideo(aula.getUrl(), 0);
            }
        });

        youTubePlayerView.getYouTubePlayerWhenReady(youTubePlayer -> {
            // do stuff with it
            youTubePlayer.addListener(new YouTubePlayerListener() {
                @Override
                public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                    Log.e("onReady", "onReady: " + youTubePlayer);

                }

                @SuppressLint("UseCompatLoadingForColorStateLists")
                @Override
                public void onStateChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlayerState playerState) {
                    Log.e("TAG", "onStateChange: " + playerState);


                    if (playerState == PlayerConstants.PlayerState.ENDED) {
                        putAula();

                    }


                }

                @Override
                public void onPlaybackQualityChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlaybackQuality playbackQuality) {

                }

                @Override
                public void onPlaybackRateChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlaybackRate playbackRate) {

                }

                @Override
                public void onError(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlayerError playerError) {

                }

                @Override
                public void onCurrentSecond(@NotNull YouTubePlayer youTubePlayer, float v) {

                }

                @Override
                public void onVideoDuration(@NotNull YouTubePlayer youTubePlayer, float v) {
                    Log.e("TAG", "onVideoDuration: " + youTubePlayer);
                }

                @Override
                public void onVideoLoadedFraction(@NotNull YouTubePlayer youTubePlayer, float v) {

                }

                @Override
                public void onVideoId(@NotNull YouTubePlayer youTubePlayer, @NotNull String s) {

                }

                @Override
                public void onApiChange(@NotNull YouTubePlayer youTubePlayer) {

                }
            });
        });

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        youTubePlayerView.release();
        Log.e("TAG", "onDestroy: ");
    }

    private void putAula() {
        try {
            aulas = matricula.getAulas();

            for (Aula aula1 : aulas
            ) {
                if (aula.getId().equals(aula1.getId())) {
                    aula1.setVisualizado(true);
                }

            }
            List<JSONObject> jsonObjects = new ArrayList<>();
            JSONObject jsonObjectinput;

            for (Aula aulaJson : aulas
            ) {
                jsonObjectinput = new JSONObject();
                jsonObjectinput.put("_id", aulaJson.getId());
                jsonObjectinput.put("video", aulaJson.getVideoId());
                jsonObjectinput.put("visualizado", aulaJson.getVisualizado());

                jsonObjects.add(jsonObjectinput);
            }

            JSONArray jsonArray = new JSONArray(jsonObjects);


            HttpAsync httpAsync = new HttpAsync(new URL(getString(R.string.url_base) + "matriculas/" + matricula.getId()));
            httpAsync.addParam("aprovado", "false");
            httpAsync.addParam("aulas", jsonArray);
            httpAsync.addParam("dataDeMatricula", matricula.getDataMatricula());
            httpAsync.setDebug(true);
            httpAsync.put(new FutureCallback() {
                @Override
                public void onBeforeExecute() {

                }

                @Override
                public void onAfterExecute() {

                }

                @Override
                public void onSuccess(int responseCode, Object object) {

                    if (responseCode == 200) {
                        try {
                        JSONObject jsonObject = (JSONObject) object;

                            Boolean aprovado = jsonObject.getBoolean("aprovado");

                            if(aprovado){

                                new AlertDialog.Builder(CursoVideoYoutubeActivity.this)
                                        .setIcon(R.drawable.icon_trofeu)
                                        .setTitle("Parabéns pela conquista")
                                        .setMessage("Você concluiu o curso de " + aula.getNome())
                                        .setPositiveButton("Ok",null)
                                        .create()
                                        .show();
                                mBtnVoltar.setBackground(getResources().getDrawable(R.drawable.button_green));
                                checkBox.setTextColor(getResources().getColorStateList(R.color.colorGreen));
                                checkBox.setButtonTintList(getResources().getColorStateList(R.color.colorGreen));
                                checkBox.setChecked(true);

                            }else{
                                Toast.makeText(CursoVideoYoutubeActivity.this, "Aula finalizada", Toast.LENGTH_LONG).show();

                                mBtnVoltar.setBackground(getResources().getDrawable(R.drawable.button_green));
                                checkBox.setTextColor(getResources().getColorStateList(R.color.colorGreen));
                                checkBox.setButtonTintList(getResources().getColorStateList(R.color.colorGreen));
                                checkBox.setChecked(true);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }





                    }

                }


                @Override
                public void onFailure(Exception exception) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}