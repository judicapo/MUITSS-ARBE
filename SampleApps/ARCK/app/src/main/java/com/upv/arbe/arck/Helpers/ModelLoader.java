package com.upv.arbe.arck.Helpers;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.google.ar.core.HitResult;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.upv.arbe.arck.MainActivity;

import java.lang.ref.WeakReference;

public class ModelLoader {

    private final WeakReference<MainActivity> owner;
    private static final String TAG = "ModelLoader";

    private Player player;

    @Nullable
    private ModelRenderable videoRenderable;
    // The color to filter out of the video.
    private static final Color CHROMA_KEY_COLOR = new Color(0.1843f, 1.0f, 0.098f);

    public ModelLoader(WeakReference<MainActivity> pOwner) {
        owner = pOwner;

        player = new Player(new WeakReference<>(owner.get()));
    }

    public void loadModel(HitResult hitResult, String path) {
        if (owner.get() == null) {
            Log.d(TAG, "Activity is null.  Cannot load model.");
            return;
        }

        // Create a renderable with a material that has a parameter of type 'samplerExternal' so that
        // it can display an ExternalTexture. The material also has an implementation of a chroma key
        // filter.
        ModelRenderable.builder()
                .setSource(owner.get(), Uri.parse(path))
                .build()
                .thenAccept(
                        renderable -> {
                            videoRenderable = renderable;
                            renderable.getMaterial().setExternalTexture("videoTexture", player.getTexture());
                            renderable.getMaterial().setFloat4("keyColor", CHROMA_KEY_COLOR);
                        })
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(owner.get(), "Unable to load video renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        player.startPlayer(hitResult, owner.get().getArFragment(), videoRenderable);
    }

    public void destroy() {
        if (player != null) {
            player.destroy();
        }
    }
}