# -package com.mehdi.heavyequipment;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.view.MotionEvent;

public class Game3DView extends GLSurfaceView {

    public Game3DView(Context context) {
        super(context);

        setEGLContextClientVersion(2);
        setRenderer(new Renderer3D());
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    private static class Renderer3D implements GLSurfaceView.Renderer {

        private final float[] projection = new float[16];
        private final float[] view = new float[16];
        private final float[] model = new float[16];
        private final float[] mvp = new float[16];

        @Override
        public void onSurfaceCreated(
                javax.microedition.khronos.egl.EGLConfig config) {

            GLES20.glClearColor(0.5f, 0.7f, 1.0f, 1.0f);
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        }

        @Override
        public void onSurfaceChanged(
                javax.microedition.khronos.opengles.GL10 gl,
                int width,
                int height) {

            GLES20.glViewport(0, 0, width, height);

            float ratio = (float) width / height;

            Matrix.frustumM(
                    projection,
                    0,
                    -ratio,
                    ratio,
                    -1,
                    1,
                    2,
                    100
            );
        }

        @Override
        public void onDrawFrame(
                javax.microedition.khronos.opengles.GL10 gl) {

            GLES20.glClear(
                    GLES20.GL_COLOR_BUFFER_BIT |
                    GLES20.GL_DEPTH_BUFFER_BIT
            );

            Matrix.setLookAtM(
                    view,
                    0,
                    0, 3, 8,
                    0, 0, 0,
                    0, 1, 0
            );

            Matrix.setIdentityM(model, 0);

            Matrix.multiplyMM(
                    mvp,
                    0,
                    view,
                    0,
                    model,
                    0
            );

            Matrix.multiplyMM(
                    mvp,
                    0,
                    projection,
                    0,
                    mvp,
                    0
            );
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
