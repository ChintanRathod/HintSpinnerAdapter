# HintSpinnerAdapter

Current spinner don't allow user to add "Hint" to it. But specially for EditText and other input fields it is necessary to have "Hint" text so that lable can be avoided.

This HintSpinnerAdapter allows user to add hint to spinner with very less effor and very effectively. Only thing user need to do is, he has to pass his own spinner adapter to this sample adapter and thats it. 

<strong>Syntax</strong> 

    hintSpinner.setAdapter(new HintSpinnerAdapter(<User_Created_Adapter_Object>, <hint_layout_item>, context));
      
<strong>Example</strong>

    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        R.array.color, android.R.layout.simple_spinner_item);
    hintSpinner.setAdapter(new HintSpinnerAdapter(adapter, R.layout.hint_row_item, this));
        
![](https://github.com/rathodchintan/HintSpinnerAdapter/blob/master/HintSpinnerAdapter%20Demo.gif)
