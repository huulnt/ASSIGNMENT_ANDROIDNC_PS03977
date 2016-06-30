package ps03977.edu.poly.com.assignment_androidnc_ps03977.library;

import android.view.View;

/**
 * Created by nhan2 on 6/30/2016.
 */
public class ScaleInOutTransformer extends ABaseTransformer {

    @Override
    protected void onTransform(View view, float position) {
        view.setPivotX(position < 0 ? 0 : view.getWidth());
        view.setPivotY(view.getHeight() / 2f);
        float scale = position < 0 ? 1f + position : 1f - position;
        view.setScaleX(scale);
        view.setScaleY(scale);
    }
}
