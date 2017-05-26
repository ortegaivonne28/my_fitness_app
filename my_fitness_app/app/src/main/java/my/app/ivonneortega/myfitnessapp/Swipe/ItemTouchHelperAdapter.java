package my.app.ivonneortega.myfitnessapp.Swipe;

/**
 * Created by ivonneortega on 4/10/17.
 */

public interface ItemTouchHelperAdapter {

    void onItemDismiss(int position);
    boolean onItemMove(int fromPosition, int toPosition);
}
