package com.catalinjujiu.testrubikrenderer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.catalinjurjiu.animcubeandroid.AnimCube;
import com.catalinjurjiu.animcubeandroid.CubeConstants;

public class MainActivity extends Activity implements AnimCube.OnCubeModelUpdatedListener {

    public static final String ANIM_CUBE_SAVE_STATE_BUNDLE_ID = "animCube";
    private static final String TAG = "AnimCubeActivity";
    private AnimCube animCube;
    private Bundle state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);
        animCube = (AnimCube) findViewById(R.id.animcube);
        animCube.setMoveSequence("R2' U M U' R2' U M' U'");
        animCube.setOnCubeModelUpdatedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.start_moves:
                animCube.startAnimation(CubeConstants.AnimationMode.AUTO_PLAY_FORWARD);
                break;
            case R.id.stop_moves:
                animCube.stopAnimation();
                break;
            case R.id.one_move_forward:
                animCube.startAnimation(CubeConstants.AnimationMode.STEP_FORWARD);
                break;
            case R.id.one_move_backward:
                animCube.startAnimation(CubeConstants.AnimationMode.STEP_BACKWARD);
                break;
            case R.id.reset_to_initial:
                animCube.resetToInitialState();
                break;
            case R.id.freeze:
                //TODO
                break;
            case R.id.save_state:
                state = animCube.saveState();
                //TODO
                break;
            case R.id.restore_state:
                animCube.restoreState(state);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState ");
        outState.putBundle(ANIM_CUBE_SAVE_STATE_BUNDLE_ID, animCube.saveState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState");
        animCube.restoreState(savedInstanceState.getBundle(ANIM_CUBE_SAVE_STATE_BUNDLE_ID));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        animCube.cleanUpResources();
        Log.d(TAG, "onDestroy: finish");
    }

    @Override
    public void onCubeModelUpdate(int[][] newCubeModel) {
        Log.d(TAG, "Cube model updated. New model:");
        int[][] cube = animCube.getCubeModel();
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < cube.length; i++) {
            stringBuilder.append("\n");
            stringBuilder.append(i);
            stringBuilder.append(":\n");
            for (int j = 0; j < cube[i].length; j++) {
                stringBuilder.append(" ");
                stringBuilder.append(cube[i][j]);
                if ((j + 1) % 3 == 0) {
                    stringBuilder.append("\n");
                } else {
                    stringBuilder.append(", ");
                }
            }
        }
        Log.d(TAG, stringBuilder.toString());
    }
}