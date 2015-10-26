package chintan.rathod.hintspinnerapdater;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;

/**
 * @author Chintan.rathod
 * An Adapter which will show inital text like "Select Country" or something else intead of first element of spinner.
 */
public class HintSpinnerAdapter implements SpinnerAdapter, ListAdapter {

    protected static final int PIVOT = 1;

    protected SpinnerAdapter adapter;

    protected Context context;

    protected int hintLayout;

    protected int hintDropdownLayout;

    protected LayoutInflater layoutInflater;

    /**
     * Use this constructor to have NO 'Select One...' item, instead use
     * the standard prompt or nothing at all.
     * @param spinnerAdapter wrapped Adapter.
     * @param hintLayout layout for nothing selected, perhaps
     * you want text grayed out like a prompt...
     * @param context
     */
    public HintSpinnerAdapter(
            SpinnerAdapter spinnerAdapter,
            int hintLayout, Context context) {

        this(spinnerAdapter, hintLayout, -1, context);
    }

    /**
     * Use this constructor to Define your 'Select One...' layout as the first
     * row in the returned choices.
     * If you do this, you probably don't want a prompt on your spinner or it'll
     * have two 'Select' rows.
     * @param spinnerAdapter wrapped Adapter. Should probably return false for isEnabled(0)
     * @param hintLayout layout for nothing selected, perhaps you want
     * text grayed out like a prompt...
     * @param hintDropdownLayout layout for your 'Select an Item...' in
     * the dropdown.
     * @param context
     */
    public HintSpinnerAdapter(SpinnerAdapter spinnerAdapter,
                              int hintLayout, int hintDropdownLayout, Context context) {
        this.adapter = spinnerAdapter;
        this.context = context;
        this.hintLayout = hintLayout;
        this.hintDropdownLayout = hintDropdownLayout;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        // This provides the View for the Selected Item in the Spinner, not
        // the dropdown (unless dropdownView is not set).
        if (position == 0) {
            return getHintView(parent);
        }
        return adapter.getView(position - PIVOT, null, parent); // Could re-use
                                                 // the convertView if possible.
    }

    /**
     * View to show in Spinner with Nothing Selected
     * Override this to do something dynamic... e.g. "37 Options Found"
     * @param parent
     * @return
     */
    protected View getHintView(ViewGroup parent) {
        return layoutInflater.inflate(hintLayout, parent, false);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        // Android BUG! http://code.google.com/p/android/issues/detail?id=17128 -
        // Spinner does not support multiple view types
        if (position == 0) {
            return hintDropdownLayout == -1 ?
              new View(context) :
              getHintDropdownView(parent);
        }

        // Could re-use the convertView if possible, use setTag...
        return adapter.getDropDownView(position - PIVOT, null, parent);
    }

    /**
     * Override this to do something dynamic... For example, "Pick your favorite color".
     * @param parent
     * @return
     */
    protected View getHintDropdownView(ViewGroup parent) {
        return layoutInflater.inflate(hintDropdownLayout, parent, false);
    }

    @Override
    public int getCount() {
        int count = adapter.getCount();
        return count == 0 ? 0 : count + PIVOT;
    }

    @Override
    public Object getItem(int position) {
        return position == 0 ? null : adapter.getItem(position - PIVOT);
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position >= PIVOT ? adapter.getItemId(position - PIVOT) : position - PIVOT;
    }

    @Override
    public boolean hasStableIds() {
        return adapter.hasStableIds();
    }

    @Override
    public boolean isEmpty() {
        return adapter.isEmpty();
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        adapter.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        adapter.unregisterDataSetObserver(observer);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return position != 0; // Don't allow the 'hint' item to be picked.
    }

}